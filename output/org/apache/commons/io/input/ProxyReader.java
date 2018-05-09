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
 * It is an alternative base class to FilterReader
 * to increase reusability, because FilterReader changes the
 * methods being called, such as read(char[]) to read(char[], int, int).
 */
/* method "markSupported" was removed from this class because it was not covered by the test suite */
/* method "ready" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "handleIOException" was removed from this class because it was not covered by the test suite */
/* method "mark" was removed from this class because it was not covered by the test suite */
/* method "reset" was removed from this class because it was not covered by the test suite */
public abstract class ProxyReader extends java.io.FilterReader {
    /**
     * Constructs a new ProxyReader.
     *
     * @param proxy
     * 		the Reader to delegate to
     */
    public ProxyReader(final java.io.Reader proxy) {
        super(proxy);
        // the proxy is stored in a protected superclass variable named 'in'
    }

    /**
     * Invokes the delegate's <code>read()</code> method.
     *
     * @return the character read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        try {
            beforeRead(1);
            final int c = in.read();
            afterRead((c != (org.apache.commons.io.IOUtils.EOF) ? 1 : org.apache.commons.io.IOUtils.EOF));
            return c;
        } catch (final java.io.IOException e) {
            handleIOException(e);
            return org.apache.commons.io.IOUtils.EOF;
        }
    }

    /**
     * Invokes the delegate's <code>read(char[])</code> method.
     *
     * @param chr
     * 		the buffer to read the characters into
     * @return the number of characters read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read(final char[] chr) throws java.io.IOException {
        try {
            beforeRead((chr != null ? chr.length : 0));
            final int n = in.read(chr);
            afterRead(n);
            return n;
        } catch (final java.io.IOException e) {
            handleIOException(e);
            return org.apache.commons.io.IOUtils.EOF;
        }
    }

    /**
     * Invokes the delegate's <code>read(char[], int, int)</code> method.
     *
     * @param chr
     * 		the buffer to read the characters into
     * @param st
     * 		The start offset
     * @param len
     * 		The number of bytes to read
     * @return the number of characters read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public int read(final char[] chr, final int st, final int len) throws java.io.IOException {
        try {
            beforeRead(len);
            final int n = in.read(chr, st, len);
            afterRead(n);
            return n;
        } catch (final java.io.IOException e) {
            handleIOException(e);
            return org.apache.commons.io.IOUtils.EOF;
        }
    }

    /**
     * Invokes the delegate's <code>read(CharBuffer)</code> method.
     *
     * @param target
     * 		the char buffer to read the characters into
     * @return the number of characters read or -1 if the end of stream
     * @throws IOException
     * 		if an I/O error occurs
     * @since 2.0
     */
    @java.lang.Override
    public int read(final java.nio.CharBuffer target) throws java.io.IOException {
        try {
            beforeRead((target != null ? target.length() : 0));
            final int n = in.read(target);
            afterRead(n);
            return n;
        } catch (final java.io.IOException e) {
            handleIOException(e);
            return org.apache.commons.io.IOUtils.EOF;
        }
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

    /**
     * Invoked by the read methods before the call is proxied. The number
     * of chars that the caller wanted to read (1 for the {@link #read()}
     * method, buffer length for {@link #read(char[])}, etc.) is given as
     * an argument.
     * <p>
     * Subclasses can override this method to add common pre-processing
     * functionality without having to override all the read methods.
     * The default implementation does nothing.
     * <p>
     * Note this method is <em>not</em> called from {@link #skip(long)} or
     * {@link #reset()}. You need to explicitly override those methods if
     * you want to add pre-processing steps also to them.
     *
     * @since 2.0
     * @param n
     * 		number of chars that the caller asked to be read
     * @throws IOException
     * 		if the pre-processing fails
     */
    protected void beforeRead(final int n) throws java.io.IOException {
    }

    /**
     * Invoked by the read methods after the proxied call has returned
     * successfully. The number of chars returned to the caller (or -1 if
     * the end of stream was reached) is given as an argument.
     * <p>
     * Subclasses can override this method to add common post-processing
     * functionality without having to override all the read methods.
     * The default implementation does nothing.
     * <p>
     * Note this method is <em>not</em> called from {@link #skip(long)} or
     * {@link #reset()}. You need to explicitly override those methods if
     * you want to add post-processing steps also to them.
     *
     * @since 2.0
     * @param n
     * 		number of chars read, or -1 if the end of stream was reached
     * @throws IOException
     * 		if the post-processing fails
     */
    protected void afterRead(final int n) throws java.io.IOException {
    }
}

