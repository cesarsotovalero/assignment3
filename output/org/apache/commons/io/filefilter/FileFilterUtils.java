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
 * Useful utilities for working with file filters. It provides access to all
 * file filter implementations in this package so you don't have to import
 * every class you use.
 *
 * @since 1.0
 * @version $Id$
 */
/* method "filter" was removed from this class because it was not covered by the test suite */
/* method "filter" was removed from this class because it was not covered by the test suite */
/* method "filter" was removed from this class because it was not covered by the test suite */
/* method "filterList" was removed from this class because it was not covered by the test suite */
/* method "filterList" was removed from this class because it was not covered by the test suite */
/* method "filterSet" was removed from this class because it was not covered by the test suite */
/* method "filterSet" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "ageFileFilter" was removed from this class because it was not covered by the test suite */
/* method "andFileFilter" was removed from this class because it was not covered by the test suite */
/* method "asFileFilter" was removed from this class because it was not covered by the test suite */
/* method "asFileFilter" was removed from this class because it was not covered by the test suite */
/* method "falseFileFilter" was removed from this class because it was not covered by the test suite */
/* method "magicNumberFileFilter" was removed from this class because it was not covered by the test suite */
/* method "magicNumberFileFilter" was removed from this class because it was not covered by the test suite */
/* method "magicNumberFileFilter" was removed from this class because it was not covered by the test suite */
/* method "magicNumberFileFilter" was removed from this class because it was not covered by the test suite */
/* method "makeCVSAware" was removed from this class because it was not covered by the test suite */
/* method "makeDirectoryOnly" was removed from this class because it was not covered by the test suite */
/* method "makeFileOnly" was removed from this class because it was not covered by the test suite */
/* method "makeSVNAware" was removed from this class because it was not covered by the test suite */
/* method "notFileFilter" was removed from this class because it was not covered by the test suite */
/* method "orFileFilter" was removed from this class because it was not covered by the test suite */
/* method "prefixFileFilter" was removed from this class because it was not covered by the test suite */
/* method "prefixFileFilter" was removed from this class because it was not covered by the test suite */
/* method "sizeFileFilter" was removed from this class because it was not covered by the test suite */
/* method "sizeFileFilter" was removed from this class because it was not covered by the test suite */
/* method "sizeRangeFileFilter" was removed from this class because it was not covered by the test suite */
/* method "trueFileFilter" was removed from this class because it was not covered by the test suite */
public class FileFilterUtils {
    // -----------------------------------------------------------------------
    /* Constructed on demand and then cached */
    private static final org.apache.commons.io.filefilter.IOFileFilter cvsFilter = org.apache.commons.io.filefilter.FileFilterUtils.notFileFilter(org.apache.commons.io.filefilter.FileFilterUtils.and(org.apache.commons.io.filefilter.FileFilterUtils.directoryFileFilter(), org.apache.commons.io.filefilter.FileFilterUtils.nameFileFilter("CVS")));

    /* Constructed on demand and then cached */
    private static final org.apache.commons.io.filefilter.IOFileFilter svnFilter = org.apache.commons.io.filefilter.FileFilterUtils.notFileFilter(org.apache.commons.io.filefilter.FileFilterUtils.and(org.apache.commons.io.filefilter.FileFilterUtils.directoryFileFilter(), org.apache.commons.io.filefilter.FileFilterUtils.nameFileFilter(".svn")));

    /**
     * FileFilterUtils is not normally instantiated.
     */
    public FileFilterUtils() {
    }

    /**
     * Returns a filter that returns true if the filename ends with the specified text.
     *
     * @param suffix
     * 		the filename suffix
     * @return a suffix checking filter
     * @see SuffixFileFilter
     */
    public static org.apache.commons.io.filefilter.IOFileFilter suffixFileFilter(final java.lang.String suffix) {
        return new org.apache.commons.io.filefilter.SuffixFileFilter(suffix);
    }

    /**
     * Returns a filter that returns true if the filename ends with the specified text.
     *
     * @param suffix
     * 		the filename suffix
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @return a suffix checking filter
     * @see SuffixFileFilter
     * @since 2.0
     */
    public static org.apache.commons.io.filefilter.IOFileFilter suffixFileFilter(final java.lang.String suffix, final org.apache.commons.io.IOCase caseSensitivity) {
        return new org.apache.commons.io.filefilter.SuffixFileFilter(suffix, caseSensitivity);
    }

    /**
     * Returns a filter that returns true if the filename matches the specified text.
     *
     * @param name
     * 		the filename
     * @return a name checking filter
     * @see NameFileFilter
     */
    public static org.apache.commons.io.filefilter.IOFileFilter nameFileFilter(final java.lang.String name) {
        return new org.apache.commons.io.filefilter.NameFileFilter(name);
    }

    /**
     * Returns a filter that returns true if the filename matches the specified text.
     *
     * @param name
     * 		the filename
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     * @return a name checking filter
     * @see NameFileFilter
     * @since 2.0
     */
    public static org.apache.commons.io.filefilter.IOFileFilter nameFileFilter(final java.lang.String name, final org.apache.commons.io.IOCase caseSensitivity) {
        return new org.apache.commons.io.filefilter.NameFileFilter(name, caseSensitivity);
    }

    /**
     * Returns a filter that checks if the file is a directory.
     *
     * @return file filter that accepts only directories and not files
     * @see DirectoryFileFilter#DIRECTORY
     */
    public static org.apache.commons.io.filefilter.IOFileFilter directoryFileFilter() {
        return org.apache.commons.io.filefilter.DirectoryFileFilter.DIRECTORY;
    }

    /**
     * Returns a filter that checks if the file is a file (and not a directory).
     *
     * @return file filter that accepts only files and not directories
     * @see FileFileFilter#FILE
     */
    public static org.apache.commons.io.filefilter.IOFileFilter fileFileFilter() {
        return org.apache.commons.io.filefilter.FileFileFilter.FILE;
    }

    /**
     * Returns a filter that ANDs the specified filters.
     *
     * @param filters
     * 		the IOFileFilters that will be ANDed together.
     * @return a filter that ANDs the specified filters
     * @throws IllegalArgumentException
     * 		if the filters are null or contain a
     * 		null value.
     * @see AndFileFilter
     * @since 2.0
     */
    public static org.apache.commons.io.filefilter.IOFileFilter and(final org.apache.commons.io.filefilter.IOFileFilter... filters) {
        return new org.apache.commons.io.filefilter.AndFileFilter(org.apache.commons.io.filefilter.FileFilterUtils.toList(filters));
    }

    /**
     * Returns a filter that ORs the specified filters.
     *
     * @param filters
     * 		the IOFileFilters that will be ORed together.
     * @return a filter that ORs the specified filters
     * @throws IllegalArgumentException
     * 		if the filters are null or contain a
     * 		null value.
     * @see OrFileFilter
     * @since 2.0
     */
    public static org.apache.commons.io.filefilter.IOFileFilter or(final org.apache.commons.io.filefilter.IOFileFilter... filters) {
        return new org.apache.commons.io.filefilter.OrFileFilter(org.apache.commons.io.filefilter.FileFilterUtils.toList(filters));
    }

    /**
     * Create a List of file filters.
     *
     * @param filters
     * 		The file filters
     * @return The list of file filters
     * @throws IllegalArgumentException
     * 		if the filters are null or contain a
     * 		null value.
     * @since 2.0
     */
    public static java.util.List<org.apache.commons.io.filefilter.IOFileFilter> toList(final org.apache.commons.io.filefilter.IOFileFilter... filters) {
        if (filters == null) {
            throw new java.lang.IllegalArgumentException("The filters must not be null");
        }
        final java.util.List<org.apache.commons.io.filefilter.IOFileFilter> list = new java.util.ArrayList<>(filters.length);
        for (int i = 0; i < (filters.length); i++) {
            if ((filters[i]) == null) {
                throw new java.lang.IllegalArgumentException((("The filter[" + i) + "] is null"));
            }
            list.add(filters[i]);
        }
        return list;
    }
}

