/**
 * !(#) NatureUtil.java
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Give a set of methods to manipulate the project nature.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 */
public class NatureUtil {

	/**
	 * Add the given nature to a project.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param project the project to add nature.
	 * @param natureId the nature ID.
	 * @param checkExistence before adding, whether check the existence of the nature to add.
	 * @return <code>true</code> if the operation has been done, else <code>false</code>.
	 * @throws CoreException if fails.
	 */
	public static boolean addNature(IProject project, String natureId, boolean checkExistence)
			throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		if (checkExistence) {
			for (String n : natures) {
				if (n.equals(natureId))
					return false;
			}
		}
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 1, natures.length);
		newNatures[0] = natureId;
		IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(newNatures);
		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
			return true;
		} else
			throw new CoreException(status);
	}

	/**
	 * Add the given nature to a project.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param project the project to add nature.
	 * @param natureId the nature ID.
	 * @return <code>true</code> if the operation has been done, else <code>false</code>.
	 * @throws CoreException if fails.
	 */
	public static boolean addNature(IProject project, String natureId) throws CoreException {
		return addNature(project, natureId, true);
	}

	/**
	 * Remove the given nature from a project.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param project the project to remote nature.
	 * @param natureId the nature ID.
	 * @return <code>true</code> if the operation has been done, else <code>false</code>.
	 * @throws CoreException if fails.
	 */
	public static boolean removeNature(IProject project, String natureId) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		for (int i = 0; i < natures.length; i++) {
			if (natures[i].equals(natureId)) {
				String[] newNatures = new String[natures.length - 1];
				System.arraycopy(natures, 0, newNatures, 0, i);
				System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
				IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(newNatures);
				if (status.getCode() == IStatus.OK) {
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return true;
				} else
					throw new CoreException(status);
			}
		}
		return false;
	}

	/**
	 * Toggle the existence of the given nature on a project. i.e. if the nature exists, remote it;
	 * if the nature does not exist, add it.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * @param project the project to toggle nature.
	 * @param natureId the nature ID.
	 * @return <code>true</code> if the operation has been done, else <code>false</code>.
	 * @throws CoreException if fails.
	 */
	public static boolean toggleNature(IProject project, String natureId) throws CoreException {
		if (project.hasNature(natureId))
			return removeNature(project, natureId);
		else
			return addNature(project, natureId, false);
	}
}
