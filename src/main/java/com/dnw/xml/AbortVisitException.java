/**
 * !(#) AbortVisitException.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Aug 31, 2015.
 */
package com.dnw.xml;

import org.xml.sax.SAXException;

/**
 * Class/Interface AbortVisitException.
 * 
 * @author manbaum
 * @since Aug 31, 2015
 */
public class AbortVisitException extends SAXException {

	/**
	 * Field serialVersionUID.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 */
	private static final long serialVersionUID = -8056204262037624413L;

	/**
	 * Constructor of AbortException.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 */
	public AbortVisitException() {
	}

	/**
	 * Constructor of AbortException.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 * @param message
	 */
	public AbortVisitException(String message) {
		super(message);
	}

	/**
	 * Constructor of AbortException.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 * @param e
	 */
	public AbortVisitException(Exception e) {
		super(e);
	}

	/**
	 * Constructor of AbortException.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 * @param message
	 * @param e
	 */
	public AbortVisitException(String message, Exception e) {
		super(message, e);
	}
}
