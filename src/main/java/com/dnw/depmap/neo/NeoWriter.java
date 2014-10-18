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

	public static final String CREATECLASS = "merge (t:Class:Type {name:{name}}) on create set t.caption={caption}, t.implements={implements}, t.extends={extends}";
	public static final String CREATEINTERFACE = "merge (t:Interface:Type {name:{name}}) on create set t.caption={caption}, t.extends={extends}";
	public static final String CREATEIMPLEMENTS = "match (t:Type {name:{name}}) match (b:Type {name:{nameb}}) merge (t)-[:Implements]->(b)";
	public static final String CREATEEXTENDS = "match (t:Type {name:{name}}) match (b:Type {name:{nameb}}) merge (t)-[:Extends]->(b)";
	public static final String CREATEMETHOD = "merge (m:Method {name:{name}}) on create set m.caption={caption}";
	public static final String CREATEDECLARE = "match (t:Type {name:{tname}})  match(m:Method {name:{mname}}) merge (t)-[:Declares]->(m)";
	public static final String CREATEINVOKE = "match (f:Method {name:{namef}}) match (t:Method {name:{namet}}) merge (f)-[:Invokes {args:{args}}]->(t)";
	public static final String CREATEOVERRIDE = "match (m:Method {name:{name}}) match (b:Method {name:{bname}}) merge (m)-[:Overrides]-(b)";

	static {
		J.register(ITypeBinding.class, new ITypeBindingConverter());
		J.register(IMethodBinding.class, new IMethodBindingConverter());
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public void createType(ITypeBinding type) {
		M p = M.m().a("name", type).a("caption", AstUtil.captionOf(type));
		if (type.isInterface()) {
			p.a("extends", type.getInterfaces());
			accessor.execute(CREATEINTERFACE, p);
		} else {
			p.a("implements", type.getInterfaces()).a("extends", type.getSuperclass());
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
		M p = M.m().a("name", type).a("nameb", base);
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
		M p = M.m().a("name", type).a("nameb", base);
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
		M p = M.m().a("name", method).a("caption", AstUtil.captionOf(method));
		accessor.execute(CREATEMETHOD, p);
	}

	/**
	 * Method createDeclare.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param type
	 * @param method
	 */
	public void createDeclare(IMethodBinding method) {
		ITypeBinding type = method.getDeclaringClass();
		M p = M.m().a("tname", type).a("mname", method);
		accessor.execute(CREATEDECLARE, p);
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
		M p = M.m().a("namef", from).a("namet", to).a("args", args);
		accessor.execute(CREATEINVOKE, p);
	}

	/**
	 * Method createOverride.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @param base
	 */
	public void createOverride(IMethodBinding method, IMethodBinding base) {
		M p = M.m().a("name", method).a("bname", base);
		accessor.execute(CREATEOVERRIDE, p);
	}
}
