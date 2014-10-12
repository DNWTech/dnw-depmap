/**
 * !(#) RestfulNeoAccessor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 12, 2014.
 */
package com.dnw.depmap.neo;

import com.dnw.depmap.json.M;

/**
 * Class/Interface RestfulApiAccessor.
 * 
 * @author manbaum
 * @since Oct 12, 2014
 */
public class RestfulNeoAccessor implements NeoAccessor {

	/**
	 * Constructor of RestfulApiAccessor.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 */
	public RestfulNeoAccessor() {

	}

	/**
	 * Overrider method execute.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param statement
	 * @param params
	 * @see com.dnw.depmap.neo.NeoAccessor#execute(java.lang.String, com.dnw.depmap.json.V)
	 */
	@Override
	public void execute(String statement, M params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void configAutoShutdown() {
		// TODO Auto-generated method stub

	}
}
