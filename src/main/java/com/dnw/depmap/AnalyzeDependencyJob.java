/**
 * !(#) AnalyzeDependencyJob.java
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
package com.dnw.depmap;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.dnw.plugin.resource.FactoryBasedResourceFinder;
import com.dnw.plugin.resource.IResourceFinder;

/**
 * Class/Interface AnalyzeDependencyJob.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public final class AnalyzeDependencyJob extends Job {

	private final IStructuredSelection selection;

	/**
	 * Constructor of AnalyzeDependencyJob.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param name
	 * @param selection
	 */
	public AnalyzeDependencyJob(String name, IStructuredSelection selection) {
		super(name);
		this.selection = selection;
	}

	/**
	 * Method filterSupportedResource.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param selection
	 * @param monitor
	 * @return
	 */
	private final IResourceFinder filterSupportedResource(IStructuredSelection selection,
			IProgressMonitor monitor) {
		FactoryBasedResourceFinder finder = new FactoryBasedResourceFinder(Activator.factory,
				monitor);
		try {
			monitor.beginTask("Finding files...", selection.size());
			for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
				Object element = it.next();
				try {
					if (element instanceof IResource) {
						((IResource)element).accept(finder);
					} else if (element instanceof IAdaptable) {
						IResource resource = (IResource)((IAdaptable)element)
								.getAdapter(IResource.class);
						if (resource != null)
							resource.accept(finder);
					}
				} catch (CoreException e) {
					Activator.console.println(e);
				}
			}
		} finally {
			monitor.done();
		}
		return finder;
	}

	/**
	 * Method visitAllResources.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resources
	 * @param sub
	 */
	private final void visitAllResources(List<IResource> resources, SubMonitor sub) {
		for (IResource resource : resources) {
			IResourceVisitor visitor = Activator.factory.createVisitor(resource, sub.newChild(100));
			if (visitor != null) {
				try {
					resource.accept(visitor);
				} catch (CoreException e) {
					Activator.console.println(e);
				}
			}
		}
	}

	/**
	 * Overrider method run.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param monitor
	 * @return
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("AnalyzeDependency", 100);
		try {
			SubMonitor sub = SubMonitor.convert(monitor, 100);
			IResourceFinder finder = filterSupportedResource(selection, sub.newChild(3));
			Activator.console.println("*** Total " + finder.getSupportedList().size()
					+ " file(s) found.");
			sub.setWorkRemaining(finder.getSupportedList().size() * 100 + 1);
			Activator.getDefault().accessor.startup();
			visitAllResources(finder.getSupportedList(), sub);
			Activator.getDefault().accessor.shutdown();
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

}
