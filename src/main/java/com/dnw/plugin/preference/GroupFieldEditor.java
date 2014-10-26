/**
 * !(#) GroupFieldEditor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 22, 2014.
 */
package com.dnw.plugin.preference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Class/Interface GroupFieldEditor.
 * 
 * @author manbaum
 * @since Oct 22, 2014
 */
public class GroupFieldEditor extends FieldEditor {

	private Group groupControl;
	private List<FieldEditor> fields;

	/**
	 * Constructor of GroupFieldEditor.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 */
	public GroupFieldEditor() {
	}

	/**
	 * Constructor of GroupFieldEditor.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	public GroupFieldEditor(String labelText, Composite parent) {
		super("", labelText, parent);
	}

	/**
	 * Method addField.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param field
	 */
	public void addField(FieldEditor field) {
		if (fields == null) {
			fields = new ArrayList<FieldEditor>();
		}
		fields.add(field);
	}

	/**
	 * Method getGroupControl.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @return
	 */
	public Group getGroupControl(Composite parent) {
		if (groupControl == null) {
			groupControl = new Group(parent, SWT.NONE);
			groupControl.setText(getLabelText());
		} else {
			checkParent(groupControl, parent);
		}
		return groupControl;
	}

	/**
	 * Overrider method adjustForNumColumns.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param numColumns
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	@Override
	protected void adjustForNumColumns(int numColumns) {
		adjustGridLayout();
	}

	/**
	 * Adjust the layout of the field editors so that they are properly aligned.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 */
	private void adjustGridLayout() {
		int numColumns = calcNumberOfColumns();
		GridLayout layout = (GridLayout)groupControl.getLayout();
		layout.numColumns = numColumns;
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.fillIntoGrid(groupControl, numColumns);
			}
		}
		layout.marginWidth = layout.marginHeight = 8;

	}

	/**
	 * Overrider method doFillIntoGrid.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param parent
	 * @param numColumns
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite,
	 *      int)
	 */
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Group group = getGroupControl(parent);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	/**
	 * Overrider method doLoad.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.load();
			}
		}
	}

	/**
	 * Overrider method doLoadDefault.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	@Override
	protected void doLoadDefault() {
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.loadDefault();
			}
		}
	}

	/**
	 * Overrider method doStore.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	@Override
	protected void doStore() {
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.store();
			}
		}
	}

	/**
	 * Overrider method getNumberOfControls.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @return
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	@Override
	public int getNumberOfControls() {
		return 1;
	}

	/**
	 * Overrider method setPage.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param dialogPage
	 * @see org.eclipse.jface.preference.FieldEditor#setPage(org.eclipse.jface.dialogs.DialogPage)
	 */
	@Override
	public void setPage(DialogPage dialogPage) {
		super.setPage(dialogPage);
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.setPage(dialogPage);
			}
		}
	}

	/**
	 * Overrider method setPreferenceStore.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param store
	 * @see org.eclipse.jface.preference.FieldEditor#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		super.setPreferenceStore(store);
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.setPreferenceStore(store);
			}
		}
	}

	/**
	 * Overrider method setPropertyChangeListener.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param listener
	 * @see org.eclipse.jface.preference.FieldEditor#setPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 */
	@Override
	public void setPropertyChangeListener(IPropertyChangeListener listener) {
		super.setPropertyChangeListener(listener);
		if (fields != null) {
			for (FieldEditor f : fields) {
				f.setPropertyChangeListener(listener);
			}
		}
	}

	/**
	 * Calculates the number of columns needed to host all field editors.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @return the number of columns
	 */
	private int calcNumberOfColumns() {
		int result = 0;
		if (fields != null) {
			for (FieldEditor f : fields) {
				result = Math.max(result, f.getNumberOfControls());
			}
		}
		return result;
	}
}
