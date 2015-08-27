/**
 * !(#) IMethodBindingConverter.java
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

import org.eclipse.jdt.core.dom.IMethodBinding;

import com.dnw.json.K;
import com.dnw.plugin.ast.AstUtil;

/**
 * <p>
 * Converts IMethodBinding objects, returns the full-qualified method name.
 * </p>
 * <p>
 * For non-static methods, the result likes
 * <code>'com.dnw.depmap.neo.NeoWriter.IMethodBindingConverter#convert(IMethodBinding)'</code>; for
 * static methods, the result likes <code>com.dnw.plugin.ast.AstUtil/nameOf(ITypeBinding)</code>.
 * </p>
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
final class MethodBindingConverter implements K<IMethodBinding> {

	/**
	 * Converts the given value to a JSON compatible value.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param value the value to convert.
	 * @return the converted value.
	 * @see com.dnw.json.K#convert(java.lang.Object)
	 */
	@Override
	public Object convert(IMethodBinding value) {
		return BindingCache.contains(value) ? BindingCache.get(value) : AstUtil.nameOf(value);
	}
}