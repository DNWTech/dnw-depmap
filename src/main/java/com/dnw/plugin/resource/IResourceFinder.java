/**
 * !(#) IResourceFinder.java
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

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;

/**
 * <p>
 * Traverses a resource hierarchy, and picks out all supported resources.
 * </p>
 * <p>
 * A typical usage likes:
 * 
 * <pre>
 * IResource resource = ...
 * IResourceFinder finder = ...
 * resource.accept(finder);
 * List&lt;IResource&gt; supported = finder.getSupportedList();
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public interface IResourceFinder extends IResourceVisitor {

	/**
	 * Checks if the given resource is supported.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource the given resource.
	 * @return <code>true</code> if the given resource is supported, else <code>false</code>.
	 */
	boolean isSupported(IResource resource);

	/**
	 * Returns the supported resource list. This method should be called after the resource
	 * traversing.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @return a list contains all supported resources.
	 */
	List<IResource> getSupportedList();
}
