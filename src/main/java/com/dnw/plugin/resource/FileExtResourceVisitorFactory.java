/**
 * !(#) FileExtResourceVisitorFactory.java
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
package com.dnw.plugin.resource;

import org.eclipse.core.resources.IResource;

/**
 * Class/Interface FileExtResourceVisitorFactory.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public class FileExtResourceVisitorFactory extends
		AbstractTypeRegistryResourceVisitorFactory<String> {

	/**
	 * Overrider method resolveKey.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource
	 * @return
	 * @see com.dnw.plugin.resource.AbstractTypeRegistryResourceVisitorFactory#resolveKey(org.eclipse.core.resources.IResource)
	 */
	@Override
	protected String resolveKey(IResource resource) {
		return resource.getFileExtension();
	}

	/**
	 * Overrider method support.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @param resource
	 * @return
	 * @see com.dnw.plugin.resource.AbstractTypeRegistryResourceVisitorFactory#support(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean support(IResource resource) {
		return resource.getType() == IResource.FILE && super.support(resource);
	}
}
