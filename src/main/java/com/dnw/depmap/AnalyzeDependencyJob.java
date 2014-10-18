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
 * The job to analyzing the dependency.
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
	 * @param name the display name of this job.
	 * @param selection the selection of Java elements to be analyzed.
	 */
	public AnalyzeDependencyJob(String name, IStructuredSelection selection) {
		super(name);
		this.selection = selection;
	}

	/**
	 * Traverses the selection to find out files of known types.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param selection the selection of Java elements to be analyzed.
	 * @param monitor the progress monitor.
	 * @return an <code>IResourceFinder</code> object holds the result.
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
	 * Analyzes each file in the given list to generate the dependency map.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resources a list of files to be analyzed.
	 * @param sub the progress monitor.
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
	 * Executes this job. Returns the result of the execution.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param monitor the monitor to be used for reporting progress and responding to cancellation.
	 *            The monitor is never <code>null</code>.
	 * @return resulting status of the run. The result must not be <code>null</code>.
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("AnalyzeDependency", 100);
		try {
			// divides the whole progress into 100 ticks.
			SubMonitor sub = SubMonitor.convert(monitor, 100);
			// finding known resources will use 3 ticks.
			IResourceFinder finder = filterSupportedResource(selection, sub.newChild(3));
			Activator.console.println("*** Total " + finder.getSupportedList().size()
					+ " file(s) found.");
			// re-divides the remaining progress according to the number of files.
			// each file can use 100 ticks. 1 more tick used as a guard.
			sub.setWorkRemaining(finder.getSupportedList().size() * 100 + 1);
			// starts up the Neo4j database.
			Activator.getDefault().accessor.startup();
			// cleans up the database if required.
			if (Activator.clearDatabase) {
				Activator.neo().clear();
			}
			// visit all resources to generate dependency map. 
			visitAllResources(finder.getSupportedList(), sub);
			// shuts down the database after generating.
			Activator.getDefault().accessor.shutdown();
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

}
