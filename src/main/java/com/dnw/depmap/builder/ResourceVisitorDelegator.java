/**
 * !(#) SampleResourceVisitor.java
 * Copyright (c) 2014 DNW Technologies.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.dnw.depmap.builder;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

public class ResourceVisitorDelegator implements IResourceVisitor {

	private final Map<String, IResourceVisitor> map = new HashMap<String, IResourceVisitor>();

	/**
	 * Constructor of SampleResourceVisitor.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param depMapBuilder
	 */
	public ResourceVisitorDelegator() {
		map.put(".java", new JavaFileVisitor());
	}

	public boolean visit(IResource resource) throws CoreException {
		IFile file = (IFile) resource.getAdapter(IFile.class);
		if (file != null) {
			IResourceVisitor visitor = map.get(file.getFileExtension());
			if (visitor != null) {
				visitor.visit(file);
			}
		} else if (resource instanceof IContainer) {
			// return true to continue visiting children.
			return true;
		}
		return false;
	}
}