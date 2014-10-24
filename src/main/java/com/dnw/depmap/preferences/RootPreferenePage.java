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
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.dnw.depmap.Activator;
import com.dnw.plugin.preference.GroupFieldEditor;

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
		setDescription("Settings used when generating depandency map.\n");
	}

	private GroupFieldEditor gf1;
	private BooleanFieldEditor flagurl;
	private StringFieldEditor dburl;
	private BooleanFieldEditor flagdir;
	private DirectoryFieldEditor dbdir;
	private GroupFieldEditor gf2;
	private BooleanFieldEditor flagwhite;
	private BooleanFieldEditor flagblack;
	private BooleanFieldEditor flagprefer;
	private StringFieldEditor whitelist;
	private StringFieldEditor blacklist;
	private GroupFieldEditor gf3;
	private BooleanFieldEditor flagexec;
	private StringFieldEditor statements;

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
		Composite p = getFieldEditorParent();

		gf1 = new GroupFieldEditor("Neo4j database settings", p);
		flagurl = new BooleanFieldEditor(PrefKeys.P_USESTANDALONE,
				"Use &standalone database (Rest API):", gf1.getGroupControl(p));
		dburl = new StringFieldEditor(PrefKeys.P_DBURL, "", gf1.getGroupControl(p));
		flagdir = new BooleanFieldEditor(PrefKeys.P_USEEMBEDDED, "Use &embedded database store:",
				gf1.getGroupControl(p));
		dbdir = new DirectoryFieldEditor(PrefKeys.P_DBDIR, "", gf1.getGroupControl(p));

		gf2 = new GroupFieldEditor("Class/Method filter list (colon ';' seperated)", p);
		flagwhite = new BooleanFieldEditor(PrefKeys.P_USEWHITE, "Use &white list",
				gf2.getGroupControl(p));
		flagblack = new BooleanFieldEditor(PrefKeys.P_USEBLACK, "Use &black list",
				gf2.getGroupControl(p));
		flagprefer = new BooleanFieldEditor(PrefKeys.P_PREFERWHITE, "P&refer white list",
				gf2.getGroupControl(p));
		whitelist = new StringFieldEditor(PrefKeys.P_WHITELIST, "Whitelist:",
				gf2.getGroupControl(p));
		blacklist = new StringFieldEditor(PrefKeys.P_BLACKLIST, "Blacklist:",
				gf2.getGroupControl(p));

		gf3 = new GroupFieldEditor("Executing Cypher statements before generating", p);
		flagexec = new BooleanFieldEditor(PrefKeys.P_USEPREEXEC, "Enable e&xecuting statements",
				gf3.getGroupControl(p));
		statements = new StringFieldEditor(PrefKeys.P_PREEXEC, "Statements:",
				gf3.getGroupControl(p));

		gf1.addField(flagurl);
		gf1.addField(dburl);
		gf1.addField(flagdir);
		gf1.addField(dbdir);
		addField(gf1);

		gf2.addField(flagwhite);
		gf2.addField(flagblack);
		gf2.addField(flagprefer);
		gf2.addField(whitelist);
		gf2.addField(blacklist);
		addField(gf2);

		gf3.addField(flagexec);
		gf3.addField(statements);
		addField(gf3);
	}

	/**
	 * Overrider method initialize.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#initialize()
	 */
	@Override
	protected void initialize() {
		super.initialize();
		Composite p = getFieldEditorParent();
		boolean value1 = flagurl.getBooleanValue();
		dburl.setEnabled(value1, gf1.getGroupControl(p));
		boolean value2 = flagurl.getBooleanValue();
		dbdir.setEnabled(!value2, gf1.getGroupControl(p));
		boolean value3 = flagwhite.getBooleanValue();
		whitelist.setEnabled(value3, gf2.getGroupControl(p));
		boolean value4 = flagblack.getBooleanValue();
		blacklist.setEnabled(value4, gf2.getGroupControl(p));
		boolean value5 = flagexec.getBooleanValue();
		statements.setEnabled(value5, gf3.getGroupControl(p));
	}

	/**
	 * Overrider method propertyChange.
	 * 
	 * @author manbaum
	 * @since Oct 23, 2014
	 * @param event
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		FieldEditor f = (FieldEditor)event.getSource();
		Composite p = getFieldEditorParent();
		if (event.getProperty() == FieldEditor.VALUE) {
			if (f.getPreferenceName().equals(PrefKeys.P_USESTANDALONE)) {
				boolean value = (Boolean)event.getNewValue();
				dburl.setEnabled(value, gf1.getGroupControl(p));
			} else if (f.getPreferenceName().equals(PrefKeys.P_USEEMBEDDED)) {
				boolean value = (Boolean)event.getNewValue();
				dbdir.setEnabled(!value, gf1.getGroupControl(p));
			} else if (f.getPreferenceName().equals(PrefKeys.P_USEWHITE)) {
				boolean value = (Boolean)event.getNewValue();
				whitelist.setEnabled(value, gf2.getGroupControl(p));
			} else if (f.getPreferenceName().equals(PrefKeys.P_USEBLACK)) {
				boolean value = (Boolean)event.getNewValue();
				blacklist.setEnabled(value, gf2.getGroupControl(p));
			} else if (f.getPreferenceName().equals(PrefKeys.P_USEPREEXEC)) {
				boolean value = (Boolean)event.getNewValue();
				statements.setEnabled(value, gf3.getGroupControl(p));
			}
		}
	}

	/**
	 * Overrider method checkState.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#checkState()
	 */
	@Override
	protected void checkState() {
		super.checkState();
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