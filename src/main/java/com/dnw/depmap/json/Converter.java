package com.dnw.depmap.json;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Converter {

	private final static Map<Class<?>, K<?>> map = new HashMap<Class<?>, K<?>>();

	public final static <T> void add(Class<T> type, K<T> converter) {
		if (type == null)
			throw new NullPointerException("type.is.null");
		if (converter == null)
			throw new NullPointerException("converter.is.null");
		if (map.containsKey(type))
			throw new IllegalArgumentException("duplicate.converter: " + type.getName());
		map.put(type, converter);
	}

	public final static <T> void remove(Class<T> type) {
		if (type == null)
			throw new NullPointerException("type.is.null");
		if (map.containsKey(type))
			map.remove(type);
	}

	public final static void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
	private final static <T> Object findAndConvert(Class<T> type, Object value) {
		K<T> k = (K<T>)map.get(type);
		if (k != null)
			return k.convert((T)value);
		throw new IllegalArgumentException("unsupported.type: " + value.getClass());
	}

	/**
	 * Method make.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param value
	 * @return
	 */
	public final static Object convert(Object value) {
		if (value == null || value instanceof String || value instanceof Number) {
			return value;
		} else if (value instanceof M) {
			return ((M)value).map;
		} else if (value instanceof L) {
			return ((L)value).list;
		} else if (value.getClass().isArray()) {
			int length = Array.getLength(value);
			Object[] array = (Object[])Array.newInstance(Object.class, length);
			for (int i = 0; i < length; i++) {
				array[i] = convert(Array.get(value, i));
			}
			return array;
		} else if (value instanceof Map) {
			Map<?, ?> src = (Map<?, ?>)value;
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map.Entry<?, ?> e : src.entrySet()) {
				map.put(String.valueOf(e.getKey()), convert(e.getValue()));
			}
			return map;
		} else if (value instanceof Collection) {
			Collection<?> src = (Collection<?>)value;
			int i = 0;
			Iterator<?> it = src.iterator();
			Object[] array = new Object[src.size()];
			while (it.hasNext()) {
				array[i++] = convert(it.next());
			}
			return array;
		} else {
			return findAndConvert(value.getClass(), value);
		}
	}

}
