/**
 * !(#) AbstractTypeRegistryResourceVisitorFactory.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 17, 2014.
 */
package com.dnw.plugin.resource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Class/Interface AbstractTypeRegistryResourceVisitorFactory
 * </p>
 * <p>
 * 
 * <pre>
 * public class MyFactory extends AbstractTypeRegistryResourceVisitorFactory&lt;String&gt; {
 *     protected String resolveKey(IResource resource) {
 *         return resource.getFileExtension();
 *     }
 * }
 * 
 * public class JavaFileVisitor extends IResourceVisitor {
 *     ...
 * }
 * 
 * public class XmlFileVisitor extends IResourceVisitor {
 *     ...
 * }
 * 
 * MyFactory myFactory = new MyFactory();
 * myFactory.registerVisitor("java", JavaFileVisitor.class);
 * myFactory.registerVisitor("xml", XmlFileVisitor.class);
 * 
 * FactoryBasedResourceFinder finder = new FactoryBasedResourceFinder(myFactory, null);
 * IProject project = ...
 * project.accept(finder);
 * List&lt;IResource&gt; javaAndXmlFiles = finder.getSupportedList();
 * </pre>
 * </p>
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public abstract class AbstractTypeRegistryResourceVisitorFactory<T> implements
		IResourceVisitorFactory {

	private final Map<T, Class<? extends IResourceVisitor>> map = new HashMap<T, Class<? extends IResourceVisitor>>();

	/**
	 * Method registerVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param key
	 * @param visitorType
	 */
	public void registerVisitor(T key, Class<? extends IResourceVisitor> visitorType) {
		if (map.containsKey(key))
			throw new IllegalArgumentException("already.registered: " + String.valueOf(key));
		map.put(key, visitorType);
	}

	/**
	 * Method unregisterVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param key
	 */
	public void unregisterVisitor(T key) {
		map.remove(key);
	}

	/**
	 * Method isRegistered.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param key
	 * @return
	 */
	public boolean isRegistered(T key) {
		return map.containsKey(key);
	}

	/**
	 * Method getVisitorType.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param key
	 * @return
	 */
	public Class<? extends IResourceVisitor> getVisitorType(T key) {
		return map.get(key);
	}

	/**
	 * Method findConstructorAcceptProgressMonitor.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param type
	 * @return
	 */
	protected Constructor<? extends IResourceVisitor> findConstructorAcceptProgressMonitor(
			Class<? extends IResourceVisitor> type) {
		try {
			return type.getConstructor(IProgressMonitor.class);
		} catch (NoSuchMethodException e) {
			logError(e);
		} catch (SecurityException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Method findDefaultConstructor.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param type
	 * @return
	 */
	protected Constructor<? extends IResourceVisitor> findDefaultConstructor(
			Class<? extends IResourceVisitor> type) {
		try {
			return type.getConstructor();
		} catch (NoSuchMethodException e) {
			logError(e);
		} catch (SecurityException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param type
	 * @param monitor
	 * @return
	 */
	protected IResourceVisitor createType(Class<? extends IResourceVisitor> type,
			IProgressMonitor monitor) {
		Constructor<? extends IResourceVisitor> ctor;

		try {
			ctor = findConstructorAcceptProgressMonitor(type);
			if (ctor != null) {
				return ctor.newInstance(monitor);
			}
		} catch (InstantiationException e) {
			logError(e);
		} catch (IllegalAccessException e) {
			logError(e);
		} catch (IllegalArgumentException e) {
			logError(e);
		} catch (InvocationTargetException e) {
			logError(e);
		}

		try {
			ctor = findDefaultConstructor(type);
			if (ctor != null) {
				return ctor.newInstance();
			}
		} catch (InstantiationException e) {
			logError(e);
		} catch (IllegalAccessException e) {
			logError(e);
		} catch (IllegalArgumentException e) {
			logError(e);
		} catch (InvocationTargetException e) {
			logError(e);
		}

		return null;
	}

	/**
	 * Method logError.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param e
	 */
	protected void logError(Exception e) {
	}

	/**
	 * Overrider method support.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource
	 * @return
	 * @see com.dnw.plugin.resource.IResourceVisitorFactory#support(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean support(IResource resource) {
		return isRegistered(resolveKey(resource));
	}

	/**
	 * Overrider method createVisitor.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource
	 * @param monitor
	 * @return
	 * @see com.dnw.plugin.resource.IResourceVisitorFactory#createVisitor(org.eclipse.core.resources.IResource,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IResourceVisitor createVisitor(IResource resource, IProgressMonitor monitor) {
		Class<? extends IResourceVisitor> type = getVisitorType(resolveKey(resource));
		return type != null ? createType(type, monitor) : null;
	}

	/**
	 * Method resolveKey.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resource
	 * @return
	 */
	protected abstract T resolveKey(IResource resource);
}
