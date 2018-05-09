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
 * Utility code for dealing with different endian systems.
 * <p>
 * Different computer architectures adopt different conventions for
 * byte ordering. In so-called "Little Endian" architectures (eg Intel),
 * the low-order byte is stored in memory at the lowest address, and
 * subsequent bytes at higher addresses. For "Big Endian" architectures
 * (eg Motorola), the situation is reversed.
 * This class helps you solve this incompatibility.
 * <p>
 * Origin of code: Excalibur
 *
 * @see org.apache.commons.io.input.SwappedDataInputStream
 */
/* method "swapDouble" was removed from this class because it was not covered by the test suite */
/* method "swapFloat" was removed from this class because it was not covered by the test suite */
/* method "swapInteger" was removed from this class because it was not covered by the test suite */
/* method "readSwappedUnsignedInteger" was removed from this class because it was not covered by the test suite */
/* method "readSwappedUnsignedInteger" was removed from this class because it was not covered by the test suite */
/* method "swapLong" was removed from this class because it was not covered by the test suite */
/* method "swapShort" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedDouble" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedDouble" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedFloat" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedFloat" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedInteger" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedInteger" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedLong" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedLong" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedShort" was removed from this class because it was not covered by the test suite */
/* method "writeSwappedShort" was removed from this class because it was not covered by the test suite */
public class EndianUtils {
    /**
     * Instances should NOT be constructed in standard programming.
     */
    public EndianUtils() {
        super();
    }

    /**
     * Reads a "short" value from a byte array at a given offset. The value is
     * converted to the opposed endian system while reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static short readSwappedShort(final byte[] data, final int offset) {
        return ((short) ((((data[(offset + 0)]) & 255) << 0) + (((data[(offset + 1)]) & 255) << 8)));
    }

    /**
     * Reads an unsigned short (16-bit) value from a byte array at a given
     * offset. The value is converted to the opposed endian system while
     * reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static int readSwappedUnsignedShort(final byte[] data, final int offset) {
        return (((data[(offset + 0)]) & 255) << 0) + (((data[(offset + 1)]) & 255) << 8);
    }

    /**
     * Reads a "int" value from a byte array at a given offset. The value is
     * converted to the opposed endian system while reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static int readSwappedInteger(final byte[] data, final int offset) {
        return (((((data[(offset + 0)]) & 255) << 0) + (((data[(offset + 1)]) & 255) << 8)) + (((data[(offset + 2)]) & 255) << 16)) + (((data[(offset + 3)]) & 255) << 24);
    }

    /**
     * Reads a "long" value from a byte array at a given offset. The value is
     * converted to the opposed endian system while reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static long readSwappedLong(final byte[] data, final int offset) {
        final long low = org.apache.commons.io.EndianUtils.readSwappedInteger(data, offset);
        final long high = org.apache.commons.io.EndianUtils.readSwappedInteger(data, (offset + 4));
        return (high << 32) + (4294967295L & low);
    }

    /**
     * Reads a "float" value from a byte array at a given offset. The value is
     * converted to the opposed endian system while reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static float readSwappedFloat(final byte[] data, final int offset) {
        return java.lang.Float.intBitsToFloat(org.apache.commons.io.EndianUtils.readSwappedInteger(data, offset));
    }

    /**
     * Reads a "double" value from a byte array at a given offset. The value is
     * converted to the opposed endian system while reading.
     *
     * @param data
     * 		source byte array
     * @param offset
     * 		starting offset in the byte array
     * @return the value read
     */
    public static double readSwappedDouble(final byte[] data, final int offset) {
        return java.lang.Double.longBitsToDouble(org.apache.commons.io.EndianUtils.readSwappedLong(data, offset));
    }

    /**
     * Reads a "short" value from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static short readSwappedShort(final java.io.InputStream input) throws java.io.IOException {
        return ((short) ((((org.apache.commons.io.EndianUtils.read(input)) & 255) << 0) + (((org.apache.commons.io.EndianUtils.read(input)) & 255) << 8)));
    }

    /**
     * Reads a unsigned short (16-bit) from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static int readSwappedUnsignedShort(final java.io.InputStream input) throws java.io.IOException {
        final int value1 = org.apache.commons.io.EndianUtils.read(input);
        final int value2 = org.apache.commons.io.EndianUtils.read(input);
        return ((value1 & 255) << 0) + ((value2 & 255) << 8);
    }

    /**
     * Reads a "int" value from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static int readSwappedInteger(final java.io.InputStream input) throws java.io.IOException {
        final int value1 = org.apache.commons.io.EndianUtils.read(input);
        final int value2 = org.apache.commons.io.EndianUtils.read(input);
        final int value3 = org.apache.commons.io.EndianUtils.read(input);
        final int value4 = org.apache.commons.io.EndianUtils.read(input);
        return ((((value1 & 255) << 0) + ((value2 & 255) << 8)) + ((value3 & 255) << 16)) + ((value4 & 255) << 24);
    }

    /**
     * Reads a "long" value from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static long readSwappedLong(final java.io.InputStream input) throws java.io.IOException {
        final byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = ((byte) (org.apache.commons.io.EndianUtils.read(input)));
        }
        return org.apache.commons.io.EndianUtils.readSwappedLong(bytes, 0);
    }

    /**
     * Reads a "float" value from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static float readSwappedFloat(final java.io.InputStream input) throws java.io.IOException {
        return java.lang.Float.intBitsToFloat(org.apache.commons.io.EndianUtils.readSwappedInteger(input));
    }

    /**
     * Reads a "double" value from an InputStream. The value is
     * converted to the opposed endian system while reading.
     *
     * @param input
     * 		source InputStream
     * @return the value just read
     * @throws IOException
     * 		in case of an I/O problem
     */
    public static double readSwappedDouble(final java.io.InputStream input) throws java.io.IOException {
        return java.lang.Double.longBitsToDouble(org.apache.commons.io.EndianUtils.readSwappedLong(input));
    }

    /**
     * Reads the next byte from the input stream.
     *
     * @param input
     * 		the stream
     * @return the byte
     * @throws IOException
     * 		if the end of file is reached
     */
    private static int read(final java.io.InputStream input) throws java.io.IOException {
        final int value = input.read();
        if ((org.apache.commons.io.IOUtils.EOF) == value) {
            throw new java.io.EOFException("Unexpected EOF reached");
        }
        return value;
    }
}

