/**
 * !(#) XMLErrorHandler.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.depmap.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.bocnet.depmap.Builder;

public class XMLErrorHandler extends DefaultHandler {

	/**
	 * Field builder.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */

	private final Builder builder;
	private IFile file;

	public XMLErrorHandler(Builder depMapBuilder, IFile file) {
		builder = depMapBuilder;
		this.file = file;
	}

	private void addMarker(SAXParseException e, int severity) {
		builder.addMarker(file, e.getMessage(), e.getLineNumber(), severity);
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_ERROR);
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		addMarker(exception, IMarker.SEVERITY_WARNING);
	}
}