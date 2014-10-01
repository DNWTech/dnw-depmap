/**
 * !(#) NatureUtil.java
 * Copyright (c) 2013 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Feb 1, 2013.
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

	public static void addNature(IProject project, String natureId)
			throws CoreException {
		addNature(project, natureId, true);
	}

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

	public static void toggleNature(IProject project, String natureId)
			throws CoreException {
		if (project.hasNature(natureId)) {
			removeNature(project, natureId);
		} else {
			addNature(project, natureId, false);
		}
	}
}
