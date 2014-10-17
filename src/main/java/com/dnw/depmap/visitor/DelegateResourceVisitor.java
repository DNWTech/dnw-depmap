/**
 * !(#) ResourceVisitorDelegator.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 29, 2014.
 */
package com.dnw.depmap.visitor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * This class implemented can visit resource trees of a Java project.
 * </p>
 * <p>
 * Usage:
 * 
 * <pre>
 * VisitorFactory factory = ...
 * IProgressMonitor monitor = ...
 * DelegateResourceVisitor visitor = new DelegateResourceVisitor(factory, monitor);
 * IResource root = ...;
 * root.accept(visitor);
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class DelegateResourceVisitor implements IResourceVisitor {

	private final VisitorFactory factory;
	private final SubMonitor monitor;

	/**
	 * Constructor of ResourceVisitorDelegator.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param monitor
	 */
	public DelegateResourceVisitor(VisitorFactory factory, IProgressMonitor monitor) {
		this.factory = factory;
		this.monitor = SubMonitor.convert(monitor);
	}

	/**
	 * Visits the given resource.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param resource the resource to visit.
	 * @return <code>true</code> if the resource's members should be visited; <code>false</code> if
	 *         they should be skipped.
	 * @throws CoreException if the visit fails for some reason.
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (monitor.isCanceled())
			return false;
		IResourceVisitor visitor = factory.createVisitor(resource, monitor.newChild(1));
		if (visitor != null) {
			visitor.visit(resource);
		}
		return true;
	}
}