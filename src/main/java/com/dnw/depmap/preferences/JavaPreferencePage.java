/**
 * !(#) JavaPreferencePage.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 2, 2015.
 */
package com.dnw.depmap.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.dnw.depmap.Activator;
import com.dnw.plugin.preference.GroupFieldEditor;
import com.dnw.plugin.preference.TextFieldEditor;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace
 * that allows us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that
 * belongs to the main plug-in class. That way, preferences can be accessed directly via the
 * preference store.
 * 
 * @author manbaum
 * @since Sep 2, 2015
 */
public class JavaPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private GroupFieldEditor gf2;
	private BooleanFieldEditor flagprefer;
	private TextFieldEditor whitelist;
	private TextFieldEditor blacklist;

	public JavaPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Java source file analysis settings.\n");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to
	 * manipulate various types of preferences. Each field editor knows how to save and restore
	 * itself.
	 */
	public void createFieldEditors() {
		final Composite p = getFieldEditorParent();

		gf2 = new GroupFieldEditor(
				"Class/Method name filter (a regexp or a string prefix with '@', one item each line)",
				p);
		whitelist = new TextFieldEditor(PrefKeys.P_WHITELIST, "Allows:", 50, 10,
				gf2.getGroupControl(p));
		blacklist = new TextFieldEditor(PrefKeys.P_BLACKLIST, "Blocks:", 50, 10,
				gf2.getGroupControl(p));
		flagprefer = new BooleanFieldEditor(PrefKeys.P_PREFERWHITE,
				"Default to allow, blocking has priority", gf2.getGroupControl(p));

		Composite c = new Composite(gf2.getGroupControl(p), SWT.BORDER);
		GridData gd = new GridData(4, 2, true, false, 2, 1);
		gd.horizontalIndent = 16;
		c.setLayoutData(gd);
		c.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));

		FillLayout fl = new FillLayout();
		fl.marginHeight = fl.marginWidth = 4;
		c.setLayout(fl);

		Label l = new Label(c, SWT.NONE);
		l.setText("If checked,\n"
				+ "  1) rejects file with name matches any item in block list,\n"
				+ "  2) accepts file with name matches any item in allow list,\n"
				+ "  3) accepts file with name not matches any item in both lists.\n"
				+ "If unchecked,\n" //
				+ "  1) accepts file with name matches any item in allow list,\n"
				+ "  2) rejects file with name matches any item in block list,\n"
				+ "  3) rejects file with name not matches any item in both lists.");

		gf2.addField(whitelist);
		gf2.addField(blacklist);
		gf2.addField(flagprefer);
		addField(gf2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}