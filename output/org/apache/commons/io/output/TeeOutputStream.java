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
package org.apache.commons.io.output;


/**
 * Classic splitter of OutputStream. Named after the unix 'tee'
 * command. It allows a stream to be branched off so there
 * are now two streams.
 */
public class TeeOutputStream extends org.apache.commons.io.output.ProxyOutputStream {
    /**
     * the second OutputStream to write to
     */
    protected java.io.OutputStream branch;

    // TODO consider making this private
    /**
     * Constructs a TeeOutputStream.
     *
     * @param out
     * 		the main OutputStream
     * @param branch
     * 		the second OutputStream
     */
    public TeeOutputStream(final java.io.OutputStream out, final java.io.OutputStream branch) {
        super(out);
        this.branch = branch;
    }

    /**
     * Write the bytes to both streams.
     *
     * @param b
     * 		the bytes to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public synchronized void write(final byte[] b) throws java.io.IOException {
        super.write(b);
        this.branch.write(b);
    }

    /**
     * Write the specified bytes to both streams.
     *
     * @param b
     * 		the bytes to write
     * @param off
     * 		The start offset
     * @param len
     * 		The number of bytes to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public synchronized void write(final byte[] b, final int off, final int len) throws java.io.IOException {
        super.write(b, off, len);
        this.branch.write(b, off, len);
    }

    /**
     * Write a byte to both streams.
     *
     * @param b
     * 		the byte to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public synchronized void write(final int b) throws java.io.IOException {
        super.write(b);
        this.branch.write(b);
    }

    /**
     * Flushes both streams.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void flush() throws java.io.IOException {
        super.flush();
        this.branch.flush();
    }

    /**
     * Closes both output streams.
     *
     * If closing the main output stream throws an exception, attempt to close the branch output stream.
     *
     * If closing the main and branch output streams both throw exceptions, which exceptions is thrown by this method is
     * currently unspecified and subject to change.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        try {
            super.close();
        } finally {
            this.branch.close();
        }
    }
}

