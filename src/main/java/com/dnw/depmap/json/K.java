package com.dnw.depmap.json;

/**
 * Interface for conversion user-type data into JSON-compatible value.
 * 
 * @author manbaum
 * @since Oct 11, 2014
 */
public interface K<T> {

	/**
	 * Method make.
	 * 
	 * @author manbaum
	 * @since Oct 11, 2014
	 * @param value
	 * @return
	 */
	Object convert(T value);
}