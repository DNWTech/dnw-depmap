/**
 * !(#) Activator.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 29, 2014.
 */
package com.dnw.depmap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.osgi.framework.BundleContext;

import com.dnw.plugin.util.ConsoleUtil;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dnw.depmap";
	private static String DBPATH = "/Users/manbaum/workspace/indigoSandbox/dnw-depmap/target/db";

	public static final ConsoleUtil console = ConsoleUtil.getConsole(PLUGIN_ID);

	// The shared instance
	private static Activator plugin;
	private static GraphDatabaseService gdb;

	/**
	 * Constructor of Activator.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public Activator() {
	}

	/**
	 * Overrider method start.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param context
	 * @throws Exception
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		gdb = new GraphDatabaseFactory()
				.newEmbeddedDatabaseBuilder(DBPATH)
				.setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size,
						"10M")
				.setConfig(GraphDatabaseSettings.string_block_size, "60")
				.setConfig(GraphDatabaseSettings.array_block_size, "300")
				.newGraphDatabase();
	}

	/**
	 * Overrider method stop.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param context
	 * @throws Exception
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		gdb.shutdown();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns Neo4j database service.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * 
	 * @return Neo4j database service.
	 */
	public static GraphDatabaseService getDatabase() {
		return gdb;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path. The path must not have a leading "." or path separator.
	 * Clients should use a path like "icons/mysample.gif" rather than
	 * "./icons/mysample.gif" or "/icons/mysample.gif".
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param path
	 *            the path.
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
