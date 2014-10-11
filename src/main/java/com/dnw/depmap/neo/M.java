package com.dnw.depmap.neo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class M {

	private final Map<String, Object> map = new HashMap<String, Object>();

	private M() {
	}

	public final Map<String, Object> map() {
		return map;
	}

	public final String json() {
		StringBuffer sb = new StringBuffer();
		JSON.emitMap(sb, map);
		return sb.toString();
	}

	public final M e(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public final M e(String key, Object... values) {
		map.put(key, values);
		return this;
	}

	public static final M m() {
		return new M();
	}

	public final static void main(String[] args) {
		HashSet<Object> set = new HashSet<Object>();
		set.add("set");
		set.add(M.m());
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("list");
		list.add(set);
		M m = M.m().e("hello", "world\b\f\t\r\n\\\'\"haha中文")
				.e("array", 5L, 6.0f, "7", M.m().e("eight", 8).map(), list);
		System.out.println(m.json());
	}
}
