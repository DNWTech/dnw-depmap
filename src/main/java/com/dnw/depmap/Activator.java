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
import org.osgi.framework.BundleContext;

import com.dnw.depmap.neo.BlackOrWhite;
import com.dnw.depmap.neo.NeoDao;
import com.dnw.depmap.neo.NeoWriter;
import com.dnw.depmap.visitor.FileExtVisitorFactory;
import com.dnw.depmap.visitor.JavaFileVisitor;
import com.dnw.neo.EmbeddedNeoAccessor;
import com.dnw.neo.NeoAccessor;
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
	private static String DBPATH = "/Users/manbaum/workspace/neo4j-community-2.1.5/data/graph.db";

	public static final ConsoleUtil console = ConsoleUtil.getConsole(PLUGIN_ID);

	// The shared instance
	private static Activator plugin;

	public static final FileExtVisitorFactory factory = new FileExtVisitorFactory();

	public NeoAccessor accessor;
	public NeoDao neo;

	static {
		BlackOrWhite.WHITE.add("com\\.dnw\\..*");
		BlackOrWhite.WHITE.add("org\\.eclipse\\.jdt\\.core\\.dom\\..*");
		BlackOrWhite.WHITE.add("java\\.lang\\.Object");
		BlackOrWhite.BLACK.add(".*");

		factory.register("java", JavaFileVisitor.class);
		//factory.register("xml", XmlFileVisitor.class);
	}

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
	 * @param context
	 * @throws Exception
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		accessor = new EmbeddedNeoAccessor(DBPATH);
		neo = new NeoDao(new NeoWriter(accessor));
	}

	/**
	 * Overrider method stop.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param context
	 * @throws Exception
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		accessor.shutdown();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Method w.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @return
	 */
	public static NeoDao w() {
		return plugin.neo;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path. The path
	 * must not have a leading "." or path separator. Clients should use a path like
	 * "icons/mysample.gif" rather than "./icons/mysample.gif" or "/icons/mysample.gif".
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param path the path.
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
