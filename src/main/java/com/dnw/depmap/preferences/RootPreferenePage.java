/**
 * !(#) RootPreferenePage.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 20, 2014.
 */
package com.dnw.depmap.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.dnw.depmap.Activator;

/**
 * <p>
 * This class represents a preference page that is contributed to the Preferences dialog. By
 * subclassing <code>FieldEditorPreferencePage</code>, we can use the field support built into JFace
 * that allows us to create a page that is small and knows how to save, restore and apply itself.
 * </p>
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that
 * belongs to the main plug-in class. That way, preferences can be accessed directly via the
 * preference store.
 * </p>
 */
public class RootPreferenePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Constructor of RootPreferenePage.
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 */
	public RootPreferenePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to
	 * manipulate various types of preferences. Each field editor knows how to save and restore
	 * itself.
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PrefKeys.P_PATH, "&Directory preference:",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(PrefKeys.P_BOOLEAN, "&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(PrefKeys.P_CHOICE,
				"An example of a multiple-choice preference", 1, new String[][] {
						{ "&Choice 1", "choice1" }, { "C&hoice 2", "choice2" } },
				getFieldEditorParent()));
		addField(new StringFieldEditor(PrefKeys.P_STRING, "A &text preference:",
				getFieldEditorParent()));
	}

	/**
	 * Overrider method init.
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 * @param workbench the workbench.
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}