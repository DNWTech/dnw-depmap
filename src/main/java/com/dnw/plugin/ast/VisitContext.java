/**
 * !(#) VisitContext.java
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
package com.dnw.plugin.ast;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Class/Interface Visit
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public class VisitContext {

	public final IFile file;
	public final ASTParser parser;
	public final ICompilationUnit unit;
	public final CompilationUnit root;
	public final SubMonitor monitor;
	public int currentPosition;

	/**
	 * Constructor of VisitContext.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param file
	 * @param parser
	 * @param unit
	 * @param root
	 * @param monitor
	 */
	public VisitContext(IFile file, ASTParser parser, ICompilationUnit unit, CompilationUnit root,
			IProgressMonitor monitor) {
		this.file = file;
		this.parser = parser;
		this.unit = unit;
		this.root = root;
		this.monitor = SubMonitor.convert(monitor, root.getLength());
		this.monitor.setTaskName(file.getFullPath().toString());
		currentPosition = 0;
	}

	/**
	 * Method fileInfo.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param node
	 * @return
	 */
	public String fileOf(ASTNode node) {
		return file.getFullPath().toString();
	}

	/**
	 * Method lineOf.
	 * 
	 * @author manbaum
	 * @since Oct 21, 2014
	 * @param node
	 * @return
	 */
	public int lineOf(ASTNode node) {
		return root.getLineNumber(node.getStartPosition());
	}

	/**
	 * Method updateProgressPreVisit.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param node
	 */
	public void updateProgressPreVisit(ASTNode node) {
		int delta = node.getStartPosition() - currentPosition;
		if (delta > 0) {
			currentPosition += delta;
			monitor.worked(delta);
		}
	}

	/**
	 * Method updateProgressPostVisit.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param node
	 */
	public void updateProgressPostVisit(ASTNode node) {
		int delta = node.getStartPosition() + node.getLength() - currentPosition;
		if (delta > 0) {
			currentPosition += delta;
			monitor.worked(delta);
		}
	}
}
