/**
 * !(#) SampleDeltaVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.depmap.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

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