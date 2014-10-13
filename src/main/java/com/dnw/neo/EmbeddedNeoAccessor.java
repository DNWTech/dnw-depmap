/**
 * !(#) EmbeddedNeoAccessor.java
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

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import com.dnw.depmap.Activator;
import com.dnw.json.M;

/**
 * Accessor of the Neo4j embedded graph database.
 * 
 * @author manbaum
 * @since Oct 12, 2014
 */
public class EmbeddedNeoAccessor implements NeoAccessor {

	private final String dbpath;
	private GraphDatabaseService gdb;
	private ExecutionEngine engine;

	/**
	 * Constructor of EmbeddedDatabaseAccessor.
	 * 
	 * @author manbaum
	 * @since Oct 12, 2014
	 * @param dbpath
	 */
	public EmbeddedNeoAccessor(String dbpath) {
		this.dbpath = dbpath;
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
		if (gdb == null) {
			throw new IllegalStateException("graph.database.does.startup");
		}
		Transaction tx = gdb.beginTx();
		try {
			Activator.console.println(" -> executing: " + statement);
			Activator.console.println(" ->     " + params.json());
			engine.execute(statement, params.map());
			tx.success();
		} finally {
			tx.close();
		}
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
		if (gdb == null) {
			gdb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(dbpath)
					.setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size, "10M")
					.setConfig(GraphDatabaseSettings.string_block_size, "60")
					.setConfig(GraphDatabaseSettings.array_block_size, "300").newGraphDatabase();
			engine = new ExecutionEngine(gdb);
		}
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
		if (gdb != null) {
			final GraphDatabaseService db = gdb;
			engine = null;
			gdb = null;
			db.shutdown();
		}
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
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				shutdown();
			}
		});
	}
}
