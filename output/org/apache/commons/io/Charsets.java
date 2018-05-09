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
package org.apache.commons.io;


/**
 * Charsets required of every implementation of the Java platform.
 *
 * From the Java documentation <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">
 * Standard charsets</a>:
 * <p>
 * <cite>Every implementation of the Java platform is required to support the following character encodings. Consult
 * the release documentation for your implementation to see if any other encodings are supported. Consult the release
 * documentation for your implementation to see if any other encodings are supported. </cite>
 * </p>
 *
 * <ul>
 * <li><code>US-ASCII</code><br>
 * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set.</li>
 * <li><code>ISO-8859-1</code><br>
 * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.</li>
 * <li><code>UTF-8</code><br>
 * Eight-bit Unicode Transformation Format.</li>
 * <li><code>UTF-16BE</code><br>
 * Sixteen-bit Unicode Transformation Format, big-endian byte order.</li>
 * <li><code>UTF-16LE</code><br>
 * Sixteen-bit Unicode Transformation Format, little-endian byte order.</li>
 * <li><code>UTF-16</code><br>
 * Sixteen-bit Unicode Transformation Format, byte order specified by a mandatory initial byte-order mark (either order
 * accepted on input, big-endian used on output.)</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
 * @since 2.3
 * @version $Id$
 */
/* method "requiredCharsets" was removed from this class because it was not covered by the test suite */
public class Charsets {
    /**
     * CharEncodingISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset ISO_8859_1 = java.nio.charset.StandardCharsets.ISO_8859_1;

    /**
     * <p>
     * Seven-bit ASCII, also known as ISO646-US, also known as the Basic Latin block of the Unicode character set.
     * </p>
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset US_ASCII = java.nio.charset.StandardCharsets.US_ASCII;

    /**
     * <p>
     * Sixteen-bit Unicode Transformation Format, The byte order specified by a mandatory initial byte-order mark
     * (either order accepted on input, big-endian used on output)
     * </p>
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset UTF_16 = java.nio.charset.StandardCharsets.UTF_16;

    /**
     * <p>
     * Sixteen-bit Unicode Transformation Format, big-endian byte order.
     * </p>
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset UTF_16BE = java.nio.charset.StandardCharsets.UTF_16BE;

    /**
     * <p>
     * Sixteen-bit Unicode Transformation Format, little-endian byte order.
     * </p>
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset UTF_16LE = java.nio.charset.StandardCharsets.UTF_16LE;

    /**
     * <p>
     * Eight-bit Unicode Transformation Format.
     * </p>
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     *
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @java.lang.Deprecated
    public static final java.nio.charset.Charset UTF_8 = java.nio.charset.StandardCharsets.UTF_8;

    /**
     * Returns the given Charset or the default Charset if the given Charset is null.
     *
     * @param charset
     * 		A charset or null.
     * @return the given Charset or the default Charset if the given Charset is null
     */
    public static java.nio.charset.Charset toCharset(final java.nio.charset.Charset charset) {
        return charset == null ? java.nio.charset.Charset.defaultCharset() : charset;
    }

    /**
     * Returns a Charset for the named charset. If the name is null, return the default Charset.
     *
     * @param charset
     * 		The name of the requested charset, may be null.
     * @return a Charset for the named charset
     * @throws java.nio.charset.UnsupportedCharsetException
     * 		If the named charset is unavailable
     */
    public static java.nio.charset.Charset toCharset(final java.lang.String charset) {
        return charset == null ? java.nio.charset.Charset.defaultCharset() : java.nio.charset.Charset.forName(charset);
    }
}

