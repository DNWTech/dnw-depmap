/**
 * !(#) Builder.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.dnw.depmap;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;

import com.dnw.depmap.builder.DeltaVisitorDelegator;
import com.dnw.depmap.builder.ResourceVisitorDelegator;

/**
 * <p>
 * This class provides the infrastructure for defining a builder and fulfills
 * the contract specified by the
 * <code>org.eclipse.core.resources.builders</code> standard extension point.
 * </p>
 * <p>
 * On creation, the <code>setInitializationData</code> method is called with any
 * parameter data specified in the declaring plug-in's manifest.
 * </p>
 * 
 * @author manbaum
 * @since Sep 30, 2014
 */
public class Builder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.dnw.depmap.builder";

	private final ResourceVisitorDelegator dispatcher;
	private final DeltaVisitorDelegator deltaVisitor;

	public Builder() {
		dispatcher = new ResourceVisitorDelegator();
		deltaVisitor = new DeltaVisitorDelegator(dispatcher);
	}

	/**
	 * Sets initialization data for this builder.
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param config
	 *            the configuration element used to trigger this execution. It
	 *            can be queried by the executable extension for specific
	 *            configuration properties.
	 * @param propertyName
	 *            the name of an attribute of the configuration element used on
	 *            the createExecutableExtension(String) call. This argument can
	 *            be used in the cases where a single configuration element is
	 *            used to define multiple executable extensions.
	 * @param data
	 *            adapter data in the form of a String, a Hashtable, or null.
	 * @throws CoreException
	 *             if fails.
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		super.setInitializationData(config, propertyName, data);
	}

	/**
	 * <p>
	 * Runs this builder in the specified manner. Subclasses should implement
	 * this method to do the processing they require.
	 * </p>
	 * <p>
	 * If the build kind is <code>INCREMENTAL_BUILD</code> or
	 * <code>AUTO_BUILD</code>, the <code>getDelta</code> method can be used
	 * during the invocation of this method to obtain information about what
	 * changes have occurred since the last invocation of this method. Any
	 * resource delta acquired is valid only for the duration of the invocation
	 * of this method. A <code>FULL_BUILD</code> has no associated build delta.
	 * </p>
	 * <p>
	 * After completing a build, this builder may return a list of projects for
	 * which it requires a resource delta the next time it is run. This
	 * builder's project is implicitly included and need not be specified. The
	 * build mechanism will attempt to maintain and compute deltas relative to
	 * the identified projects when asked the next time this builder is run.
	 * Builders must re-specify the list of interesting projects every time they
	 * are run as this is not carried forward beyond the next build. Projects
	 * mentioned in return value but which do not exist will be ignored and no
	 * delta will be made available for them.
	 * </p>
	 * <p>
	 * This method is long-running; progress and cancellation are provided by
	 * the given progress monitor. All builders should report their progress and
	 * honor cancel requests in a timely manner. Cancelation requests should be
	 * propagated to the caller by throwing
	 * <code>OperationCanceledException</code>.
	 * </p>
	 * <p>
	 * All builders should try to be robust in the face of trouble. In
	 * situations where failing the build by throwing <code>CoreException</code>
	 * is the only option, a builder has a choice of how best to communicate the
	 * problem back to the caller. One option is to use the
	 * <code>IResourceStatus.BUILD_FAILED</code> status code along with a
	 * suitable message; another is to use a <code>MultiStatus</code> containing
	 * finer-grained problem diagnoses.
	 * </p>
	 * 
	 * @author manbaum
	 * @since Sep 30, 2014
	 * 
	 * @param kind
	 *            the kind of build being requested. Valid values are:<br/>
	 *            <ul>
	 *            <li><code>FULL_BUILD</code> - indicates a full build.</li>
	 *            <li><code>INCREMENTAL_BUILD</code>- indicates an incremental
	 *            build.</li>
	 *            <li><code>AUTO_BUILD</code> - indicates an automatically
	 *            triggered incremental build (autobuilding on).</li>
	 *            </ul>
	 * @param args
	 *            a table of builder-specific arguments keyed by argument name
	 *            (key type: <code>String</code>, value type:
	 *            <code>String</code>); <code>null</code> is equivalent to an
	 *            empty map.
	 * @param monitor
	 *            a progress monitor, or <code>null</code> if progress reporting
	 *            and cancellation are not desired.
	 * @return the list of projects for which this builder would like deltas the
	 *         next time it is run or <code>null</code> if none.
	 * @throws CoreException
	 *             if this build fails.
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	/**
	 * Called when full build has been required.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @param monitor
	 *            a progress monitor, or <code>null</code> if progress reporting
	 *            and cancellation are not desired.
	 * @throws CoreException
	 */
	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		getProject().accept(new ResourceVisitorDelegator());
	}

	/**
	 * Called when incremental build has been required.
	 * 
	 * @author manbaum
	 * @since Oct 1, 2014
	 * 
	 * @param delta
	 *            the resource delta for the project.
	 * @param monitor
	 *            a progress monitor, or <code>null</code> if progress reporting
	 *            and cancellation are not desired.
	 * @throws CoreException
	 */
	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		delta.accept(deltaVisitor);
	}
}
