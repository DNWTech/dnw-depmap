/**
 * !(#) J.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 11, 2014.
 */
package com.dnw.json;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnw.plugin.util.WeakCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An utility class to make and parse JSON format string.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public final class J {

	// using a cache to improve the performance.
	private final static WeakCache<Class<?>, K<?>> cache = new WeakCache<Class<?>, K<?>>();
	private final static Map<Class<?>, K<?>> map = new HashMap<Class<?>, K<?>>();
	private static K<Object> defaultConverter;

	private final static ObjectMapper mapper = new ObjectMapper();

	private final static Pattern NAMEPATTERN = Pattern.compile("^[\\x21-\\x7e]+$");

	/**
	 * Registers a default type converter. Pass in a <code>null</code> to unregister.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param converter the default type converter, <code>null</code> for unregister.
	 */
	public final static void registerDefaultConverter(K<Object> converter) {
		defaultConverter = converter;
	}

	/**
	 * Returns the current default type converter.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @return current default type converter.
	 */
	public final static K<Object> getDefaultConverter() {
		return defaultConverter;
	}

	/**
	 * Registers a type converter.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param type the type.
	 * @param converter the type converter.
	 * @throws IllegalArgumentException if the type has been registered ever.
	 */
	public final static <T> void register(Class<T> type, K<T> converter) {
		if (type == null)
			throw new NullPointerException("type.is.null");
		if (converter == null)
			throw new NullPointerException("converter.is.null");
		if (map.containsKey(type))
			throw new IllegalArgumentException("duplicate.converter: " + type.getName());
		map.put(type, converter);
	}

	/**
	 * Unregisters a type converter.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param type the type.
	 */
	public final static <T> void unregister(Class<T> type) {
		if (type == null)
			throw new NullPointerException("type.is.null");
		if (map.containsKey(type)) {
			map.remove(type);
		}
	}

	/**
	 * Returns the current registered converter for the given type.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param type the given type.
	 * @return the current registered converter.
	 */
	@SuppressWarnings("unchecked")
	public final static <T> K<T> getConverter(Class<T> type) {
		return (K<T>)map.get(type);
	}

	/**
	 * Checks if a type converter has been registered.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param type the type.
	 * @return <code>true</code> if it has been registered, else <code>false</code>.
	 */
	public final static <T> boolean isRegistered(Class<T> type) {
		return map.containsKey(type);
	}

	/**
	 * Clears type converter registry.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 */
	public final static void clearRegistry() {
		map.clear();
	}

	/**
	 * Finds the corresponding type converter.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param type the given type.
	 * @return the corresponding type converter.
	 */
	private final static K<?> findConverter(Class<?> type) {
		K<?> k = cache.get(type);
		if (k != null)
			return k;

		k = map.get(type);
		if (k != null) {
			cache.put(type, k);
			return k;
		}

		Class<?> s = type.getSuperclass();
		if (s != null) {
			k = findConverter(s);
			if (k != null) {
				cache.put(type, k);
				return k;
			}
		}

		for (Class<?> i : type.getInterfaces()) {
			k = findConverter(i);
			if (k != null) {
				cache.put(type, k);
				return k;
			}
		}
		return null;
	}

	/**
	 * Tries to convert the given value using the registered converter.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param value the value.
	 * @return the converted value.
	 * @throws IllegalArgumentException if no corresponding converter registered.
	 */
	@SuppressWarnings("unchecked")
	private final static <T> Object tryConverter(Object value) {
		K<T> k = (K<T>)findConverter(value.getClass());
		if (k != null)
			return k.convert((T)value);
		if (defaultConverter != null)
			return defaultConverter.convert(value);
		throw new IllegalArgumentException("unsupported.type: " + value.getClass());
	}

	/**
	 * Tries to convert the given value to a JSON compatible value. A global internal cache will be
	 * used to improve the performance.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param value the value to convert.
	 * @return the result value.
	 */
	public final static Object convert(Object value) {
		if (value == null || value instanceof CharSequence || value instanceof Number
				|| value instanceof Boolean)
			return value;
		else if (value instanceof M)
			return ((M)value).map;
		else if (value instanceof Map)
			return M.m().cp((Map<?, ?>)value).map;
		else if (value instanceof L)
			return ((L)value).list;
		else if (value instanceof Iterable)
			return L.l().sc((Iterable<?>)value).list;
		else if (value.getClass().isArray())
			return L.l().sc(value).list;
		else
			return tryConverter(value);
	}

	/**
	 * Clears the global internal converter cache, i.e. after modified the converter registry.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 */
	public final static void clearConverterCache() {
		cache.clear();
	}

	/**
	 * Appends the given name to the string buffer, a name usually used as an object key.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param name the name to append.
	 * @throws IllegalArgumentException if it's not a valid name.
	 */
	public final static void emitName(final StringBuffer sb, final String name) {
		Matcher m = NAMEPATTERN.matcher(name);
		if (!m.matches())
			throw new IllegalArgumentException("not.a.valid.name");
		sb.append(name);
	}

	/**
	 * Escapes each character to make the string can be denoted as a JSON string.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param text a string to have all characters to be escaped.
	 * @return the escaped string.
	 */
	public final static String escape(final CharSequence text) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == 0) {
				sb.append("\\0");
			} else if (ch == '\n') {
				sb.append("\\n");
			} else if (ch == '\r') {
				sb.append("\\r");
			} else if (ch < 32) {
				sb.append("\\x");
				if (ch < 16) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(ch));
			} else if (ch == '\\' || ch == '\'' || ch == '\"') {
				sb.append("\\");
				sb.append(ch);
			} else if (ch <= 126) {
				sb.append(ch);
			} else {
				int n = Character.codePointAt(text, i);
				sb.append("\\u");
				if (n < 16) {
					sb.append("000");
				} else if (n < 256) {
					sb.append("00");
				} else if (n < 4096) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(n));
			}
		}
		return sb.toString();
	}

	/**
	 * Appends the given string to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param text the string to append.
	 */
	public final static void emitString(final StringBuffer sb, final CharSequence text) {
		sb.append('\"');
		sb.append(J.escape(text));
		sb.append('\"');
	}

	/**
	 * Appends the given number to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param number the number to append.
	 */
	public final static void emitNumber(final StringBuffer sb, final Number number) {
		sb.append(String.valueOf(number));
	}

	/**
	 * Appends the given boolean value to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param sb the string buffer.
	 * @param flag the boolean value to append.
	 */
	public final static void emitBoolean(final StringBuffer sb, final Boolean flag) {
		sb.append(String.valueOf(flag));
	}

	/**
	 * Appends the given <code>java.util.Map</code> to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param map the map to append.
	 */
	public final static void emitMap(final StringBuffer sb, final Map<?, ?> map) {
		sb.append('{');
		boolean first = true;
		for (Map.Entry<?, ?> e : map.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			sb.append('\"');
			J.emitName(sb, String.valueOf(e.getKey()));
			sb.append("\":");
			J.emit(sb, e.getValue());
		}
		sb.append('}');
	}

	/**
	 * Appends the given <code>java.lang.Iterable</code> to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @param sb the string buffer.
	 * @param collection the iterable collection to append.
	 */
	public final static void emitIterable(final StringBuffer sb, final Iterable<?> collection) {
		sb.append('[');
		boolean first = true;
		for (Object v : collection) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			J.emit(sb, v);
		}
		sb.append(']');
	}

	/**
	 * Appends the given array to the string buffer.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param array the array to append.
	 */
	public final static void emitArray(final StringBuffer sb, final Object array) {
		sb.append('[');
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++) {
			if (i > 0) {
				sb.append(',');
			}
			J.emit(sb, Array.get(array, i));
		}
		sb.append(']');
	}

	/**
	 * Appends the given object of unknown type to the string buffer. Unknown type objects are
	 * denoted as string <code>'[Object &lt;qualified type name&gt;]'</code>, e.g.
	 * <code>'[Object java.lang.Object]'</code>.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param unknown the object to be append.
	 */
	public final static void emitUnknown(final StringBuffer sb, final Object unknown) {
		sb.append("\"[Object ");
		sb.append(unknown.getClass().getName());
		sb.append("]\"");
	}

	/**
	 * <p>
	 * Appends the given object to the string buffer.
	 * </p>
	 * <p>
	 * This method can correctly recognize the following values:
	 * <ul>
	 * <li>Value of <code>null</code>, denoted as JavaScript literal <code>null</code>.</li>
	 * <li>Values in class <code>boolean</code>, <code>java.lang.Boolean</code>, denoted as
	 * JavaScrpt boolean literal <code>true</code> or <code>false</code>.</li>
	 * <li>Values in class <code>byte</code>, <code>short</code>, <code>int</code>,
	 * <code>long</code>, <code>float</code>, <code>double</code>, or in their box types, or in
	 * class derived from <code>java.lang.Number</code>, like <code>java.math.BigInteger</code> or
	 * <code>java.math.BigDecimal</code>, denoted as JavaScript numeric literal.</li>
	 * <li>Values in class <code>char</code>, <code>java.lang.Character</code>,
	 * <code>java.lang.String</code>, or in class derived from <code>java.lang.CharSequence</code> ,
	 * denoted as JavaScript string literal, make character escape if need.</li>
	 * <li>Values in class <code>com.dnw.json.M</code>, or in class derived from
	 * <code>java.util.Map</code>, denoted as JavaScript object literal.</li>
	 * <li>Values in class <code>com.dnw.json.L</code>, or in class derived from
	 * <code>java.lang.Iterable</code>, denoted as JavaScript array literal.</li>
	 * <li>Values of array with elements in any class, denoted as JavaScript array literal.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * All values in other types, which are not listed above, are denoted as JavaScript string
	 * literal <code>'[Object &lt;qualified type name&gt;]'</code>, e.g.
	 * <code>'[Object java.lang.Object]'</code>.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param sb the string buffer.
	 * @param value the object to be append.
	 */
	public final static void emit(final StringBuffer sb, final Object value) {
		if (value == null) {
			sb.append("null");
		} else if (value instanceof CharSequence) {
			J.emitString(sb, (CharSequence)value);
		} else if (value instanceof Character) {
			J.emitString(sb, String.valueOf(value));
		} else if (value instanceof Number) {
			J.emitNumber(sb, (Number)value);
		} else if (value instanceof Boolean) {
			J.emitBoolean(sb, (Boolean)value);
		} else if (value instanceof M) {
			J.emitMap(sb, ((M)value).map);
		} else if (value instanceof Map) {
			J.emitMap(sb, (Map<?, ?>)value);
		} else if (value instanceof L) {
			J.emitIterable(sb, ((L)value).list);
		} else if (value instanceof Iterable) {
			J.emitIterable(sb, (Iterable<?>)value);
		} else if (value.getClass().isArray()) {
			J.emitArray(sb, value);
		} else {
			J.emitUnknown(sb, value);
		}
	}

	/**
	 * Returns a corresponding JSON string of the given Java object.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 * @param value the given Java object.
	 * @return a JSON string.
	 */
	public final static String make(Object value) {
		StringBuffer sb = new StringBuffer();
		emit(sb, value);
		return sb.toString();
	}

	/**
	 * Resolves an object <code>JsonNode</code>, returns a <code>com.dnw.json.M</code> object. For
	 * each value of key-value pair, recursively calls <code>parse(JsonNode)</code> to resolve it.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param node the given <code>JsonNode</code>.
	 * @return a <code>com.dnw.json.M</code> object.
	 */
	private final static M resolveObject(JsonNode node) {
		M m = M.m();
		Iterator<String> i = node.fieldNames();
		while (i.hasNext()) {
			String key = i.next();
			m.a(key, resolve(node.get(key)));
		}
		return m;
	}

	/**
	 * Resolves an array <code>JsonNode</code>, returns a <code>com.dnw.json.L</code> object. For
	 * each element in this array, recursively calls <code>parse(JsonNode)</code> to resolve it.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param node the given <code>JsonNode</code>.
	 * @return a <code>com.dnw.json.L</code> object.
	 */
	private final static L resolveArray(JsonNode node) {
		L l = L.l();
		Iterator<JsonNode> i = node.elements();
		while (i.hasNext()) {
			l.a(resolve(i.next()));
		}
		return l;
	}

	/**
	 * Resolves a JsonNode, returns the corresponding Java object.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param node the given <code>JsonNode</code>.
	 * @return the corresponding Java object.
	 */
	private final static Object resolve(JsonNode node) {
		if (node.isNull()) {
			return null;
		} else if (node.isTextual()) {
			return node.asText();
		} else if (node.isIntegralNumber()) {
			return node.asLong();
		} else if (node.isDouble()) {
			return node.asDouble();
		} else if (node.isBoolean()) {
			return node.asBoolean();
		} else if (node.isObject()) {
			return resolveObject(node);
		} else if (node.isArray()) {
			return resolveArray(node);
		}
		return null;
	}

	/**
	 * <p>
	 * Parses a JSON string, returns a Java object to represent the data.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>For JavaScript literal <code>null</code>, returns a <code>null</code>.</li>
	 * <li>For JavaScript literal string, returns a corresponding <code>java.lang.String</code>
	 * object.</li>
	 * <li>For JavaScript literal integral number, returns a corresponding
	 * <code>java.lang.Long</code> object.</li>
	 * <li>For JavaScript literal decimal number, returns a corresponding
	 * <code>java.lang.Double</code> object.</li>
	 * <li>For JavaScript literal boolean value, returns a corresponding
	 * <code>java.lang.Boolean</code> object.</li>
	 * <li>For JavaScript literal object, returns a corresponding <code>com.dnw.json.M</code>
	 * object.</li>
	 * <li>For JavaScript literal array, returns a corresponding <code>com.dnw.json.L</code> object.
	 * </li>
	 * </ul>
	 * </p>
	 * <p>
	 * N.B. The method catches all checked exceptions happened during parsing, and returns a
	 * <code>null</code>.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param jsonData a string represented JSON data.
	 * @return an object represents the JSON data.
	 */
	public final static Object parse(String jsonData) {
		try {
			JsonNode node = mapper.readTree(jsonData);
			return resolve(node);
		} catch (JsonProcessingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
