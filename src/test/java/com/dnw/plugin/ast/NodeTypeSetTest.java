/**
 * !(#) NodeTypeSetTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class/Interface NodeTypeSetTest.
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class NodeTypeSetTest {

	private final NodeTypeBitMapSet set = new NodeTypeBitMapSet();

	/**
	 * Method setUp.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
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
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Method testAddRemove_0.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testAddRemove_0() {
		Assert.assertEquals(set.contains(0), false);
		set.add(0);
		Assert.assertEquals(set.contains(0), true);
		set.remove(0);
		Assert.assertEquals(set.contains(0), false);
	}

	/**
	 * Method testAddRemove_8.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testAddRemove_8() {
		Assert.assertEquals(set.contains(8), false);
		set.add(8);
		Assert.assertEquals(set.contains(8), true);
		set.remove(8);
		Assert.assertEquals(set.contains(8), false);
	}

	/**
	 * Method testAddRemove_127.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testAddRemove_127() {
		Assert.assertEquals(set.contains(127), false);
		set.add(127);
		Assert.assertEquals(set.contains(127), true);
		set.remove(127);
		Assert.assertEquals(set.contains(127), false);
	}

	/**
	 * Method testAdd_negetive.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testAdd_negetive() {
		try {
			set.add(-3);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith("invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	/**
	 * Method testAdd_greatThan127.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testAdd_greatThan127() {
		try {
			set.add(128);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith("invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	/**
	 * Method testRemove_negetive.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testRemove_negetive() {
		try {
			set.remove(-3);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith("invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	/**
	 * Method testRemove_greaterThan127.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 */
	@Test
	public void testRemove_greaterThan127() {
		try {
			set.remove(128);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith("invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}
}
