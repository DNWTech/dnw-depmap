/**
 * !(#) Element.java
 * Copyright (c) 2015 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Aug 27, 2015.
 */
package com.dnw.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

/**
 * Class/Interface Element.
 * 
 * @author manbaum
 * @since Aug 27, 2015
 */
public final class Element {

	private final IFile file;
	public final int lineNumber;
	public final int columnNumber;
	public final Element top;
	public final Element parent;
	public final int level;

	public final String uri;
	public final String localName;
	public final String qName;
	private final Map<String, String> attributes = new LinkedHashMap<String, String>();

	private final static String KEY_ID = "id";
	private final static String KEY_NAME = "name";
	private final static String KEY_VALUE = "value";
	public final String id;
	public final String name;
	public final String value;
	private final Map<String, Element> names = new HashMap<String, Element>();
	private final Map<String, List<Element>> dups = new HashMap<String, List<Element>>();

	private final ArrayList<Element> children = new ArrayList<Element>();
	private final Map<String, List<Element>> qnames = new HashMap<String, List<Element>>();
	public final int index;

	private final StringBuffer text = new StringBuffer();

	public final String path;
	public final String fullpath;
	public final String uniquePath;

	/**
	 * Constructor of Element.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param file
	 * @param loc
	 * @param top
	 * @param parent
	 * @param level
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 */
	public Element(IFile file, Locator loc, Element top, Element parent, int level, String uri,
			String localName, String qName, Attributes attributes) {
		this.file = file;
		this.lineNumber = loc.getLineNumber();
		this.columnNumber = loc.getColumnNumber();
		this.top = top;
		this.parent = parent;
		this.level = level;

		this.uri = uri;
		this.localName = localName;
		this.qName = qName;

		// Java final 的丑陋！
		String[] r = arrangeAttributes(attributes);
		this.id = r[0];
		this.name = r[1];
		this.value = r[2];

		// Java final 的丑陋！
		this.index = parent != null ? updateParent() : 0;

		this.path = path();
		this.fullpath = level > 1 ? parent.fullpath + '/' + path : path;
		this.uniquePath = filepath() + '|' + fullpath;
	}

	/**
	 * Method arrangeAttributes.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param attributes
	 */
	private String[] arrangeAttributes(Attributes attributes) {
		String id = null;
		String name = null;
		String value = null;
		for (int i = 0; i < attributes.getLength(); i++) {
			String k = attributes.getQName(i);
			String v = attributes.getValue(i);
			this.attributes.put(k, v);
			if (k.equals(KEY_NAME)) {
				name = v;
				if (parent != null) {
					assignName(name, parent);
				}
			} else if (k.equals(KEY_ID)) {
				id = v;
				if (top != null) {
					assignName(id, top.parent);
				}
			} else if (k.equals(KEY_VALUE)) {
				value = v;
			}
		}
		return new String[] { id, name, value };
	}

	/**
	 * Method assignNameTo.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param name
	 * @param p
	 */
	private void assignName(String name, Element p) {
		if (p.names.containsKey(name)) {
			List<Element> l = p.dups.get(name);
			if (l == null) {
				l = new ArrayList<Element>();
				p.dups.put(name, l);
				l.add(p.names.get(name));
			}
			l.add(this);
		} else {
			p.names.put(name, this);
		}
	}

	/**
	 * Method updateParent.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 */
	private int updateParent() {
		parent.children.add(this);
		List<Element> l = parent.qnames.get(qName);
		if (l == null) {
			l = new ArrayList<Element>();
			parent.qnames.put(qName, l);
		}
		l.add(this);
		return l.size() - 1;
	}

	/**
	 * Method path.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @return
	 */
	private String path() {
		if (parent != null && index > 0) {
			StringBuffer sb = new StringBuffer(qName);
			sb.append('[').append(index).append(']');
			return sb.toString();
		} else {
			return qName;
		}
	}

	/**
	 * Method filepath.
	 * 
	 * @author manbaum
	 * @since Aug 29, 2015
	 * @return
	 */
	public String filepath() {
		return file.getFullPath().toPortableString();
	}

	/**
	 * Method filename.
	 * 
	 * @author manbaum
	 * @since Aug 31, 2015
	 * @return
	 */
	public String filename() {
		return file.getName();
	}

	/**
	 * Method countAttributes.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public int countAttributes() {
		return attributes.size();
	}

	/**
	 * Method attributeNames.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public Set<String> attributeNames() {
		return attributes.keySet();
	}

	/**
	 * Method getAttribute.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param qname
	 * @return
	 */
	public String getAttribute(String qname) {
		return attributes.get(qname);
	}

	/**
	 * Method attributes.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public Set<Entry<String, String>> attributeEntries() {
		return attributes.entrySet();
	}

	/**
	 * Method containsAttribute.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param qname
	 * @return
	 */
	public boolean containsAttribute(String qname) {
		return attributes.containsKey(qname);
	}

	/**
	 * Method duplicatedNames.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public Set<String> duplicatedNames() {
		return dups.keySet();
	}

	/**
	 * Method eLementListWithDuplicateName.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param name
	 * @return
	 */
	public List<Element> eLementsWithDuplicateName(String name) {
		return Collections.unmodifiableList(dups.get(name));
	}

	/**
	 * Method countChildren.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public int countChildren() {
		return children.size();
	}

	/**
	 * Method getChild.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @param index
	 * @return
	 */
	public Element getChild(int index) {
		return children.get(index);
	}

	/**
	 * Method appendText. <br/>
	 * N.B. should only be called by RegistryBasedDocumentHandler.characters().
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param s
	 */
	public void appendText(String s) {
		if (text.length() > 0) {
			text.append(' ');
		}
		text.append(s);
	}

	/**
	 * Method textLength.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public int textLength() {
		return text.length();
	}

	/**
	 * Method getText.
	 * 
	 * @author manbaum
	 * @since Aug 30, 2015
	 * @return
	 */
	public String getText() {
		return text.toString();
	}

	/**
	 * Method resolve.<br/>
	 * N.B. empty string will be resolved as this element itself.<br/>
	 * N.B. null string will be resolved as null.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param xpath
	 * @return
	 */
	public Element resolve(String xpath) {
		if (xpath == null) {
			return null;
		} else {
			xpath = xpath.trim();
			int start = 0;
			Element e = this;
			if (xpath.charAt(0) == '/') {
				start = 1;
				if (top != null) {
					e = top.parent;
				}
			}
			return e.resolve(xpath, start);
		}
	}

	/**
	 * Method resolve.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param xpath
	 * @param start
	 * @return
	 */
	private Element resolve(String xpath, int start) {
		if (start < xpath.length()) {
			int i = xpath.indexOf('/', start);
			String item = xpath.substring(start, i >= 0 ? i : xpath.length());
			Element e = resolveItem(item.trim());
			return e == null ? null : e.resolve(xpath, i >= 0 ? i + 1 : xpath.length());
		} else {
			return this;
		}
	}

	/**
	 * Method resolveItem.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param item
	 * @return
	 */
	private Element resolveItem(String item) {
		if (item.charAt(0) == '@') {
			return names.get(item.substring(1));
		} else {
			int i = 0;
			String qname = item;
			int lb = item.indexOf('[');
			if (lb >= 0) {
				int rb = item.indexOf(']', lb);
				qname = item.substring(0, lb).trim();
				String s = item.substring(lb + 1, rb >= 0 ? rb : item.length());
				try {
					i = Integer.parseInt(s.trim());
				} catch (NumberFormatException e) {
					// ignores
				}
			}
			List<Element> l = qnames.get(qname);
			if (l == null) {
				return null;
			} else {
				if (i < 0) {
					i += l.size();
				}
				return i >= 0 && i < l.size() ? l.get(i) : null;
			}
		}
	}

	/**
	 * Overrider method toString.
	 * 
	 * @author manbaum
	 * @since Aug 27, 2015
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString("");
	}

	/**
	 * Method toString.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param indent
	 * @return
	 */
	public String toString(String indent) {
		StringBuffer sb = new StringBuffer();
		toBuffer(sb, indent);
		return sb.toString();
	}

	/**
	 * Method toBuffer.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 * @param indent
	 */
	private void toBuffer(StringBuffer sb, String indent) {
		head(sb, indent);
		if (children.size() > 0) {
			info(sb);
		}
		body(sb, indent);
		tail(sb, indent);
		if (children.size() == 0) {
			info(sb);
		}
	}

	/**
	 * Method head.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @return
	 */
	public String brief() {
		return brief("");
	}

	/**
	 * Method head.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param indent
	 * @return
	 */
	public String brief(String indent) {
		StringBuffer sb = new StringBuffer();
		if (children.size() > 0) {
			head(sb, indent);
			info(sb);
		} else {
			toBuffer(sb, indent);
		}
		return sb.toString();
	}

	/**
	 * Method brief.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 */
	private void info(StringBuffer sb) {
		sb.append(" <!-- file:").append(filename()).append(" line:").append(lineNumber).append(' ')
				.append(fullpath).append(" -->");
	}

	/**
	 * Method head.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 * @param indent
	 */
	private void head(StringBuffer sb, String indent) {
		sb.append(indent).append('<').append(qName);
		attributes(sb);
		if (children.size() == 0 && text.length() == 0) {
			sb.append('/');
		}
		sb.append('>');
	}

	/**
	 * Method attributes.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 */
	private void attributes(StringBuffer sb) {
		for (Entry<String, String> e : attributes.entrySet()) {
			sb.append(' ').append(e.getKey()).append("=\"").append(e.getValue()).append('\"');
		}
	}

	/**
	 * Method body.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 * @param indent
	 */
	private void body(StringBuffer sb, String indent) {
		if (children.size() > 0) {
			for (Element e : children) {
				sb.append('\n');
				e.toBuffer(sb, indent + ' ');
			}
		} else if (text.length() > 0) {
			sb.append(text);
		}
	}

	/**
	 * Method tail.
	 * 
	 * @author manbaum
	 * @since Aug 28, 2015
	 * @param sb
	 * @param indent
	 */
	private void tail(StringBuffer sb, String indent) {
		if (children.size() > 0) {
			sb.append('\n').append(indent).append("</").append(qName).append('>');
		} else if (text.length() > 0) {
			sb.append("</").append(qName).append('>');
		}
	}
}
