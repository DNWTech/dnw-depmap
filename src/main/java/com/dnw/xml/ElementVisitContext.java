/**
 * !(#) VisitContext.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Aug 28, 2015.
 */
package com.dnw.xml;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;

/**
 * Class/Interface VisitContext.
 * 
 * @author manbaum
 * @since Aug 28, 2015
 */
public class ElementVisitContext {

	public final IFile file;
	public final IDocument document;
	public final IProgressMonitor monitor;

	/**
	 * Constructor of VisitContext.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param file
	 */
	public ElementVisitContext(IFile file, IDocument document, IProgressMonitor monitor) {
		this.file = file;
		this.document = document;
		this.monitor = monitor;
	}
}
