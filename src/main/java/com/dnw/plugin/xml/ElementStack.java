/**
 * !(#) ElementStack.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Aug 27, 2015.
 */
package com.dnw.plugin.xml;

import java.util.ArrayList;

/**
 * Class/Interface ElementStack.
 * 
 * @author manbaum
 * @since Aug 27, 2015
 */
public class ElementStack {

	private ArrayList<Element> list = new ArrayList<Element>();

	/**
	 * Constructor of ElementStack.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 */
	public ElementStack() {
	}

	/**
	 * Method reset.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 */
	public void reset() {
		list.clear();
	}

	/**
	 * Method push.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param e
	 */
	public void push(Element e) {
		list.add(e);
	}

	/**
	 * Method pop.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @return
	 */
	public Element pop() {
		return list.size() > 0 ? list.remove(list.size() - 1) : null;
	}

	/**
	 * Method top.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @return
	 */
	public Element top() {
		return peek(0);
	}

	/**
	 * Method peek.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param index
	 * @return
	 */
	public Element peek(int index) {
		return index < list.size() ? list.get(list.size() - index - 1) : null;
	}

	/**
	 * Method size.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @return
	 */
	public int size() {
		return list.size();
	}
}
