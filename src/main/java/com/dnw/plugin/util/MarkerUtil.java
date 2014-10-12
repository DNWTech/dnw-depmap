/**
 * !(#) MarkerUtil.java
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

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Class/Interface MarkerUtil.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 */
public class MarkerUtil {

	public static final String NODE_STARTPOSITION = "NODE_STARTPOSITION";

	public static final String NODE_LENGTH = "NODE_LENGTH";

	/**
	 * Method deleteMarkers.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param file
	 * @param markerType
	 * @throws CoreException
	 */
	public static void deleteMarkers(IFile file, String markerType) throws CoreException {
		file.deleteMarkers(markerType, false, IResource.DEPTH_ZERO);
	}

	/**
	 * Method createMarker.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param file
	 * @param markerType
	 * @param severity
	 * @param message
	 * @param lineNumber
	 * @return
	 * @throws CoreException
	 */
	public static IMarker createMarker(IFile file, String markerType, int severity, String message,
			int lineNumber) throws CoreException {
		IMarker marker = file.createMarker(markerType);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber < 1 ? 1 : lineNumber);
		return marker;
	}

	/**
	 * Method createMarker.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param file
	 * @param markerType
	 * @param severity
	 * @param message
	 * @param lineNumber
	 * @param startPos
	 * @param endPos
	 * @return
	 * @throws CoreException
	 */
	public static IMarker createMarker(IFile file, String markerType, int severity, String message,
			int lineNumber, int startPos, int endPos) throws CoreException {
		IMarker marker = file.createMarker(markerType);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber < 1 ? 1 : lineNumber);
		marker.setAttribute(IMarker.CHAR_START, startPos);
		marker.setAttribute(IMarker.CHAR_END, endPos);
		return marker;
	}

	/**
	 * Method sort.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param markers
	 */
	public static void sort(IMarker[] markers) {
		Arrays.sort(markers, 0, markers.length, new MarkerComparator());
	}

	/**
	 * Method filter.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param markers
	 * @param exclusive
	 * @param markerType
	 * @return
	 * @throws CoreException
	 */
	public static IMarker[] filter(IMarker[] markers, IMarker exclusive, String markerType)
			throws CoreException {
		ArrayList<IMarker> list = new ArrayList<IMarker>();
		for (IMarker m : markers) {
			if (m != exclusive && m.getType().equals(markerType)) {
				list.add(m);
			}
		}
		return list.toArray(new IMarker[list.size()]);
	}
}
