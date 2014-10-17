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
package com.dnw.depmap.resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.dnw.depmap.Activator;
import com.dnw.plugin.ast.ASTVisitorAdapter;
import com.dnw.plugin.ast.VisitContext;

/**
 * Class/Interface JavaFileVisitor.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class JavaFileVisitor implements IResourceVisitor {

	private final IProgressMonitor monitor;

	/**
	 * Constructor of JavaFileVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param monitor
	 */
	public JavaFileVisitor(IProgressMonitor monitor) {
		this.monitor = monitor;
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
		IFile file = (IFile)resource;
		try {
			monitor.beginTask(file.getFullPath().toString(), 10);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
			parser.setSource(unit);
			CompilationUnit root = (CompilationUnit)parser.createAST(null);
			VisitContext context = new VisitContext(file, parser, unit, root, monitor);
			ASTVisitor visitor = new ASTVisitorAdapter(context, Activator.delegator);
			root.accept(visitor);
		} finally {
			monitor.done();
		}
		return false;
	}
}
