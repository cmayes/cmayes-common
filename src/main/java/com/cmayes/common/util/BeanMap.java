package com.cmayes.common.util;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Extended version of the class described in this post: {@link http
 * ://binkley.blogspot.com/2006/08/mapping-java-bean.html}.
 * 
 * @param <T>
 *            The type of the bean being wrapped.
 */
public class BeanMap<T> extends AbstractMap<String, Object> {
    /** The mapped bean. */
    private final T bean;
    /** The bean fields' descriptors. */
    private final Map<String, PropertyDescriptor> descriptors;

    /** Whether to disallow type conversions. */
    private boolean isStrict;

    /**
     * Creates a map proxy for the given bean, allowing for type conversion.
     * 
     * @param wrappedBean
     *            The bean to wrap.
     */
    public BeanMap(final T wrappedBean) {
        this(wrappedBean, false);
    }

    /**
     * Creates a map proxy for the given bean, specifying whether to allow type
     * conversion.
     * 
     * @param wrappedBean
     *            The bean to wrap.
     * @param strict
     *            false = allow type conversion and case-insensitive property
     *            names.
     */
    public BeanMap(final T wrappedBean, final boolean strict) {
        this.isStrict = strict;
        this.bean = asNotNull(wrappedBean, "Missing bean");

        final Map<String, PropertyDescriptor> localDescriptors = new HashMap<String, PropertyDescriptor>();

        try {
            for (final PropertyDescriptor descriptor : Introspector
                    .getBeanInfo(wrappedBean.getClass())
                    .getPropertyDescriptors()) {
                // Only support simple setter/getters.
                if (!(descriptor instanceof IndexedPropertyDescriptor)) {
                    if (isStrict) {
                        localDescriptors.put(descriptor.getName(), descriptor);
                    } else {
                        localDescriptors.put(
                                descriptor.getName().toLowerCase(), descriptor);
                    }
                }
            }
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException(
                    "Problems introspecting the given bean: ", e);
        }

        this.descriptors = Collections.unmodifiableMap(localDescriptors);
    }

    /**
     * Returns a set view of the mappings contained in this map. Each element in
     * the returned set is a {@link Map.Entry}.
     * 
     * @return a set view of the mappings contained in this map.
     * @see java.util.AbstractMap#entrySet()
     */
    public Set<Entry<String, Object>> entrySet() {
        return new BeanSet();
    }

    /**
     * Returns the value of the given bean field name.
     * 
     * @param key
     *            key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *         <tt>null</tt> if the map contains no mapping for this key.
     * 
     * @see java.util.AbstractMap#get(java.lang.Object)
     */
    @Override
    public Object get(final Object key) {
        return super.get(checkKey(key));
    }

    /**
     * Returns the type of the given bean field.
     * 
     * @param key
     *            The name of the bean field.
     * @return The type of the given bean field.
     */
    public Class<?> getType(final String key) {
        return descriptors.get(checkKey(key)).getPropertyType();
    }

    /**
     * Sets the specified value for the given bean field name.
     * 
     * @param key
     *            key with which the specified value is to be associated.
     * @param value
     *            value to be associated with the specified key.
     * @return previous value associated with specified key, or <tt>null</tt> if
     *         there was no mapping for key. A <tt>null</tt> return can also
     *         indicate that the map previously associated <tt>null</tt> with
     *         the specified key, if the implementation supports <tt>null</tt>
     *         values.
     * 
     * @throws ClassCastException
     *             If the value's type is incompatible with the bean field's
     *             type.
     * @throws IllegalArgumentException
     *             If the value is null.
     * @see java.util.AbstractMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public Object put(final String key, final Object value) {
        checkKey(key);
        return new BeanEntry(descriptors.get(checkKey(key))).setValue(value);
    }

    /**
     * *
     * 
     * @param key
     *            key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or <tt>null</tt> if
     *         there was no mapping for key.
     * 
     * @throws ClassCastException
     *             If the key is not a String.
     * @see java.util.AbstractMap#remove(java.lang.Object)
     */
    @Override
    public Object remove(final Object key) {
        return super.remove(checkKey(key));
    }

    /**
     * Makes sure the key is not null and a String representation, forcing to
     * lower case to make a match easier.
     * 
     * @param key
     *            The key to check.
     * @return The key cast to String.
     * @throws IllegalArgumentException
     *             If the value is null or the bean field does not exist.
     */
    private String checkKey(final Object key) {
        final String name;
        if (isStrict) {
            name = asNotNull(key, "Missing key").toString();
        } else {
            name = asNotNull(key, "Missing key").toString().toLowerCase();
        }

        if (!containsKey(asNotNull(name, "Missing key"))) {
            throw new IllegalArgumentException("Bad key: " + key);
        }
        return name;
    }

    /**
     * Wraps the descriptors set to present the bean fields.
     */
    private class BeanSet extends AbstractSet<Entry<String, Object>> {

        /**
         * Returns an iterator over the elements in this collection. There are
         * no guarantees concerning the order in which the elements are returned
         * (unless this collection is an instance of some class that provides a
         * guarantee).
         * 
         * @return an <tt>Iterator</tt> over the elements in this collection
         * @see java.util.AbstractCollection#iterator()
         */
        public Iterator<Entry<String, Object>> iterator() {
            return new BeanIterator(descriptors.values().iterator());
        }

        /**
         * Returns the number of fields in the bean.
         * 
         * @return the number of fields in the bean.
         * 
         * @see java.util.AbstractCollection#size()
         */
        public int size() {
            return descriptors.size();
        }
    }

    /**
     * Iterates over the bean's property descriptors.
     */
    private class BeanIterator implements Iterator<Entry<String, Object>> {
        /** The iterator to wrap. */
        private final Iterator<PropertyDescriptor> it;

        /**
         * Creates a wrapper for the property descriptor.
         * 
         * @param wrappedIt
         *            The iterator to wrap.
         */
        public BeanIterator(final Iterator<PropertyDescriptor> wrappedIt) {
            this.it = wrappedIt;
        }

        /**
         * Returns whether there are any elements left in the iterator.
         * 
         * @return Whether there are any elements left in the iterator.
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         * 
         * @return the next element in the iteration.
         * @throws NoSuchElementException
         *             When iteration has no more elements.
         * @see java.util.Iterator#next()
         */
        public Entry<String, Object> next() {
            return new BeanEntry(it.next());
        }

        /**
         * Removes from the underlying collection the last element returned by
         * the iterator. This method can be called only once per call to
         * <tt>next</tt>. The behavior of an iterator is unspecified if the
         * underlying collection is modified while the iteration is in progress
         * in any way other than by calling this method.
         */
        public void remove() {
            it.remove();
        }
    }

    /**
     * Wraps a descriptor to present data as a {@link Map.Entry} instance.
     */
    private class BeanEntry implements Entry<String, Object> {
        /** The wrapped descriptor. */
        private final PropertyDescriptor descriptor;

        /**
         * Wraps the descriptor as a bean entry.
         * 
         * @param wrappedDesc
         *            The descriptor to wrap.
         */
        public BeanEntry(final PropertyDescriptor wrappedDesc) {
            this.descriptor = wrappedDesc;
        }

        /**
         * Returns the bean field name.
         * 
         * @return The bean field name.
         * @see java.util.Map$Entry#getKey()
         */
        public String getKey() {
            return descriptor.getName();
        }

        /**
         * Returns the bean field's value.
         * 
         * @return The bean field's value.
         * @see java.util.Map$Entry#getDefaultValue()
         */
        public Object getValue() {
            return unwrap(new Wrapped() {
                public Object run() throws IllegalAccessException,
                        InvocationTargetException {
                    final Method method = descriptor.getReadMethod();
                    // A write-only bean.
                    if (null == method) {
                        throw new UnsupportedOperationException("No getter: "
                                + descriptor.getName());
                    }
                    return method.invoke(bean);
                }
            });
        }

        /**
         * Sets the bean field's value.
         * 
         * @param value
         *            The value to set.
         * @return The previous value, or null.
         * @see java.util.Map$Entry#setDefaultValue(java.lang.Object)
         */
        public Object setValue(final Object value) {
            return unwrap(new Wrapped() {
                public Object run() throws IllegalAccessException,
                        InvocationTargetException {
                    final Method method = descriptor.getWriteMethod();
                    // A read-only bean.
                    if (null == method) {
                        throw new UnsupportedOperationException("No setter: "
                                + descriptor.getName());
                    }
                    final Object old = getValue();

                    if (isStrict) {
                        method.invoke(bean, value);
                    } else {
                        Object[] arguments = createWriteMethodArguments(method,
                                value);
                        method.invoke(bean, arguments);
                    }
                    return old;
                }
            });
        }
    }

    /**
     * (Copied from commons-beanutils BeanMap) Creates an array of parameters to
     * pass to the given mutator method. If the given object is not the right
     * type to pass to the method directly, it will be converted using
     * {@link #convertType(Class,Object)}.
     * 
     * @param method
     *            the mutator method
     * @param value
     *            the value to pass to the mutator method
     * @return an array containing one object that is either the given value or
     *         a transformed value
     * @throws IllegalAccessException
     *             if {@link #convertType(Class,Object)} raises it
     * @throws IllegalArgumentException
     *             if any other exception is raised by
     *             {@link #convertType(Class,Object)}
     */
    protected Object[] createWriteMethodArguments(Method method, Object value)
            throws IllegalAccessException {
        try {
            if (value != null) {
                Class<?>[] types = method.getParameterTypes();
                if (types != null && types.length > 0) {
                    Class<?> paramType = types[0];
                    if (!paramType.isAssignableFrom(value.getClass())) {
                        value = TypeUtils.convertType(paramType, value);
                    }
                }
            }
            Object[] answer = { value };
            return answer;
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Wraps the reflection code to make the checked exceptions easier to
     * handle.
     */
    private static interface Wrapped {
        /**
         * Runs the operation.
         * 
         * @return The result of the operation.
         * @throws IllegalAccessException
         *             For illegal access.
         * @throws InvocationTargetException
         *             For reflection issues.
         */
        Object run() throws IllegalAccessException, InvocationTargetException;
    }

    /**
     * Runs the wrapped operation, converting checked exceptions to runtime
     * exceptions.
     * 
     * @param wrapped
     *            The wrapped operation.
     * @return The results of the wrapped operation.
     */
    private static Object unwrap(final Wrapped wrapped) {
        try {
            return wrapped.run();

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);

        } catch (final InvocationTargetException e) {
            // Javadocs for setValue indicate cast is ok.
            throw (RuntimeException) e.getCause();
        }
    }

    /**
     * @return the bean
     */
    public T getBean() {
        return bean;
    }

    /**
     * Returns whether the given property is read-only by checking to see
     * whether it has a setter.
     * 
     * @param propName
     *            The name of the property.
     * @return Whether the given property is read-only.
     * @throws IllegalArgumentException
     *             If there is no property with the given name.
     */
    public boolean isReadOnlyProperty(final String propName) {
        final PropertyDescriptor propDesc = asNotNull(
                descriptors.get(checkKey(propName)),
                "No field found with name " + propName);

        if (propDesc.getWriteMethod() == null) {
            return true;
        }
        return false;
    }

    /**
     * Generic transformer interface.
     */
    interface Transformer {
        /**
         * Transforms from one type to another.
         * 
         * @param input
         *            The instance to convert.
         * @return The transformed instance.
         */
        Object transform(Object input);
    }

    /**
     * @return the isStrict
     */
    public boolean isStrict() {
        return isStrict;
    }

    /**
     * @param strict
     *            the isStrict to set
     */
    public void setStrict(final boolean strict) {
        this.isStrict = strict;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.AbstractMap#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(final Object key) {
        // TODO Auto-generated method stub
        if (isStrict) {
            return super.containsKey(key);
        } else {
            if (key == null) {
                return false;
            }
            return descriptors.containsKey(key.toString().toLowerCase());
        }
    }
}
