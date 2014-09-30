package com.bocnet.plugin.ast;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTypeSetTest {

	private final NodeTypeSet set = new NodeTypeSet();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddRemove_0() {
		Assert.assertEquals(set.contains(0), false);
		set.add(0);
		Assert.assertEquals(set.contains(0), true);
		set.remove(0);
		Assert.assertEquals(set.contains(0), false);
	}

	@Test
	public void testAddRemove_8() {
		Assert.assertEquals(set.contains(8), false);
		set.add(8);
		Assert.assertEquals(set.contains(8), true);
		set.remove(8);
		Assert.assertEquals(set.contains(8), false);
	}

	@Test
	public void testAddRemove_127() {
		Assert.assertEquals(set.contains(127), false);
		set.add(127);
		Assert.assertEquals(set.contains(127), true);
		set.remove(127);
		Assert.assertEquals(set.contains(127), false);
	}

	@Test
	public void testAdd_negetive() {
		try {
			set.add(-3);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith(
						"invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	@Test
	public void testAdd_greatThan127() {
		try {
			set.add(128);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith(
						"invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	@Test
	public void testRemove_negetive() {
		try {
			set.remove(-3);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith(
						"invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}

	@Test
	public void testRemove_greaterThan127() {
		try {
			set.remove(128);
		} catch (Exception ex) {
			if (ex instanceof IllegalArgumentException) {
				Assert.assertTrue(ex.getMessage().startsWith(
						"invalid.ASTNode.type: "));
				return;
			}
		}
		Assert.fail();
	}
}
