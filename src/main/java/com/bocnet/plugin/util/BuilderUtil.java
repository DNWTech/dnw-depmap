/**
 * !(#) BuilderUtil.java
 * Copyright (c) 2013 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Feb 1, 2013.
 */
package com.bocnet.plugin.util;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * Class/Interface BuilderUtil.
 * 
 * @author manbaum
 * @since Feb 1, 2013
 * 
 */
public class BuilderUtil {

	public static void addBuilder(IProject project, String builderId,
			boolean checkExistance) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		if (checkExistance) {
			for (ICommand c : commands) {
				if (c.getBuilderName().equals(builderId))
					return;
			}
		}
		ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 1, commands.length);
		ICommand command = description.newCommand();
		command.setBuilderName(builderId);
		newCommands[0] = command;
		description.setBuildSpec(newCommands);
		project.setDescription(description, null);
	}

	public static void addBuilder(IProject project, String builderId)
			throws CoreException {
		addBuilder(project, builderId, true);
	}

	public static void removeBuilder(IProject project, String builderId)
			throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderId)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
						commands.length - i - 1);
				description.setBuildSpec(newCommands);
				project.setDescription(description, null);
				return;
			}
		}
	}
}
