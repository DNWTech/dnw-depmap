package com.bocnet.depmap.builder;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.MessageConsole;
import org.xml.sax.SAXException;

import com.bocnet.plugin.util.ConsoleUtil;
import com.bocnet.plugin.util.MarkerUtil;

public class XmlFileVisitor implements IResourceVisitor {

	public static final String MARKER_TYPE = "com.bocnet.depmap.xmlProblem";

	private final static MessageConsole console = ConsoleUtil
			.getConsole("com.bocnet.depmap");

	public final SAXParserFactory parserFactory = SAXParserFactory
			.newInstance();

	public XmlFileVisitor() {
	}

	private void doParse(IFile file, XmlFileErrorHandler reporter) {
		try {
			parserFactory.newSAXParser().parse(file.getContents(), reporter);
		} catch (SAXException e) {
			ConsoleUtil.println(console, e);
		} catch (IOException e) {
			ConsoleUtil.println(console, e);
		} catch (ParserConfigurationException e) {
			ConsoleUtil.println(console, e);
		} catch (CoreException e) {
			ConsoleUtil.println(console, e);
		}
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param file
	 * 
	 * @see com.bocnet.plugin.util.BuilderVisitor#visit(org.eclipse.core.resources.IFile)
	 */
	public boolean visit(IResource resource) throws CoreException {
		IFile file = (IFile) resource.getAdapter(IFile.class);
		if (file != null) {
			MarkerUtil.deleteMarkers(file, MARKER_TYPE);
			XmlFileErrorHandler reporter = new XmlFileErrorHandler(file, MARKER_TYPE);
			doParse(file, reporter);
		}
		return false;
	}
}