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
 * A {@link java.io.FileFilter} providing conditional AND logic across a list of
 * file filters. This filter returns {@code true} if all filters in the
 * list return {@code true}. Otherwise, it returns {@code false}.
 * Checking of the file filter list stops when the first filter returns
 * {@code false}.
 *
 * @since 1.0
 * @version $Id$
 * @see FileFilterUtils#and(IOFileFilter...)
 */
/* method "removeFileFilter" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "getFileFilters" was removed from this class because it was not covered by the test suite */
/* method "addFileFilter" was removed from this class because it was not covered by the test suite */
/* method "setFileFilters" was removed from this class because it was not covered by the test suite */
public class AndFileFilter extends org.apache.commons.io.filefilter.AbstractFileFilter implements java.io.Serializable , org.apache.commons.io.filefilter.ConditionalFileFilter {
    private static final long serialVersionUID = 7215974688563965257L;

    /**
     * The list of file filters.
     */
    private final java.util.List<org.apache.commons.io.filefilter.IOFileFilter> fileFilters;

    /**
     * Constructs a new instance of <code>AndFileFilter</code>.
     *
     * @since 1.1
     */
    public AndFileFilter() {
        this.fileFilters = new java.util.ArrayList<>();
    }

    /**
     * Constructs a new instance of <code>AndFileFilter</code>
     * with the specified list of filters.
     *
     * @param fileFilters
     * 		a List of IOFileFilter instances, copied, null ignored
     * @since 1.1
     */
    public AndFileFilter(final java.util.List<org.apache.commons.io.filefilter.IOFileFilter> fileFilters) {
        if (fileFilters == null) {
            this.fileFilters = new java.util.ArrayList<>();
        }else {
            this.fileFilters = new java.util.ArrayList<>(fileFilters);
        }
    }

    /**
     * Constructs a new file filter that ANDs the result of two other filters.
     *
     * @param filter1
     * 		the first filter, must not be null
     * @param filter2
     * 		the second filter, must not be null
     * @throws IllegalArgumentException
     * 		if either filter is null
     */
    public AndFileFilter(final org.apache.commons.io.filefilter.IOFileFilter filter1, final org.apache.commons.io.filefilter.IOFileFilter filter2) {
        if ((filter1 == null) || (filter2 == null)) {
            throw new java.lang.IllegalArgumentException("The filters must not be null");
        }
        this.fileFilters = new java.util.ArrayList<>(2);
        addFileFilter(filter1);
        addFileFilter(filter2);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public boolean accept(final java.io.File file) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        for (final org.apache.commons.io.filefilter.IOFileFilter fileFilter : fileFilters) {
            if (!(fileFilter.accept(file))) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public boolean accept(final java.io.File file, final java.lang.String name) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        for (final org.apache.commons.io.filefilter.IOFileFilter fileFilter : fileFilters) {
            if (!(fileFilter.accept(file, name))) {
                return false;
            }
        }
        return true;
    }
}

