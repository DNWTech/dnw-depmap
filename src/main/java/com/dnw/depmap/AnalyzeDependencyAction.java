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

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

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
			Job job = new AnalyzeDependencyJob("AnalyzeDependency", (IStructuredSelection)selection);
			job.schedule();
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
