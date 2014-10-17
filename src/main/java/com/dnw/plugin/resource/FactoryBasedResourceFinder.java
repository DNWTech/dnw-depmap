/**
 * !(#) FactoryBasedResourceFinder.java
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
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A resource finder, using a resource visitor factory to determine whether a resource is supported.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public class FactoryBasedResourceFinder extends AbstractResourceFinder {

	private final IResourceVisitorFactory factory;

	/**
	 * Constructor of FactoryBasedResourceFinder.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param factory a resource visitor factory. if a <code>null</code> passed in, assumes any kind
	 *            of resource will be acceptable and supported.
	 * @param monitor the progress monitor, used when doing resource traverse. if a
	 *            <code>null</code> passed in, a <code>NullProgressMonitor</code> will be created
	 *            and used instead.
	 */
	public FactoryBasedResourceFinder(IResourceVisitorFactory factory, IProgressMonitor monitor) {
		super(monitor);
		this.factory = factory;
	}

	/**
	 * Checks if the given resource is supported.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource the given resource.
	 * @return <code>true</code> if the given resource is supported, else <code>false</code>.
	 * @see com.dnw.plugin.resource.IResourceFinder#isSupported(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean isSupported(IResource resource) {
		return factory != null ? factory.support(resource) : true;
	}
}
