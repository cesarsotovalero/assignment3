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
 * Character stream that handles all the necessary Voodoo to figure out the
 * charset encoding of the XML document written to the stream.
 *
 * @see XmlStreamReader
 * @since 2.0
 */
/* method "getDefaultEncoding" was removed from this class because it was not covered by the test suite */
public class XmlStreamWriter extends java.io.Writer {
    private static final int BUFFER_SIZE = 4096;

    private final java.io.OutputStream out;

    private final java.lang.String defaultEncoding;

    private java.io.StringWriter xmlPrologWriter = new java.io.StringWriter(org.apache.commons.io.output.XmlStreamWriter.BUFFER_SIZE);

    private java.io.Writer writer;

    private java.lang.String encoding;

    static final java.util.regex.Pattern ENCODING_PATTERN = org.apache.commons.io.input.XmlStreamReader.ENCODING_PATTERN;

    /**
     * Constructs a new XML stream writer for the specified output stream
     * with a default encoding of UTF-8.
     *
     * @param out
     * 		The output stream
     */
    public XmlStreamWriter(final java.io.OutputStream out) {
        this(out, null);
    }

    /**
     * Constructs a new XML stream writer for the specified output stream
     * with the specified default encoding.
     *
     * @param out
     * 		The output stream
     * @param defaultEncoding
     * 		The default encoding if not encoding could be detected
     */
    public XmlStreamWriter(final java.io.OutputStream out, final java.lang.String defaultEncoding) {
        this.out = out;
        this.defaultEncoding = (defaultEncoding != null) ? defaultEncoding : "UTF-8";
    }

    /**
     * Constructs a new XML stream writer for the specified file
     * with a default encoding of UTF-8.
     *
     * @param file
     * 		The file to write to
     * @throws FileNotFoundException
     * 		if there is an error creating or
     * 		opening the file
     */
    public XmlStreamWriter(final java.io.File file) throws java.io.FileNotFoundException {
        this(file, null);
    }

    /**
     * Constructs a new XML stream writer for the specified file
     * with the specified default encoding.
     *
     * @param file
     * 		The file to write to
     * @param defaultEncoding
     * 		The default encoding if not encoding could be detected
     * @throws FileNotFoundException
     * 		if there is an error creating or
     * 		opening the file
     */
    public XmlStreamWriter(final java.io.File file, final java.lang.String defaultEncoding) throws java.io.FileNotFoundException {
        this(new java.io.FileOutputStream(file), defaultEncoding);
    }

    /**
     * Returns the detected encoding.
     *
     * @return the detected encoding
     */
    public java.lang.String getEncoding() {
        return encoding;
    }

    /**
     * Closes the underlying writer.
     *
     * @throws IOException
     * 		if an error occurs closing the underlying writer
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        if ((writer) == null) {
            encoding = defaultEncoding;
            writer = new java.io.OutputStreamWriter(out, encoding);
            writer.write(xmlPrologWriter.toString());
        }
        writer.close();
    }

    /**
     * Flushes the underlying writer.
     *
     * @throws IOException
     * 		if an error occurs flushing the underlying writer
     */
    @java.lang.Override
    public void flush() throws java.io.IOException {
        if ((writer) != null) {
            writer.flush();
        }
    }

    /**
     * Detects the encoding.
     *
     * @param cbuf
     * 		the buffer to write the characters from
     * @param off
     * 		The start offset
     * @param len
     * 		The number of characters to write
     * @throws IOException
     * 		if an error occurs detecting the encoding
     */
    private void detectEncoding(final char[] cbuf, final int off, final int len) throws java.io.IOException {
        int size = len;
        final java.lang.StringBuffer xmlProlog = xmlPrologWriter.getBuffer();
        if (((xmlProlog.length()) + len) > (org.apache.commons.io.output.XmlStreamWriter.BUFFER_SIZE)) {
            size = (org.apache.commons.io.output.XmlStreamWriter.BUFFER_SIZE) - (xmlProlog.length());
        }
        xmlPrologWriter.write(cbuf, off, size);
        // try to determine encoding
        if ((xmlProlog.length()) >= 5) {
            if (xmlProlog.substring(0, 5).equals("<?xml")) {
                // try to extract encoding from XML prolog
                final int xmlPrologEnd = xmlProlog.indexOf("?>");
                if (xmlPrologEnd > 0) {
                    // ok, full XML prolog written: let's extract encoding
                    final java.util.regex.Matcher m = org.apache.commons.io.output.XmlStreamWriter.ENCODING_PATTERN.matcher(xmlProlog.substring(0, xmlPrologEnd));
                    if (m.find()) {
                        encoding = m.group(1).toUpperCase(java.util.Locale.ROOT);
                        encoding = encoding.substring(1, ((encoding.length()) - 1));
                    }else {
                        // no encoding found in XML prolog: using default
                        // encoding
                        encoding = defaultEncoding;
                    }
                }else {
                    if ((xmlProlog.length()) >= (org.apache.commons.io.output.XmlStreamWriter.BUFFER_SIZE)) {
                        // no encoding found in first characters: using default
                        // encoding
                        encoding = defaultEncoding;
                    }
                }
            }else {
                // no XML prolog: using default encoding
                encoding = defaultEncoding;
            }
            if ((encoding) != null) {
                // encoding has been chosen: let's do it
                xmlPrologWriter = null;
                writer = new java.io.OutputStreamWriter(out, encoding);
                writer.write(xmlProlog.toString());
                if (len > size) {
                    writer.write(cbuf, (off + size), (len - size));
                }
            }
        }
    }

    /**
     * Writes the characters to the underlying writer, detecting encoding.
     *
     * @param cbuf
     * 		the buffer to write the characters from
     * @param off
     * 		The start offset
     * @param len
     * 		The number of characters to write
     * @throws IOException
     * 		if an error occurs detecting the encoding
     */
    @java.lang.Override
    public void write(final char[] cbuf, final int off, final int len) throws java.io.IOException {
        if ((xmlPrologWriter) != null) {
            detectEncoding(cbuf, off, len);
        }else {
            writer.write(cbuf, off, len);
        }
    }
}

