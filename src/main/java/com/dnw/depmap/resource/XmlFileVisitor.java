/**
 * !(#) XmlFileVisitor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 30, 2014.
 */
package com.dnw.depmap.resource;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.dnw.depmap.Activator;
import com.dnw.xml.AbortVisitException;
import com.dnw.xml.DefaultXmlHandler;
import com.dnw.xml.ElementVisitContext;

/**
 * Class/Interface XmlFileVisitor.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class XmlFileVisitor implements IResourceVisitor {

	public static final String MARKER_TYPE = "com.dnw.depmap.xmlProblem";

	public final SAXParserFactory parserFactory = SAXParserFactory.newInstance();

	private final IProgressMonitor monitor;

	/**
	 * Constructor of XmlFileVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param monitor
	 */
	public XmlFileVisitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * Method doParse.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param file
	 * @param handler
	 */
	private void doParse(IFile file, DefaultHandler handler) {
		try {
			Activator.getDefault().console.forceprintln("*** File: " + file.getFullPath());
			SAXParser p = parserFactory.newSAXParser();
			p.parse(file.getContents(), handler);
		} catch (AbortVisitException e) {
			Activator.getDefault().console.forceprintln("*** XML Parsing canceled!");
		} catch (SAXException e) {
			Activator.getDefault().console.println(e);
		} catch (IOException e) {
			Activator.getDefault().console.println(e);
		} catch (ParserConfigurationException e) {
			Activator.getDefault().console.println(e);
		} catch (CoreException e) {
			Activator.getDefault().console.println(e);
		}
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param file
	 * @see com.dnw.plugin.util.BuilderVisitor#visit(org.eclipse.core.resources.IFile)
	 */
	@Override
	public boolean visit(IResource resource) throws CoreException {
		IFile file = (IFile)resource;
		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		manager.connect(file.getLocation(), LocationKind.LOCATION, null);
		try {
			ITextFileBuffer buffer = manager.getTextFileBuffer(file.getLocation(),
					LocationKind.LOCATION);
			IDocument document = buffer.getDocument();
			monitor.beginTask(file.getFullPath().toOSString(), document.getNumberOfLines());
			ElementVisitContext context = new ElementVisitContext(file, document, monitor);
			DefaultXmlHandler handler = new DefaultXmlHandler(context,
					Activator.getDefault().xmlvisitor);
			doParse(file, handler);
		} finally {
			monitor.done();
			manager.disconnect(file.getLocation(), LocationKind.LOCATION, null);
		}
		return false;
	}
}