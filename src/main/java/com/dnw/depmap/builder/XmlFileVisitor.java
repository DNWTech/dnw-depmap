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
package com.dnw.depmap.builder;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.MessageConsole;
import org.xml.sax.SAXException;

import com.dnw.plugin.util.ConsoleUtil;
import com.dnw.plugin.util.MarkerUtil;

/**
 * Class/Interface XmlFileVisitor.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class XmlFileVisitor implements IResourceVisitor {

	public static final String MARKER_TYPE = "com.dnw.depmap.xmlProblem";

	private final static MessageConsole console = ConsoleUtil
			.getConsole("com.dnw.depmap");

	public final SAXParserFactory parserFactory = SAXParserFactory
			.newInstance();

	/**
	 * Constructor of XmlFileVisitor.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	public XmlFileVisitor() {
	}

	/**
	 * Method doParse.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param file
	 * @param reporter
	 */
	private void doParse(IFile file, XmlFileErrorHandler reporter) {
		try {
			parserFactory.newSAXParser().parse(file.getContents(), reporter);
		} catch (SAXException e) {
			ConsoleUtil.println(console, e);
		} catch (IOException e) {
			ConsoleUtil.println(console, e);
		} catch (ParserConfigurationException e) {
			ConsoleUtil.println(console, e);
		} catch (CoreException e) {
			ConsoleUtil.println(console, e);
		}
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param file
	 * 
	 * @see com.dnw.plugin.util.BuilderVisitor#visit(org.eclipse.core.resources.IFile)
	 */
	public boolean visit(IResource resource) throws CoreException {
		IFile file = (IFile) resource.getAdapter(IFile.class);
		if (file != null) {
			MarkerUtil.deleteMarkers(file, MARKER_TYPE);
			XmlFileErrorHandler reporter = new XmlFileErrorHandler(file,
					MARKER_TYPE);
			doParse(file, reporter);
		}
		return false;
	}
}