/**
 * !(#) AnalyzeDependencyAction.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 10, 2014.
 */
package com.dnw.depmap;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dnw.plugin.resource.FactoryBasedResourceFinder;
import com.dnw.plugin.resource.FactoryBasedResourceVisitor;

/**
 * Class/Interface AnalyzeDependencyAction.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class AnalyzeDependencyAction implements IObjectActionDelegate {

	private ISelection selection;

	/**
	 * Overrider method run.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection ss = (IStructuredSelection)selection;
			Job job = new Job("AnalyzeDependency") {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask("AnalyzeDependency", 100);
					try {
						SubMonitor sub = SubMonitor.convert(monitor, 1000);
						FactoryBasedResourceFinder finder = filterSupportedResource(ss,
								sub.newChild(50));
						Activator.getDefault().accessor.startup();
						sub.setWorkRemaining(finder.getSupportedList().size() * 100 + 1);
						for (IResource resource : finder.getSupportedList()) {
							IResourceVisitor visitor = Activator.factory.createVisitor(resource,
									sub.newChild(100));
							if (visitor != null) {
								try {
									resource.accept(visitor);
								} catch (CoreException e) {
									Activator.console.println(e);
								}
							}
						}
						Activator.getDefault().accessor.shutdown();
					} finally {
						monitor.done();
					}
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		}
	}

	private FactoryBasedResourceFinder filterSupportedResource(IStructuredSelection selection,
			IProgressMonitor monitor) {
		FactoryBasedResourceFinder finder = new FactoryBasedResourceFinder(Activator.factory,
				monitor);
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
		return finder;
	}

	/**
	 * Method processStructuredSelection.
	 * 
	 * @author manbaum
	 * @since Oct 16, 2014
	 * @param pm
	 * @param selection
	 */
	private final void processStructuredSelection(IProgressMonitor monitor,
			IStructuredSelection selection) {
		Activator.getDefault().accessor.startup();
		try {
			@SuppressWarnings("rawtypes") Iterator it;
			int n = selection.size();
			SubMonitor sub = SubMonitor.convert(monitor, n * 100);
			for (it = selection.iterator(); it.hasNext(); n--) {
				if (monitor.isCanceled())
					break;
				Object element = it.next();
				sub.setWorkRemaining(n * 100);
				if (element instanceof IResource) {
					analyzeDependency(sub.newChild(100), (IResource)element);
				} else if (element instanceof IAdaptable) {
					IResource resource = (IResource)((IAdaptable)element)
							.getAdapter(IResource.class);
					if (resource != null) {
						analyzeDependency(sub.newChild(100), resource);
					}
				}
			}
		} finally {
			monitor.done();
			Activator.getDefault().accessor.shutdown();

		}
	}

	/**
	 * Method analyzeDependency.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param unit
	 */
	private void analyzeDependency(IProgressMonitor monitor, IResource resource) {
		FactoryBasedResourceVisitor visitor = new FactoryBasedResourceVisitor(Activator.factory, monitor);
		try {
			resource.accept(visitor);
		} catch (JavaModelException e) {
			Activator.console.println(e);
		} catch (CoreException e) {
			Activator.console.println(e);
		}
	}

	/**
	 * Overrider method selectionChanged.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action
	 * @param selection
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Overrider method setActivePart.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action
	 * @param targetPart
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
