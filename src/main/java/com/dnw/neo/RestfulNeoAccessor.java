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
package com.dnw.neo;

import com.dnw.json.M;

/**
 * Accessor of the Neo4j graph database using REST API.
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
	 * @since Oct 18, 2014
	 * @param statement
	 * @see com.dnw.neo.NeoAccessor#execute(java.lang.String)
	 */
	@Override
	public void execute(String statement) {
		// TODO Auto-generated method stub

	}

	/**
	 * Executes a cypher query.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param statement the cypher statement.
	 * @param params the query parameters.
	 * @see com.dnw.neo.NeoAccessor#execute(java.lang.String, com.dnw.json.M)
	 */
	@Override
	public void execute(String statement, M params) {
		// TODO Auto-generated method stub
	}

	/**
	 * Startup the embedded database server.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @see com.dnw.neo.NeoAccessor#startup()
	 */
	@Override
	public void startup() {
	}

	/**
	 * Shutdown the embedded database server.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @see com.dnw.neo.NeoAccessor#shutdown()
	 */
	@Override
	public void shutdown() {
	}

	/**
	 * Adds a hook to JVM, shutdown the embedded database server when the JVM shutdown.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @see com.dnw.neo.NeoAccessor#configAutoShutdown()
	 */
	@Override
	public void configAutoShutdown() {
	}
}
