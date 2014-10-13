/**
 * !(#) K.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 11, 2014.
 */
package com.dnw.json;

/**
 * Interface for conversion data into JSON compatible value.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public interface K<T> {

	/**
	 * Converts the given value to a JSON compatible value.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param value the value to convert.
	 * @return the converted value.
	 */
	Object convert(T value);
}