/**
 * !(#) MarkerComparator.java
 * Copyright (c) 2013 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Feb 1, 2013.
 */
package com.dnw.plugin.util;

import java.util.Comparator;

import org.eclipse.core.resources.IMarker;

/**
 * Class/Interface MarkerComparator.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 * 
 */
public class MarkerComparator implements Comparator<IMarker> {

	public int compare(IMarker m1, IMarker m2) {
		if (m1.getResource() == m2.getResource()) {
			int p1 = m1.getAttribute(IMarker.CHAR_START, 0);
			int p2 = m2.getAttribute(IMarker.CHAR_START, 0);
			return p2 - p1;
		} else
			return -1;
	}
}
