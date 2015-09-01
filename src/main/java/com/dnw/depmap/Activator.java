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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.dnw.depmap.ast.MethodDeclarationVisitor;
import com.dnw.depmap.ast.MethodInvocationVisitor;
import com.dnw.depmap.ast.TypeDeclarationVisitor;
import com.dnw.depmap.neo.NeoDao;
import com.dnw.depmap.neo.NeoWriter;
import com.dnw.depmap.preferences.PrefKeys;
import com.dnw.depmap.resource.FileFindFacatory;
import com.dnw.depmap.resource.JavaFileVisitor;
import com.dnw.depmap.resource.XmlFileVisitor;
import com.dnw.depmap.xml.DefaultElementVisitor;
import com.dnw.matcher.CommonFilter;
import com.dnw.matcher.RegexMatcher;
import com.dnw.matcher.StringMatcher;
import com.dnw.neo.EmbeddedNeoAccessor;
import com.dnw.neo.NeoAccessor;
import com.dnw.neo.RestfulNeoAccessor;
import com.dnw.plugin.ast.GeneralVisitorRegistry;
import com.dnw.plugin.ast.IVisitorDelegator;
import com.dnw.plugin.ast.IVisitorRegistry;
import com.dnw.plugin.ast.NodeTypeBitMapSet;
import com.dnw.plugin.ast.RegistryBasedVisitorDelegator;
import com.dnw.plugin.util.ConsoleUtil;
import com.dnw.plugin.xml.IElementVisitor;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dnw.depmap";
	// The shared instance.
	private static Activator plugin;

	// The plug-in's console.
	public final ConsoleUtil console = ConsoleUtil.getConsole(PLUGIN_ID);

	// For filtering files, can be set in preference page.
	public final CommonFilter<String> filenameFilter = new CommonFilter<String>();
	// This factory defines what kinds of files will be inspected in a project.
	public final FileFindFacatory factory = new FileFindFacatory(filenameFilter);
	// The white list limits what packages or classes should be focused.
	// The content of the filter list will be set in preference page.
	public final CommonFilter<String> filter = new CommonFilter<String>();

	// The AST node type set defines a stop set. (not used now)
	// All nodes with its type in this set will be ignored, do not traverse it to improve the performance.
	public final NodeTypeBitMapSet stopSet = new NodeTypeBitMapSet();
	// Register the corresponding AST visitor to visit the AST.
	public final IVisitorRegistry registry = new GeneralVisitorRegistry();
	// Apply the stop set on the visitors.
	public final IVisitorDelegator delegator = new RegistryBasedVisitorDelegator(registry, stopSet);

	// For visiting XML elements.
	public final IElementVisitor xmlvisitor = new DefaultElementVisitor();

	// The Neo4j database settings.
	// These 3 settings will be set in preference page.
	private boolean useEmbedded = false;
	private String DBURL;
	private String DBPATH;
	// Neo4j database Cypher language executor.
	public NeoAccessor accessor;
	// Neo4j accessor to generate all AST nodes and its relations.
	public NeoDao neo;
	// If true, a set of cypher statements will be executed before/after file process, 
	// also these settings can be set in preference page.
	public boolean preExec = false;
	public List<String> statements_pre = new ArrayList<String>();
	public boolean postExec = false;
	public List<String> statements_post = new ArrayList<String>();

	// When the settings in preference page change, we should re-load them, this listener works for that.
	private final IPropertyChangeListener listener;

	/**
	 * Constructor of Activator.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 */
	public Activator() {
		// Examines .java and .xml files.
		factory.registerVisitor("java", JavaFileVisitor.class);
		factory.registerVisitor("xml", XmlFileVisitor.class);

		// Those AST nodes should be carefully handled.
		registry.add(TypeDeclaration.class, new TypeDeclarationVisitor());
		registry.add(MethodDeclaration.class, new MethodDeclarationVisitor());
		registry.add(MethodInvocation.class, new MethodInvocationVisitor());

		// create preference page change listener.
		listener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				loadPreference();
			}
		};
	}

	/**
	 * Loads preference settings from preference store.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 */
	private void loadPreference() {
		// loads Neo4j database settings.
		IPreferenceStore store = super.getPreferenceStore();
		if (store.getBoolean(PrefKeys.P_USESTANDALONE)) {
			useEmbedded = false;
			DBURL = store.getString(PrefKeys.P_DBURL);
		} else if (store.getBoolean(PrefKeys.P_USEEMBEDDED)) {
			useEmbedded = true;
			DBPATH = store.getString(PrefKeys.P_DBDIR);
		} else {
			useEmbedded = false;
			DBURL = "http://localhost:7474/db/data";
			store.setValue(PrefKeys.P_USESTANDALONE, true);
			store.setValue(PrefKeys.P_DBURL, DBURL);
		}

		// loads filename filter settings.
		filenameFilter.clearAllows();
		filenameFilter.clearBlocks();
		filenameFilter.setPreferWhite(store.getBoolean(PrefKeys.P_PREFERFILES));
		String white = store.getString(PrefKeys.P_WHITEFILES);
		if (white != null) {
			String[] list = white.split("\\s*\\n\\s*");
			for (String s : list) {
				s = s.trim();
				if (!s.isEmpty() && !s.startsWith("#")) {
					console.println("allow path: \"" + s + "\"");
					if (s.startsWith("@"))
						filenameFilter.addAllowMatcher(new StringMatcher(s.substring(1)));
					else
						filenameFilter.addAllowMatcher(new RegexMatcher(s));
				}
			}
		}
		String black = store.getString(PrefKeys.P_BLACKFILES);
		if (black != null) {
			String[] list = black.split("\\s*\\n\\s*");
			for (String s : list) {
				s = s.trim();
				if (!s.isEmpty() && !s.startsWith("#")) {
					console.println("block path: \"" + s + "\"");
					if (s.startsWith("@"))
						filenameFilter.addBlockMatcher(new StringMatcher(s.substring(1)));
					else
						filenameFilter.addBlockMatcher(new RegexMatcher(s));
				}
			}
		}

		// loads class/package filter settings.
		filter.clearAllows();
		filter.clearBlocks();
		filter.setPreferWhite(store.getBoolean(PrefKeys.P_PREFERWHITE));
		white = store.getString(PrefKeys.P_WHITELIST);
		if (white != null) {
			String[] list = white.split("\\s*\\n\\s*");
			for (String s : list) {
				s = s.trim();
				if (!s.isEmpty() && !s.startsWith("#")) {
					console.println("allow: \"" + s + "\"");
					if (s.startsWith("@"))
						filter.addAllowMatcher(new StringMatcher(s.substring(1)));
					else
						filter.addAllowMatcher(new RegexMatcher(s));
				}
			}
		}
		black = store.getString(PrefKeys.P_BLACKLIST);
		if (black != null) {
			String[] list = black.split("\\s*\\n\\s*");
			for (String s : list) {
				s = s.trim();
				if (!s.isEmpty() && !s.startsWith("#")) {
					console.println("block: \"" + s + "\"");
					if (s.startsWith("@"))
						filter.addBlockMatcher(new StringMatcher(s.substring(1)));
					else
						filter.addBlockMatcher(new RegexMatcher(s));
				}
			}
		}

		// loads pre/post executing Cypher statements setting.
		preExec = store.getBoolean(PrefKeys.P_USEPREEXEC);
		statements_pre.clear();
		if (preExec) {
			String ss = store.getString(PrefKeys.P_PREEXEC);
			if (ss != null) {
				String[] array = ss.split("\\s*\\n\\s*");
				for (String s : array) {
					s = s.trim();
					if (!s.isEmpty() && !s.startsWith("#")) {
						console.println("pre-statement: \"" + s + "\"");
						statements_pre.add(s);
					}
				}
			} else {
				preExec = false;
			}
		}
		postExec = store.getBoolean(PrefKeys.P_USEPOSTEXEC);
		statements_post.clear();
		if (postExec) {
			String ss = store.getString(PrefKeys.P_POSTEXEC);
			if (ss != null) {
				String[] array = ss.split("\\s*\\n\\s*");
				for (String s : array) {
					s = s.trim();
					if (!s.isEmpty() && !s.startsWith("#")) {
						console.println("post-statement: \"" + s + "\"");
						statements_post.add(s);
					}
				}
			} else {
				postExec = false;
			}
		}

		// loads logging setting.
		boolean verboseToConsole = store.getBoolean(PrefKeys.P_LOGVERBOSETOCON);
		console.setVerbose(verboseToConsole);
		console.forceprintln("*** " + (verboseToConsole ? "Enable" : "Disable")
				+ " verbose logging to console.");
		boolean verboseToFile = store.getBoolean(PrefKeys.P_LOGVERBOSETOFILE);
		String verboseFile = store.getString(PrefKeys.P_LOGVERBOSEFILE);
		if (verboseToFile && !verboseFile.isEmpty()) {
			console.forceprintln("*** Enable verbose logging to file: " + verboseFile);
		}

		// re-creates something according to the new settings.
		accessor = useEmbedded ? new EmbeddedNeoAccessor(DBPATH) : new RestfulNeoAccessor(DBURL);
		neo = new NeoDao(new NeoWriter(accessor), filter);
	}

	/**
	 * Starts up this plug-in.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param context the bundle context for this plug-in.
	 * @throws Exception if this plug-in did not start up properly.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		loadPreference();
		super.getPreferenceStore().addPropertyChangeListener(listener);
	}

	/**
	 * Stops this plug-in.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * @param context the bundle context for this plug-in.
	 * @throws Exception if this plug-in did not stopped properly.
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.getPreferenceStore().removePropertyChangeListener(listener);
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
	 * Exposes the database operations.
	 * 
	 * @author manbaum
	 * @since Oct 13, 2014
	 * @return the DAO of Neo4j database.
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
