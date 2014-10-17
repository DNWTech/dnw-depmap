/**
 * !(#) AbstractResourceFinder.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 17, 2014.
 */
package com.dnw.plugin.resource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Helps to create the <code>ResourceFinder</code> implementation. The derived class MUST implements
 * the <code>isSupported(IRecource)</code> method as declared in <code>IResourceFinder</code>
 * interface.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public abstract class AbstractResourceFinder implements IResourceFinder {

	protected final IProgressMonitor monitor;
	protected final ArrayList<IResource> supportedList = new ArrayList<IResource>();

	/**
	 * Constructor of AbstractResourceFinder.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param monitor a progress monitor, used when doing resource traverse. if a <code>null</code>
	 *            passed in, a <code>NullProgressMonitor</code> will be created and used instead.
	 */
	protected AbstractResourceFinder(IProgressMonitor monitor) {
		this.monitor = monitor != null ? monitor : new NullProgressMonitor();
	}

	/**
	 * Visit the given resource, pick out all supported resources.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource he resource to visit.
	 * @return <code>true</code> if the resource's members should be visited; <code>false</code> if
	 *         they should be skipped.
	 * @throws CoreException if the visit fails for some reason.
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (monitor.isCanceled())
			return false;
		if (isSupported(resource))
			supportedList.add(resource);
		return true;
	}

	/**
	 * Returns the supported resource list.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @return a list contains all supported resources.
	 * @see com.dnw.plugin.resource.IResourceFinder#getSupportedList()
	 */
	@Override
	public List<IResource> getSupportedList() {
		return supportedList;
	}

	/**
	 * Clears the internal list, then you can reuse this object to do another traversing.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 */
	public void clear() {
		supportedList.clear();
	}
}
