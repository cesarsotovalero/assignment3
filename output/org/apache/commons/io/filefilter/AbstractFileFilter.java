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
 * An abstract class which implements the Java FileFilter and FilenameFilter
 * interfaces via the IOFileFilter interface.
 * <p>
 * Note that a subclass <b>must</b> override one of the accept methods,
 * otherwise your class will infinitely loop.
 *
 * @since 1.0
 * @version $Id$
 */
/* method "accept" was removed from this class because it was not covered by the test suite */
/* method "accept" was removed from this class because it was not covered by the test suite */
public abstract class AbstractFileFilter implements org.apache.commons.io.filefilter.IOFileFilter {
    /**
     * Provide a String representation of this file filter.
     *
     * @return a String representation
     */
    @java.lang.Override
    public java.lang.String toString() {
        return getClass().getSimpleName();
    }
}

