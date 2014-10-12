/**
 * !(#) MarkerComparator.java
 * Copyright (c) 2013 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Feb 1, 2013.
 */
package com.dnw.plugin.util;

import java.util.Comparator;

import org.eclipse.core.resources.IMarker;

/**
 * Class/Interface MarkerComparator.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 */
public class MarkerComparator implements Comparator<IMarker> {

	/**
	 * Overrider method compare.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param m1
	 * @param m2
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(IMarker m1, IMarker m2) {
		if (m1.getResource() == m2.getResource()) {
			int p1 = m1.getAttribute(IMarker.CHAR_START, 0);
			int p2 = m2.getAttribute(IMarker.CHAR_START, 0);
			return p2 - p1;
		} else
			return -1;
	}
}
