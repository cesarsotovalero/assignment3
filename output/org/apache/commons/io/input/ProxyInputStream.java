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
 * A Proxy stream which acts as expected, that is it passes the method
 * calls on to the proxied stream and doesn't change which methods are
 * being called.
 * <p>
 * It is an alternative base class to FilterInputStream
 * to increase reusability, because FilterInputStream changes the
 * methods being called, such as read(byte[]) to read(byte[], int, int).
 * <p>
 * See the protected methods for ways in which a subclass can easily decorate
 * a stream with custom pre-, post- or error processing functionality.
 */
/* method "markSupported" was removed from this class because it was not covered by the test suite */
/* method "available" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "afterRead" was removed from this class because it was not covered by the test suite */
/* method "beforeRead" was removed from this class because it was not covered by the test suite */
/* method "handleIOException" was removed from this class because it was not covered by the test suite */
/* method "mark" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
public abstract class ProxyInputStream extends java.io.FilterInputStream {
    /**
     * Constructs a new ProxyInputStream.
     *
     * @param proxy
     * 		the InputStream to delegate to
     */
    public ProxyInputStream(final java.io.InputStream proxy) {
        super(proxy);
        // the proxy is stored in a protected superclass variable named 'in'
    }

    /**
     * Invokes the delegate's <code>close()</code> method.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        try {
            in.close();
        } catch (final java.io.IOException e) {
            handleIOException(e);
        }
    }
}

