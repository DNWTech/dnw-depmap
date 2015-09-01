/**
 * !(#) FileFindFacatory.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 1, 2015.
 */
package com.dnw.depmap.resource;

import org.eclipse.core.resources.IResource;

import com.dnw.matcher.IFilterService;
import com.dnw.plugin.resource.FileExtResourceVisitorFactory;

/**
 * Class/Interface FileFindFacatory.
 * 
 * @author manbaum
 * @since Sep 1, 2015
 */
public class FileFindFacatory extends FileExtResourceVisitorFactory {

	private final IFilterService<String> filepathFilter;

	/**
	 * Constructor of FileFindFacatory.
	 * 
	 * @author manbaum
	 * @since Sep 1, 2015
	 * @param filepathFilter
	 */
	public FileFindFacatory(IFilterService<String> filepathFilter) {
		this.filepathFilter = filepathFilter;
	}

	/**
	 * Getter of the field filepathFilter.
	 * 
	 * @author manbaum
	 * @since Sep 1, 2015
	 * @return value of the field filepathFilter.
	 */
	public IFilterService<String> getFilepathFilter() {
		return filepathFilter;
	}

	/**
	 * Overrider method support.
	 * 
	 * @author manbaum
	 * @since Sep 1, 2015
	 * @param resource
	 * @return
	 * @see com.dnw.plugin.resource.FileExtResourceVisitorFactory#support(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean support(IResource resource) {
		if (filepathFilter != null) {
			if (filepathFilter.blocks(resource.getFullPath().toPortableString()))
				return false;
		}
		return super.support(resource);
	}

}
