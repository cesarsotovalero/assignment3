/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.filefilter;


/**
 * Filters filenames for a certain prefix.
 * <p>
 * For example, to print all files and directories in the
 * current directory whose name starts with <code>Test</code>:
 *
 * <pre>
 * File dir = new File(".");
 * String[] files = dir.list( new PrefixFileFilter("Test") );
 * for ( int i = 0; i &lt; files.length; i++ ) {
 *     System.out.println(files[i]);
 * }
 * </pre>
 *
 * @since 1.0
 * @version $Id$
 * @see FileFilterUtils#prefixFileFilter(String)
 * @see FileFilterUtils#prefixFileFilter(String, IOCase)
 */
public class PrefixFileFilter extends org.apache.commons.io.filefilter.AbstractFileFilter implements java.io.Serializable {
    private static final long serialVersionUID = 8533897440809599867L;

    /**
     * The filename prefixes to search for
     */
    private final java.lang.String[] prefixes;

    /**
     * Whether the comparison is case sensitive.
     */
    private final org.apache.commons.io.IOCase caseSensitivity;

    /**
     * Constructs a new Prefix file filter for a single prefix.
     *
     * @param prefix
     * 		the prefix to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the prefix is null
     */
    public PrefixFileFilter(final java.lang.String prefix) {
        this(prefix, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Prefix file filter for a single prefix
     * specifying case-sensitivity.
     *
     * @param prefix
     * 		the prefix to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the prefix is null
     * @since 1.4
     */
    public PrefixFileFilter(final java.lang.String prefix, final org.apache.commons.io.IOCase caseSensitivity) {
        if (prefix == null) {
            throw new java.lang.IllegalArgumentException("The prefix must not be null");
        }
        this.prefixes = new java.lang.String[]{ prefix };
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Constructs a new Prefix file filter for any of an array of prefixes.
     * <p>
     * The array is not cloned, so could be changed after constructing the
     * instance. This would be inadvisable however.
     *
     * @param prefixes
     * 		the prefixes to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the prefix array is null
     */
    public PrefixFileFilter(final java.lang.String[] prefixes) {
        this(prefixes, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Prefix file filter for any of an array of prefixes
     * specifying case-sensitivity.
     *
     * @param prefixes
     * 		the prefixes to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the prefix is null
     * @since 1.4
     */
    public PrefixFileFilter(final java.lang.String[] prefixes, final org.apache.commons.io.IOCase caseSensitivity) {
        if (prefixes == null) {
            throw new java.lang.IllegalArgumentException("The array of prefixes must not be null");
        }
        this.prefixes = new java.lang.String[prefixes.length];
        java.lang.System.arraycopy(prefixes, 0, this.prefixes, 0, prefixes.length);
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Constructs a new Prefix file filter for a list of prefixes.
     *
     * @param prefixes
     * 		the prefixes to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the prefix list is null
     * @throws ClassCastException
     * 		if the list does not contain Strings
     */
    public PrefixFileFilter(final java.util.List<java.lang.String> prefixes) {
        this(prefixes, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Prefix file filter for a list of prefixes
     * specifying case-sensitivity.
     *
     * @param prefixes
     * 		the prefixes to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the prefix list is null
     * @throws ClassCastException
     * 		if the list does not contain Strings
     * @since 1.4
     */
    public PrefixFileFilter(final java.util.List<java.lang.String> prefixes, final org.apache.commons.io.IOCase caseSensitivity) {
        if (prefixes == null) {
            throw new java.lang.IllegalArgumentException("The list of prefixes must not be null");
        }
        this.prefixes = prefixes.toArray(new java.lang.String[prefixes.size()]);
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Checks to see if the filename starts with the prefix.
     *
     * @param file
     * 		the File to check
     * @return true if the filename starts with one of our prefixes
     */
    @java.lang.Override
    public boolean accept(final java.io.File file) {
        final java.lang.String name = file.getName();
        for (final java.lang.String prefix : this.prefixes) {
            if (caseSensitivity.checkStartsWith(name, prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the filename starts with the prefix.
     *
     * @param file
     * 		the File directory
     * @param name
     * 		the filename
     * @return true if the filename starts with one of our prefixes
     */
    @java.lang.Override
    public boolean accept(final java.io.File file, final java.lang.String name) {
        for (final java.lang.String prefix : prefixes) {
            if (caseSensitivity.checkStartsWith(name, prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Provide a String representation of this file filter.
     *
     * @return a String representation
     */
    @java.lang.Override
    public java.lang.String toString() {
        final java.lang.StringBuilder buffer = new java.lang.StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if ((prefixes) != null) {
            for (int i = 0; i < (prefixes.length); i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(prefixes[i]);
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}

