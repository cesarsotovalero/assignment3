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
 * A functional, light weight {@link Reader} that emulates
 * a reader of a specified size.
 * <p>
 * This implementation provides a light weight
 * object for testing with an {@link Reader}
 * where the contents don't matter.
 * <p>
 * One use case would be for testing the handling of
 * large {@link Reader} as it can emulate that
 * scenario without the overhead of actually processing
 * large numbers of characters - significantly speeding up
 * test execution times.
 * <p>
 * This implementation returns a space from the method that
 * reads a character and leaves the array unchanged in the read
 * methods that are passed a character array.
 * If alternative data is required the <code>processChar()</code> and
 * <code>processChars()</code> methods can be implemented to generate
 * data, for example:
 *
 * <pre>
 *  public class TestReader extends NullReader {
 *      public TestReader(int size) {
 *          super(size);
 *      }
 *      protected char processChar() {
 *          return ... // return required value here
 *      }
 *      protected void processChars(char[] chars, int offset, int length) {
 *          for (int i = offset; i &lt; length; i++) {
 *              chars[i] = ... // set array value here
 *          }
 *      }
 *  }
 * </pre>
 *
 * @since 1.3
 * @version $Id$
 */
/* method "markSupported" was removed from this class because it was not covered by the test suite */
/* method "processChar" was removed from this class because it was not covered by the test suite */
/* method "getPosition" was removed from this class because it was not covered by the test suite */
/* method "getSize" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "mark" was removed from this class because it was not covered by the test suite */
/* method "processChars" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
public class NullReader extends java.io.Reader {
    private final long size;

    private long position;

    private long mark = -1;

    private long readlimit;

    private boolean eof;

    private final boolean throwEofException;

    private final boolean markSupported;

    /**
     * Create a {@link Reader} that emulates a specified size
     * which supports marking and does not throw EOFException.
     *
     * @param size
     * 		The size of the reader to emulate.
     */
    public NullReader(final long size) {
        this(size, true, false);
    }

    /**
     * Create a {@link Reader} that emulates a specified
     * size with option settings.
     *
     * @param size
     * 		The size of the reader to emulate.
     * @param markSupported
     * 		Whether this instance will support
     * 		the <code>mark()</code> functionality.
     * @param throwEofException
     * 		Whether this implementation
     * 		will throw an {@link EOFException} or return -1 when the
     * 		end of file is reached.
     */
    public NullReader(final long size, final boolean markSupported, final boolean throwEofException) {
        this.size = size;
        this.markSupported = markSupported;
        this.throwEofException = throwEofException;
    }

    /**
     * Close this Reader - resets the internal state to
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
     * Read a character.
     *
     * @return Either The character value returned by <code>processChar()</code>
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
        return processChar();
    }

    /**
     * Read some characters into the specified array.
     *
     * @param chars
     * 		The character array to read into
     * @return The number of characters read or <code>-1</code>
     * if the end of file has been reached and
     * <code>throwEofException</code> is set to {@code false}.
     * @throws EOFException
     * 		if the end of file is reached and
     * 		<code>throwEofException</code> is set to {@code true}.
     * @throws IOException
     * 		if trying to read past the end of file.
     */
    @java.lang.Override
    public int read(final char[] chars) throws java.io.IOException {
        return read(chars, 0, chars.length);
    }

    /**
     * Read the specified number characters into an array.
     *
     * @param chars
     * 		The character array to read into.
     * @param offset
     * 		The offset to start reading characters into.
     * @param length
     * 		The number of characters to read.
     * @return The number of characters read or <code>-1</code>
     * if the end of file has been reached and
     * <code>throwEofException</code> is set to {@code false}.
     * @throws EOFException
     * 		if the end of file is reached and
     * 		<code>throwEofException</code> is set to {@code true}.
     * @throws IOException
     * 		if trying to read past the end of file.
     */
    @java.lang.Override
    public int read(final char[] chars, final int offset, final int length) throws java.io.IOException {
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
        processChars(chars, offset, returnLength);
        return returnLength;
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

