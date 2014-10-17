/**
 * !(#) FindSupportResourceVisitor.java
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
package com.dnw.depmap.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Class/Interface FindSupportResourceVisitor.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public class FindSupportResourceVisitor implements IResourceVisitor {

	private final VisitorFactory factory;
	private final IProgressMonitor monitor;
	private final List<IResource> resources = new ArrayList<IResource>();
	private long totalLength;
	private long maxLength;

	/**
	 * Constructor of FindSupportResourceVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param factory
	 * @param monitor
	 */
	public FindSupportResourceVisitor(VisitorFactory factory, IProgressMonitor monitor) {
		this.factory = factory;
		this.monitor = monitor != null ? monitor : new NullProgressMonitor();
		this.totalLength = 0L;
		this.maxLength = 1L;
	}

	/**
	 * Method getSupportted.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @return
	 */
	public List<IResource> getSupportted() {
		return resources;
	}

	/**
	 * Method getTotalLength.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @return
	 */
	public long getTotalLength() {
		return totalLength;
	}

	/**
	 * Method getMaxLength.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @return
	 */
	public long getMaxLength() {
		return maxLength;
	}

	/**
	 * Method clear.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 */
	public void clear() {
		resources.clear();
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource
	 * @return
	 * @throws CoreException
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (monitor.isCanceled())
			return false;
		if (factory.support(resource)) {
			resources.add(resource);
			long length = resource.getFullPath().toFile().length();
			totalLength += length;
			if (length > maxLength)
				maxLength = length;
		}
		return true;
	}
}
