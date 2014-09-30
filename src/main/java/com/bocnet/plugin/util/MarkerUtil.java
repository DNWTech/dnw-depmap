/**
 * !(#) MarkerUtil.java
 * Copyright (c) 2013 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Feb 1, 2013.
 */
package com.bocnet.plugin.util;

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
 * 
 */
public class MarkerUtil {

	public static final String NODE_STARTPOSITION = "NODE_STARTPOSITION";

	public static final String NODE_LENGTH = "NODE_LENGTH";

	public static void deleteMarkers(IFile file, String markerType)
			throws CoreException {
		file.deleteMarkers(markerType, false, IResource.DEPTH_ZERO);
	}

	public static IMarker createMarker(IFile file, String markerType,
			int severity, String message, int lineNumber) throws CoreException {
		IMarker marker = file.createMarker(markerType);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber < 1 ? 1
				: lineNumber);
		return marker;
	}

	public static IMarker createMarker(IFile file, String markerType,
			int severity, String message, int lineNumber, int startPos,
			int endPos) throws CoreException {
		IMarker marker = file.createMarker(markerType);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber < 1 ? 1
				: lineNumber);
		marker.setAttribute(IMarker.CHAR_START, startPos);
		marker.setAttribute(IMarker.CHAR_END, endPos);
		return marker;
	}

	public static void sort(IMarker[] markers) {
		Arrays.sort(markers, 0, markers.length, new MarkerComparator());
	}

	public static IMarker[] filter(IMarker[] markers, IMarker exclusive,
			String markerType) throws CoreException {
		ArrayList<IMarker> list = new ArrayList<IMarker>();
		for (IMarker m : markers) {
			if (m != exclusive && m.getType().equals(markerType)) {
				list.add(m);
			}
		}
		return list.toArray(new IMarker[list.size()]);
	}
}
