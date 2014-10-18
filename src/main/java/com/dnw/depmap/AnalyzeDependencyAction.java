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
 * The menu action used by this plug-in. Activated when the user selects the menu item 'Analyze
 * Dependency'.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class AnalyzeDependencyAction implements IObjectActionDelegate {

	private ISelection selection;

	/**
	 * This method is called by the proxy action when the action has been triggered.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action the action proxy that handles the presentation portion of the action.
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			// Starts a job to do analyzing in background, and displays a progress bar in the progress view.
			Job job = new AnalyzeDependencyJob("AnalyzeDependency", (IStructuredSelection)selection);
			job.schedule();
		}
	}

	/**
	 * Notifies this action delegate that the selection in the workbench has changed.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action the action proxy that handles presentation portion of the action.
	 * @param selection the current selection, or <code>null</code> if there is no selection.
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Sets the active part for the delegate. The active part is commonly used to get a working
	 * context for the action, such as the shell for any dialog which is needed.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param action the action proxy that handles presentation portion of the action; must not be
	 *            <code>null</code>.
	 * @param targetPart the new part target; must not be <code>null</code>.
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
