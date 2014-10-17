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

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.dnw.depmap.ast.MethodDeclarationVisitor;
import com.dnw.depmap.ast.MethodInvocationVisitor;
import com.dnw.depmap.ast.TypeDeclarationVisitor;
import com.dnw.depmap.neo.NeoDao;
import com.dnw.depmap.neo.NeoWriter;
import com.dnw.depmap.resource.JavaFileVisitor;
import com.dnw.matcher.RegexMatcher;
import com.dnw.matcher.StringMatcher;
import com.dnw.matcher.WhiteList;
import com.dnw.neo.EmbeddedNeoAccessor;
import com.dnw.neo.NeoAccessor;
import com.dnw.plugin.ast.DefaultVisitorRegistry;
import com.dnw.plugin.ast.IVisitorDelegator;
import com.dnw.plugin.ast.IVisitorRegistry;
import com.dnw.plugin.ast.NodeTypeSet;
import com.dnw.plugin.ast.RegistryBasedVisitorDelegator;
import com.dnw.plugin.resource.FileExtResourceVisitorFactory;
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

	// The shared instance
	private static Activator plugin;

	public static final ConsoleUtil console = ConsoleUtil.getConsole(PLUGIN_ID);
	public static final FileExtResourceVisitorFactory factory = new FileExtResourceVisitorFactory();
	public static final WhiteList<String> filter = new WhiteList<String>();

	public static final NodeTypeSet stopSet = new NodeTypeSet();
	public static final IVisitorRegistry registry = new DefaultVisitorRegistry();
	public static final IVisitorDelegator delegator = new RegistryBasedVisitorDelegator(registry,
			stopSet);

	static {
		factory.registerVisitor("java", JavaFileVisitor.class);
		// factory.registerVisitor("xml", XmlFileVisitor.class);

		filter.addMatcher(new StringMatcher("java.lang.Object"));
		filter.addMatcher(new RegexMatcher("org\\.eclipse\\.jdt\\.core\\.dom\\..*"));
		filter.addMatcher(new RegexMatcher("com\\.dnw\\..*"));

		registry.add(TypeDeclaration.class, new TypeDeclarationVisitor());
		registry.add(MethodDeclaration.class, new MethodDeclarationVisitor());
		registry.add(MethodInvocation.class, new MethodInvocationVisitor());
	}

	public NeoAccessor accessor;
	public NeoDao neo;

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
		neo = new NeoDao(new NeoWriter(accessor), filter);
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
	public static NeoDao neo() {
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
