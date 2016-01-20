/**
 * !(#) IElementVisitor.java
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
package com.dnw.plugin.xml;

/**
 * Class/Interface IElementVisitor.
 * 
 * @author manbaum
 * @since Aug 28, 2015
 */
public interface IElementVisitor {

	/**
	 * Method beginDocument.
	 * 
	 * @author manbaum
	 * @since Jan 20, 2016
	 * @param context
	 */
	void beginDocument(ElementVisitContext context);

	/**
	 * Method endDocument.
	 * 
	 * @author manbaum
	 * @since Jan 20, 2016
	 * @param context
	 */
	void endDocument(ElementVisitContext context);

	/**
	 * Method visitBegin.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param context
	 * @param e
	 */
	void beginElement(ElementVisitContext context, Element e);

	/**
	 * Method visitEnd.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param context
	 * @param e
	 */
	void endElement(ElementVisitContext context, Element e);
}
