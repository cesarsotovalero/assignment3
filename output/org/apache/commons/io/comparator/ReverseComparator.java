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
 * Reverses the result of comparing two objects using
 * the delegate {@link Comparator}.
 *
 * @since 1.4
 */
class ReverseComparator extends org.apache.commons.io.comparator.AbstractFileComparator implements java.io.Serializable {
    private static final long serialVersionUID = -4808255005272229056L;

    private final java.util.Comparator<java.io.File> delegate;

    /**
     * Construct an instance with the specified delegate {@link Comparator}.
     *
     * @param delegate
     * 		The comparator to delegate to
     */
    public ReverseComparator(final java.util.Comparator<java.io.File> delegate) {
        if (delegate == null) {
            throw new java.lang.IllegalArgumentException("Delegate comparator is missing");
        }
        this.delegate = delegate;
    }

    /**
     * Compare using the delegate Comparator, but reversing the result.
     *
     * @param file1
     * 		The first file to compare
     * @param file2
     * 		The second file to compare
     * @return the result from the delegate {@link Comparator#compare(Object, Object)}
     * reversing the value (i.e. positive becomes negative and vice versa)
     */
    @java.lang.Override
    public int compare(final java.io.File file1, final java.io.File file2) {
        return delegate.compare(file2, file1);// parameters switched round
        
    }

    /**
     * String representation of this file comparator.
     *
     * @return String representation of this file comparator
     */
    @java.lang.Override
    public java.lang.String toString() {
        return (((super.toString()) + "[") + (delegate.toString())) + "]";
    }
}

