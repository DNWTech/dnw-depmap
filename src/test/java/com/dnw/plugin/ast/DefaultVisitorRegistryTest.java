/**
 * !(#) DefaultVisitorRegistryTest.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Sep 30, 2014.
 */
package com.dnw.plugin.ast;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dnw.plugin.ast.mock.MockCompilationUnitVisitor;

/**
 * Class/Interface DefaultVisitorRegistryTest.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class DefaultVisitorRegistryTest {

	private final DefaultVisitorRegistry reg = new DefaultVisitorRegistry();

	/**
	 * Method setUp.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Method tearDown.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Method testAddRemove.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 */
	@Test
	public void testAddRemove() {
		MockCompilationUnitVisitor v = new MockCompilationUnitVisitor();
		reg.add(CompilationUnit.class, v);
		Assert.assertSame(reg.lookup(CompilationUnit.class), v);
		Object v2 = reg.remove(CompilationUnit.class);
		Assert.assertSame(v2, v);
	}
}
