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
package org.apache.commons.io.comparator;


/**
 * Compare the <b>names</b> of two files for order (see {@link File#getName()}).
 * <p>
 * This comparator can be used to sort lists or arrays of files
 * by their name either in a case-sensitive, case-insensitive or
 * system dependent case sensitive way. A number of singleton instances
 * are provided for the various case sensitivity options (using {@link IOCase})
 * and the reverse of those options.
 * <p>
 * Example of a <i>case-sensitive</i> file name sort using the
 * {@link #NAME_COMPARATOR} singleton instance:
 * <pre>
 *       List&lt;File&gt; list = ...
 *       ((AbstractFileComparator) NameFileComparator.NAME_COMPARATOR).sort(list);
 * </pre>
 * <p>
 * Example of a <i>reverse case-insensitive</i> file name sort using the
 * {@link #NAME_INSENSITIVE_REVERSE} singleton instance:
 * <pre>
 *       File[] array = ...
 *       ((AbstractFileComparator) NameFileComparator.NAME_INSENSITIVE_REVERSE).sort(array);
 * </pre>
 * <p>
 *
 * @since 1.4
 */
/* method "toString" was removed from this class because it was not covered by the test suite */
public class NameFileComparator extends org.apache.commons.io.comparator.AbstractFileComparator implements java.io.Serializable {
    private static final long serialVersionUID = 8397947749814525798L;

    /**
     * Case-sensitive name comparator instance (see {@link IOCase#SENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> NAME_COMPARATOR = new org.apache.commons.io.comparator.NameFileComparator();

    /**
     * Reverse case-sensitive name comparator instance (see {@link IOCase#SENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> NAME_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.NameFileComparator.NAME_COMPARATOR);

    /**
     * Case-insensitive name comparator instance (see {@link IOCase#INSENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> NAME_INSENSITIVE_COMPARATOR = new org.apache.commons.io.comparator.NameFileComparator(org.apache.commons.io.IOCase.INSENSITIVE);

    /**
     * Reverse case-insensitive name comparator instance (see {@link IOCase#INSENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> NAME_INSENSITIVE_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.NameFileComparator.NAME_INSENSITIVE_COMPARATOR);

    /**
     * System sensitive name comparator instance (see {@link IOCase#SYSTEM})
     */
    public static final java.util.Comparator<java.io.File> NAME_SYSTEM_COMPARATOR = new org.apache.commons.io.comparator.NameFileComparator(org.apache.commons.io.IOCase.SYSTEM);

    /**
     * Reverse system sensitive name comparator instance (see {@link IOCase#SYSTEM})
     */
    public static final java.util.Comparator<java.io.File> NAME_SYSTEM_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.NameFileComparator.NAME_SYSTEM_COMPARATOR);

    /**
     * Whether the comparison is case sensitive.
     */
    private final org.apache.commons.io.IOCase caseSensitivity;

    /**
     * Construct a case sensitive file name comparator instance.
     */
    public NameFileComparator() {
        this.caseSensitivity = org.apache.commons.io.IOCase.SENSITIVE;
    }

    /**
     * Construct a file name comparator instance with the specified case-sensitivity.
     *
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     */
    public NameFileComparator(final org.apache.commons.io.IOCase caseSensitivity) {
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Compare the names of two files with the specified case sensitivity.
     *
     * @param file1
     * 		The first file to compare
     * @param file2
     * 		The second file to compare
     * @return a negative value if the first file's name
     * is less than the second, zero if the names are the
     * same and a positive value if the first files name
     * is greater than the second file.
     */
    @java.lang.Override
    public int compare(final java.io.File file1, final java.io.File file2) {
        return caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
    }
}

