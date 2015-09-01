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
		store.setDefault(PrefKeys.P_WHITEFILES, ".*/src/main/.*");
		store.setDefault(PrefKeys.P_BLACKFILES, ".*/\\..*");
		store.setDefault(PrefKeys.P_PREFERFILES, true);
		store.setDefault(PrefKeys.P_WHITELIST, "");
		store.setDefault(PrefKeys.P_BLACKLIST, "");
		store.setDefault(PrefKeys.P_PREFERWHITE, true);
		store.setDefault(PrefKeys.P_USEPREEXEC, true);
		store.setDefault(PrefKeys.P_PREEXEC, "match ()-[r]-() delete r\n" // delete all relations.
				+ "match (n) delete n\n" // delete all nodes.
				+ "create constraint on (t:Class) assert t.name is unique\n" // create unique constraint on type names.
				+ "create constraint on (t:Interface) assert t.name is unique\n" // create unique constraint on type names.
				+ "create constraint on (t:Type) assert t.name is unique\n" // create unique constraint on type names.
				+ "create constraint on (m:Method) assert m.name is unique\n" // create unique constraint on method names.
				+ "create constraint on (a:Annotation) assert a.name is unique\n" // create unique constraint on annotation names.
				+ "create constraint on (e:XMLRoot) assert e.path is unique\n" //
				+ "create constraint on (e:XMLTop) assert e.path is unique\n" //
				+ "create constraint on (e:XMLElement) assert e.path is unique\n" //
				+ "create constraint on (a:XMLAttribute) assert a.path is unique\n" //
				+ "create index on :Type (caption)\n" // create index on type captions.
				+ "create index on :Method (caption)\n" // create index on method captions.
				+ "create index on :Annotation (caption)\n" // create index on annotation captions.
				+ "create index on :XMLElement (qname)\n" //
				+ "create index on :XMLElement (top)\n" //
				+ "create index on :XMLElement (parent)\n" //
				+ "create index on :XMLElement (level)\n" //
				+ "create index on :XMLElement (text)\n" //
				+ "create index on :XMLElement (id)\n" //
				+ "create index on :XMLElement (name)\n" //
				+ "create index on :XMLElement (value)\n" //
				+ "create index on :XMLElement (class)\n" //
				+ "create index on :XMLAttribute (qname)\n" //
				+ "create index on :XMLAttribute (parent)\n" //
		);
		store.setDefault(PrefKeys.P_USEPOSTEXEC, false);
		store.setDefault(PrefKeys.P_POSTEXEC, "" // nothing now
		);
		store.setDefault(PrefKeys.P_LOGVERBOSETOCON, false);
		store.setDefault(PrefKeys.P_LOGVERBOSETOFILE, false);
		store.setDefault(PrefKeys.P_LOGVERBOSEFILE, "./dnw-depmap.log");
	}
}
