/**
 * !(#) Writer.java
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
import java.util.Map;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

/**
 * Class/Interface Writer.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 * 
 */
public class Writer {

	private final GraphDatabaseService gdb;
	private final ExecutionEngine engine;

	public Writer(GraphDatabaseService gdb) {
		this.gdb = gdb;
		engine = new ExecutionEngine(gdb);
	}

	public void execute(String statement, Map<String, Object> params) {
		Transaction tx = gdb.beginTx();
		try {
			engine.execute(statement, params);
			tx.success();
		} finally {
			tx.close();
		}
	}

	public static final String CREATETYPE = "merge (t:Type {name:{typename}}) on create set t.displayname={displayname}, t.createtime=timestamp())";
	public static final String CREATEMETHOD = "match (t:Type {name:{typename}})  merge (t)-[:Declare]->(m:Method {name:{methodname}})";
	public static final String CREATEINVOKE = "match (f:Method {name:{namef}}) match (t:Method {name:{namet}}) merge (f)-[:Invoke {args:{args}}]->(t)";

	public String makeTypeName(ITypeBinding t) {
		return t.getQualifiedName();
	}

	public String makeMethodName(IMethodBinding m) {
		return null;
	}

	public String makeMethodDisplayName(IMethodBinding m) {
		StringBuffer sb = new StringBuffer();
		int f = m.getModifiers();
		if (Modifier.isStatic(f)) {
			sb.append('S');
		}
		sb.append((m.getModifiers() & Modifier.PUBLIC) != 0 ? '+' : '-');
		return sb.toString();
	}

	public void createType(ITypeBinding type) {
		if (type != null) {
			M p = M.m().e("typename", type.getQualifiedName())
					.e("displayname", type.getName());
			execute(CREATETYPE, p.map());
		}
	}

	public void createMethod(IMethodBinding method) {
		if (method != null) {
			ITypeBinding type = method.getDeclaringClass();
			createType(type);
			M p = M.m().e("typename", type.getQualifiedName())
					.e("methodname", method.toString());
			execute(CREATEMETHOD, p.map());
		}
	}

	public void createInvocation(IMethodBinding from, IMethodBinding to,
			List<String> args) {
		if (from == null || to == null) {
			M p = M.m().e("namef", from.toString()).e("namet", to.toString())
					.e("args", args);
			execute(CREATEINVOKE, p.map());
		}
	}
}
