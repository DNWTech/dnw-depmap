/**
 * !(#) NeoWriter.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 10, 2014.
 */
package com.dnw.depmap.neo;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.dnw.json.J;
import com.dnw.json.M;
import com.dnw.neo.NeoAccessor;
import com.dnw.plugin.ast.AstUtil;
import com.dnw.plugin.xml.Element;

/**
 * Class/Interface NeoWriter.
 * 
 * @author manbaum
 * @since Oct 10, 2014
 */
public class NeoWriter {

	private final NeoAccessor accessor;

	/**
	 * Constructor of NeoWriter.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param accessor
	 */
	public NeoWriter(NeoAccessor accessor) {
		this.accessor = accessor;
	}

	public static final String CREATECLASS = "merge (t:Class:Type {name:{name}}) "
			+ "on create set t.caption={caption}, t.implements={implements}, t.extends={extends} ";
	public static final String CREATEINTERFACE = "merge (t:Interface:Type {name:{name}}) "
			+ "on create set t.caption={caption}, t.extends={extends} ";
	public static final String ADDTYPEFILE = "match (t:Type {name:{name}}) "
			+ "set t.file={file}, t.lineNumber={lineNumber} ";
	public static final String CREATEIMPLEMENT = "match (f:Type {name:{fname}}) "
			+ "match (t:Type {name:{tname}}) " + "merge (f)-[:Implements]->(t) ";
	public static final String CREATEEXTENDS = "match (f:Type {name:{fname}}) "
			+ "match (t:Type {name:{tname}}) " + "merge (f)-[:Extends]->(t) ";
	public static final String CREATEMETHOD = "merge (m:Method {name:{name}}) "
			+ "on create set m.caption={caption} ";
	public static final String ADDMETHODFILE = "match (m:Method {name:{name}}) "
			+ "set m.file={file}, m.lineNumber={lineNumber} ";
	public static final String CREATEDECLARE = "match (t:Type {name:{tname}}) "
			+ "match(m:Method {name:{mname}}) " + "merge (t)-[:Declares]->(m) ";
	public static final String CREATEINVOKE = "match (f:Method {name:{fname}}) "
			+ "match (t:Method {name:{tname}}) "
			+ "merge (f)-[:Invokes {expression:{expr}, arguments:{args}}]->(t) ";
	public static final String CREATEOVERRIDE = "match (f:Method {name:{fname}}) "
			+ "match (t:Method {name:{tname}}) " + "merge (f)-[:Overrides]-(t) ";
	public static final String CREATEANNOTATION = "merge (a:Annotation {name:{name}}) "
			+ "on create set a.caption={caption} ";
	public static final String ADDANNOTATIONFILE = "match (a:Annotation {name:{name}}) "
			+ "set a.file={file}, a.lineNumber={lineNumber} ";
	public static final String CREATETYPEHAS = "match (t:Type {name:{tname}}) "
			+ "match (a:Annotation {name:{aname}}) " + "merge (t)-[:Has]->(a) ";
	public static final String CREATEMETHODHAS = "match (m:Method {name:{mname}}) "
			+ "match (a:Annotation {name:{aname}}) " + "merge (m)-[:Has]->(a) ";

	public static final String CREATEXMLROOT = "merge (e:XMLRoot:XMLElement {path:{path}}) "
			+ "on create set e.qname={qname}, e.index={index}, "
			+ "e.top={top}, e.parent={parent}, e.level={level}, "
			+ "e.caption={qname}, e.file={file}, e.lineNumber={lineNumber} ";
	public static final String CREATEXMLTOP = "merge (e:XMLTop:XMLElement {path:{path}}) "
			+ "on create set e.qname={qname}, e.index={index}, "
			+ "e.top={top}, e.parent={parent}, e.level={level}, "
			+ "e.caption={qname}, e.file={file}, e.lineNumber={lineNumber} ";
	public static final String CREATEXMLELEMENT = "merge (e:XMLElement {path:{path}}) "
			+ "on create set e.qname={qname}, e.index={index}, "
			+ "e.top={top}, e.parent={parent}, e.level={level}, "
			+ "e.caption={qname}, e.file={file}, e.lineNumber={lineNumber} ";
	public static final String CREATEXMLCONTAIN = "match (e:XMLElement {path:{epath}}) "
			+ "match (c:XMLElement {path:{cpath}}) " + "merge (e)-[:Contains]->(c) ";
	public static final String UPDATEXMLELEMENT = "match (t:XMLElement {path:{path}}) "
			+ "set t += {props} ";
	public static final String CREATEXMLATTRIBUTE = "merge (a:XMLAttribute {path:{path}}) "
			+ "on create set a.qname={qname}, a.value={value}, "
			+ "a.parent={parent}, a.caption={caption} ";
	public static final String CREATEXMLWITH = "match (e:XMLElement {path:{epath}}) "
			+ "match (a:XMLAttribute {path:{apath}}) " + "merge (e)-[:With]->(a) ";
	public static final String UPDATEXMLATTR = "match (a:XMLAttribute {path:{path}}) "
			+ "set a += {props} ";

	static {
		J.register(ITypeBinding.class, new TypeBindingConverter());
		J.register(IMethodBinding.class, new MethodBindingConverter());
	}

	/**
	 * Method createType.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 */
	public void createType(ITypeBinding type, String filepath, int lineNumber) {
		M p = M.m().a("name", type).a("caption", AstUtil.captionOf(type));
		if (type.isInterface()) {
			p.a("extends", type.getInterfaces());
			accessor.execute(CREATEINTERFACE, p);
		} else {
			p.a("implements", type.getInterfaces()).a("extends", type.getSuperclass());
			accessor.execute(CREATECLASS, p);
		}
		if (!filepath.isEmpty()) {
			p.a("file", filepath).a("lineNumber", lineNumber);
			accessor.execute(ADDTYPEFILE, p);
		}
	}

	/**
	 * Method addFileInfo.
	 * 
	 * @author manbaum
	 * @since Oct 27, 2014
	 * @param type
	 * @param filepath
	 * @param lineNumber
	 */
	public void addTypeFileInfo(ITypeBinding type, String filepath, int lineNumber) {
		M p = M.m().a("name", type).a("file", filepath).a("lineNumber", lineNumber);
		accessor.execute(ADDTYPEFILE, p);
	}

	/**
	 * Method createImplements.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @param base
	 */
	public void createImplement(ITypeBinding type, ITypeBinding base) {
		M p = M.m().a("fname", type).a("tname", base);
		accessor.execute(CREATEIMPLEMENT, p);
	}

	/**
	 * Method createExtends.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param type
	 * @param base
	 */
	public void createExtends(ITypeBinding type, ITypeBinding base) {
		M p = M.m().a("fname", type).a("tname", base);
		accessor.execute(CREATEEXTENDS, p);
	}

	/**
	 * Method createMethod.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param method
	 */
	public void createMethod(IMethodBinding method, String filepath, int lineNumber) {
		M p = M.m().a("name", method).a("caption", AstUtil.captionOf(method));
		accessor.execute(CREATEMETHOD, p);
		if (!filepath.isEmpty()) {
			p.a("file", filepath).a("lineNumber", lineNumber);
			accessor.execute(ADDMETHODFILE, p);
		}
	}

	/**
	 * Method addFileInfo.
	 * 
	 * @author manbaum
	 * @since Oct 27, 2014
	 * @param method
	 * @param filepath
	 * @param lineNumber
	 */
	public void addMethodFileInfo(IMethodBinding method, String filepath, int lineNumber) {
		M p = M.m().a("name", method).a("file", filepath).a("lineNumber", lineNumber);
		accessor.execute(ADDMETHODFILE, p);
	}

	/**
	 * Method createDeclare.
	 * 
	 * @author manbaum
	 * @since Oct 14, 2014
	 * @param type
	 * @param method
	 */
	public void createDeclare(IMethodBinding method) {
		ITypeBinding type = method.getDeclaringClass();
		M p = M.m().a("tname", type).a("mname", method);
		accessor.execute(CREATEDECLARE, p);
	}

	/**
	 * Method createInvocation.
	 * 
	 * @author manbaum
	 * @since Oct 10, 2014
	 * @param from
	 * @param to
	 * @param expr
	 * @param args
	 */
	public void createInvocation(IMethodBinding from, IMethodBinding to, String expr,
			List<String> args) {
		M p = M.m().a("fname", from).a("tname", to).a("expr", expr).a("args", args);
		accessor.execute(CREATEINVOKE, p);
	}

	/**
	 * Method createOverride.
	 * 
	 * @author manbaum
	 * @since Oct 18, 2014
	 * @param method
	 * @param base
	 */
	public void createOverride(IMethodBinding method, IMethodBinding base) {
		M p = M.m().a("fname", method).a("tname", base);
		accessor.execute(CREATEOVERRIDE, p);
	}

	/**
	 * Method createAnnotation.
	 * 
	 * @author manbaum
	 * @since Jan 22, 2015
	 * @param type
	 * @param filepath
	 * @param lineNumber
	 */
	public void createAnnotation(ITypeBinding annotation, String filepath, int lineNumber) {
		M p = M.m().a("name", annotation).a("caption", AstUtil.captionOf(annotation));
		accessor.execute(CREATEANNOTATION, p);
		if (filepath != null) {
			p.a("file", filepath).a("lineNumber", lineNumber);
			accessor.execute(ADDANNOTATIONFILE, p);
		}
	}

	/**
	 * Method addAnnotationFileInfo.
	 * 
	 * @author manbaum
	 * @since Jan 22, 2015
	 * @param annotation
	 * @param filepath
	 * @param lineNumber
	 */
	public void addAnnotationFileInfo(ITypeBinding annotation, String filepath, int lineNumber) {
		M p = M.m().a("name", annotation).a("file", filepath).a("lineNumber", lineNumber);
		accessor.execute(ADDANNOTATIONFILE, p);
	}

	/**
	 * Method createTypeHas.
	 * 
	 * @author manbaum
	 * @since Jan 22, 2015
	 * @param type
	 * @param annotation
	 */
	public void createTypeHas(ITypeBinding type, ITypeBinding annotation) {
		M p = M.m().a("tname", type).a("aname", annotation);
		accessor.execute(CREATETYPEHAS, p);
	}

	/**
	 * Method createMethodHas.
	 * 
	 * @author manbaum
	 * @since Jan 23, 2015
	 * @param method
	 * @param annotation
	 */
	public void createMethodHas(IMethodBinding method, ITypeBinding annotation) {
		M p = M.m().a("mname", method).a("aname", annotation);
		accessor.execute(CREATEMETHODHAS, p);
	}

	/**
	 * Method createXmlElement.
	 * 
	 * @author manbaum
	 * @since Aug 29, 2015
	 * @param e
	 */
	public void createXmlElement(Element e) {
		M p = M.m().a("path", e.uniquePath).a("qname", e.qName).a("index", e.index)
				.a("top", e.top != null ? e.top.uniquePath : null)
				.a("parent", e.parent != null ? e.parent.uniquePath : null).a("level", e.level)
				.a("file", e.filepath()).a("lineNumber", e.lineNumber);
		if (e.level == 1) {
			accessor.execute(CREATEXMLTOP, p);
		} else if (e.level == 0) {
			accessor.execute(CREATEXMLROOT, p);
		} else {
			accessor.execute(CREATEXMLELEMENT, p);
		}
		for (Entry<String, String> t : e.attributeEntries()) {
			String apath = e.uniquePath + '%' + t.getKey();
			String caption = t.getValue().length() > 16 ? t.getKey() : t.getKey() + "= "
					+ t.getValue();
			M pa = M.m().a("path", apath).a("qname", t.getKey()).a("value", t.getValue())
					.a("parent", e.uniquePath).a("caption", caption);
			accessor.execute(CREATEXMLATTRIBUTE, pa);
			M pw = M.m().a("epath", e.uniquePath).a("apath", apath);
			accessor.execute(CREATEXMLWITH, pw);
		}
		if (e.parent != null) {
			M pc = M.m().a("epath", e.parent.uniquePath).a("cpath", e.uniquePath);
			accessor.execute(CREATEXMLCONTAIN, pc);
		}
	}

	/**
	 * Method updateXmlElement.
	 * 
	 * @author manbaum
	 * @since Aug 29, 2015
	 * @param e
	 */
	public void updateXmlElement(Element e, String caption) {
		M props = M.m().a("id", e.id).a("name", e.name).a("value", e.value).a("caption", caption)
				.a("childrenCount", e.countChildren()).a("attributeCount", e.countAttributes());
		if (e.containsAttribute("class")) {
			props.a("class", e.getAttribute("class"));
		}
		if (e.containsAttribute("parent")) {
			props.a("parentId", e.getAttribute("parent"));
		}
		if (e.countChildren() == 0 && e.textLength() > 0) {
			props.a("text", e.getText());
		}
		M p = M.m().a("path", e.uniquePath).a("props", props);
		accessor.execute(UPDATEXMLELEMENT, p);
	}
}

// bfw
// match (t:XMLTop)-[:Contains*]->(x{qname:'ref'}) with t,x match (n:XMLTop) where n.id=x.text
//       merge (t)-[r:Refers]->(n) on create set r.name=x.name, r.thru=x.path

// spring
// match (t:XMLTop)-[*]->(x:XMLAttribute{qname:'ref'}) with t,x match (n:XMLTop) where n.id=x.value
//       merge (t)-[r:Refers]->(n) on create set r.name=x.name, r.thru=x.path
