/**
 * !(#) IResourceVisitorFactory.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 16, 2014.
 */
package com.dnw.plugin.resource;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Represents a factory to create resource visitor.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public interface IResourceVisitorFactory {

	/**
	 * Checks whether the given resource is supported or not.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource the given resource.
	 * @return <code>true</code> if it's supported, else <code>false</code>.
	 */
	boolean support(IResource resource);

	/**
	 * Creates a resource visitor which can visit the given resource.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource the given resource.
	 * @param monitor the progress monitor used when doing visit.
	 * @return the corresponding visitor.
	 */
	IResourceVisitor createVisitor(IResource resource, IProgressMonitor monitor);
}
