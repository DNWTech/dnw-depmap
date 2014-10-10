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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.dnw.depmap.Activator;

/**
 * Class/Interface Writer.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 * 
 */
public class Writer {

	public final static class Pair {
		public final String key;
		public final Object value;

		public Pair(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}

	public static Pair p(String key, Object value) {
		return new Pair(key, value);
	}

	public static Map<String, Object> m(Pair... pairs) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (Pair p : pairs) {
			result.put(p.key, p.value);
		}
		return result;
	}

	public static List<Object> a(Object... values) {
		return Arrays.asList(values);
	}

	private final GraphDatabaseService gdb;
	private final ExecutionEngine engine;

	public Writer(GraphDatabaseService gdb) {
		this.gdb = gdb;
		engine = new ExecutionEngine(gdb);
	}

	public void createClass(ITypeBinding type) {
		if (type == null)
			return;
		Transaction tx = gdb.beginTx();
		try {
			Map<String, Object> p = m(p("typename", type.getQualifiedName()));
			engine.execute("merge (t:Type {name:{typename}})", p);
			tx.success();
		} finally {
			tx.close();
		}
	}

	public void createMethod(IMethodBinding method) {
		if (method == null)
			return;
		ITypeBinding type = method.getDeclaringClass();
		createClass(type);
		Transaction tx = gdb.beginTx();
		try {
			Map<String, Object> p = m(p("typename", type.getQualifiedName()),
					p("methodname", method.toString()));
			engine.execute(
					"match (t:Type {name:{typename}})  merge (t)-[:Declare]->(m:Method {name:{methodname}})",
					p);
			tx.success();
		} finally {
			tx.close();
		}
	}

	public void createInvocation(IMethodBinding from, IMethodBinding to,
			List<String> args) {
		if (from == null || to == null)
			return;
		Transaction tx = gdb.beginTx();
		try {
			Map<String, Object> p = m(p("namef", from.toString()),
					p("namet", to.toString()), p("args", args));
			engine.execute(
					"match (f:Method {name:{namef}}) match (t:Method {name:{namet}}) merge (f)-[:Invoke {args:{args}}]->(t)",
					p);
			tx.success();
		} finally {
			tx.close();
		}
	}

	public String fileInfo(ASTNode node) {
		CompilationUnit unit = (CompilationUnit) node.getRoot();
		IResource resource;
		try {
			resource = unit.getJavaElement().getCorrespondingResource();
		} catch (JavaModelException e) {
			Activator.console.println(e);
			return "";
		}
		IFile file = (IFile) resource.getAdapter(IFile.class);
		if (file == null)
			return "";
		String filename = file.getFullPath().toString();
		String linenum = String.valueOf(unit.getLineNumber(node
				.getStartPosition()));
		return filename + ":" + linenum;
	}

	public void tellTypeDeclaration(TypeDeclaration node) {
		ITypeBinding type = node.resolveBinding();
		StringBuffer sb = new StringBuffer();
		sb.append("*** ");
		sb.append(node.getName());
		sb.append(" has been binding to ");
		sb.append(type != null ? type.getQualifiedName() : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}

	public void tellMethodDeclaration(MethodDeclaration node) {
		IMethodBinding method = node.resolveBinding();
		StringBuffer sb = new StringBuffer();
		sb.append("*** ");
		sb.append(node.getName());
		sb.append(" has been binding to ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}

	public void tellMethodInvocation(MethodInvocation node) {
		IMethodBinding method = node.resolveMethodBinding();
		StringBuffer sb = new StringBuffer();
		sb.append("*** ");
		sb.append(node.getName());
		sb.append(" has been binding to ");
		sb.append(method != null ? method.getName() + "()" : "nothing");
		sb.append(" (");
		sb.append(fileInfo(node));
		sb.append(")");
		Activator.console.println(sb.toString());
	}

	public void meetTypeDeclaration(TypeDeclaration node) {
		tellTypeDeclaration(node);
		ITypeBinding type = node.resolveBinding();
		createClass(type);
	}

	/**
	 * Method MethodDeclaration.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * 
	 * @param node
	 */
	public void meetMethodDeclaration(MethodDeclaration node) {
		tellMethodDeclaration(node);
		IMethodBinding method = node.resolveBinding();
		createMethod(method);
	}

	public void meetMethodInvocation(MethodInvocation node) {
		tellMethodInvocation(node);
		ASTNode p = node.getParent();
		while (p != null) {
			if (p instanceof MethodDeclaration) {
				break;
			} else {
				p = p.getParent();
			}
		}
		if (p == null)
			return;
		MethodDeclaration decl = (MethodDeclaration) p;
		tellMethodDeclaration(decl);
		IMethodBinding from = decl.resolveBinding();
		createMethod(from);

		IMethodBinding to = node.resolveMethodBinding();
		if (to == null)
			return;
		createMethod(to);

		@SuppressWarnings("unchecked")
		List<Expression> exprs = node.arguments();
		List<String> args = new ArrayList<String>(exprs.size());
		for (Expression e : exprs) {
			args.add(e.toString());
		}
		createInvocation(from, to, args);
	}
}
