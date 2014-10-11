/**
 * !(#) JavaFileVisitor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 29, 2014.
 */
package com.dnw.depmap.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.dnw.depmap.ast.MethodDeclarationVisitor;
import com.dnw.depmap.ast.MethodInvocationVisitor;
import com.dnw.depmap.ast.TypeDeclarationVisitor;
import com.dnw.plugin.ast.AstVisitorBridge;
import com.dnw.plugin.ast.DefaultVisitorDelegator;
import com.dnw.plugin.ast.DefaultVisitorRegistry;
import com.dnw.plugin.ast.NodeTypeSet;
import com.dnw.plugin.ast.VisitorDelegator;
import com.dnw.plugin.ast.VisitorRegistry;

/**
 * Class/Interface JavaFileVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class JavaFileVisitor implements IResourceVisitor {

	public static final NodeTypeSet stopSet = new NodeTypeSet();
	public static final VisitorRegistry registry = new DefaultVisitorRegistry();
	public static final VisitorDelegator delegator = new DefaultVisitorDelegator(
			stopSet, registry);

	static {
		registry.add(TypeDeclaration.class, new TypeDeclarationVisitor());
		registry.add(MethodDeclaration.class, new MethodDeclarationVisitor());
		registry.add(MethodInvocation.class, new MethodInvocationVisitor());
	}

	@Override
	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param resource
	 * @return
	 * @throws CoreException
	 * 
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	public boolean visit(IResource resource) throws CoreException {
		IFile file = (IFile) resource.getAdapter(IFile.class);
		if (file != null) {
			ASTVisitor visitor = new AstVisitorBridge(delegator);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
			parser.setSource(unit);
			ASTNode root = parser.createAST(null);
			root.accept(visitor);
			return true;
		}
		return false;
	}
}
