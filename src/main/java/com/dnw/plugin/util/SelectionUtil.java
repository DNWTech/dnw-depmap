/**
 * !(#) SelectionUtil.java
 * Copyright (c) 2013 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Feb 2, 2013.
 */
package com.dnw.plugin.util;

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
 */
public class SelectionUtil {

	/**
	 * Method findProject.
	 * 
	 * @author manbaum
	 * @since Feb 2, 2013
	 * @param selection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static IProject findProject(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection)selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject)element;
				} else if (element instanceof IAdaptable) {
					project = (IProject)((IAdaptable)element).getAdapter(IProject.class);
				}
				return project;
			}
		}
		return null;
	}
}
