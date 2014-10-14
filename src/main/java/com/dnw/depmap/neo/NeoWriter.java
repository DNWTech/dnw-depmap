/**
 * !(#) NeoWriter.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 10, 2014.
 */
package com.dnw.depmap.neo;

import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.dnw.json.J;
import com.dnw.json.K;
import com.dnw.json.M;
import com.dnw.neo.NeoAccessor;
import com.dnw.plugin.ast.AstUtil;

/**
 * Class/Interface NeoWriter.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class NeoWriter {

	private final NeoAccessor accessor;

	/**
	 * Constructor of NeoWriter.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param accessor
	 */
	public NeoWriter(NeoAccessor accessor) {
		this.accessor = accessor;
	}

	public static final String CREATECLASS = "merge (t:Class {name:{name}}) on create set t.displayname={dname}, t.implements={impls}, t.extends={parent}";
	public static final String CREATEINTERFACE = "merge (t:Interface {name:{name}}) on create set t.displayname={dname}, t.extends={parent}";
	public static final String CREATEIMPLEMENTS = "match (t:Type {name:{name}}) match (b:Type {name:{nameb}}) merge (t)-[:Implements]->(b)";
	public static final String CREATEEXTENDS = "match (t:Type {name:{name}}) match (b:Type {name:{nameb}}) merge (t)-[:Extends]->(b)";
	public static final String CREATEMETHOD = "match (t:Type {name:{type}})  merge (t)-[:Declare]->(m:Method {name:{name}}) on create set m.displayname={dname}";
	public static final String CREATEINVOKE = "match (f:Method {name:{namef}}) match (t:Method {name:{namet}}) merge (f)-[:Invoke {args:{args}}]->(t)";

	/**
	 * Class/Interface ITypeBindingConverter.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 */
	private final static class ITypeBindingConverter implements K<ITypeBinding> {

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
			return AstUtil.nameOf(value);
		}
	}

	static {
		J.register(ITypeBinding.class, new ITypeBindingConverter());
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public void createType(ITypeBinding type) {
		M p = M.m().a("name", AstUtil.nameOf(type)).a("dname", AstUtil.displayNameOf(type));
		if (type.isInterface()) {
			p.a("parent", type.getInterfaces());
			accessor.execute(CREATEINTERFACE, p);
		} else {
			p.a("impls", type.getInterfaces()).a("parent", type.getSuperclass());
			accessor.execute(CREATECLASS, p);
		}
	}

	/**
	 * Method createImplements.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @param base
	 */
	public void createImplements(ITypeBinding type, ITypeBinding base) {
		M p = M.m().a("name", AstUtil.nameOf(type)).a("nameb", AstUtil.nameOf(base));
		accessor.execute(CREATEIMPLEMENTS, p);
	}

	/**
	 * Method createExtends.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @param base
	 */
	public void createExtends(ITypeBinding type, ITypeBinding base) {
		M p = M.m().a("name", AstUtil.nameOf(type)).a("nameb", AstUtil.nameOf(base));
		accessor.execute(CREATEEXTENDS, p);
	}

	/**
	 * Method createMethod.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 */
	public void createMethod(IMethodBinding method) {
		ITypeBinding type = method.getDeclaringClass();
		// createType(type);
		M p = M.m().a("type", AstUtil.nameOf(type)).a("name", AstUtil.nameOf(method))
				.a("dname", AstUtil.displayNameOf(method));
		accessor.execute(CREATEMETHOD, p);
	}

	/**
	 * Method createInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param from
	 * @param to
	 * @param args
	 */
	public void createInvocation(IMethodBinding from, IMethodBinding to, List<?> args) {
		// createMethod(from);
		createMethod(to);
		M p = M.m().a("namef", AstUtil.nameOf(from)).a("namet", AstUtil.nameOf(to)).a("args", args);
		accessor.execute(CREATEINVOKE, p);
	}
}
