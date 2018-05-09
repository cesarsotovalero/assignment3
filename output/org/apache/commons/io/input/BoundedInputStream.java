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
 * This is a stream that will only supply bytes up to a certain length - if its
 * position goes above that, it will stop.
 * <p>
 * This is useful to wrap ServletInputStreams. The ServletInputStream will block
 * if you try to read content from it that isn't there, because it doesn't know
 * whether the content hasn't arrived yet or whether the content has finished.
 * So, one of these, initialized with the Content-length sent in the
 * ServletInputStream's header, will stop it blocking, providing it's been sent
 * with a correct content length.
 *
 * @since 2.0
 */
/* method "isPropagateClose" was removed from this class because it was not covered by the test suite */
/* method "markSupported" was removed from this class because it was not covered by the test suite */
/* method "available" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "close" was removed from this class because it was not covered by the test suite */
/* method "mark" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
/* method "setPropagateClose" was removed from this class because it was not covered by the test suite */
public class BoundedInputStream extends java.io.InputStream {
    /**
     * the wrapped input stream
     */
    private final java.io.InputStream in;

    /**
     * the max length to provide
     */
    private final long max;

    /**
     * the number of bytes already returned
     */
    private long pos = 0;

    /**
     * the marked position
     */
    private long mark = org.apache.commons.io.IOUtils.EOF;

    /**
     * flag if close should be propagated
     */
    private boolean propagateClose = true;

    /**
     * Creates a new <code>BoundedInputStream</code> that wraps the given input
     * stream and limits it to a certain size.
     *
     * @param in
     * 		The wrapped input stream
     * @param size
     * 		The maximum number of bytes to return
     */
    public BoundedInputStream(final java.io.InputStream in, final long size) {
        // Some badly designed methods - eg the servlet API - overload length
        // such that "-1" means stream finished
        this.max = size;
        this.in = in;
    }

    /**
     * Creates a new <code>BoundedInputStream</code> that wraps the given input
     * stream and is unlimited.
     *
     * @param in
     * 		The wrapped input stream
     */
    public BoundedInputStream(final java.io.InputStream in) {
        this(in, org.apache.commons.io.IOUtils.EOF);
    }

    /**
     * Invokes the delegate's <code>read()</code> method if
     * the current position is less than the limit.
     *
     * @return the byte read or -1 if the end of stream or
     * the limit has been reached.
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        if (((max) >= 0) && ((pos) >= (max))) {
            return org.apache.commons.io.IOUtils.EOF;
        }
        final int result = in.read();
        (pos)++;
        return result;
    }

    /**
     * Invokes the delegate's <code>read(byte[])</code> method.
     *
     * @param b
     * 		the buffer to read the bytes into
     * @return the number of bytes read or -1 if the end of stream or
     * the limit has been reached.
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read(final byte[] b) throws java.io.IOException {
        return this.read(b, 0, b.length);
    }

    /**
     * Invokes the delegate's <code>read(byte[], int, int)</code> method.
     *
     * @param b
     * 		the buffer to read the bytes into
     * @param off
     * 		The start offset
     * @param len
     * 		The number of bytes to read
     * @return the number of bytes read or -1 if the end of stream or
     * the limit has been reached.
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read(final byte[] b, final int off, final int len) throws java.io.IOException {
        if (((max) >= 0) && ((pos) >= (max))) {
            return org.apache.commons.io.IOUtils.EOF;
        }
        final long maxRead = ((max) >= 0) ? java.lang.Math.min(len, ((max) - (pos))) : len;
        final int bytesRead = in.read(b, off, ((int) (maxRead)));
        if (bytesRead == (org.apache.commons.io.IOUtils.EOF)) {
            return org.apache.commons.io.IOUtils.EOF;
        }
        pos += bytesRead;
        return bytesRead;
    }
}

