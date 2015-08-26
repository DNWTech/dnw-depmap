/**
 * !(#) NeoDao.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 14, 2014.
 */
package com.dnw.depmap.neo;

import java.util.List;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.dnw.matcher.IFilterService;
import com.dnw.plugin.ast.AstUtil;

/**
 * Class/Interface NeoDao.
 * 
 * @author manbaum
 * @since Oct 14, 2014
 */
public class NeoDao {

	private final static String UNKNOWN_FILE = "";
	private final static int UNKNOWN_LINE = -1;

	private final NeoWriter w;
	private final IFilterService<String> filter;

	/**
	 * Constructor of NeoDao.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param accessor
	 */
	public NeoDao(NeoWriter writer, IFilterService<String> filter) {
		this.w = writer;
		this.filter = filter;
	}

	/**
	 * Getter of the field w.
	 * 
	 * @author manbaum
	 * @since Oct 24, 2014
	 * @return value of the field w.
	 */
	public NeoWriter w() {
		return w;
	}

	/**
	 * Method isBlocked.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param type
	 * @return
	 */
	private boolean isBlocked(ITypeBinding type) {
		return filter.blocks(AstUtil.nameOf(type));
	}

	/**
	 * Method isBlocked.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @return
	 */
	private boolean isBlocked(IMethodBinding method) {
		return filter.blocks(AstUtil.nameOf(method));
	}

	/**
	 * Method isCached.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param binding
	 * @return
	 */
	private boolean isCached(IBinding binding) {
		return BindingCache.contains(binding);
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public boolean createBareType(ITypeBinding type, String filepath, int linenumber) {
		if (type == null)
			return false;
		if (isBlocked(type))
			return false;
		ITypeBinding declaration = type.getTypeDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addTypeFileInfo(type, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createType(declaration, filepath, linenumber);
		return true;
	}

	/**
	 * Method internalCreateMethod.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @return
	 */
	public boolean createBareMethod(IMethodBinding method, String filepath, int linenumber) {
		if (method == null)
			return false;
		if (isBlocked(method))
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (!createBareType(type, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		IMethodBinding declaration = method.getMethodDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addMethodFileInfo(method, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createMethod(declaration, filepath, linenumber);
		w.createDeclare(declaration);
		return true;
	}

	/**
	 * Method createBareInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param from
	 * @param to
	 * @param args
	 * @return
	 */
	public boolean createBareInvocation(IMethodBinding from, IMethodBinding to, List<String> args) {
		if (!createBareMethod(from, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		if (!createBareMethod(to, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		IMethodBinding df = from.getMethodDeclaration();
		IMethodBinding dt = to.getMethodDeclaration();
		w.createInvocation(df, dt, args);
		return true;
	}

	/**
	 * Creates a type with full type hierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 */
	public boolean createType(ITypeBinding type, String filepath, int linenumber) {
		if (type == null)
			return false;
		if (isBlocked(type))
			return false;
		ITypeBinding declaration = type.getTypeDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addTypeFileInfo(type, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createType(declaration, filepath, linenumber);
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t, UNKNOWN_FILE, UNKNOWN_LINE)) {
					ITypeBinding d = t.getTypeDeclaration();
					w.createExtends(declaration, d);
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				if (createType(t, UNKNOWN_FILE, UNKNOWN_LINE)) {
					ITypeBinding d = t.getTypeDeclaration();
					w.createImplements(declaration, d);
				}
			}
			ITypeBinding t = type.getSuperclass();
			if (t != null)
				if (createType(t, UNKNOWN_FILE, UNKNOWN_LINE)) {
					ITypeBinding d = t.getTypeDeclaration();
					w.createExtends(declaration, d);
				}
		}
		return true;
	}

	/**
	 * Creates a method with its override hierarchy.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 * @return
	 */
	public boolean createMethod(IMethodBinding method, String filepath, int linenumber) {
		if (method == null)
			return false;
		if (isBlocked(method))
			return false;
		ITypeBinding type = method.getDeclaringClass();
		if (!createType(type, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		IMethodBinding declaration = method.getMethodDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addMethodFileInfo(method, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createMethod(declaration, filepath, linenumber);
		w.createDeclare(declaration);
		if (type.isInterface()) {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m)) {
						IMethodBinding d = m.getMethodDeclaration();
						if (createMethod(m, UNKNOWN_FILE, UNKNOWN_LINE))
							w.createOverride(declaration, d);
					}
				}
			}
		} else {
			for (ITypeBinding t : type.getInterfaces()) {
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m)) {
						IMethodBinding d = m.getMethodDeclaration();
						if (createMethod(m, UNKNOWN_FILE, UNKNOWN_LINE))
							w.createOverride(declaration, d);
					}
				}
			}
			ITypeBinding t = type.getSuperclass();
			if (t != null)
				for (IMethodBinding m : t.getDeclaredMethods()) {
					if (method.overrides(m)) {
						IMethodBinding d = m.getMethodDeclaration();
						if (createMethod(m, UNKNOWN_FILE, UNKNOWN_LINE))
							w.createOverride(declaration, d);
					}
				}
		}
		return true;
	}

	/**
	 * Method createInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param from
	 * @param to
	 * @param args
	 */
	public boolean createInvocation(IMethodBinding from, IMethodBinding to, List<String> args) {
		if (!createMethod(from, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		if (!createMethod(to, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		IMethodBinding df = from.getMethodDeclaration();
		IMethodBinding dt = to.getMethodDeclaration();
		w.createInvocation(df, dt, args);
		return true;
	}

	/**
	 * Method createTypeAnnotation.
	 * 
	 * @author manbaum
	 * @since Jan 22, 2015
	 * @param type
	 * @param annotation
	 * @param filepath
	 * @param linenumber
	 * @return
	 */
	public boolean createTypeAnnotation(ITypeBinding type, ITypeBinding annotation,
			String filepath, int linenumber) {
		if (!createType(type, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		if (annotation == null)
			return false;
		if (isBlocked(annotation))
			return false;
		ITypeBinding declaration = annotation.getTypeDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addAnnotationFileInfo(annotation, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createAnnotation(declaration, filepath, linenumber);
		w.createTypeHas(type, declaration);
		return true;
	}

	/**
	 * Method createMethodAnnotation.
	 * 
	 * @author manbaum
	 * @since Jan 23, 2015
	 * @param method
	 * @param annotation
	 * @param filepath
	 * @param linenumber
	 * @return
	 */
	public boolean createMethodAnnotation(IMethodBinding method, ITypeBinding annotation,
			String filepath, int linenumber) {
		if (!createMethod(method, UNKNOWN_FILE, UNKNOWN_LINE))
			return false;
		if (annotation == null)
			return false;
		if (isBlocked(annotation))
			return false;
		ITypeBinding declaration = annotation.getTypeDeclaration();
		if (isCached(declaration)) {
			if (!filepath.isEmpty()) {
				w.addAnnotationFileInfo(annotation, filepath, linenumber);
			}
			return true;
		}
		BindingCache.put(declaration, AstUtil.nameOf(declaration));
		w.createAnnotation(declaration, filepath, linenumber);
		w.createMethodHas(method, declaration);
		return true;
	}
}
