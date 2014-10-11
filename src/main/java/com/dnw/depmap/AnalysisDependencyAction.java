/**
 * !(#) AnalysisDependencyAction.java
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dnw.depmap.builder.ResourceVisitorDelegator;

/**
 * Class/Interface AnalysisDependencyAction.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 * 
 */
public class AnalysisDependencyAction implements IObjectActionDelegate {

	private ISelection selection;

	/**
	 * Overrider method run.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * 
	 * @param action
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			@SuppressWarnings("rawtypes")
			Iterator it;
			for (it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IResource resource = null;
				if (element instanceof IResource) {
					resource = (IResource) element;
				} else if (element instanceof IAdaptable) {
					resource = (IResource) ((IAdaptable) element)
							.getAdapter(IResource.class);
				}
				if (resource != null) {
					analysisDependency(resource);
				}
			}
		}
	}

	/**
	 * Method analysisDependency.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * 
	 * @param unit
	 */
	private void analysisDependency(IResource resource) {
		ResourceVisitorDelegator visitor = new ResourceVisitorDelegator(null);
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
	 * 
	 * @param action
	 * @param selection
	 * 
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
	 * 
	 * @param action
	 * @param targetPart
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
