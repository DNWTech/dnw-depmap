/**
 * !(#) DefaultVisitorFactory.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 16, 2014.
 */
package com.dnw.depmap.visitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

import com.dnw.depmap.Activator;

/**
 * Class/Interface DefaultVisitorFactory.
 * 
 * @author manbaum
 * @since Oct 16, 2014
 */
public class FileExtVisitorFactory implements VisitorFactory {

	private final Map<String, Class<? extends IResourceVisitor>> map = new HashMap<String, Class<? extends IResourceVisitor>>();

	/**
	 * Method register.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param ext
	 * @param type
	 */
	public final void register(String ext, Class<? extends IResourceVisitor> type) {
		if (map.containsKey(ext))
			throw new IllegalArgumentException("already.added: " + ext);
		map.put(ext, type);
	}

	/**
	 * Method unregister.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param ext
	 */
	public final void unregister(String ext) {
		map.remove(ext);
	}

	/**
	 * Method support.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param ext
	 * @return
	 */
	public final boolean support(String ext) {
		return map.containsKey(ext);
	}

	/**
	 * Method get.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param ext
	 * @return
	 */
	public final Class<? extends IResourceVisitor> get(String ext) {
		return map.get(ext);
	}

	/**
	 * Overrider method support.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param resource
	 * @return
	 * @see com.dnw.depmap.visitor.VisitorFactory#support(org.eclipse.core.resources.IResource)
	 */
	public final boolean support(IResource resource) {
		return support(resource.getFileExtension());
	}

	/**
	 * Method getVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param file
	 * @param monitor
	 * @return
	 */
	public IResourceVisitor createVisitor(IResource file, IProgressMonitor monitor) {
		String ext = file.getFileExtension();
		Class<? extends IResourceVisitor> type = map.get(ext.toLowerCase());
		if (type != null) {
			try {
				Constructor<? extends IResourceVisitor> ctor = type
						.getConstructor(IProgressMonitor.class);
				return ctor.newInstance(monitor);
			} catch (InstantiationException e) {
				Activator.console.println(e);
			} catch (IllegalAccessException e) {
				Activator.console.println(e);
			} catch (NoSuchMethodException e) {
				Activator.console.println(e);
			} catch (SecurityException e) {
				Activator.console.println(e);
			} catch (IllegalArgumentException e) {
				Activator.console.println(e);
			} catch (InvocationTargetException e) {
				Activator.console.println(e);
			}
		}
		return null;
	}
}
