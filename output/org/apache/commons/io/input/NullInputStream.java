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
 * A functional, light weight {@link InputStream} that emulates
 * a stream of a specified size.
 * <p>
 * This implementation provides a light weight
 * object for testing with an {@link InputStream}
 * where the contents don't matter.
 * <p>
 * One use case would be for testing the handling of
 * large {@link InputStream} as it can emulate that
 * scenario without the overhead of actually processing
 * large numbers of bytes - significantly speeding up
 * test execution times.
 * <p>
 * This implementation returns zero from the method that
 * reads a byte and leaves the array unchanged in the read
 * methods that are passed a byte array.
 * If alternative data is required the <code>processByte()</code> and
 * <code>processBytes()</code> methods can be implemented to generate
 * data, for example:
 *
 * <pre>
 *  public class TestInputStream extends NullInputStream {
 *      public TestInputStream(int size) {
 *          super(size);
 *      }
 *      protected int processByte() {
 *          return ... // return required value here
 *      }
 *      protected void processBytes(byte[] bytes, int offset, int length) {
 *          for (int i = offset; i &lt; length; i++) {
 *              bytes[i] = ... // set array value here
 *          }
 *      }
 *  }
 * </pre>
 *
 * @since 1.3
 * @version $Id$
 */
/* method "markSupported" was removed from this class because it was not covered by the test suite */
/* method "available" was removed from this class because it was not covered by the test suite */
/* method "processByte" was removed from this class because it was not covered by the test suite */
/* method "getPosition" was removed from this class because it was not covered by the test suite */
/* method "getSize" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "mark" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
public class NullInputStream extends java.io.InputStream {
    private final long size;

    private long position;

    private long mark = -1;

    private long readlimit;

    private boolean eof;

    private final boolean throwEofException;

    private final boolean markSupported;

    /**
     * Create an {@link InputStream} that emulates a specified size
     * which supports marking and does not throw EOFException.
     *
     * @param size
     * 		The size of the input stream to emulate.
     */
    public NullInputStream(final long size) {
        this(size, true, false);
    }

    /**
     * Create an {@link InputStream} that emulates a specified
     * size with option settings.
     *
     * @param size
     * 		The size of the input stream to emulate.
     * @param markSupported
     * 		Whether this instance will support
     * 		the <code>mark()</code> functionality.
     * @param throwEofException
     * 		Whether this implementation
     * 		will throw an {@link EOFException} or return -1 when the
     * 		end of file is reached.
     */
    public NullInputStream(final long size, final boolean markSupported, final boolean throwEofException) {
        this.size = size;
        this.markSupported = markSupported;
        this.throwEofException = throwEofException;
    }

    /**
     * Close this input stream - resets the internal state to
     * the initial values.
     *
     * @throws IOException
     * 		If an error occurs.
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        eof = false;
        position = 0;
        mark = -1;
    }

    /**
     * Read a byte.
     *
     * @return Either The byte value returned by <code>processByte()</code>
     * or <code>-1</code> if the end of file has been reached and
     * <code>throwEofException</code> is set to {@code false}.
     * @throws EOFException
     * 		if the end of file is reached and
     * 		<code>throwEofException</code> is set to {@code true}.
     * @throws IOException
     * 		if trying to read past the end of file.
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        if (eof) {
            throw new java.io.IOException("Read after end of file");
        }
        if ((position) == (size)) {
            return doEndOfFile();
        }
        (position)++;
        return processByte();
    }

    /**
     * Read some bytes into the specified array.
     *
     * @param bytes
     * 		The byte array to read into
     * @return The number of bytes read or <code>-1</code>
     * if the end of file has been reached and
     * <code>throwEofException</code> is set to {@code false}.
     * @throws EOFException
     * 		if the end of file is reached and
     * 		<code>throwEofException</code> is set to {@code true}.
     * @throws IOException
     * 		if trying to read past the end of file.
     */
    @java.lang.Override
    public int read(final byte[] bytes) throws java.io.IOException {
        return read(bytes, 0, bytes.length);
    }

    /**
     * Read the specified number bytes into an array.
     *
     * @param bytes
     * 		The byte array to read into.
     * @param offset
     * 		The offset to start reading bytes into.
     * @param length
     * 		The number of bytes to read.
     * @return The number of bytes read or <code>-1</code>
     * if the end of file has been reached and
     * <code>throwEofException</code> is set to {@code false}.
     * @throws EOFException
     * 		if the end of file is reached and
     * 		<code>throwEofException</code> is set to {@code true}.
     * @throws IOException
     * 		if trying to read past the end of file.
     */
    @java.lang.Override
    public int read(final byte[] bytes, final int offset, final int length) throws java.io.IOException {
        if (eof) {
            throw new java.io.IOException("Read after end of file");
        }
        if ((position) == (size)) {
            return doEndOfFile();
        }
        position += length;
        int returnLength = length;
        if ((position) > (size)) {
            returnLength = length - ((int) ((position) - (size)));
            position = size;
        }
        processBytes(bytes, offset, returnLength);
        return returnLength;
    }

    /**
     * Process the bytes for the <code>read(byte[], offset, length)</code>
     * method.
     * <p>
     * This implementation leaves the byte array unchanged.
     *
     * @param bytes
     * 		The byte array
     * @param offset
     * 		The offset to start at.
     * @param length
     * 		The number of bytes.
     */
    protected void processBytes(final byte[] bytes, final int offset, final int length) {
        // do nothing - overridable by subclass
    }

    /**
     * Handle End of File.
     *
     * @return <code>-1</code> if <code>throwEofException</code> is
     * set to {@code false}
     * @throws EOFException
     * 		if <code>throwEofException</code> is set
     * 		to {@code true}.
     */
    private int doEndOfFile() throws java.io.EOFException {
        eof = true;
        if (throwEofException) {
            throw new java.io.EOFException();
        }
        return org.apache.commons.io.IOUtils.EOF;
    }
}

