/**
 * !(#) FileFieldEditor.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 3, 2015.
 */
package com.dnw.plugin.preference;

import java.io.File;

import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

/**
 * A field editor for a file path type preference. A standard file dialog appears when the user
 * presses the change button.
 * 
 * @author manbaum
 * @since Sep 3, 2015
 */
public class FileFieldEditor extends StringButtonFieldEditor {

	/**
	 * List of legal file extension suffixes, or <code>null</code> for system defaults.
	 */
	private String[] extensions = null;

	/**
	 * Initial path for the Browse dialog.
	 */
	private File filterPath = null;

	/**
	 * Indicates whether the path must be absolute; <code>false</code> by default.
	 */
	private boolean enforceAbsolute = false;

	/**
	 * Indicates whether the result file is used for save; <code>false</code> by default.
	 */
	private boolean fileForSave = false;

	/**
	 * Creates a new file field editor
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 */
	protected FileFieldEditor() {
	}

	/**
	 * Creates a file field editor.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param name the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param parent the parent of the field editor's control
	 */
	public FileFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, false, parent);
	}

	/**
	 * Creates a file field editor.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param name the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param fileForSave <code>true</code> if the file is used for save, and <code>false</code>
	 *            otherwise.
	 * @param parent the parent of the field editor's control
	 */
	public FileFieldEditor(String name, String labelText, boolean fileForSave, Composite parent) {
		this(name, labelText, false, fileForSave, parent);
	}

	/**
	 * Creates a file field editor.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param name the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param enforceAbsolute <code>true</code> if the file path must be absolute, and
	 *            <code>false</code> otherwise
	 * @param fileForSave <code>true</code> if the file is used for save, and <code>false</code>
	 *            otherwise.
	 * @param parent the parent of the field editor's control
	 */
	public FileFieldEditor(String name, String labelText, boolean enforceAbsolute,
			boolean fileForSave, Composite parent) {
		this(name, labelText, enforceAbsolute, fileForSave, VALIDATE_ON_FOCUS_LOST, parent);
	}

	/**
	 * Creates a file field editor.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param name the name of the preference this field editor works on
	 * @param labelText the label text of the field editor
	 * @param enforceAbsolute <code>true</code> if the file path must be absolute, and
	 *            <code>false</code> otherwise
	 * @param fileForSave <code>true</code> if the file is used for save, and <code>false</code>
	 *            otherwise.
	 * @param validationStrategy either {@link StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE} to
	 *            perform on the fly checking, or
	 *            {@link StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST} (the default) to perform
	 *            validation only after the text has been typed in
	 * @param parent the parent of the field editor's control.
	 * @see StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE
	 * @see StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST
	 */
	public FileFieldEditor(String name, String labelText, boolean enforceAbsolute,
			boolean fileForSave, int validationStrategy, Composite parent) {
		init(name, labelText);
		this.enforceAbsolute = enforceAbsolute;
		this.fileForSave = fileForSave;
		setErrorMessage(JFaceResources.getString("FileFieldEditor.errorMessage"));//$NON-NLS-1$
		setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
		setValidateStrategy(validationStrategy);
		createControl(parent);
	}

	/**
	 * Overrider method declared on StringButtonFieldEditor. Opens the file chooser dialog and
	 * returns the selected file.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @return
	 * @see org.eclipse.jface.preference.StringButtonFieldEditor#changePressed()
	 */
	protected String changePressed() {
		File f = new File(getTextControl().getText());
		if (!f.exists() && !fileForSave) {
			f = null;
		}
		File d = getFile(f);
		return d == null ? null : d.getAbsolutePath();
	}

	/**
	 * Overrider method declared on StringFieldEditor. Checks whether the text input field specifies
	 * an existing file.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @return
	 * @see org.eclipse.jface.preference.StringFieldEditor#checkState()
	 */
	protected boolean checkState() {

		String msg = null;

		String path = getTextControl().getText();
		if (path != null) {
			path = path.trim();
		} else {
			path = "";//$NON-NLS-1$
		}
		if (path.length() == 0) {
			if (!isEmptyStringAllowed()) {
				msg = getErrorMessage();
			}
		} else {
			File file = new File(path);
			if (file.isFile()) {
				if (enforceAbsolute && !file.isAbsolute()) {
					msg = JFaceResources.getString("FileFieldEditor.errorMessage2");//$NON-NLS-1$
				}
			} else if (!fileForSave) {
				msg = getErrorMessage();
			}
		}

		if (msg != null) { // error
			showErrorMessage(msg);
			return false;
		}

		if (doCheckState()) { // OK!
			clearErrorMessage();
			return true;
		}
		msg = getErrorMessage(); // subclass might have changed it in the #doCheckState()
		if (msg != null) {
			showErrorMessage(msg);
		}
		return false;
	}

	/**
	 * Helper to open the file chooser dialog.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param startingDirectory the directory to open the dialog on.
	 * @return File The File the user selected or <code>null</code> if they do not.
	 */
	private File getFile(File startingDirectory) {

		FileDialog dialog = new FileDialog(getShell(), (fileForSave ? SWT.SAVE : SWT.OPEN)
				| SWT.SHEET);
		if (startingDirectory != null) {
			dialog.setFileName(startingDirectory.getPath());
		} else if (filterPath != null) {
			dialog.setFilterPath(filterPath.getPath());
		}
		if (extensions != null) {
			dialog.setFilterExtensions(extensions);
		}
		String file = dialog.open();
		if (file != null) {
			file = file.trim();
			if (file.length() > 0) {
				return new File(file);
			}
		}

		return null;
	}

	/**
	 * Sets this file field editor's file extension filter.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param extensions a list of file extension, or <code>null</code> to set the filter to the
	 *            system's default value
	 */
	public void setFileExtensions(String[] extensions) {
		this.extensions = extensions;
	}

	/**
	 * Sets the initial path for the Browse dialog.
	 * 
	 * @author manbaum
	 * @since Sep 3, 2015
	 * @param path initial path for the Browse dialog
	 */
	public void setFilterPath(File path) {
		filterPath = path;
	}
}
