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
 * Group field editor is a container to host a number of other editors, and use a <code>Group</code>
 * widget to hold all these editors.
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
	 * @param labelText the label text of the <code>Group</code> widget.
	 * @param parent the parent of the field editor's control.
	 */
	public GroupFieldEditor(String labelText, Composite parent) {
		super("", labelText, parent);
	}

	/**
	 * Adds the given field editor to this group.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param field the field editor.
	 */
	public void addField(FieldEditor field) {
		if (fields == null) {
			fields = new ArrayList<FieldEditor>();
		}
		fields.add(field);
	}

	/**
	 * Returns this field editor's group control.
	 * <p>
	 * The control is created if it does not yet exist.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param parent the parent.
	 * @return the group control.
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
	 * Adjusts the horizontal span of this field editor's basic controls.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param numColumns the number of columns.
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
	 * Fills this field editor's basic controls into the given parent.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param parent the composite used as a parent for the basic controls; the parent's layout must
	 *            be a <code>GridLayout</code>.
	 * @param numColumns the number of columns.
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
	 * Initializes this field editor with the preference value from the preference store.
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
	 * Initializes this field editor with the default preference value from the preference store.
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
	 * Stores the preference value from this field editor into the preference store.
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
	 * Returns the number of basic controls this field editor consists of.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @return the number of controls.
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	@Override
	public int getNumberOfControls() {
		return 1;
	}

	/**
	 * Set the page to be the receiver.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param dialogPage the dialog page.
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
	 * Sets the preference store used by this field editor.
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param store the preference store, or <code>null</code> if none.
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
	 * <p>
	 * Sets or removes the property change listener for this field editor.
	 * </p>
	 * <p>
	 * Note that field editors can support only a single listener.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Oct 22, 2014
	 * @param listener a property change listener, or <code>null</code> to remove.
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
	 * @return the number of columns.
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
