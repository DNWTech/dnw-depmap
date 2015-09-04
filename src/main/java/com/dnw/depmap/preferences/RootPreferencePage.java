/**
 * !(#) RootPreferencePage.java
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
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.dnw.depmap.Activator;
import com.dnw.plugin.preference.FileFieldEditor;
import com.dnw.plugin.preference.GroupFieldEditor;
import com.dnw.plugin.preference.TextFieldEditor;

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
 * 
 * @author manbaum
 * @since Oct 20, 2014
 */
public class RootPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Constructor of RootPreferenePage.
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 */
	public RootPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Depandency map generating settings.\n");
	}

	private GroupFieldEditor gf5;
	private BooleanFieldEditor flagpreferfiles;
	private TextFieldEditor whitefiles;
	private TextFieldEditor blackfiles;

	private GroupFieldEditor gf4;
	private BooleanFieldEditor flagverbosetocon;
	private BooleanFieldEditor flagverbosetofile;
	private FileFieldEditor verbosefile;

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
		final Composite p = getFieldEditorParent();

		gf5 = new GroupFieldEditor(
				"File name filter (a regexp or a string prefix with '@', one item each line)", p);
		whitefiles = new TextFieldEditor(PrefKeys.P_WHITEFILES, "Allows:", 50, 6,
				gf5.getGroupControl(p));
		blackfiles = new TextFieldEditor(PrefKeys.P_BLACKFILES, "Blocks:", 50, 6,
				gf5.getGroupControl(p));
		flagpreferfiles = new BooleanFieldEditor(PrefKeys.P_PREFERFILES,
				"Default to allow, blocking has priority", gf5.getGroupControl(p));

		Composite c = new Composite(gf5.getGroupControl(p), SWT.BORDER);
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

		gf4 = new GroupFieldEditor("Logging settings", p);
		flagverbosetocon = new BooleanFieldEditor(PrefKeys.P_LOGVERBOSETOCON,
				"Output verbose logging in console", gf4.getGroupControl(p));
		flagverbosetofile = new BooleanFieldEditor(PrefKeys.P_LOGVERBOSETOFILE,
				"Output verbose logging in file", gf4.getGroupControl(p));
		verbosefile = new FileFieldEditor(PrefKeys.P_LOGVERBOSEFILE, "File:", true,
				gf4.getGroupControl(p));

		gf5.addField(whitefiles);
		gf5.addField(blackfiles);
		gf5.addField(flagpreferfiles);
		addField(gf5);

		gf4.addField(flagverbosetocon);
		gf4.addField(flagverbosetofile);
		gf4.addField(verbosefile);
		addField(gf4);
	}

	/**
	 * Initializes all field editors.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#initialize()
	 */
	@Override
	protected void initialize() {
		super.initialize();
		Composite p = getFieldEditorParent();
		boolean value1 = flagverbosetofile.getBooleanValue();
		verbosefile.setEnabled(value1, gf4.getGroupControl(p));
	}

	/**
	 * The field editor preference page implementation of this <code>IPreferencePage</code> (and
	 * <code>IPropertyChangeListener</code>) method intercepts <code>IS_VALID</code> events but
	 * passes other events on to its superclass.
	 * 
	 * @author manbaum
	 * @since Oct 23, 2014
	 * @param event the property change event object describing which property changed and how.
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		FieldEditor f = (FieldEditor)event.getSource();
		Composite p = getFieldEditorParent();
		if (event.getProperty() == FieldEditor.VALUE) {
			if (f.getPreferenceName().equals(PrefKeys.P_LOGVERBOSETOFILE)) {
				boolean value = (Boolean)event.getNewValue();
				verbosefile.setEnabled(value, gf4.getGroupControl(p));
			}
		}
	}

	/**
	 * <p>
	 * Initializes this preference page for the given workbench. This method is called automatically
	 * </p>
	 * <p>
	 * as the preference page is being created and initialized. Clients must not call this method.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 * @param workbench the workbench.
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}