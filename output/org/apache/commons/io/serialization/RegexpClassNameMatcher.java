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
 * A {@link ClassNameMatcher} that uses regular expressions.
 * <p>
 * This object is immutable and thread-safe.
 * </p>
 */
final class RegexpClassNameMatcher implements org.apache.commons.io.serialization.ClassNameMatcher {
    private final java.util.regex.Pattern pattern;

    // Class is thread-safe
    /**
     * Constructs an object based on the specified regular expression.
     *
     * @param regex
     * 		a regular expression for evaluating acceptable class names
     */
    public RegexpClassNameMatcher(final java.lang.String regex) {
        this(java.util.regex.Pattern.compile(regex));
    }

    /**
     * Constructs an object based on the specified pattern.
     *
     * @param pattern
     * 		a pattern for evaluating acceptable class names
     * @throws IllegalArgumentException
     * 		if <code>pattern</code> is null
     */
    public RegexpClassNameMatcher(final java.util.regex.Pattern pattern) {
        if (pattern == null) {
            throw new java.lang.IllegalArgumentException("Null pattern");
        }
        this.pattern = pattern;
    }

    @java.lang.Override
    public boolean matches(final java.lang.String className) {
        return pattern.matcher(className).matches();
    }
}

