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
 * {@link OutputStream} implementation that transforms a byte stream to a
 * character stream using a specified charset encoding and writes the resulting
 * stream to a {@link Writer}. The stream is transformed using a
 * {@link CharsetDecoder} object, guaranteeing that all charset
 * encodings supported by the JRE are handled correctly.
 * <p>
 * The output of the {@link CharsetDecoder} is buffered using a fixed size buffer.
 * This implies that the data is written to the underlying {@link Writer} in chunks
 * that are no larger than the size of this buffer. By default, the buffer is
 * flushed only when it overflows or when {@link #flush()} or {@link #close()}
 * is called. In general there is therefore no need to wrap the underlying {@link Writer}
 * in a {@link java.io.BufferedWriter}. {@link WriterOutputStream} can also
 * be instructed to flush the buffer after each write operation. In this case, all
 * available data is written immediately to the underlying {@link Writer}, implying that
 * the current position of the {@link Writer} is correlated to the current position
 * of the {@link WriterOutputStream}.
 * <p>
 * {@link WriterOutputStream} implements the inverse transformation of {@link java.io.OutputStreamWriter};
 * in the following example, writing to {@code out2} would have the same result as writing to
 * {@code out} directly (provided that the byte sequence is legal with respect to the
 * charset encoding):
 * <pre>
 * OutputStream out = ...
 * Charset cs = ...
 * OutputStreamWriter writer = new OutputStreamWriter(out, cs);
 * WriterOutputStream out2 = new WriterOutputStream(writer, cs);</pre>
 * {@link WriterOutputStream} implements the same transformation as {@link java.io.InputStreamReader},
 * except that the control flow is reversed: both classes transform a byte stream
 * into a character stream, but {@link java.io.InputStreamReader} pulls data from the underlying stream,
 * while {@link WriterOutputStream} pushes it to the underlying stream.
 * <p>
 * Note that while there are use cases where there is no alternative to using
 * this class, very often the need to use this class is an indication of a flaw
 * in the design of the code. This class is typically used in situations where an existing
 * API only accepts an {@link OutputStream} object, but where the stream is known to represent
 * character data that must be decoded for further use.
 * <p>
 * Instances of {@link WriterOutputStream} are not thread safe.
 *
 * @see org.apache.commons.io.input.ReaderInputStream
 * @since 2.0
 */
public class WriterOutputStream extends java.io.OutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private final java.io.Writer writer;

    private final java.nio.charset.CharsetDecoder decoder;

    private final boolean writeImmediately;

    /**
     * ByteBuffer used as input for the decoder. This buffer can be small
     * as it is used only to transfer the received data to the
     * decoder.
     */
    private final java.nio.ByteBuffer decoderIn = java.nio.ByteBuffer.allocate(128);

    /**
     * CharBuffer used as output for the decoder. It should be
     * somewhat larger as we write from this buffer to the
     * underlying Writer.
     */
    private final java.nio.CharBuffer decoderOut;

    /**
     * Constructs a new {@link WriterOutputStream} with a default output buffer size of
     * 1024 characters. The output buffer will only be flushed when it overflows or when
     * {@link #flush()} or {@link #close()} is called.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param decoder
     * 		the charset decoder
     * @since 2.1
     */
    public WriterOutputStream(final java.io.Writer writer, final java.nio.charset.CharsetDecoder decoder) {
        this(writer, decoder, org.apache.commons.io.output.WriterOutputStream.DEFAULT_BUFFER_SIZE, false);
    }

    /**
     * Constructs a new {@link WriterOutputStream}.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param decoder
     * 		the charset decoder
     * @param bufferSize
     * 		the size of the output buffer in number of characters
     * @param writeImmediately
     * 		If {@code true} the output buffer will be flushed after each
     * 		write operation, i.e. all available data will be written to the
     * 		underlying {@link Writer} immediately. If {@code false}, the
     * 		output buffer will only be flushed when it overflows or when
     * 		{@link #flush()} or {@link #close()} is called.
     * @since 2.1
     */
    public WriterOutputStream(final java.io.Writer writer, final java.nio.charset.CharsetDecoder decoder, final int bufferSize, final boolean writeImmediately) {
        org.apache.commons.io.output.WriterOutputStream.checkIbmJdkWithBrokenUTF16(decoder.charset());
        this.writer = writer;
        this.decoder = decoder;
        this.writeImmediately = writeImmediately;
        decoderOut = java.nio.CharBuffer.allocate(bufferSize);
    }

    /**
     * Constructs a new {@link WriterOutputStream}.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param charset
     * 		the charset encoding
     * @param bufferSize
     * 		the size of the output buffer in number of characters
     * @param writeImmediately
     * 		If {@code true} the output buffer will be flushed after each
     * 		write operation, i.e. all available data will be written to the
     * 		underlying {@link Writer} immediately. If {@code false}, the
     * 		output buffer will only be flushed when it overflows or when
     * 		{@link #flush()} or {@link #close()} is called.
     */
    public WriterOutputStream(final java.io.Writer writer, final java.nio.charset.Charset charset, final int bufferSize, final boolean writeImmediately) {
        this(writer, charset.newDecoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE).replaceWith("?"), bufferSize, writeImmediately);
    }

    /**
     * Constructs a new {@link WriterOutputStream} with a default output buffer size of
     * 1024 characters. The output buffer will only be flushed when it overflows or when
     * {@link #flush()} or {@link #close()} is called.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param charset
     * 		the charset encoding
     */
    public WriterOutputStream(final java.io.Writer writer, final java.nio.charset.Charset charset) {
        this(writer, charset, org.apache.commons.io.output.WriterOutputStream.DEFAULT_BUFFER_SIZE, false);
    }

    /**
     * Constructs a new {@link WriterOutputStream}.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param charsetName
     * 		the name of the charset encoding
     * @param bufferSize
     * 		the size of the output buffer in number of characters
     * @param writeImmediately
     * 		If {@code true} the output buffer will be flushed after each
     * 		write operation, i.e. all available data will be written to the
     * 		underlying {@link Writer} immediately. If {@code false}, the
     * 		output buffer will only be flushed when it overflows or when
     * 		{@link #flush()} or {@link #close()} is called.
     */
    public WriterOutputStream(final java.io.Writer writer, final java.lang.String charsetName, final int bufferSize, final boolean writeImmediately) {
        this(writer, java.nio.charset.Charset.forName(charsetName), bufferSize, writeImmediately);
    }

    /**
     * Constructs a new {@link WriterOutputStream} with a default output buffer size of
     * 1024 characters. The output buffer will only be flushed when it overflows or when
     * {@link #flush()} or {@link #close()} is called.
     *
     * @param writer
     * 		the target {@link Writer}
     * @param charsetName
     * 		the name of the charset encoding
     */
    public WriterOutputStream(final java.io.Writer writer, final java.lang.String charsetName) {
        this(writer, charsetName, org.apache.commons.io.output.WriterOutputStream.DEFAULT_BUFFER_SIZE, false);
    }

    /**
     * Constructs a new {@link WriterOutputStream} that uses the default character encoding
     * and with a default output buffer size of 1024 characters. The output buffer will only
     * be flushed when it overflows or when {@link #flush()} or {@link #close()} is called.
     *
     * @param writer
     * 		the target {@link Writer}
     * @deprecated 2.5 use {@link #WriterOutputStream(Writer, Charset)} instead
     */
    @java.lang.Deprecated
    public WriterOutputStream(final java.io.Writer writer) {
        this(writer, java.nio.charset.Charset.defaultCharset(), org.apache.commons.io.output.WriterOutputStream.DEFAULT_BUFFER_SIZE, false);
    }

    /**
     * Write bytes from the specified byte array to the stream.
     *
     * @param b
     * 		the byte array containing the bytes to write
     * @param off
     * 		the start offset in the byte array
     * @param len
     * 		the number of bytes to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void write(final byte[] b, int off, int len) throws java.io.IOException {
        while (len > 0) {
            final int c = java.lang.Math.min(len, decoderIn.remaining());
            decoderIn.put(b, off, c);
            processInput(false);
            len -= c;
            off += c;
        } 
        if (writeImmediately) {
            flushOutput();
        }
    }

    /**
     * Write bytes from the specified byte array to the stream.
     *
     * @param b
     * 		the byte array containing the bytes to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void write(final byte[] b) throws java.io.IOException {
        write(b, 0, b.length);
    }

    /**
     * Write a single byte to the stream.
     *
     * @param b
     * 		the byte to write
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void write(final int b) throws java.io.IOException {
        write(new byte[]{ ((byte) (b)) }, 0, 1);
    }

    /**
     * Flush the stream. Any remaining content accumulated in the output buffer
     * will be written to the underlying {@link Writer}. After that
     * {@link Writer#flush()} will be called.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void flush() throws java.io.IOException {
        flushOutput();
        writer.flush();
    }

    /**
     * Close the stream. Any remaining content accumulated in the output buffer
     * will be written to the underlying {@link Writer}. After that
     * {@link Writer#close()} will be called.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        processInput(true);
        flushOutput();
        writer.close();
    }

    /**
     * Decode the contents of the input ByteBuffer into a CharBuffer.
     *
     * @param endOfInput
     * 		indicates end of input
     * @throws IOException
     * 		if an I/O error occurs
     */
    private void processInput(final boolean endOfInput) throws java.io.IOException {
        // Prepare decoderIn for reading
        decoderIn.flip();
        java.nio.charset.CoderResult coderResult;
        while (true) {
            coderResult = decoder.decode(decoderIn, decoderOut, endOfInput);
            // The decoder is configured to replace malformed input and unmappable characters,
            // so we should not get here.
            if (coderResult.isOverflow()) {
                flushOutput();
            }// The decoder is configured to replace malformed input and unmappable characters,
            // so we should not get here.
            else
                if (coderResult.isUnderflow()) {
                    break;
                }else {
                    throw new java.io.IOException("Unexpected coder result");
                }
            
        } 
        // Discard the bytes that have been read
        decoderIn.compact();
    }

    /**
     * Flush the output.
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    private void flushOutput() throws java.io.IOException {
        if ((decoderOut.position()) > 0) {
            writer.write(decoderOut.array(), 0, decoderOut.position());
            decoderOut.rewind();
        }
    }

    /**
     * Check if the JDK in use properly supports the given charset.
     *
     * @param charset
     * 		the charset to check the support for
     */
    private static void checkIbmJdkWithBrokenUTF16(final java.nio.charset.Charset charset) {
        if (!("UTF-16".equals(charset.name()))) {
            return ;
        }
        final java.lang.String TEST_STRING_2 = "v\u00e9s";
        final byte[] bytes = TEST_STRING_2.getBytes(charset);
        final java.nio.charset.CharsetDecoder charsetDecoder2 = charset.newDecoder();
        final java.nio.ByteBuffer bb2 = java.nio.ByteBuffer.allocate(16);
        final java.nio.CharBuffer cb2 = java.nio.CharBuffer.allocate(TEST_STRING_2.length());
        final int len = bytes.length;
        for (int i = 0; i < len; i++) {
            bb2.put(bytes[i]);
            bb2.flip();
            try {
                charsetDecoder2.decode(bb2, cb2, (i == (len - 1)));
            } catch (final java.lang.IllegalArgumentException e) {
                throw new java.lang.UnsupportedOperationException(("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. " + "Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream"));
            }
            bb2.compact();
        }
        cb2.rewind();
        if (!(TEST_STRING_2.equals(cb2.toString()))) {
            throw new java.lang.UnsupportedOperationException(("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. " + "Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream"));
        }
    }
}

