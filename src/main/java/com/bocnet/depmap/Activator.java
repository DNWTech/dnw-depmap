package com.bocnet.depmap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author manbaum
 * @since Oct 1, 2014
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.bocnet.depmap";

	// The shared instance
	private static Activator plugin;

	/**
	 * Constructor of Activator.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 */
	public Activator() {
	}

	/**
	 * Overrider method start.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @param context
	 * @throws Exception
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * Overrider method stop.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @param context
	 * @throws Exception
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path. The path must not have a leading "." or path separator.
	 * Clients should use a path like "icons/mysample.gif" rather than
	 * "./icons/mysample.gif" or "/icons/mysample.gif".
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @param path
	 *            the path.
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
