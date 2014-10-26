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
		store.setDefault(PrefKeys.P_USESTANDALONE, true);
		store.setDefault(PrefKeys.P_DBURL, "http://localhost:7474/db/data");
		store.setDefault(PrefKeys.P_USEEMBEDDED, false);
		store.setDefault(PrefKeys.P_DBDIR, "./neo4j-community-2.1.5/data/graph.db");
		store.setDefault(PrefKeys.P_WHITELIST, "com\\.dnw\\..*\n@java.lang.Object");
		store.setDefault(PrefKeys.P_BLACKLIST, "");
		store.setDefault(PrefKeys.P_PREFERWHITE, true);
		store.setDefault(PrefKeys.P_USEPREEXEC, false);
		store.setDefault(PrefKeys.P_PREEXEC, "match ()-[r]-() delete r\n" // delete all relations
				+ "match (n) delete n\n" // delete all nodes
				+ "drop index on :Type(caption)\n"
				+ "drop index on :Method(caption)\n"
				+ "drop constraint on (t:Type) ASSERT t.name is unique\n" // delete unique constraint on type names
				+ "drop constraint on (m:Method) ASSERT m.name is unique\n" // delete unique constraint on method names
				+ "create index on :Type(caption)\n"
				+ "create index on :Method(caption)\n"
				+ "create constraint on (t:Type) ASSERT t.name is unique\n" // create unique constraint on type names
				+ "create constraint on (m:Method) ASSERT m.name is unique"); // create unique constraint on method names
	}
}
