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
 * Broken input stream. This stream always throws an {@link IOException} from
 * all the {@link InputStream} methods where the exception is declared.
 * <p>
 * This class is mostly useful for testing error handling in code that uses an
 * input stream.
 *
 * @since 2.0
 */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
public class BrokenInputStream extends java.io.InputStream {
    /**
     * The exception that is thrown by all methods of this class.
     */
    private final java.io.IOException exception;

    /**
     * Creates a new stream that always throws the given exception.
     *
     * @param exception
     * 		the exception to be thrown
     */
    public BrokenInputStream(final java.io.IOException exception) {
        this.exception = exception;
    }

    /**
     * Creates a new stream that always throws an {@link IOException}
     */
    public BrokenInputStream() {
        this(new java.io.IOException("Broken input stream"));
    }

    /**
     * Throws the configured exception.
     *
     * @return nothing
     * @throws IOException
     * 		always thrown
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        throw exception;
    }

    /**
     * Throws the configured exception.
     *
     * @return nothing
     * @throws IOException
     * 		always thrown
     */
    @java.lang.Override
    public int available() throws java.io.IOException {
        throw exception;
    }

    /**
     * Throws the configured exception.
     *
     * @throws IOException
     * 		always thrown
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        throw exception;
    }
}

