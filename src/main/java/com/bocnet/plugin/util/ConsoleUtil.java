/**
 * !(#) ConsoleUtil.java
 * Copyright (c) 2013 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Jan 31, 2013.
 */
package com.bocnet.plugin.util;

import java.text.MessageFormat;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

/**
 * Class/Interface ConsoleUtil.
 * 
 * @author manbaum
 * @since Jan 31, 2013
 * 
 */
public class ConsoleUtil {

	public static MessageConsole createConsole(IConsoleManager consoleManager,
			String name) {
		MessageConsole console = new MessageConsole(name, null);
		consoleManager.addConsoles(new IConsole[] { console });
		return console;
	}

	public static MessageConsole findConsole(IConsoleManager consoleManager,
			String name) {
		for (IConsole c : consoleManager.getConsoles()) {
			if (c.getName().equals(name))
				return (MessageConsole) c;
		}
		return null;
	}

	public static MessageConsole createConsole(String name) {
		return createConsole(ConsolePlugin.getDefault().getConsoleManager(),
				name);
	}

	public static MessageConsole findConsole(String name) {
		return findConsole(ConsolePlugin.getDefault().getConsoleManager(), name);
	}

	public static MessageConsole getConsole(String name) {
		IConsoleManager consoleManager = ConsolePlugin.getDefault()
				.getConsoleManager();
		MessageConsole c = findConsole(consoleManager, name);
		if (c == null) {
			c = createConsole(consoleManager, name);
		}
		consoleManager.showConsoleView(c);
		return c;
	}

	public static String messageOfThrowable(Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append(e.getClass().getName());
		sb.append(": ");
		sb.append(e.getMessage());
		for (StackTraceElement s : e.getStackTrace()) {
			sb.append("\n    ");
			sb.append(s.getClassName());
			sb.append(".");
			sb.append(s.getMethodName());
			sb.append("() (line ");
			sb.append(s.getLineNumber());
			sb.append(")");
		}
		return sb.toString();
	}

	public static void print(MessageConsole console, String message) {
		if (console != null) {
			console.newMessageStream().print(message);
		}
	}

	public static void println(MessageConsole console, String message) {
		if (console != null) {
			console.newMessageStream().println(message);
		}
	}

	public static void print(MessageConsole console, Throwable e) {
		if (console != null) {
			console.newMessageStream().print(messageOfThrowable(e));
		}
	}

	public static void println(MessageConsole console, Throwable e) {
		if (console != null) {
			console.newMessageStream().println(messageOfThrowable(e));
		}
	}

	public static void format(MessageConsole console, String pattern,
			Object... arguments) {
		if (console != null) {
			console.newMessageStream().print(
					MessageFormat.format(pattern, arguments));
		}
	}

	public static void formatln(MessageConsole console, String pattern,
			Object... arguments) {
		if (console != null) {
			console.newMessageStream().println(
					MessageFormat.format(pattern, arguments));
		}
	}
}
