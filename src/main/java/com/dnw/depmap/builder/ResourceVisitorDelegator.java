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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.dnw.depmap.Activator;

/**
 * <p>
 * This class implemented can visit resource trees of a Java project.
 * </p>
 * <p>
 * Usage:
 * 
 * <pre>
 * ResourceVisitorDelegator visitor = new ResourceVisitorDelegator();
 * IResource root = ...;
 * root.accept(visitor);
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class ResourceVisitorDelegator implements IResourceVisitor {

	private static final Map<String, IResourceVisitor> map = new HashMap<String, IResourceVisitor>();

	private final IProgressMonitor monitor;

	static {
		map.put("java", new JavaFileVisitor());
		map.put("xml", new XmlFileVisitor());
	}

	/**
	 * Constructor of ResourceVisitorDelegator.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param monitor
	 */
	public ResourceVisitorDelegator(IProgressMonitor monitor) {
		this.monitor = monitor;
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
		IFile file = (IFile)resource.getAdapter(IFile.class);
		if (file != null) {
			String ext = file.getFileExtension();
			if (ext != null) {
				IResourceVisitor visitor = map.get(ext.toLowerCase());
				if (visitor != null) {
					Activator.console.println("=== Visit file: " + file.getName());
					visitor.visit(file);
				} else {
					Activator.console.println("=== No visitor for: " + file.getName());
				}
			}
			return false;
		}
		return true;
	}
}