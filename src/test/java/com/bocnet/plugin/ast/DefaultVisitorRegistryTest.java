package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bocnet.plugin.ast.mock.MockCompilationUnitVisitor;

public class DefaultVisitorRegistryTest {

	private final DefaultVisitorRegistry reg = new DefaultVisitorRegistry();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddRemove() {
		MockCompilationUnitVisitor v = new MockCompilationUnitVisitor();
		reg.add(CompilationUnit.class, v);
		Assert.assertSame(reg.lookup(CompilationUnit.class), v);
		Object v2 = reg.remove(CompilationUnit.class);
		Assert.assertSame(v2, v);
	}
}
