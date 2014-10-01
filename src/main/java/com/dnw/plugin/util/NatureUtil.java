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
 * Class/Interface NatureUtil.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 */
public class NatureUtil {

	/**
	 * Method addNature.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * 
	 * @param project
	 * @param natureId
	 * @param checkExistance
	 * @throws CoreException
	 */
	public static void addNature(IProject project, String natureId,
			boolean checkExistance) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		if (checkExistance) {
			for (String n : natures) {
				if (n.equals(natureId))
					return;
			}
		}
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 1, natures.length);
		newNatures[0] = natureId;
		IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(
				newNatures);
		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} else
			throw new CoreException(status);
	}

	/**
	 * Method addNature.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * 
	 * @param project
	 * @param natureId
	 * @throws CoreException
	 */
	public static void addNature(IProject project, String natureId)
			throws CoreException {
		addNature(project, natureId, true);
	}

	/**
	 * Method removeNature.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * 
	 * @param project
	 * @param natureId
	 * @throws CoreException
	 */
	public static void removeNature(IProject project, String natureId)
			throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		for (int i = 0; i < natures.length; i++) {
			if (natures[i].equals(natureId)) {
				String[] newNatures = new String[natures.length - 1];
				System.arraycopy(natures, 0, newNatures, 0, i);
				System.arraycopy(natures, i + 1, newNatures, i, natures.length
						- i - 1);
				IStatus status = ResourcesPlugin.getWorkspace()
						.validateNatureSet(newNatures);
				if (status.getCode() == IStatus.OK) {
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				} else
					throw new CoreException(status);
			}
		}
	}

	/**
	 * Method toggleNature.
	 * 
	 * @author manbaum
	 * @since Feb 1, 2013
	 * 
	 * @param project
	 * @param natureId
	 * @throws CoreException
	 */
	public static void toggleNature(IProject project, String natureId)
			throws CoreException {
		if (project.hasNature(natureId)) {
			removeNature(project, natureId);
		} else {
			addNature(project, natureId, false);
		}
	}
}
