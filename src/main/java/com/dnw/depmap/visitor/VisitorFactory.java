/**
 * !(#) VisitorFactory.java
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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Class/Interface VisitorFactory.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public interface VisitorFactory {

	/**
	 * Method support.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource
	 * @return
	 */
	boolean support(IResource resource);

	/**
	 * Method findVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource
	 * @param monitor
	 * @return
	 */
	IResourceVisitor createVisitor(IResource resource, IProgressMonitor monitor);
}
