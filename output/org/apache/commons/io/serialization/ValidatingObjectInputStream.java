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
 * An <code>ObjectInputStream</code> that's restricted to deserialize
 * a limited set of classes.
 *
 * <p>
 * Various accept/reject methods allow for specifying which classes
 * can be deserialized.
 * </p>
 *
 * <p>
 * Design inspired by <a
 * href="http://www.ibm.com/developerworks/library/se-lookahead/">IBM
 * DeveloperWorks Article</a>.
 * </p>
 */
public class ValidatingObjectInputStream extends java.io.ObjectInputStream {
    private final java.util.List<org.apache.commons.io.serialization.ClassNameMatcher> acceptMatchers = new java.util.ArrayList<>();

    private final java.util.List<org.apache.commons.io.serialization.ClassNameMatcher> rejectMatchers = new java.util.ArrayList<>();

    /**
     * Constructs an object to deserialize the specified input stream.
     * At least one accept method needs to be called to specify which
     * classes can be deserialized, as by default no classes are
     * accepted.
     *
     * @param input
     * 		an input stream
     * @throws IOException
     * 		if an I/O error occurs while reading stream header
     */
    public ValidatingObjectInputStream(final java.io.InputStream input) throws java.io.IOException {
        super(input);
    }

    /**
     * Check that the classname conforms to requirements.
     *
     * @param name
     * 		The class name
     * @throws InvalidClassException
     * 		when a non-accepted class is encountered
     */
    private void validateClassName(final java.lang.String name) throws java.io.InvalidClassException {
        // Reject has precedence over accept
        for (final org.apache.commons.io.serialization.ClassNameMatcher m : rejectMatchers) {
            if (m.matches(name)) {
                invalidClassNameFound(name);
            }
        }
        boolean ok = false;
        for (final org.apache.commons.io.serialization.ClassNameMatcher m : acceptMatchers) {
            if (m.matches(name)) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            invalidClassNameFound(name);
        }
    }

    /**
     * Called to throw <code>InvalidClassException</code> if an invalid
     * class name is found during deserialization. Can be overridden, for example
     * to log those class names.
     *
     * @param className
     * 		name of the invalid class
     * @throws InvalidClassException
     * 		if the specified class is not allowed
     */
    protected void invalidClassNameFound(final java.lang.String className) throws java.io.InvalidClassException {
        throw new java.io.InvalidClassException(("Class name not accepted: " + className));
    }

    @java.lang.Override
    protected java.lang.Class<?> resolveClass(final java.io.ObjectStreamClass osc) throws java.io.IOException, java.lang.ClassNotFoundException {
        validateClassName(osc.getName());
        return super.resolveClass(osc);
    }

    /**
     * Accept the specified classes for deserialization, unless they
     * are otherwise rejected.
     *
     * @param classes
     * 		Classes to accept
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream accept(final java.lang.Class<?>... classes) {
        for (final java.lang.Class<?> c : classes) {
            acceptMatchers.add(new org.apache.commons.io.serialization.FullClassNameMatcher(c.getName()));
        }
        return this;
    }

    /**
     * Reject the specified classes for deserialization, even if they
     * are otherwise accepted.
     *
     * @param classes
     * 		Classes to reject
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream reject(final java.lang.Class<?>... classes) {
        for (final java.lang.Class<?> c : classes) {
            rejectMatchers.add(new org.apache.commons.io.serialization.FullClassNameMatcher(c.getName()));
        }
        return this;
    }

    /**
     * Accept the wildcard specified classes for deserialization,
     * unless they are otherwise rejected.
     *
     * @param patterns
     * 		Wildcard filename patterns as defined by
     * 		{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String) FilenameUtils.wildcardMatch}
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream accept(final java.lang.String... patterns) {
        for (final java.lang.String pattern : patterns) {
            acceptMatchers.add(new org.apache.commons.io.serialization.WildcardClassNameMatcher(pattern));
        }
        return this;
    }

    /**
     * Reject the wildcard specified classes for deserialization,
     * even if they are otherwise accepted.
     *
     * @param patterns
     * 		Wildcard filename patterns as defined by
     * 		{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String) FilenameUtils.wildcardMatch}
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream reject(final java.lang.String... patterns) {
        for (final java.lang.String pattern : patterns) {
            rejectMatchers.add(new org.apache.commons.io.serialization.WildcardClassNameMatcher(pattern));
        }
        return this;
    }

    /**
     * Accept class names that match the supplied pattern for
     * deserialization, unless they are otherwise rejected.
     *
     * @param pattern
     * 		standard Java regexp
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream accept(final java.util.regex.Pattern pattern) {
        acceptMatchers.add(new org.apache.commons.io.serialization.RegexpClassNameMatcher(pattern));
        return this;
    }

    /**
     * Reject class names that match the supplied pattern for
     * deserialization, even if they are otherwise accepted.
     *
     * @param pattern
     * 		standard Java regexp
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream reject(final java.util.regex.Pattern pattern) {
        rejectMatchers.add(new org.apache.commons.io.serialization.RegexpClassNameMatcher(pattern));
        return this;
    }

    /**
     * Accept class names where the supplied ClassNameMatcher matches for
     * deserialization, unless they are otherwise rejected.
     *
     * @param m
     * 		the matcher to use
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream accept(final org.apache.commons.io.serialization.ClassNameMatcher m) {
        acceptMatchers.add(m);
        return this;
    }

    /**
     * Reject class names where the supplied ClassNameMatcher matches for
     * deserialization, even if they are otherwise accepted.
     *
     * @param m
     * 		the matcher to use
     * @return this object
     */
    public org.apache.commons.io.serialization.ValidatingObjectInputStream reject(final org.apache.commons.io.serialization.ClassNameMatcher m) {
        rejectMatchers.add(m);
        return this;
    }
}

