/**
 * !(#) TestNeo4j.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Oct 8, 2014.
 */
package com.dnw.neo4j;

import java.io.File;
import java.util.Iterator;

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
 * 
 */
public class TestNeo4j {

	private static String DBPATH = System.getProperty("user.dir")
			+ "/target/db";

	private static enum RelTypes implements RelationshipType {
		KNOWS
	}

	private static void checkPath() {
		File f = new File(DBPATH);
		if (f.exists() && f.isDirectory())
			return;
		throw new IllegalStateException("invalid.database.dir: " + DBPATH);
	}

	public static void main(String[] args) {
		checkPath();
		// testCreate();
		testRead();
	}

	public static void testCreate() {
		GraphDatabaseService gdb = new GraphDatabaseFactory()
				.newEmbeddedDatabaseBuilder(DBPATH)
				.setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size,
						"10M")
				.setConfig(GraphDatabaseSettings.string_block_size, "60")
				.setConfig(GraphDatabaseSettings.array_block_size, "300")
				.newGraphDatabase();
		registerShutdownHook(gdb);
		Transaction tx = gdb.beginTx();
		try {
			Node first = createMessageNode(gdb, "Hello, ");
			Node second = createMessageNode(gdb, "World!");
			Relationship rel = createMessageRelationship(first, second,
					"brave Neo4j ");
			tx.success();
		} finally {
			tx.close();
		}
		gdb.shutdown();
	}

	public static void testRead() {
		GraphDatabaseService gdb = new GraphDatabaseFactory()
				.newEmbeddedDatabaseBuilder(DBPATH)
				.setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size,
						"10M")
				.setConfig(GraphDatabaseSettings.string_block_size, "60")
				.setConfig(GraphDatabaseSettings.array_block_size, "300")
				.newGraphDatabase();
		registerShutdownHook(gdb);
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

	public static Node createMessageNode(GraphDatabaseService gdb,
			String message) {
		Node node = gdb.createNode();
		node.setProperty("message", message);
		return node;
	}

	public static Relationship createMessageRelationship(Node from, Node to,
			String message) {
		Relationship rel = from.createRelationshipTo(to, RelTypes.KNOWS);
		rel.setProperty("message", message);
		return rel;
	}

	/**
	 * Method registerShutdownHook.
	 * 
	 * @author manbaum
	 * @since Oct 8, 2014
	 * 
	 * @param gdb
	 */
	private static void registerShutdownHook(final GraphDatabaseService gdb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				gdb.shutdown();
			}
		});
	}
}
