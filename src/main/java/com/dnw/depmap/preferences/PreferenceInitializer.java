/**
 * !(#) PreferenceInitializer.java
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.dnw.depmap.Activator;

/**
 * Class used to initialize default preference values.
 * 
 * @author manbaum
 * @since Oct 20, 2014
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * This method is called by the preference initializer to initialize default preference values.
	 * Clients should get the correct node for their bundle and then set the default values on it.
	 * 
	 * @author manbaum
	 * @since Oct 20, 2014
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PrefKeys.P_BOOLEAN, true);
		store.setDefault(PrefKeys.P_CHOICE, "choice2");
		store.setDefault(PrefKeys.P_STRING, "Default value");
	}

}
