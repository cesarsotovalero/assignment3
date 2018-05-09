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
 * Abstract file {@link Comparator} which provides sorting for file arrays and lists.
 *
 * @since 2.0
 */
abstract class AbstractFileComparator implements java.util.Comparator<java.io.File> {
    /**
     * Sort an array of files.
     * <p>
     * This method uses {@link Arrays#sort(Object[], Comparator)}
     * and returns the original array.
     *
     * @param files
     * 		The files to sort, may be null
     * @return The sorted array
     * @since 2.0
     */
    public java.io.File[] sort(final java.io.File... files) {
        if (files != null) {
            java.util.Arrays.sort(files, this);
        }
        return files;
    }

    /**
     * Sort a List of files.
     * <p>
     * This method uses {@link Collections#sort(List, Comparator)}
     * and returns the original list.
     *
     * @param files
     * 		The files to sort, may be null
     * @return The sorted list
     * @since 2.0
     */
    public java.util.List<java.io.File> sort(final java.util.List<java.io.File> files) {
        if (files != null) {
            java.util.Collections.sort(files, this);
        }
        return files;
    }

    /**
     * String representation of this file comparator.
     *
     * @return String representation of this file comparator
     */
    @java.lang.Override
    public java.lang.String toString() {
        return getClass().getSimpleName();
    }
}

