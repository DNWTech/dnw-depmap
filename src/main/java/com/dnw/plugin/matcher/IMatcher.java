/**
 * !(#) IChecker.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 17, 2014.
 */
package com.dnw.plugin.matcher;

/**
 * Class/Interface IChecker.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public interface IMatcher<T> {

	/**
	 * Method matches.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param value
	 * @return
	 */
	boolean matches(T value);
}
