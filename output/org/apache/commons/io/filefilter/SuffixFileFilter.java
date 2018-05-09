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
 * Filters files based on the suffix (what the filename ends with).
 * This is used in retrieving all the files of a particular type.
 * <p>
 * For example, to retrieve and print all <code>*.java</code> files
 * in the current directory:
 *
 * <pre>
 * File dir = new File(".");
 * String[] files = dir.list( new SuffixFileFilter(".java") );
 * for (int i = 0; i &lt; files.length; i++) {
 *     System.out.println(files[i]);
 * }
 * </pre>
 *
 * @since 1.0
 * @version $Id$
 * @see FileFilterUtils#suffixFileFilter(String)
 * @see FileFilterUtils#suffixFileFilter(String, IOCase)
 */
/* method "toString" was removed from this class because it was not covered by the test suite */
public class SuffixFileFilter extends org.apache.commons.io.filefilter.AbstractFileFilter implements java.io.Serializable {
    private static final long serialVersionUID = -3389157631240246157L;

    /**
     * The filename suffixes to search for
     */
    private final java.lang.String[] suffixes;

    /**
     * Whether the comparison is case sensitive.
     */
    private final org.apache.commons.io.IOCase caseSensitivity;

    /**
     * Constructs a new Suffix file filter for a single extension.
     *
     * @param suffix
     * 		the suffix to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the suffix is null
     */
    public SuffixFileFilter(final java.lang.String suffix) {
        this(suffix, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Suffix file filter for a single extension
     * specifying case-sensitivity.
     *
     * @param suffix
     * 		the suffix to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the suffix is null
     * @since 1.4
     */
    public SuffixFileFilter(final java.lang.String suffix, final org.apache.commons.io.IOCase caseSensitivity) {
        if (suffix == null) {
            throw new java.lang.IllegalArgumentException("The suffix must not be null");
        }
        this.suffixes = new java.lang.String[]{ suffix };
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Constructs a new Suffix file filter for an array of suffixes.
     * <p>
     * The array is not cloned, so could be changed after constructing the
     * instance. This would be inadvisable however.
     *
     * @param suffixes
     * 		the suffixes to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the suffix array is null
     */
    public SuffixFileFilter(final java.lang.String[] suffixes) {
        this(suffixes, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Suffix file filter for an array of suffixes
     * specifying case-sensitivity.
     *
     * @param suffixes
     * 		the suffixes to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the suffix array is null
     * @since 1.4
     */
    public SuffixFileFilter(final java.lang.String[] suffixes, final org.apache.commons.io.IOCase caseSensitivity) {
        if (suffixes == null) {
            throw new java.lang.IllegalArgumentException("The array of suffixes must not be null");
        }
        this.suffixes = new java.lang.String[suffixes.length];
        java.lang.System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Constructs a new Suffix file filter for a list of suffixes.
     *
     * @param suffixes
     * 		the suffixes to allow, must not be null
     * @throws IllegalArgumentException
     * 		if the suffix list is null
     * @throws ClassCastException
     * 		if the list does not contain Strings
     */
    public SuffixFileFilter(final java.util.List<java.lang.String> suffixes) {
        this(suffixes, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Constructs a new Suffix file filter for a list of suffixes
     * specifying case-sensitivity.
     *
     * @param suffixes
     * 		the suffixes to allow, must not be null
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @throws IllegalArgumentException
     * 		if the suffix list is null
     * @throws ClassCastException
     * 		if the list does not contain Strings
     * @since 1.4
     */
    public SuffixFileFilter(final java.util.List<java.lang.String> suffixes, final org.apache.commons.io.IOCase caseSensitivity) {
        if (suffixes == null) {
            throw new java.lang.IllegalArgumentException("The list of suffixes must not be null");
        }
        this.suffixes = suffixes.toArray(new java.lang.String[suffixes.size()]);
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Checks to see if the filename ends with the suffix.
     *
     * @param file
     * 		the File to check
     * @return true if the filename ends with one of our suffixes
     */
    @java.lang.Override
    public boolean accept(final java.io.File file) {
        final java.lang.String name = file.getName();
        for (final java.lang.String suffix : this.suffixes) {
            if (caseSensitivity.checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the filename ends with the suffix.
     *
     * @param file
     * 		the File directory
     * @param name
     * 		the filename
     * @return true if the filename ends with one of our suffixes
     */
    @java.lang.Override
    public boolean accept(final java.io.File file, final java.lang.String name) {
        for (final java.lang.String suffix : this.suffixes) {
            if (caseSensitivity.checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }
}

