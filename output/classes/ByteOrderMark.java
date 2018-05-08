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
package classes;


/**
 * Byte Order Mark (BOM) representation - see {@link org.apache.commons.io.input.BOMInputStream}.
 *
 * @see org.apache.commons.io.input.BOMInputStream
 * @see <a href="http://en.wikipedia.org/wiki/Byte_order_mark">Wikipedia: Byte Order Mark</a>
 * @see <a href="http://www.w3.org/TR/2006/REC-xml-20060816/#sec-guessing">W3C: Autodetection of Character Encodings
 * (Non-Normative)</a>
 * @version $Id$
 * @since 2.0
 */
/* method "equals" was removed from this class because it was not covered by the test suite */
/* method "getBytes" was removed from this class because it was not covered by the test suite */
/* method "get" was removed from this class because it was not covered by the test suite */
/* method "hashCode" was removed from this class because it was not covered by the test suite */
/* method "length" was removed from this class because it was not covered by the test suite */
/* method "getCharsetName" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
public class ByteOrderMark implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * UTF-8 BOM
     */
    public static final classes.ByteOrderMark UTF_8 = new classes.ByteOrderMark("UTF-8", 239, 187, 191);

    /**
     * UTF-16BE BOM (Big-Endian)
     */
    public static final classes.ByteOrderMark UTF_16BE = new classes.ByteOrderMark("UTF-16BE", 254, 255);

    /**
     * UTF-16LE BOM (Little-Endian)
     */
    public static final classes.ByteOrderMark UTF_16LE = new classes.ByteOrderMark("UTF-16LE", 255, 254);

    /**
     * UTF-32BE BOM (Big-Endian)
     *
     * @since 2.2
     */
    public static final classes.ByteOrderMark UTF_32BE = new classes.ByteOrderMark("UTF-32BE", 0, 0, 254, 255);

    /**
     * UTF-32LE BOM (Little-Endian)
     *
     * @since 2.2
     */
    public static final classes.ByteOrderMark UTF_32LE = new classes.ByteOrderMark("UTF-32LE", 255, 254, 0, 0);

    /**
     * Unicode BOM character; external form depends on the encoding.
     *
     * @see <a href="http://unicode.org/faq/utf_bom.html#BOM">Byte Order Mark (BOM) FAQ</a>
     * @since 2.5
     */
    public static final char UTF_BOM = '\ufeff';

    private final java.lang.String charsetName;

    private final int[] bytes;

    /**
     * Construct a new BOM.
     *
     * @param charsetName
     * 		The name of the charset the BOM represents
     * @param bytes
     * 		The BOM's bytes
     * @throws IllegalArgumentException
     * 		if the charsetName is null or
     * 		zero length
     * @throws IllegalArgumentException
     * 		if the bytes are null or zero
     * 		length
     */
    public ByteOrderMark(final java.lang.String charsetName, final int... bytes) {
        if ((charsetName == null) || (charsetName.isEmpty())) {
            throw new java.lang.IllegalArgumentException("No charsetName specified");
        }
        if ((bytes == null) || ((bytes.length) == 0)) {
            throw new java.lang.IllegalArgumentException("No bytes specified");
        }
        this.charsetName = charsetName;
        this.bytes = new int[bytes.length];
        java.lang.System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }
}

