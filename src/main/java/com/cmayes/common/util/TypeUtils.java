package com.cmayes.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.exception.ParamIllegalArgumentException;
import com.cmayes.common.util.BeanMap.Transformer;

/**
 * Tools to help with type conversion, etc.
 */
public final class TypeUtils {
    /**
     * Maps primitive Class types to transformers. The transformer transform
     * strings into the appropriate primitive wrapper.
     */
    public static final HashMap<Type, Transformer> XFORMERS = new HashMap<Type, Transformer>();

    /**
     * Private constructor for util class.
     */
    private TypeUtils() {

    }

    /**
     * Wraps the checked exceptions from {@link #convertType(Class, Object)} and
     * throws runtime exceptions instead.
     * 
     * @param <T>
     *            The type to convert to.
     * @param newType
     *            the type to convert the value to
     * @param value
     *            the value to convert
     * @return the converted value
     * @throws NumberFormatException
     *             if newType is a primitive type, and the string representation
     *             of the given value cannot be converted to that type
     * @throws EnvironmentException
     *             If there are environment-relate problems converting the
     *             value.
     */
    public static <T> T runtimeConvertType(final Class<T> newType,
            final Object value) {
        try {
            return convertType(newType, value);
        } catch (final InstantiationException e) {
            throw new EnvironmentException("Problems converting value", e);
        } catch (final IllegalAccessException e) {
            throw new EnvironmentException("Security issues converting value",
                    e);
        } catch (final InvocationTargetException e) {
            throw new EnvironmentException("Problems converting value", e);
        }
    }

    /**
     * (Copied from commons-beanutils BeanMap) Converts the given value to the
     * given type. First, reflection is is used to find a public constructor
     * declared by the given class that takes one argument, which must be the
     * precise type of the given value. If such a constructor is found, a new
     * object is created by passing the given value to that constructor, and the
     * newly constructed object is returned.
     * <P>
     * 
     * If no such constructor exists, and the given type is a primitive type,
     * then the given value is converted to a string using its
     * {@link Object#toString() toString()} method, and that string is parsed
     * into the correct primitive type using, for instance,
     * {@link Integer#valueOf(String)} to convert the string into an
     * <code>int</code>.
     * <P>
     * 
     * If no special constructor exists and the given type is not a primitive
     * type, this method returns the original value.
     * 
     * @param <T>
     *            the type to convert the value to.
     * @param newType
     *            the type to convert the value to.
     * @param value
     *            the value to convert
     * @return the converted value or null if the value is null.
     * @throws NumberFormatException
     *             if newType is a primitive type, and the string representation
     *             of the given value cannot be converted to that type
     * @throws InstantiationException
     *             if the constructor found with reflection raises it
     * @throws InvocationTargetException
     *             if the constructor found with reflection raises it
     * @throws IllegalAccessException
     *             If there's an illegal access.
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertType(final Class<T> newType, final Object value)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (value == null) {
            return null;
        }

        if (newType.isEnum()) {
            @SuppressWarnings("rawtypes")
            final Class<? extends Enum> clazz = newType.asSubclass(Enum.class);
            return (T) Enum.valueOf(clazz, (String) value);
        }

        if (value.getClass().isArray()) {
            final Object[] objArray = (Object[]) value;
            if (newType.isArray()) {
                return (T) objArray;
            } else if (Set.class.isAssignableFrom(newType)) {
                final Set<Object> hashSet = new HashSet<Object>();

                for (int i = 0; i < objArray.length; i++) {
                    hashSet.add(objArray[i]);
                }
                return (T) hashSet;
            } else if (Collection.class.isAssignableFrom(newType)) {
                final Collection<Object> hashSet = new ArrayList<Object>();

                for (int i = 0; i < objArray.length; i++) {
                    hashSet.add(objArray[i]);
                }
                return (T) hashSet;
            } else {
                throw new ParamIllegalArgumentException(
                        "Can't convert array %s to type %s", value, newType);
            }
        }

        // Take care of non-standard boolean strings
        if (newType.equals(Boolean.TYPE)
                && value.getClass().equals(String.class)) {
            final String strVal = value.toString();
            if (strVal.equalsIgnoreCase("y")) {
                return (T) Boolean.TRUE;
            } else {
                return (T) Boolean.valueOf(strVal);
            }
        }

        // try call constructor
        final Class<?>[] types = { value.getClass() };
        try {
            final Constructor<?> constructor = newType.getConstructor(types);
            final Object[] arguments = { value };
            return (T) constructor.newInstance(arguments);
        } catch (final NoSuchMethodException e) {
            final Transformer transformer = XFORMERS.get(newType);
            if ((value instanceof Boolean)
                    && (Number.class.isAssignableFrom(newType))) {
                return (T) transformer.transform(((Boolean) value) ? 1 : 0);
            }
            // try using the transformers
            if (transformer != null) {
                return (T) transformer.transform(value);
            }
            // The value's class is assignable to the target, so try returning
            // the value unmodified.
            if (newType.isAssignableFrom(value.getClass())) {
                return (T) value;
            }
            throw new IllegalArgumentException("Cannot convert value " + value
                    + ", class " + value.getClass() + " to " + newType);
        }
    }

    static {
        XFORMERS.put(Boolean.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Boolean.valueOf(input.toString());
            }
        });
        XFORMERS.put(Boolean.class, new Transformer() {
            public Object transform(final Object input) {
                return Boolean.valueOf(input.toString());
            }
        });
        XFORMERS.put(Character.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Character.valueOf(input.toString().charAt(0));
            }
        });
        XFORMERS.put(Character.class, new Transformer() {
            public Object transform(final Object input) {
                return Character.valueOf(input.toString().charAt(0));
            }
        });
        XFORMERS.put(Byte.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Byte.valueOf(input.toString());
            }
        });
        XFORMERS.put(Byte.class, new Transformer() {
            public Object transform(final Object input) {
                return Byte.valueOf(input.toString());
            }
        });
        XFORMERS.put(Short.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Short.valueOf(input.toString());
            }
        });
        XFORMERS.put(Short.class, new Transformer() {
            public Object transform(final Object input) {
                return Short.valueOf(input.toString());
            }
        });
        XFORMERS.put(Integer.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Integer.valueOf(input.toString());
            }
        });
        XFORMERS.put(Integer.class, new Transformer() {
            public Object transform(final Object input) {
                return Integer.valueOf(input.toString());
            }
        });
        XFORMERS.put(Long.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Long.valueOf(input.toString());
            }
        });
        XFORMERS.put(Long.class, new Transformer() {
            public Object transform(final Object input) {
                return Long.valueOf(input.toString());
            }
        });
        XFORMERS.put(Float.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Float.valueOf(input.toString());
            }
        });
        XFORMERS.put(Float.class, new Transformer() {
            public Object transform(final Object input) {
                return Float.valueOf(input.toString());
            }
        });
        XFORMERS.put(Double.TYPE, new Transformer() {
            public Object transform(final Object input) {
                return Double.valueOf(input.toString());
            }
        });
        XFORMERS.put(Double.class, new Transformer() {
            public Object transform(final Object input) {
                return Double.valueOf(input.toString());
            }
        });
        XFORMERS.put(Number.class, new Transformer() {
            public Object transform(final Object input) {
                return NumberUtils.createNumber(input.toString());
            }
        });
        XFORMERS.put(String.class, new Transformer() {
            public Object transform(final Object input) {
                return input.toString();
            }
        });
        XFORMERS.put(Class.class, new Transformer() {
            public Object transform(final Object input) {
                try {
                    return Class.forName(input.toString());
                } catch (final ClassNotFoundException e) {
                    throw new IllegalArgumentException("No class found for "
                            + input.toString(), e);
                }
            }
        });
    }
}
