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
 * Reads lines in a file reversely (similar to a BufferedReader, but starting at
 * the last line). Useful for e.g. searching in log files.
 *
 * @since 2.2
 */
public class ReversedLinesFileReader implements java.io.Closeable {
    private class FilePart {
        private final long no;

        private final byte[] data;

        private byte[] leftOver;

        private int currentLastBytePos;

        /**
         * ctor
         *
         * @param no
         * 		the part number
         * @param length
         * 		its length
         * @param leftOverOfLastFilePart
         * 		remainder
         * @throws IOException
         * 		if there is a problem reading the file
         */
        private FilePart(final long no, final int length, final byte[] leftOverOfLastFilePart) throws java.io.IOException {
            this.no = no;
            final int dataLength = length + (leftOverOfLastFilePart != null ? leftOverOfLastFilePart.length : 0);
            this.data = new byte[dataLength];
            final long off = (no - 1) * (blockSize);
            // read data
            /* file not empty */
            if (no > 0) {
                randomAccessFile.seek(off);
                final int countRead = randomAccessFile.read(data, 0, length);
                if (countRead != length) {
                    throw new java.lang.IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            // copy left over part into data arr
            if (leftOverOfLastFilePart != null) {
                java.lang.System.arraycopy(leftOverOfLastFilePart, 0, data, length, leftOverOfLastFilePart.length);
            }
            this.currentLastBytePos = (data.length) - 1;
            this.leftOver = null;
        }

        /**
         * Handles block rollover
         *
         * @return the new FilePart or null
         * @throws IOException
         * 		if there was a problem reading the file
         */
        private org.apache.commons.io.input.ReversedLinesFileReader.FilePart rollOver() throws java.io.IOException {
            if ((currentLastBytePos) > (-1)) {
                throw new java.lang.IllegalStateException((("Current currentLastCharPos unexpectedly positive... " + "last readLine() should have returned something! currentLastCharPos=") + (currentLastBytePos)));
            }
            if ((no) > 1) {
                return new org.apache.commons.io.input.ReversedLinesFileReader.FilePart(((no) - 1), blockSize, leftOver);
            }
            // NO 1 was the last FilePart, we're finished
            if ((leftOver) != null) {
                throw new java.lang.IllegalStateException(("Unexpected leftover of the last block: leftOverOfThisFilePart=" + (new java.lang.String(leftOver, encoding))));
            }
            return null;
        }

        /**
         * Reads a line.
         *
         * @return the line or null
         * @throws IOException
         * 		if there is an error reading from the file
         */
        private java.lang.String readLine() throws java.io.IOException {
            java.lang.String line = null;
            int newLineMatchByteCount;
            final boolean isLastFilePart = (no) == 1;
            int i = currentLastBytePos;
            while (i > (-1)) {
                if ((!isLastFilePart) && (i < (avoidNewlineSplitBufferSize))) {
                    // avoidNewlineSplitBuffer: for all except the last file part we
                    // take a few bytes to the next file part to avoid splitting of newlines
                    createLeftOver();
                    break;// skip last few bytes and leave it to the next file part
                    
                }
                // --- check for newline ---
                /* found newline */
                if ((newLineMatchByteCount = getNewLineMatchByteCount(data, i)) > 0) {
                    final int lineStart = i + 1;
                    final int lineLengthBytes = ((currentLastBytePos) - lineStart) + 1;
                    if (lineLengthBytes < 0) {
                        throw new java.lang.IllegalStateException(("Unexpected negative line length=" + lineLengthBytes));
                    }
                    final byte[] lineData = new byte[lineLengthBytes];
                    java.lang.System.arraycopy(data, lineStart, lineData, 0, lineLengthBytes);
                    line = new java.lang.String(lineData, encoding);
                    currentLastBytePos = i - newLineMatchByteCount;
                    break;// found line
                    
                }
                // --- move cursor ---
                i -= byteDecrement;
                // --- end of file part handling ---
                if (i < 0) {
                    createLeftOver();
                    break;// end of file part
                    
                }
            } 
            // --- last file part handling ---
            if (isLastFilePart && ((leftOver) != null)) {
                // there will be no line break anymore, this is the first line of the file
                line = new java.lang.String(leftOver, encoding);
                leftOver = null;
            }
            return line;
        }

        /**
         * Creates the buffer containing any left over bytes.
         */
        private void createLeftOver() {
            final int lineLengthBytes = (currentLastBytePos) + 1;
            if (lineLengthBytes > 0) {
                // create left over for next block
                leftOver = new byte[lineLengthBytes];
                java.lang.System.arraycopy(data, 0, leftOver, 0, lineLengthBytes);
            }else {
                leftOver = null;
            }
            currentLastBytePos = -1;
        }

        /**
         * Finds the new-line sequence and return its length.
         *
         * @param data
         * 		buffer to scan
         * @param i
         * 		start offset in buffer
         * @return length of newline sequence or 0 if none found
         */
        private int getNewLineMatchByteCount(final byte[] data, final int i) {
            for (final byte[] newLineSequence : newLineSequences) {
                boolean match = true;
                for (int j = (newLineSequence.length) - 1; j >= 0; j--) {
                    final int k = (i + j) - ((newLineSequence.length) - 1);
                    match &= (k >= 0) && ((data[k]) == (newLineSequence[j]));
                }
                if (match) {
                    return newLineSequence.length;
                }
            }
            return 0;
        }
    }

    private static final java.lang.String EMPTY_STRING = "";

    private static final int DEFAULT_BLOCK_SIZE = 4096;

    private final int blockSize;

    private final java.nio.charset.Charset encoding;

    private final java.io.RandomAccessFile randomAccessFile;

    private final long totalByteLength;

    private final long totalBlockCount;

    private final byte[][] newLineSequences;

    private final int avoidNewlineSplitBufferSize;

    private final int byteDecrement;

    private org.apache.commons.io.input.ReversedLinesFileReader.FilePart currentFilePart;

    private boolean trailingNewlineOfFileSkipped = false;

    /**
     * Creates a ReversedLinesFileReader with default block size of 4KB and the
     * platform's default encoding.
     *
     * @param file
     * 		the file to be read
     * @throws IOException
     * 		if an I/O error occurs
     * @deprecated 2.5 use {@link #ReversedLinesFileReader(File, Charset)} instead
     */
    @java.lang.Deprecated
    public ReversedLinesFileReader(final java.io.File file) throws java.io.IOException {
        this(file, org.apache.commons.io.input.ReversedLinesFileReader.DEFAULT_BLOCK_SIZE, java.nio.charset.Charset.defaultCharset());
    }

    /**
     * Creates a ReversedLinesFileReader with default block size of 4KB and the
     * specified encoding.
     *
     * @param file
     * 		the file to be read
     * @param charset
     * 		the encoding to use
     * @throws IOException
     * 		if an I/O error occurs
     * @since 2.5
     */
    public ReversedLinesFileReader(final java.io.File file, final java.nio.charset.Charset charset) throws java.io.IOException {
        this(file, org.apache.commons.io.input.ReversedLinesFileReader.DEFAULT_BLOCK_SIZE, charset);
    }

    /**
     * Creates a ReversedLinesFileReader with the given block size and encoding.
     *
     * @param file
     * 		the file to be read
     * @param blockSize
     * 		size of the internal buffer (for ideal performance this should
     * 		match with the block size of the underlying file system).
     * @param encoding
     * 		the encoding of the file
     * @throws IOException
     * 		if an I/O error occurs
     * @since 2.3
     */
    public ReversedLinesFileReader(final java.io.File file, final int blockSize, final java.nio.charset.Charset encoding) throws java.io.IOException {
        this.blockSize = blockSize;
        this.encoding = encoding;
        // --- check & prepare encoding ---
        final java.nio.charset.Charset charset = org.apache.commons.io.Charsets.toCharset(encoding);
        final java.nio.charset.CharsetEncoder charsetEncoder = charset.newEncoder();
        final float maxBytesPerChar = charsetEncoder.maxBytesPerChar();
        // UTF-8 works fine out of the box, for multibyte sequences a second UTF-8 byte can never be a newline byte
        // http://en.wikipedia.org/wiki/UTF-8
        // Same as for UTF-8
        // http://www.herongyang.com/Unicode/JIS-Shift-JIS-Encoding.html
        // Windows code page 932 (Japanese)
        // Windows code page 949 (Korean)
        // Windows code page 936 (Simplified Chinese)
        // Windows code page 950 (Traditional Chinese)
        // UTF-16 new line sequences are not allowed as second tuple of four byte sequences,
        // however byte order has to be specified
        if (maxBytesPerChar == 1.0F) {
            // all one byte encodings are no problem
            byteDecrement = 1;
        }// UTF-8 works fine out of the box, for multibyte sequences a second UTF-8 byte can never be a newline byte
        // http://en.wikipedia.org/wiki/UTF-8
        // Same as for UTF-8
        // http://www.herongyang.com/Unicode/JIS-Shift-JIS-Encoding.html
        // Windows code page 932 (Japanese)
        // Windows code page 949 (Korean)
        // Windows code page 936 (Simplified Chinese)
        // Windows code page 950 (Traditional Chinese)
        // UTF-16 new line sequences are not allowed as second tuple of four byte sequences,
        // however byte order has to be specified
        else
            if (charset == (java.nio.charset.StandardCharsets.UTF_8)) {
                byteDecrement = 1;
            }else
                if (((((charset == (java.nio.charset.Charset.forName("Shift_JIS"))) || (charset == (java.nio.charset.Charset.forName("windows-31j")))) || (charset == (java.nio.charset.Charset.forName("x-windows-949")))) || (charset == (java.nio.charset.Charset.forName("gbk")))) || (charset == (java.nio.charset.Charset.forName("x-windows-950")))) {
                    byteDecrement = 1;
                }else
                    if ((charset == (java.nio.charset.StandardCharsets.UTF_16BE)) || (charset == (java.nio.charset.StandardCharsets.UTF_16LE))) {
                        byteDecrement = 2;
                    }else
                        if (charset == (java.nio.charset.StandardCharsets.UTF_16)) {
                            throw new java.io.UnsupportedEncodingException(("For UTF-16, you need to specify the byte order (use UTF-16BE or " + "UTF-16LE)"));
                        }else {
                            throw new java.io.UnsupportedEncodingException(((("Encoding " + encoding) + " is not supported yet (feel free to ") + "submit a patch)"));
                        }
                    
                
            
        
        // NOTE: The new line sequences are matched in the order given, so it is important that \r\n is BEFORE \n
        newLineSequences = new byte[][]{ "\r\n".getBytes(encoding) , "\n".getBytes(encoding) , "\r".getBytes(encoding) };
        avoidNewlineSplitBufferSize = newLineSequences[0].length;
        // Open file
        randomAccessFile = new java.io.RandomAccessFile(file, "r");
        totalByteLength = randomAccessFile.length();
        int lastBlockLength = ((int) ((totalByteLength) % blockSize));
        if (lastBlockLength > 0) {
            totalBlockCount = ((totalByteLength) / blockSize) + 1;
        }else {
            totalBlockCount = (totalByteLength) / blockSize;
            if ((totalByteLength) > 0) {
                lastBlockLength = blockSize;
            }
        }
        currentFilePart = new org.apache.commons.io.input.ReversedLinesFileReader.FilePart(totalBlockCount, lastBlockLength, null);
    }

    /**
     * Creates a ReversedLinesFileReader with the given block size and encoding.
     *
     * @param file
     * 		the file to be read
     * @param blockSize
     * 		size of the internal buffer (for ideal performance this should
     * 		match with the block size of the underlying file system).
     * @param encoding
     * 		the encoding of the file
     * @throws IOException
     * 		if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException
     * 		thrown instead of {@link UnsupportedEncodingException} in
     * 		version 2.2 if the encoding is not supported.
     */
    public ReversedLinesFileReader(final java.io.File file, final int blockSize, final java.lang.String encoding) throws java.io.IOException {
        this(file, blockSize, org.apache.commons.io.Charsets.toCharset(encoding));
    }

    /**
     * Returns the lines of the file from bottom to top.
     *
     * @return the next line or null if the start of the file is reached
     * @throws IOException
     * 		if an I/O error occurs
     */
    public java.lang.String readLine() throws java.io.IOException {
        java.lang.String line = currentFilePart.readLine();
        while (line == null) {
            currentFilePart = currentFilePart.rollOver();
            if ((currentFilePart) != null) {
                line = currentFilePart.readLine();
            }else {
                // no more fileparts: we're done, leave line set to null
                break;
            }
        } 
        // aligned behaviour with BufferedReader that doesn't return a last, empty line
        if ((org.apache.commons.io.input.ReversedLinesFileReader.EMPTY_STRING.equals(line)) && (!(trailingNewlineOfFileSkipped))) {
            trailingNewlineOfFileSkipped = true;
            line = readLine();
        }
        return line;
    }

    /**
     * Closes underlying resources.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        randomAccessFile.close();
    }
}

