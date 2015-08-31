/**
 * !(#) AnalyzeDependencyJob.java
 * Copyright (c) 2014 DNW Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     DNW Technologies - initial API and implementation
 *
 * Create by manbaum since Oct 17, 2014.
 */
package com.dnw.depmap;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.dnw.depmap.neo.BindingCache;
import com.dnw.plugin.resource.FactoryBasedResourceFinder;
import com.dnw.plugin.resource.IResourceFinder;

/**
 * The job to analyzing the dependency.
 * 
 * @author manbaum
 * @since Oct 17, 2014
 */
public final class AnalyzeDependencyJob extends Job {

	private final IStructuredSelection selection;
	private volatile boolean isCanceled = false;

	/**
	 * Constructor of AnalyzeDependencyJob.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param name the display name of this job.
	 * @param selection the selection of Java elements to be analyzed.
	 */
	public AnalyzeDependencyJob(String name, IStructuredSelection selection) {
		super(name);
		this.selection = selection;
	}

	/**
	 * Traverses the selection to find out files of known types.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param selection the selection of Java elements to be analyzed.
	 * @param monitor the progress monitor.
	 * @return an <code>IResourceFinder</code> object holds the result.
	 */
	private final IResourceFinder filterSupportedResource(IStructuredSelection selection,
			IProgressMonitor monitor) {
		FactoryBasedResourceFinder finder = new FactoryBasedResourceFinder(
				Activator.getDefault().factory, monitor);
		try {
			monitor.beginTask("Finding files...", selection.size());
			for (@SuppressWarnings("rawtypes") Iterator it = selection.iterator(); it.hasNext();) {
				if (isCanceled) {
					Activator.getDefault().console
							.forceprintln("*** File finding has been canceled.");
					break;
				}
				Object element = it.next();
				try {
					if (element instanceof IResource) {
						((IResource)element).accept(finder);
					} else if (element instanceof IAdaptable) {
						IResource resource = (IResource)((IAdaptable)element)
								.getAdapter(IResource.class);
						if (resource != null)
							resource.accept(finder);
					}
				} catch (CoreException e) {
					Activator.getDefault().console.println(e);
				}
			}
		} finally {
			monitor.done();
		}
		return finder;
	}

	/**
	 * Overrider method canceling.
	 * 
	 * @author manbaum
	 * @since Aug 26, 2015
	 * @see org.eclipse.core.runtime.jobs.Job#canceling()
	 */
	@Override
	protected void canceling() {
		super.canceling();
		isCanceled = true;
	}

	/**
	 * Analyzes each file in the given list to generate the dependency map.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param resources a list of files to be analyzed.
	 * @param sub the progress monitor.
	 */
	private final void visitAllResources(List<IResource> resources, SubMonitor sub) {
		int count = 0;
		for (IResource resource : resources) {
			if (isCanceled) {
				Activator.getDefault().console
						.forceprintln("*** Analysis process has been canceled, " + count
								+ " file(s) proessed.");
				break;
			}
			IResourceVisitor visitor = Activator.getDefault().factory.createVisitor(resource,
					sub.newChild(100));
			if (visitor != null) {
				try {
					++count;
					resource.accept(visitor);
				} catch (CoreException e) {
					Activator.getDefault().console.println(e);
				}
			}
		}
	}

	/**
	 * Executes this job. Returns the result of the execution.
	 * 
	 * @author manbaum
	 * @since Oct 17, 2014
	 * @param monitor the monitor to be used for reporting progress and responding to cancellation.
	 *            The monitor is never <code>null</code>.
	 * @return resulting status of the run. The result must not be <code>null</code>.
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		long beginTime = System.currentTimeMillis();
		monitor.beginTask("AnalyzeDependency", 100);
		try {
			// divides the whole progress into 100 ticks.
			SubMonitor sub = SubMonitor.convert(monitor, 100);
			// finding known resources will use 3 ticks.
			IResourceFinder finder = filterSupportedResource(selection, sub.newChild(3));
			Activator.getDefault().console.forceprintln("*** Total "
					+ finder.getSupportedList().size() + " file(s) found.");
			if (isCanceled) {
				return Status.CANCEL_STATUS;
			}
			// re-divides the remaining progress according to the number of files.
			// each file can use 100 ticks. 1 more tick used as a guard.
			sub.setWorkRemaining(finder.getSupportedList().size() * 100 + 1);
			// starts up the Neo4j database.
			Activator.getDefault().accessor.startup();
			// cleans up the database if required.
			if (Activator.getDefault().preExec) {
				for (String s : Activator.getDefault().statements) {
					Activator.getDefault().accessor.execute(s);
				}
			}
			// clears the binding cache.
			BindingCache.clear();
			// visit all resources to generate dependency map.
			visitAllResources(finder.getSupportedList(), sub);
			// shuts down the database after generating.
			Activator.getDefault().accessor.shutdown();
		} finally {
			monitor.done();
			long duration = System.currentTimeMillis() - beginTime;
			Activator.getDefault().console.forceprintln("*** Analysis process finished, total "
					+ tellDuration(duration) + " elapsed.");
		}
		return isCanceled ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}

	private final static long F_SECOND = 1000L;
	private final static long F_MINUTE = F_SECOND * 60L;
	private final static long F_HOUR = F_MINUTE * 60L;
	private final static long F_DAY = F_HOUR * 24L;
	private final static long F_MONTH = F_DAY * 30L;
	private final static long F_YEAR = F_DAY * 365L;

	/**
	 * Method tellDuration.
	 * 
	 * @author manbaum
	 * @since Aug 26, 2015
	 * @param duration
	 * @return
	 */
	private final static String tellDuration(long duration) {
		StringBuffer sb = new StringBuffer();
		DecimalFormat f = new DecimalFormat("'.'000");
		long rest = duration;
		int highest = 0;

		long year = rest / F_YEAR;
		rest -= year * F_YEAR;
		if (year > 0) {
			if (highest < 6)
				highest = 6;
			sb.append(year);
			sb.append('Y');
		}

		long month = rest / F_MONTH;
		rest -= month * F_MONTH;
		if (month > 0) {
			if (highest < 5)
				highest = 5;
			sb.append(month);
			sb.append('M');
		} else if (highest > 5) {
			sb.append("0M");
		}

		long day = rest / F_DAY;
		rest -= day * F_DAY;
		if (day > 0) {
			if (highest < 4)
				highest = 4;
			sb.append(day);
			sb.append('D');
		} else if (highest > 4) {
			sb.append("0D");
		}

		long hour = rest / F_HOUR;
		rest -= hour * F_HOUR;
		if (hour > 0) {
			if (highest < 3)
				highest = 3;
			sb.append(hour);
			sb.append('h');
		} else if (highest > 3) {
			sb.append("0h");
		}

		long minute = rest / F_MINUTE;
		rest -= minute * F_MINUTE;
		if (minute > 0) {
			if (highest < 2)
				highest = 2;
			sb.append(minute);
			sb.append('m');
		} else if (highest > 2) {
			sb.append("0m");
		}

		long second = rest / F_SECOND;
		rest -= second * F_SECOND;
		if (second > 0) {
			if (highest < 1)
				highest = 1;
			sb.append(second);
			if (highest <= 2) {
				sb.append(f.format(rest));
			}
			sb.append('s');
		} else {
			sb.append('0');
			if (highest <= 2) {
				sb.append(f.format(rest));
			}
			sb.append('s');
		}

		return sb.toString();
	}
}
