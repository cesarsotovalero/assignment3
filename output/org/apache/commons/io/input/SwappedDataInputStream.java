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
 * DataInput for systems relying on little endian data formats.
 * When read, values will be changed from little endian to big
 * endian formats for internal usage.
 * <p>
 * <b>Origin of code: </b>Avalon Excalibur (IO)
 */
public class SwappedDataInputStream extends org.apache.commons.io.input.ProxyInputStream implements java.io.DataInput {
    /**
     * Constructs a SwappedDataInputStream.
     *
     * @param input
     * 		InputStream to read from
     */
    public SwappedDataInputStream(final java.io.InputStream input) {
        super(input);
    }

    /**
     * Return <code>{@link #readByte()} != 0</code>
     *
     * @return false if the byte read is zero, otherwise true
     * @throws IOException
     * 		if an I/O error occurs
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     */
    @java.lang.Override
    public boolean readBoolean() throws java.io.EOFException, java.io.IOException {
        return 0 != (readByte());
    }

    /**
     * Invokes the delegate's <code>read()</code> method.
     *
     * @return the byte read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     */
    @java.lang.Override
    public byte readByte() throws java.io.EOFException, java.io.IOException {
        return ((byte) (in.read()));
    }

    /**
     * Reads a character delegating to {@link #readShort()}.
     *
     * @return the byte read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     */
    @java.lang.Override
    public char readChar() throws java.io.EOFException, java.io.IOException {
        return ((char) (readShort()));
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedDouble(InputStream)}.
     *
     * @return the read long
     * @throws IOException
     * 		if an I/O error occurs
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     */
    @java.lang.Override
    public double readDouble() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedDouble(in);
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedFloat(InputStream)}.
     *
     * @return the read long
     * @throws IOException
     * 		if an I/O error occurs
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     */
    @java.lang.Override
    public float readFloat() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedFloat(in);
    }

    /**
     * Invokes the delegate's <code>read(byte[] data, int, int)</code> method.
     *
     * @param data
     * 		the buffer to read the bytes into
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void readFully(final byte[] data) throws java.io.EOFException, java.io.IOException {
        readFully(data, 0, data.length);
    }

    /**
     * Invokes the delegate's <code>read(byte[] data, int, int)</code> method.
     *
     * @param data
     * 		the buffer to read the bytes into
     * @param offset
     * 		The start offset
     * @param length
     * 		The number of bytes to read
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void readFully(final byte[] data, final int offset, final int length) throws java.io.EOFException, java.io.IOException {
        int remaining = length;
        while (remaining > 0) {
            final int location = (offset + length) - remaining;
            final int count = read(data, location, remaining);
            if ((org.apache.commons.io.IOUtils.EOF) == count) {
                throw new java.io.EOFException();
            }
            remaining -= count;
        } 
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedInteger(InputStream)}.
     *
     * @return the read long
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int readInt() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedInteger(in);
    }

    /**
     * Not currently supported - throws {@link UnsupportedOperationException}.
     *
     * @return the line read
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public java.lang.String readLine() throws java.io.EOFException, java.io.IOException {
        throw new java.lang.UnsupportedOperationException("Operation not supported: readLine()");
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedLong(InputStream)}.
     *
     * @return the read long
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public long readLong() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedLong(in);
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedShort(InputStream)}.
     *
     * @return the read long
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public short readShort() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedShort(in);
    }

    /**
     * Invokes the delegate's <code>read()</code> method.
     *
     * @return the byte read or -1 if the end of stream
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int readUnsignedByte() throws java.io.EOFException, java.io.IOException {
        return in.read();
    }

    /**
     * Delegates to {@link EndianUtils#readSwappedUnsignedShort(InputStream)}.
     *
     * @return the read long
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int readUnsignedShort() throws java.io.EOFException, java.io.IOException {
        return org.apache.commons.io.EndianUtils.readSwappedUnsignedShort(in);
    }

    /**
     * Not currently supported - throws {@link UnsupportedOperationException}.
     *
     * @return UTF String read
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public java.lang.String readUTF() throws java.io.EOFException, java.io.IOException {
        throw new java.lang.UnsupportedOperationException("Operation not supported: readUTF()");
    }

    /**
     * Invokes the delegate's <code>skip(int)</code> method.
     *
     * @param count
     * 		the number of bytes to skip
     * @return the number of bytes to skipped or -1 if the end of stream
     * @throws EOFException
     * 		if an end of file is reached unexpectedly
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int skipBytes(final int count) throws java.io.EOFException, java.io.IOException {
        return ((int) (in.skip(count)));
    }
}

