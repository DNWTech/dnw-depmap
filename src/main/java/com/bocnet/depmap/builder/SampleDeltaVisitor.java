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
import org.eclipse.core.runtime.CoreException;

import com.bocnet.depmap.Builder;

public class SampleDeltaVisitor implements IResourceDeltaVisitor {
	/**
	 * Field builder.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */

	private final Builder builder;

	/**
	 * Constructor of SampleDeltaVisitor.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param depMapBuilder
	 */
	public SampleDeltaVisitor(Builder depMapBuilder) {
		builder = depMapBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
	 * .core.resources.IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			// handle added resource
			builder.checkXML(resource);
			break;
		case IResourceDelta.REMOVED:
			// handle removed resource
			break;
		case IResourceDelta.CHANGED:
			// handle changed resource
			builder.checkXML(resource);
			break;
		}
		// return true to continue visiting children.
		return true;
	}
}