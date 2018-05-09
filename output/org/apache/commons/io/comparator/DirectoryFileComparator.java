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
 * Compare two files using the {@link File#isDirectory()} method.
 * <p>
 * This comparator can be used to sort lists or arrays by directories and files.
 * <p>
 * Example of sorting a list of files/directories using the
 * {@link #DIRECTORY_COMPARATOR} singleton instance:
 * <pre>
 *       List&lt;File&gt; list = ...
 *       ((AbstractFileComparator) DirectoryFileComparator.DIRECTORY_COMPARATOR).sort(list);
 * </pre>
 * <p>
 * Example of doing a <i>reverse</i> sort of an array of files/directories using the
 * {@link #DIRECTORY_REVERSE} singleton instance:
 * <pre>
 *       File[] array = ...
 *       ((AbstractFileComparator) DirectoryFileComparator.DIRECTORY_REVERSE).sort(array);
 * </pre>
 * <p>
 *
 * @since 2.0
 */
public class DirectoryFileComparator extends org.apache.commons.io.comparator.AbstractFileComparator implements java.io.Serializable {
    private static final int TYPE_FILE = 2;

    private static final int TYPE_DIRECTORY = 1;

    private static final long serialVersionUID = 296132640160964395L;

    /**
     * Singleton default comparator instance
     */
    public static final java.util.Comparator<java.io.File> DIRECTORY_COMPARATOR = new org.apache.commons.io.comparator.DirectoryFileComparator();

    /**
     * Singleton reverse default comparator instance
     */
    public static final java.util.Comparator<java.io.File> DIRECTORY_REVERSE = new org.apache.commons.io.comparator.ReverseComparator(org.apache.commons.io.comparator.DirectoryFileComparator.DIRECTORY_COMPARATOR);

    /**
     * Compare the two files using the {@link File#isDirectory()} method.
     *
     * @param file1
     * 		The first file to compare
     * @param file2
     * 		The second file to compare
     * @return the result of calling file1's
     * {@link File#compareTo(File)} with file2 as the parameter.
     */
    @java.lang.Override
    public int compare(final java.io.File file1, final java.io.File file2) {
        return (getType(file1)) - (getType(file2));
    }

    /**
     * Convert type to numeric value.
     *
     * @param file
     * 		The file
     * @return 1 for directories and 2 for files
     */
    private int getType(final java.io.File file) {
        return file.isDirectory() ? org.apache.commons.io.comparator.DirectoryFileComparator.TYPE_DIRECTORY : org.apache.commons.io.comparator.DirectoryFileComparator.TYPE_FILE;
    }
}

