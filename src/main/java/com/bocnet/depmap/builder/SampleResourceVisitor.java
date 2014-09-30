/**
 * !(#) SampleResourceVisitor.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.depmap.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;

import com.bocnet.depmap.Builder;

public class SampleResourceVisitor implements IResourceVisitor {
	/**
	 * Field builder.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */

	private final Builder builder;

	/**
	 * Constructor of SampleResourceVisitor.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param depMapBuilder
	 */
	public SampleResourceVisitor(Builder depMapBuilder) {
		builder = depMapBuilder;
	}

	public boolean visit(IResource resource) {
		builder.checkXML(resource);
		// return true to continue visiting children.
		return true;
	}
}