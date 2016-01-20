/**
 * !(#) DefaultVisitor.java
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
package com.dnw.depmap.xml;

import java.util.List;

import com.dnw.depmap.Activator;
import com.dnw.plugin.xml.Element;
import com.dnw.plugin.xml.ElementVisitContext;
import com.dnw.plugin.xml.IElementVisitor;

/**
 * Class/Interface DefaultVisitor.
 * 
 * @author manbaum
 * @since Aug 28, 2015
 */
public class DefaultElementVisitor implements IElementVisitor {

	/**
	 * Overrider method beginDocument.
	 * 
	 * @author manbaum
	 * @since Jan 20, 2016
	 * @param context
	 * @see com.dnw.plugin.xml.IElementVisitor#beginDocument(com.dnw.plugin.xml.ElementVisitContext)
	 */
	public void beginDocument(ElementVisitContext context) {
		context.printHeader(Activator.getDefault().console);
	}

	/**
	 * Overrider method endDocument.
	 * 
	 * @author manbaum
	 * @since Jan 20, 2016
	 * @param context
	 * @see com.dnw.plugin.xml.IElementVisitor#endDocument(com.dnw.plugin.xml.ElementVisitContext)
	 */
	public void endDocument(ElementVisitContext context) {
	}

	/**
	 * Overrider method visitBegin.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param context
	 * @param e
	 * @see com.dnw.plugin.xml.IElementVisitor#beginElement(com.dnw.plugin.xml.Element,
	 *      com.dnw.plugin.xml.ElementVisitContext)
	 */
	@Override
	public void beginElement(ElementVisitContext context, Element e) {
		Activator.getDefault().console.println("--- Create element: " + e.brief());
		Activator.getDefault().neo.createElement(e);
	}

	/**
	 * Overrider method visitEnd.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param context
	 * @param e
	 * @see com.dnw.plugin.xml.IElementVisitor#endElement(com.dnw.plugin.xml.Element,
	 *      com.dnw.plugin.xml.ElementVisitContext)
	 */
	@Override
	public void endElement(ElementVisitContext context, Element e) {
		if (e.duplicatedNames().size() > 0) {
			context.forceprintHeader(Activator.getDefault().console);
			for (String name : e.duplicatedNames()) {
				List<Element> l = e.eLementsWithDuplicateName(name);
				Activator.getDefault().console.forceprintln("!!! Dupliate name in " + e.brief());
				for (Element n : l) {
					Activator.getDefault().console.forceprintln(n.brief("    "));
				}
			}
		}
		Activator.getDefault().console.println("--- Update element: " + e.brief());
		Activator.getDefault().neo.updateElement(e);
	}
}
