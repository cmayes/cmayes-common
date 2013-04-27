package com.cmayes.common.util;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.exception.TooManyException;

/**
 * Collection-related utilities.
 */
public final class CollectionUtils2 {
    /** Logger. */
    private static final Log LOGGER = LogFactory.getLog(CollectionUtils2.class);

    /**
     * Private util constructor.
     */
    private CollectionUtils2() {

    }

    /**
     * Converts the original collection into a collection of the target type.
     * 
     * @param tgtType
     *            The target type.
     * @param origTypes
     *            The collection to convert.
     * 
     * @param <T>
     *            The target type.
     * @return The converted collection.
     * @throws IllegalArgumentException
     *             If there are conversion problems.
     */
    public static <T> Collection<T> convertCollection(final Class<T> tgtType,
            final Collection<?> origTypes) throws IllegalArgumentException {
        final Collection<T> convList = new ArrayList<T>();
        for (Object origObject : origTypes) {
            try {
                convList.add(TypeUtils.runtimeConvertType(tgtType, origObject));
            } catch (final EnvironmentException e) {
                final Throwable cause = e.getCause();
                final Throwable wrapMe = cause == null ? e : cause;
                throw new IllegalArgumentException(
                        "Problems converting value %s", wrapMe);
            }
        }
        return convList;
    }

    /**
     * Creates a list of the given type for the given elements.
     * 
     * @param <T>
     *            The type of the elements and the list.
     * @param elements
     *            The elements to add to the list.
     * @return The filled list.
     */
    public static <T> List<T> createList(final T... elements) {
        if (elements == null) {
            return new ArrayList<T>();
        } else {
            return new ArrayList<T>(Arrays.asList(elements));
        }
    }

    /**
     * Returns the single element in a collection.
     * 
     * @param <T>
     *            The type of the collection.
     * @param collection
     *            The collection to evaluate.
     * @return The single element in a collection.
     * @throws IllegalArgumentException
     *             If the collection's size is not 1.
     */
    public static <T> T getSingle(final Collection<T> collection) {
        if (asNotNull(collection, "Null collection").isEmpty()) {
            throw new NotFoundException("No elements in collection");
        }
        if (collection.size() > 1) {
            throw new TooManyException("Expected one element, got "
                    + collection.size());
        }

        if (collection instanceof List<?>) {
            return ((List<T>) collection).get(0);
        }
        return collection.iterator().next();
    }

    /**
     * Returns the first element of a collection.
     * 
     * @param <T>
     *            The type of the collection.
     * @param collection
     *            The collection to evaluate.
     * @return The first element of a collection.
     * @throws IllegalArgumentException
     *             If the collection is empty.
     */
    public static <T> T getFirst(final Collection<T> collection) {
        if ((asNotNull(collection, "Null collection").size() < 1)) {
            throw new NotFoundException("Empty collection");
        }
        if (collection instanceof List<?>) {
            return ((List<T>) collection).get(0);
        }
        return collection.iterator().next();
    }

    /**
     * Creates a list of the given type for the given elements.
     * 
     * @param <T>
     *            The type of the elements and the list.
     * @param elements
     *            The elements to add to the list.
     * @return The filled list.
     */
    public static <T> Set<T> createSet(final T... elements) {
        Set<T> coll = new HashSet<T>();
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                coll.add(elements[i]);
            }
        }
        return coll;
    }

    /**
     * Returns the collection value for the given key, creating and associating
     * a new, empty {@link ArrayList} if the key's value is null.
     * 
     * @param <K>
     *            The map's key type.
     * @param <V>
     *            The map's collection value type.
     * @param collectionMap
     *            The map to evaluate.
     * @param key
     *            The key to fetch.
     * @return A collection of values for the given key.
     */
    public static <K, V> Collection<V> initCollectionValue(
            final Map<K, Collection<V>> collectionMap, final K key) {
        Collection<V> collection = collectionMap.get(key);
        if (collection == null) {
            collection = new ArrayList<V>();
            collectionMap.put(key, collection);
        }
        return collection;
    }

    /**
     * Returns the collection value for the given key, creating and associating
     * a new, empty {@link ArrayList} if the key's value is null.
     * 
     * @param <K>
     *            The map's key type.
     * @param <V>
     *            The map's collection value type.
     * @param objMap
     *            The map to evaluate.
     * @param key
     *            The key to fetch.
     * @param valClass
     *            The class to instantiate.
     * @return A collection of values for the given key.
     */
    public static <K, V> V initObjectValue(final Map<K, V> objMap, final K key,
            final Class<V> valClass) {
        V val = objMap.get(key);
        if (val == null) {
            try {
                val = valClass.newInstance();
                objMap.put(key, val);
            } catch (final InstantiationException e) {
                throw new EnvironmentException(
                        "Instantiation exception for class " + valClass, e);
            } catch (final IllegalAccessException e) {
                throw new EnvironmentException(
                        "Illegal access exception for class " + valClass, e);
            }
        }
        return val;
    }

    /**
     * Returns the map value for the given key, creating and associating a new,
     * empty {@link HashMap} if the key's value is null.
     * 
     * @param <I>
     *            The index key type.
     * @param <K>
     *            The map's key type.
     * @param <V>
     *            The map's value type.
     * @param mapMap
     *            The map to evaluate.
     * @param idxKey
     *            The index key to fetch.
     * @return A collection of values for the given key.
     */
    public static <I, K, V> Map<K, V> initMapValue(
            final Map<I, Map<K, V>> mapMap, final I idxKey) {
        Map<K, V> indexedMap = mapMap.get(idxKey);
        if (indexedMap == null) {
            indexedMap = new HashMap<K, V>();
            mapMap.put(idxKey, indexedMap);
        }
        return indexedMap;
    }

    /**
     * Returns the set value for the given key, creating and associating a new,
     * empty {@link HashSet} if the key's value is null.
     * 
     * @param <K>
     *            The map's key type.
     * @param <V>
     *            The map's set value type.
     * @param setMap
     *            The map to evaluate.
     * @param key
     *            The key to fetch.
     * @return A set of values for the given key.
     */
    public static <K, V> Set<V> initSetValue(final Map<K, Set<V>> setMap,
            final K key) {
        Set<V> set = setMap.get(key);
        if (set == null) {
            set = new HashSet<V>();
            setMap.put(key, set);
        }
        return set;
    }

    /**
     * Returns the list value for the given key, creating and associating a new,
     * empty {@link ArrayList} if the key's value is null.
     * 
     * @param <K>
     *            The map's key type.
     * @param <V>
     *            The map's list value type.
     * @param listMap
     *            The map to evaluate.
     * @param key
     *            The key to fetch.
     * @return A list of values for the given key.
     */
    public static <K, V> List<V> initListValue(final Map<K, List<V>> listMap,
            final K key) {
        List<V> list = listMap.get(key);
        if (list == null) {
            list = new ArrayList<V>();
            listMap.put(key, list);
        }
        return list;
    }

    /**
     * Collects the values of a given field on a collection of beans.
     * 
     * @param <T>
     *            The return type of the target field.
     * @param sources
     *            The beans to evaluate.
     * @param fieldName
     *            The name of the field to call.
     * @param fieldReturnType
     *            The field's return type.
     * @return The collected values of the target field.
     * @throws ClassCastException
     *             If the return type of the method is not the one specified.
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> collectBeanValues(
            final Collection<?> sources, final String fieldName,
            final Class<T> fieldReturnType) {
        Collection<T> values = new ArrayList<T>();
        if (sources == null) {
            return values;
        }
        for (Object object : sources) {
            final BeanMap<Object> bean = new BeanMap<Object>(object);
            values.add((T) bean.get(fieldName));
        }
        return values;
    }

    /**
     * Returns a collection of all elements that do not have the given value for
     * the given bean name.
     * 
     * @param <T>
     *            The type of bean to filter.
     * @param sources
     *            The beans to evaluate.
     * @param fieldName
     *            The name of the field to call.
     * @param value
     *            The value to check for.
     * @return All beans that do not have the given value.
     */
    public static <T> Collection<T> removeByBeanValue(
            final Collection<T> sources, final String fieldName,
            final Object value) {
        final Collection<T> retainedValues = new ArrayList<T>();
        for (T candidate : sources) {
            final BeanMap<T> bean = new BeanMap<T>(candidate);
            if (bean.get(fieldName) == null) {
                retainedValues.add(candidate);
            } else if (!bean.get(fieldName).equals(value)) {
                retainedValues.add(candidate);
            }
        }
        return retainedValues;
    }

    /**
     * Returns a collection of all elements that have the given value for the
     * given bean name.
     * 
     * @param <T>
     *            The type of bean to filter.
     * @param sources
     *            The beans to evaluate.
     * @param fieldName
     *            The name of the field to call.
     * @param value
     *            The value to check for.
     * @return All beans that have the given value.
     */
    public static <T> Collection<T> retainByBeanValue(
            final Collection<T> sources, final String fieldName,
            final Object value) {
        final Collection<T> retainedValues = new ArrayList<T>();
        for (T candidate : sources) {
            final BeanMap<T> bean = new BeanMap<T>(candidate);
            if ((bean.get(fieldName) != null)
                    && (bean.get(fieldName).equals(value))) {
                retainedValues.add(candidate);
            }
        }
        return retainedValues;
    }

    /**
     * Returns a collection of all elements that do not have any of the given
     * values for the given bean name.
     * 
     * @param <T>
     *            The type of bean to filter.
     * @param sources
     *            The beans to evaluate.
     * @param fieldName
     *            The name of the field to call.
     * @param values
     *            The values to check for.
     * @return All beans that do not have any of the given values.
     */
    public static <T> Collection<T> removeByBeanValueInCollection(
            final Collection<T> sources, final String fieldName,
            final Collection<?> values) {
        final Collection<T> retainedValues = new ArrayList<T>();
        for (T candidate : sources) {
            final BeanMap<T> bean = new BeanMap<T>(candidate);
            final Object beanVal = bean.get(fieldName);
            LOGGER.debug("Bean val: " + beanVal);
            LOGGER.debug("values: " + values);
            if (!values.contains(beanVal)) {
                LOGGER.debug("Adding: " + beanVal);
                retainedValues.add(candidate);
            }
        }
        return retainedValues;
    }

    /**
     * Returns a collection of all elements that have any of the given values
     * for the given bean name.
     * 
     * @param <T>
     *            The type of bean to filter.
     * @param sources
     *            The beans to evaluate.
     * @param fieldName
     *            The name of the field to call.
     * @param values
     *            The values to check for.
     * @return All beans that have any of the given values.
     */
    public static <T> Collection<T> retainByBeanValueInCollection(
            final Collection<T> sources, final String fieldName,
            final Collection<?> values) {
        final Collection<T> retainedValues = new ArrayList<T>();
        for (T candidate : sources) {
            final BeanMap<T> bean = new BeanMap<T>(candidate);
            final Object beanVal = bean.get(fieldName);
            if (values.contains(beanVal)) {
                retainedValues.add(candidate);
            }
        }
        return retainedValues;
    }

    /**
     * Maps the source collection to the value of the given field name.
     * 
     * @param <K>
     *            The value of the given field.
     * @param <V>
     *            The source collection type.
     * @param sources
     *            The elements to map.
     * @param fieldName
     *            The name of the field to call.
     * @param fieldReturnType
     *            The field's return type.
     * @return The given values mapped by the unique return value of the
     *         requested field.
     */
    public static <K, V> Map<K, V> mapByUniqueValue(
            final Collection<V> sources, final String fieldName,
            final Class<K> fieldReturnType) {
        return mapByUniqueValue(sources, fieldName, fieldReturnType, false);
    }

    /**
     * Maps the source collection to the value of the given field name.
     * 
     * @param <K>
     *            The value of the given field.
     * @param <V>
     *            The source collection type.
     * @param sources
     *            The elements to map.
     * @param fieldName
     *            The name of the field to call.
     * @param fieldReturnType
     *            The field's return type.
     * @param allowDupes
     *            Allow duplicate keys. The last duplicate entry will be the
     *            value.
     * @return The given values mapped by the unique return value of the
     *         requested field.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> mapByUniqueValue(
            final Collection<V> sources, final String fieldName,
            final Class<K> fieldReturnType, final boolean allowDupes) {
        final Map<K, V> valMap = new HashMap<K, V>();
        for (V curBean : sources) {
            final BeanMap<V> beanMap = new BeanMap<V>(curBean);
            final V result = valMap.put((K) beanMap.get(fieldName), curBean);
            if ((result != null) && !allowDupes) {
                throw new IllegalArgumentException("Duplicate value for field "
                        + fieldName + ", value " + beanMap.get(fieldName));
            }
        }
        return valMap;
    }

    /**
     * Maps the source collection to a map keyed by the given field's value. The
     * values are lists of one or more instances that have the key's field
     * value.
     * 
     * @param <K>
     *            The value of the given field.
     * @param <V>
     *            The source collection type.
     * @param sources
     *            The elements to map.
     * @param fieldName
     *            The name of the field to call.
     * @param fieldReturnType
     *            The field's return type.
     * @return The given values mapped by the unique return value of the
     *         requested field.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, List<V>> mapByValue(
            final Collection<V> sources, final String fieldName,
            final Class<K> fieldReturnType) {
        final Map<K, List<V>> valMap = new HashMap<K, List<V>>();
        for (V curBean : sources) {
            final BeanMap<V> beanMap = new BeanMap<V>(curBean);
            final K fieldVal = (K) beanMap.get(fieldName);
            final List<V> valList = initListValue(valMap, fieldVal);
            valList.add(curBean);
        }
        return valMap;
    }

    /**
     * Returns a list of the lower-case names of the enumerations in the given
     * enum array.
     * 
     * @param extractMe
     *            The enum array to extract from.
     * @return The the lower-case names of the enumerations in the given enum
     *         array.
     */
    public static List<String> extractEnumNames(final Enum<?>[] extractMe) {
        final ArrayList<String> names = new ArrayList<String>();
        for (Enum<?> enum1 : extractMe) {
            names.add(enum1.name().toLowerCase());
        }
        return names;
    }
}
