/**
 * !(#) XmlFileErrorHandler.java
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.dnw.depmap.Activator;
import com.dnw.plugin.util.MarkerUtil;

/**
 * Class/Interface XmlFileErrorHandler.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class XmlFileErrorHandler extends DefaultHandler {

	private final IFile file;
	private final String markerType;

	/**
	 * Constructor of XmlFileErrorHandler.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param file
	 * @param markerType
	 */
	public XmlFileErrorHandler(IFile file, String markerType) {
		this.file = file;
		this.markerType = markerType;
	}

	/**
	 * Method addMarker.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param e
	 * @param severity
	 */
	private void addMarker(SAXParseException e, int severity) {
		try {
			MarkerUtil.createMarker(file, markerType, severity, e.getMessage(), e.getLineNumber());
		} catch (CoreException ex) {
			Activator.console.println(ex);
		}
	}

	/**
	 * Overrider method error.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param exception
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	/**
	 * Overrider method fatalError.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param exception
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	/**
	 * Overrider method warning.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param exception
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
	 */
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_WARNING);
	}
}