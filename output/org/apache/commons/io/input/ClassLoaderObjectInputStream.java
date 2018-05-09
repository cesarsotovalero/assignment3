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
package org.apache.commons.io.input;


/**
 * A special ObjectInputStream that loads a class based on a specified
 * <code>ClassLoader</code> rather than the system default.
 * <p>
 * This is useful in dynamic container environments.
 *
 * @since 1.1
 */
public class ClassLoaderObjectInputStream extends java.io.ObjectInputStream {
    /**
     * The class loader to use.
     */
    private final java.lang.ClassLoader classLoader;

    /**
     * Constructs a new ClassLoaderObjectInputStream.
     *
     * @param classLoader
     * 		the ClassLoader from which classes should be loaded
     * @param inputStream
     * 		the InputStream to work on
     * @throws IOException
     * 		in case of an I/O error
     * @throws StreamCorruptedException
     * 		if the stream is corrupted
     */
    public ClassLoaderObjectInputStream(final java.lang.ClassLoader classLoader, final java.io.InputStream inputStream) throws java.io.IOException, java.io.StreamCorruptedException {
        super(inputStream);
        this.classLoader = classLoader;
    }

    /**
     * Resolve a class specified by the descriptor using the
     * specified ClassLoader or the super ClassLoader.
     *
     * @param objectStreamClass
     * 		descriptor of the class
     * @return the Class object described by the ObjectStreamClass
     * @throws IOException
     * 		in case of an I/O error
     * @throws ClassNotFoundException
     * 		if the Class cannot be found
     */
    @java.lang.Override
    protected java.lang.Class<?> resolveClass(final java.io.ObjectStreamClass objectStreamClass) throws java.io.IOException, java.lang.ClassNotFoundException {
        try {
            return java.lang.Class.forName(objectStreamClass.getName(), false, classLoader);
        } catch (final java.lang.ClassNotFoundException cnfe) {
            // delegate to super class loader which can resolve primitives
            return super.resolveClass(objectStreamClass);
        }
    }

    /**
     * Create a proxy class that implements the specified interfaces using
     * the specified ClassLoader or the super ClassLoader.
     *
     * @param interfaces
     * 		the interfaces to implement
     * @return a proxy class implementing the interfaces
     * @throws IOException
     * 		in case of an I/O error
     * @throws ClassNotFoundException
     * 		if the Class cannot be found
     * @see java.io.ObjectInputStream#resolveProxyClass(java.lang.String[])
     * @since 2.1
     */
    @java.lang.Override
    protected java.lang.Class<?> resolveProxyClass(final java.lang.String[] interfaces) throws java.io.IOException, java.lang.ClassNotFoundException {
        final java.lang.Class<?>[] interfaceClasses = new java.lang.Class[interfaces.length];
        for (int i = 0; i < (interfaces.length); i++) {
            interfaceClasses[i] = java.lang.Class.forName(interfaces[i], false, classLoader);
        }
        try {
            return java.lang.reflect.Proxy.getProxyClass(classLoader, interfaceClasses);
        } catch (final java.lang.IllegalArgumentException e) {
            return super.resolveProxyClass(interfaces);
        }
    }
}

