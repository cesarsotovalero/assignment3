/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.io.serialization;


/**
 * A {@link ClassNameMatcher} that matches on full class names.
 * <p>
 * This object is immutable and thread-safe.
 * </p>
 */
final class FullClassNameMatcher implements org.apache.commons.io.serialization.ClassNameMatcher {
    private final java.util.Set<java.lang.String> classesSet;

    /**
     * Constructs an object based on the specified class names.
     *
     * @param classes
     * 		a list of class names
     */
    public FullClassNameMatcher(final java.lang.String... classes) {
        classesSet = java.util.Collections.unmodifiableSet(new java.util.HashSet<>(java.util.Arrays.asList(classes)));
    }

    @java.lang.Override
    public boolean matches(final java.lang.String className) {
        return classesSet.contains(className);
    }
}

