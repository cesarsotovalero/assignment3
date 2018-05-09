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
 * Compare the file name <b>extensions</b> for order
 * (see {@link FilenameUtils#getExtension(String)}).
 * <p>
 * This comparator can be used to sort lists or arrays of files
 * by their file extension either in a case-sensitive, case-insensitive or
 * system dependent case sensitive way. A number of singleton instances
 * are provided for the various case sensitivity options (using {@link IOCase})
 * and the reverse of those options.
 * <p>
 * Example of a <i>case-sensitive</i> file extension sort using the
 * {@link #EXTENSION_COMPARATOR} singleton instance:
 * <pre>
 *       List&lt;File&gt; list = ...
 *       ((AbstractFileComparator) ExtensionFileComparator.EXTENSION_COMPARATOR).sort(list);
 * </pre>
 * <p>
 * Example of a <i>reverse case-insensitive</i> file extension sort using the
 * {@link #EXTENSION_INSENSITIVE_REVERSE} singleton instance:
 * <pre>
 *       File[] array = ...
 *       ((AbstractFileComparator) ExtensionFileComparator.EXTENSION_INSENSITIVE_REVERSE).sort(array);
 * </pre>
 * <p>
 *
 * @since 1.4
 */
public class ExtensionFileComparator extends org.apache.commons.io.comparator.AbstractFileComparator implements java.io.Serializable {
    private static final long serialVersionUID = 1928235200184222815L;

    /**
     * Case-sensitive extension comparator instance (see {@link IOCase#SENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_COMPARATOR = new org.apache.commons.io.comparator.ExtensionFileComparator();

    /**
     * Reverse case-sensitive extension comparator instance (see {@link IOCase#SENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.ExtensionFileComparator.EXTENSION_COMPARATOR);

    /**
     * Case-insensitive extension comparator instance (see {@link IOCase#INSENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_INSENSITIVE_COMPARATOR = new org.apache.commons.io.comparator.ExtensionFileComparator(org.apache.commons.io.IOCase.INSENSITIVE);

    /**
     * Reverse case-insensitive extension comparator instance (see {@link IOCase#INSENSITIVE})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_INSENSITIVE_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.ExtensionFileComparator.EXTENSION_INSENSITIVE_COMPARATOR);

    /**
     * System sensitive extension comparator instance (see {@link IOCase#SYSTEM})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_SYSTEM_COMPARATOR = new org.apache.commons.io.comparator.ExtensionFileComparator(org.apache.commons.io.IOCase.SYSTEM);

    /**
     * Reverse system sensitive path comparator instance (see {@link IOCase#SYSTEM})
     */
    public static final java.util.Comparator<java.io.File> EXTENSION_SYSTEM_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.ExtensionFileComparator.EXTENSION_SYSTEM_COMPARATOR);

    /**
     * Whether the comparison is case sensitive.
     */
    private final org.apache.commons.io.IOCase caseSensitivity;

    /**
     * Construct a case sensitive file extension comparator instance.
     */
    public ExtensionFileComparator() {
        this.caseSensitivity = org.apache.commons.io.IOCase.SENSITIVE;
    }

    /**
     * Construct a file extension comparator instance with the specified case-sensitivity.
     *
     * @param caseSensitivity
     * 		how to handle case sensitivity, null means case-sensitive
     */
    public ExtensionFileComparator(final org.apache.commons.io.IOCase caseSensitivity) {
        this.caseSensitivity = (caseSensitivity == null) ? org.apache.commons.io.IOCase.SENSITIVE : caseSensitivity;
    }

    /**
     * Compare the extensions of two files the specified case sensitivity.
     *
     * @param file1
     * 		The first file to compare
     * @param file2
     * 		The second file to compare
     * @return a negative value if the first file's extension
     * is less than the second, zero if the extensions are the
     * same and a positive value if the first files extension
     * is greater than the second file.
     */
    @java.lang.Override
    public int compare(final java.io.File file1, final java.io.File file2) {
        final java.lang.String suffix1 = org.apache.commons.io.FilenameUtils.getExtension(file1.getName());
        final java.lang.String suffix2 = org.apache.commons.io.FilenameUtils.getExtension(file2.getName());
        return caseSensitivity.checkCompareTo(suffix1, suffix2);
    }

    /**
     * String representation of this file comparator.
     *
     * @return String representation of this file comparator
     */
    @java.lang.Override
    public java.lang.String toString() {
        return (((super.toString()) + "[caseSensitivity=") + (caseSensitivity)) + "]";
    }
}

