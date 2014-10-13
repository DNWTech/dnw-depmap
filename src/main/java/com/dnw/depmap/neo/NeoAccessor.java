/**
 * !(#) NeoAccessor.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 12, 2014.
 */
package com.dnw.depmap.neo;

import com.dnw.json.M;

public interface NeoAccessor {

	void execute(String statement, M params);

	void startup();

	void shutdown();

	void configAutoShutdown();
}
