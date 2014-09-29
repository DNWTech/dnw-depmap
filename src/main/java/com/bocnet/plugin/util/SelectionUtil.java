/**
 * !(#) SelectionUtil.java
 * Copyright (c) 2013 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Feb 2, 2013.
 */
package com.bocnet.plugin.util;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Class/Interface SelectionUtil.
 * 
 * @author manbaum
 * @since Feb 2, 2013
 * 
 */
public class SelectionUtil {

	@SuppressWarnings("rawtypes")
	public static IProject findProject(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}
				return project;
			}
		}
		return null;
	}
}
