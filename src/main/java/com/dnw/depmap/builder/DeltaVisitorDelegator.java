/**
 * !(#) DeltaVisitorDelegator.java
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
package com.dnw.depmap.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * Class/Interface DeltaVisitorDelegator.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class DeltaVisitorDelegator implements IResourceDeltaVisitor {

	private final IResourceVisitor visitor;

	/**
	 * Constructor of DeltaVisitorDelegator.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param depMapBuilder
	 */
	public DeltaVisitorDelegator(IResourceVisitor visitor) {
		this.visitor = visitor;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param delta
	 * @return
	 * @throws CoreException
	 * 
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			// handle added resource
			visitor.visit(resource);
			break;
		case IResourceDelta.REMOVED:
			// handle removed resource
			break;
		case IResourceDelta.CHANGED:
			// handle changed resource
			visitor.visit(resource);
			break;
		}
		// return true to continue visiting children.
		return true;
	}
}