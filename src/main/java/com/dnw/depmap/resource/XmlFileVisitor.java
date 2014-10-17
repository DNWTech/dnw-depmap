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
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;

import com.dnw.depmap.Activator;
import com.dnw.plugin.util.MarkerUtil;

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
	 * @param reporter
	 */
	private void doParse(IFile file, XmlFileErrorHandler reporter) {
		try {
			parserFactory.newSAXParser().parse(file.getContents(), reporter);
		} catch (SAXException e) {
			Activator.console.println(e);
		} catch (IOException e) {
			Activator.console.println(e);
		} catch (ParserConfigurationException e) {
			Activator.console.println(e);
		} catch (CoreException e) {
			Activator.console.println(e);
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
		try {
			monitor.beginTask(file.getFullPath().toOSString(), 10);
			MarkerUtil.deleteMarkers(file, MARKER_TYPE);
			XmlFileErrorHandler reporter = new XmlFileErrorHandler(file, MARKER_TYPE);
			doParse(file, reporter);
		} finally {
			monitor.done();
		}
		return false;
	}
}