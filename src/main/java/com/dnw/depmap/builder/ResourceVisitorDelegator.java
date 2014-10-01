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
package com.dnw.depmap.builder;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * Class/Interface ResourceVisitorDelegator.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
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

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param resource
	 * @return
	 * @throws CoreException
	 * 
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
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