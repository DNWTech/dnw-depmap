/**
 * !(#) ITypeBindingConverter.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 18, 2014.
 */
package com.dnw.depmap.neo;

import org.eclipse.jdt.core.dom.ITypeBinding;

import com.dnw.json.K;
import com.dnw.plugin.ast.AstUtil;

/**
 * Converts ITypeBinding objects, returns the full-qualified type name.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
final class ITypeBindingConverter implements K<ITypeBinding> {

	/**
	 * Converts the given value to a JSON compatible value.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param value the value to convert.
	 * @return the converted value.
	 * @see com.dnw.json.K#convert(java.lang.Object)
	 */
	@Override
	public Object convert(ITypeBinding value) {
		String cached = BindingCache.get(value);
		return cached != null ? cached : AstUtil.nameOf(value);
	}
}