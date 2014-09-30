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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.MessageConsole;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.bocnet.plugin.util.ConsoleUtil;
import com.bocnet.plugin.util.MarkerUtil;

public class XmlFileErrorHandler extends DefaultHandler {

	private final static MessageConsole console = ConsoleUtil
			.getConsole("com.bocnet.depmap");

	private final IFile file;
	private final String markerType;

	public XmlFileErrorHandler(IFile file, String markerType) {
		this.file = file;
		this.markerType = markerType;
	}

	private void addMarker(SAXParseException e, int severity) {
		try {
			MarkerUtil.createMarker(file, markerType, severity, e.getMessage(),
					e.getLineNumber());
		} catch (CoreException ex) {
			ConsoleUtil.print(console, ex);
		}
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