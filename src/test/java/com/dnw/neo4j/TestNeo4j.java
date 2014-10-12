/**
 * !(#) TestNeo4j.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *     
 * Create by manbaum on Oct 8, 2014.
 */
package com.dnw.neo4j;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.tooling.GlobalGraphOperations;

/**
 * Class/Interface TestNeo4j.
 * 
 * @author manbaum
 * @since Oct 8, 2014
 */
public final class TestNeo4j {

	private static String path1 = System.getProperty("user.dir") + "/target/db";

	private static String path2 = "/Users/manbaum/workspace/neo4j-community-2.1.5/data/graph.db";

	private static enum RelTypes implements RelationshipType {
		KNOWS
	}

	private GraphDatabaseService gdb;
	private ExecutionEngine engine;

	public TestNeo4j() {
		gdb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(checkPath(path2))
				.setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size, "10M")
				.setConfig(GraphDatabaseSettings.string_block_size, "60")
				.setConfig(GraphDatabaseSettings.array_block_size, "300").newGraphDatabase();
		engine = new ExecutionEngine(gdb);
		registerShutdownHook(this);
	}

	private String checkPath(String path) {
		File f = new File(path);
		if (f.exists() && f.isDirectory())
			return path;
		throw new IllegalStateException("invalid.database.dir: " + path);
	}

	public static void main(String[] args) {
		TestNeo4j t = new TestNeo4j();
		// t.testCreate();
		// t.testRead();
		t.testMatch();
	}

	public final static class Pair {

		public final String key;
		public final Object value;

		public Pair(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}

	public static Map<String, Object> m(Pair... pairs) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (Pair p : pairs) {
			result.put(p.key, p.value);
		}
		return result;
	}

	public static Pair p(String key, Object value) {
		return new Pair(key, value);
	}

	public static List<Object> a(Object... values) {
		return Arrays.asList(values);
	}

	public void testMatch() {
		Transaction tx = gdb.beginTx();
		Map<String, Object> p1 = m(p("name", "test1"));
		Map<String, Object> p2 = m(p("name", "test2"));
		Map<String, Object> p3 = m(p("namex", "test1"), p("namey", "test2"),
				p("props", m(p("args", a("1", "2", "3")))));
		try {
			engine.execute("merge (n:Method {name:{name}})", p1);
			engine.execute("merge (n:Method {name:{name}})", p2);
			engine.execute(
					"match (x:Method {name:{namex}}), (y:Method {name:{namey}}) create (x)-[r:Invoke {props}]->(y)",
					p3);
			tx.success();
		} finally {
			tx.close();
		}
	}

	public void testCreate() {
		Transaction tx = gdb.beginTx();
		try {
			Node first = createMessageNode(gdb, "Hello, ");
			Node second = createMessageNode(gdb, "World!");
			Relationship rel = createMessageRelationship(first, second, "brave Neo4j ");
			tx.success();
		} finally {
			tx.close();
		}
		gdb.shutdown();
	}

	public void testRead() {
		Transaction tx = gdb.beginTx();
		GlobalGraphOperations op = GlobalGraphOperations.at(gdb);
		try {
			Iterable<Node> nitc = op.getAllNodes();
			if (nitc != null) {
				Iterator<Node> nit = nitc.iterator();
				while (nit.hasNext()) {
					Node n = nit.next();
					System.out.println(n.getProperty("message"));
				}
			}
			Iterable<Relationship> ritc = op.getAllRelationships();
			if (ritc != null) {
				Iterator<Relationship> rit = ritc.iterator();
				while (rit.hasNext()) {
					Relationship r = rit.next();
					System.out.println(r.getProperty("message"));
				}
			}
		} finally {
			tx.close();
		}
		gdb.shutdown();
	}

	public Node createMessageNode(GraphDatabaseService gdb, String message) {
		Node node = gdb.createNode();
		node.setProperty("message", message);
		return node;
	}

	public Relationship createMessageRelationship(Node from, Node to, String message) {
		Relationship rel = from.createRelationshipTo(to, RelTypes.KNOWS);
		rel.setProperty("message", message);
		return rel;
	}

	/**
	 * Method registerShutdownHook.
	 * 
	 * @author manbaum
	 * @since Oct 8, 2014
	 * @param gdb
	 */
	private void registerShutdownHook(final TestNeo4j t) {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				t.gdb.shutdown();
				t.gdb = null;
				t.engine = null;
			}
		});
	}
}
