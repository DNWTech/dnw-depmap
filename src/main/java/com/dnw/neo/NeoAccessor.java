/**
 * !(#) NeoAccessor.java
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
 * Defines the interface to access the Neo4j graph database.
 * 
 * @author manbaum
 * @since Oct 12, 2014
 */
public interface NeoAccessor {

	/**
	 * Executes a cypher query.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param statement the cypher statement.
	 */
	void execute(String statement);

	/**
	 * Executes a cypher query.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param statement the cypher statement.
	 * @param params the query parameters.
	 */
	void execute(String statement, M params);

	/**
	 * Startup the embedded database server.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 */
	void startup();

	/**
	 * Shutdown the embedded database server.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 */
	void shutdown();

	/**
	 * Adds a hook to JVM, shutdown the embedded database server when the JVM shutdown.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 */
	void configAutoShutdown();
}
