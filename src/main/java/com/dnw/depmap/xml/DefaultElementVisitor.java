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
import com.dnw.xml.Element;
import com.dnw.xml.ElementVisitContext;
import com.dnw.xml.IElementVisitor;

/**
 * Class/Interface DefaultVisitor.
 * 
 * @author manbaum
 * @since Aug 28, 2015
 */
public class DefaultElementVisitor implements IElementVisitor {

	/**
	 * Overrider method visitBegin.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param context
	 * @param e
	 * @see com.dnw.xml.IElementVisitor#beginElement(com.dnw.xml.Element,
	 *      com.dnw.xml.ElementVisitContext)
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
	 * @see com.dnw.xml.IElementVisitor#endElement(com.dnw.xml.Element,
	 *      com.dnw.xml.ElementVisitContext)
	 */
	@Override
	public void endElement(ElementVisitContext context, Element e) {
		for (String name : e.duplicatedNames()) {
			List<Element> l = e.eLementsWithDuplicateName(name);
			Activator.getDefault().console.forceprintln("*** Dupliate name in " + e.brief());
			for (Element n : l) {
				Activator.getDefault().console.forceprintln(n.brief("    "));
			}
		}
		Activator.getDefault().console.println("--- Update element: " + e.brief());
		Activator.getDefault().neo.updateElement(e);
	}
}
