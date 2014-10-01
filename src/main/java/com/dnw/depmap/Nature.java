/**
 * !(#) Nature.java
 * Copyright (c) 2014 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 30, 2014.
 */
package com.dnw.depmap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.dnw.plugin.util.BuilderUtil;

/**
 * A class for project nature runtime classes. It can configure a project with
 * the project nature, or de-configure it. When a project is configured with a
 * project nature, this is recorded in the list of project natures on the
 * project. Individual project natures may expose a more specific runtime type,
 * with additional API for manipulating the project in a nature-specific way.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class Nature implements IProjectNature {

	/**
	 * ID of this project nature.
	 */
	public static final String NATURE_ID = "com.dnw.depmap.nature";

	private IProject project;

	/**
	 * Configures this nature for its project. This is called by the workspace
	 * when natures are added to the project using
	 * <code>IProject.setDescription</code> and should not be called directly by
	 * clients. The nature extension id is added to the list of natures before
	 * this method is called, and need not be added here. Exceptions thrown by
	 * this method will be propagated back to the caller of
	 * <code>IProject.setDescription</code>, but the nature will remain in the
	 * project description.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @throws CoreException
	 *             if this method fails.
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
		BuilderUtil.addBuilder(project, Builder.BUILDER_ID);
	}

	/**
	 * De-configures this nature for its project. This is called by the
	 * workspace when natures are removed from the project using
	 * <code>IProject.setDescription</code> and should not be called directly by
	 * clients. The nature extension id is removed from the list of natures
	 * before this method is called, and need not be removed here. Exceptions
	 * thrown by this method will be propagated back to the caller of
	 * <code>IProject.setDescription</code>, but the nature will still be
	 * removed from the project description.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @throws CoreException
	 *             if this method fails.
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
		BuilderUtil.removeBuilder(project, Builder.BUILDER_ID);
	}

	/**
	 * Returns the project to which this project nature applies.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @return the project handle.
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Sets the project to which this nature applies. Used when instantiating
	 * this project nature runtime. This is called by
	 * <code>IProject.create()</code> or <code>IProject.setDescription()</code>
	 * and should not be called directly by clients.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param project
	 *            the project to which this nature applies.
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(IProject project) {
		this.project = project;
	}
}
