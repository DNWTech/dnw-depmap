/**
 * !(#) RegistryBasedDocumentHandler.java
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
package com.dnw.plugin.xml;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class/Interface RegistryBasedDocumentHandler.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class DefaultXmlHandler extends DefaultHandler {

	private final ElementVisitContext context;
	private final IElementVisitor visitor;

	private Locator locator;
	private int lastLine;
	private final ElementStack stack = new ElementStack();
	private Element top;

	/**
	 * Constructor of RegistryBasedDocumentHandler.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * @param file
	 * @param context
	 * @param registry
	 */
	public DefaultXmlHandler(ElementVisitContext context, IElementVisitor visitor) {
		this.context = context;
		this.visitor = visitor;
	}

	/**
	 * Overrider method setDocumentLocator.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param locator
	 * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	/**
	 * Overrider method startDocument.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		stack.reset();
		top = null;
		lastLine = 0;
	}

	/**
	 * Overrider method endDocument.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
	}

	/**
	 * Method workedLines.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 * @return
	 */
	private int workedLines() {
		int worked = locator.getLineNumber() - lastLine;
		lastLine = locator.getLineNumber();
		return worked;
	}

	/**
	 * Overrider method startElement.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		Element parent = stack.top();
		int level = stack.size();
		Element e = new Element(context.file, locator, top, parent, level, uri, localName, qName,
				attributes);
		stack.push(e);
		if (stack.size() == 2) {
			top = e;
		}
		if (visitor != null) {
			visitor.beginElement(context, e);
		}
		context.monitor.worked(workedLines());
	}

	/**
	 * Overrider method endElement.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @param uri
	 * @param localName
	 * @param qName
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		Element e = stack.pop();
		if (stack.size() < 2) {
			top = null;
		}
		if (visitor != null) {
			visitor.endElement(context, e);
		}
		context.monitor.worked(workedLines());
		if (context.monitor.isCanceled()) {
			throw new AbortVisitException();
		}
	}

	/**
	 * Overrider method characters.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @param ch
	 * @param start
	 * @param length
	 * @throws SAXException
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();
		if (!s.isEmpty()) {
			stack.top().appendText(s);
		}
	}
}