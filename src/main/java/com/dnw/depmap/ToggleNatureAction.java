/**
 * !(#) ToggleNatureAction.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 30, 2014.
 */
package com.dnw.depmap;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.console.MessageConsole;

import com.dnw.plugin.util.ConsoleUtil;
import com.dnw.plugin.util.NatureUtil;

/**
 * An object action that is contributed into the 'Add/Remove DependencyMap
 * Nature' popup menu.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class ToggleNatureAction implements IObjectActionDelegate {

	private final static MessageConsole console = ConsoleUtil
			.getConsole("com.dnw.depmap");

	private ISelection selection;

	/**
	 * <p>
	 * Performs this action. This method is called by the proxy action when the
	 * action has been triggered. Implement this method to do the actual work.
	 * </p>
	 * <p>
	 * Note: If the action delegate also implements
	 * <code>IActionDelegate2</code>, then this method is not invoked but
	 * instead the <code>runWithEvent(IAction, Event)</code> method is called.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param action
	 *            the action proxy that handles the presentation portion of the
	 *            action.
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			@SuppressWarnings("rawtypes")
			Iterator it;
			for (it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}
				if (project != null) {
					toggleNature(project);
				}
			}
		}
	}

	/**
	 * <p>
	 * Notifies this action delegate that the selection in the workbench has
	 * changed. Implementers can use this opportunity to change the availability
	 * of the action or to modify other presentation properties.
	 * </p>
	 * <p>
	 * When the selection changes, the action enablement state is updated based
	 * on the criteria specified in the plugin.xml file. Then the delegate is
	 * notified of the selection change regardless of whether the enablement
	 * criteria in the <code>plugin.xml</code> file is met.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param action
	 *            the action proxy that handles presentation portion of the
	 *            action.
	 * @param selection
	 *            the current selection, or <code>null</code> if there is no
	 *            selection.
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * <p>
	 * Sets the active part for the delegate. The active part is commonly used
	 * to get a working context for the action, such as the shell for any dialog
	 * which is needed.
	 * </p>
	 * <p>
	 * This method will be called every time the action appears in a popup menu.
	 * The targetPart may change with each invocation.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param action
	 *            the action proxy that handles presentation portion of the
	 *            action; must not be <code>null</code>.
	 * @param targetPart
	 *            the new part target; must not be <code>null</code>.
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Toggles sample nature on a project.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param project
	 *            to have the nature added or removed.
	 */
	private void toggleNature(IProject project) {
		try {
			NatureUtil.toggleNature(project, Nature.NATURE_ID);
		} catch (CoreException ex) {
			ConsoleUtil.print(console, ex);
		}
	}
}
