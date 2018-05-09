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
 * Character stream that handles all the necessary Voodoo to figure out the
 * charset encoding of the XML document within the stream.
 * <p>
 * IMPORTANT: This class is not related in any way to the org.xml.sax.XMLReader.
 * This one IS a character stream.
 * <p>
 * All this has to be done without consuming characters from the stream, if not
 * the XML parser will not recognized the document as a valid XML. This is not
 * 100% true, but it's close enough (UTF-8 BOM is not handled by all parsers
 * right now, XmlStreamReader handles it and things work in all parsers).
 * <p>
 * The XmlStreamReader class handles the charset encoding of XML documents in
 * Files, raw streams and HTTP streams by offering a wide set of constructors.
 * <p>
 * By default the charset encoding detection is lenient, the constructor with
 * the lenient flag can be used for a script (following HTTP MIME and XML
 * specifications). All this is nicely explained by Mark Pilgrim in his blog, <a
 * href="http://diveintomark.org/archives/2004/02/13/xml-media-types">
 * Determining the character encoding of a feed</a>.
 * <p>
 * Originally developed for <a href="http://rome.dev.java.net">ROME</a> under
 * Apache License 2.0.
 *
 * @see org.apache.commons.io.output.XmlStreamWriter
 * @since 2.0
 */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "doRawStream" was removed from this class because it was not covered by the test suite */
/* method "getDefaultEncoding" was removed from this class because it was not covered by the test suite */
/* method "getEncoding" was removed from this class because it was not covered by the test suite */
public class XmlStreamReader extends java.io.Reader {
    private static final int BUFFER_SIZE = 4096;

    private static final java.lang.String UTF_8 = "UTF-8";

    private static final java.lang.String US_ASCII = "US-ASCII";

    private static final java.lang.String UTF_16BE = "UTF-16BE";

    private static final java.lang.String UTF_16LE = "UTF-16LE";

    private static final java.lang.String UTF_32BE = "UTF-32BE";

    private static final java.lang.String UTF_32LE = "UTF-32LE";

    private static final java.lang.String UTF_16 = "UTF-16";

    private static final java.lang.String UTF_32 = "UTF-32";

    private static final java.lang.String EBCDIC = "CP1047";

    private static final org.apache.commons.io.ByteOrderMark[] BOMS = new org.apache.commons.io.ByteOrderMark[]{ org.apache.commons.io.ByteOrderMark.UTF_8 , org.apache.commons.io.ByteOrderMark.UTF_16BE , org.apache.commons.io.ByteOrderMark.UTF_16LE , org.apache.commons.io.ByteOrderMark.UTF_32BE , org.apache.commons.io.ByteOrderMark.UTF_32LE };

    // UTF_16LE and UTF_32LE have the same two starting BOM bytes.
    private static final org.apache.commons.io.ByteOrderMark[] XML_GUESS_BYTES = new org.apache.commons.io.ByteOrderMark[]{ new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.UTF_8, 60, 63, 120, 109) , new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.UTF_16BE, 0, 60, 0, 63) , new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.UTF_16LE, 60, 0, 63, 0) , new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.UTF_32BE, 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109) , new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.UTF_32LE, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0) , new org.apache.commons.io.ByteOrderMark(org.apache.commons.io.input.XmlStreamReader.EBCDIC, 76, 111, 167, 148) };

    private final java.io.Reader reader;

    private final java.lang.String encoding;

    private final java.lang.String defaultEncoding;

    private static final java.util.regex.Pattern CHARSET_PATTERN = java.util.regex.Pattern.compile("charset=[\"\']?([.[^; \"\']]*)[\"\']?");

    public static final java.util.regex.Pattern ENCODING_PATTERN = java.util.regex.Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:\'.[^\']*\'))", java.util.regex.Pattern.MULTILINE);

    private static final java.lang.String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";

    private static final java.lang.String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";

    private static final java.lang.String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";

    private static final java.lang.String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";

    private static final java.lang.String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";

    /**
     * Creates a Reader for a File.
     * <p>
     * It looks for the UTF-8 BOM first, if none sniffs the XML prolog charset,
     * if this is also missing defaults to UTF-8.
     * <p>
     * It does a lenient charset encoding detection, check the constructor with
     * the lenient parameter for details.
     *
     * @param file
     * 		File to create a Reader from.
     * @throws IOException
     * 		thrown if there is a problem reading the file.
     */
    public XmlStreamReader(final java.io.File file) throws java.io.IOException {
        this(new java.io.FileInputStream(file));
    }

    /**
     * Creates a Reader for a raw InputStream.
     * <p>
     * It follows the same logic used for files.
     * <p>
     * It does a lenient charset encoding detection, check the constructor with
     * the lenient parameter for details.
     *
     * @param is
     * 		InputStream to create a Reader from.
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    public XmlStreamReader(final java.io.InputStream is) throws java.io.IOException {
        this(is, true);
    }

    /**
     * Creates a Reader for a raw InputStream.
     * <p>
     * It follows the same logic used for files.
     * <p>
     * If lenient detection is indicated and the detection above fails as per
     * specifications it then attempts the following:
     * <p>
     * If the content type was 'text/html' it replaces it with 'text/xml' and
     * tries the detection again.
     * <p>
     * Else if the XML prolog had a charset encoding that encoding is used.
     * <p>
     * Else if the content type had a charset encoding that encoding is used.
     * <p>
     * Else 'UTF-8' is used.
     * <p>
     * If lenient detection is indicated an XmlStreamReaderException is never
     * thrown.
     *
     * @param is
     * 		InputStream to create a Reader from.
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     * @throws XmlStreamReaderException
     * 		thrown if the charset encoding could not
     * 		be determined according to the specs.
     */
    public XmlStreamReader(final java.io.InputStream is, final boolean lenient) throws java.io.IOException {
        this(is, lenient, null);
    }

    /**
     * Creates a Reader for a raw InputStream.
     * <p>
     * It follows the same logic used for files.
     * <p>
     * If lenient detection is indicated and the detection above fails as per
     * specifications it then attempts the following:
     * <p>
     * If the content type was 'text/html' it replaces it with 'text/xml' and
     * tries the detection again.
     * <p>
     * Else if the XML prolog had a charset encoding that encoding is used.
     * <p>
     * Else if the content type had a charset encoding that encoding is used.
     * <p>
     * Else 'UTF-8' is used.
     * <p>
     * If lenient detection is indicated an XmlStreamReaderException is never
     * thrown.
     *
     * @param is
     * 		InputStream to create a Reader from.
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @param defaultEncoding
     * 		The default encoding
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     * @throws XmlStreamReaderException
     * 		thrown if the charset encoding could not
     * 		be determined according to the specs.
     */
    public XmlStreamReader(final java.io.InputStream is, final boolean lenient, final java.lang.String defaultEncoding) throws java.io.IOException {
        this.defaultEncoding = defaultEncoding;
        final org.apache.commons.io.input.BOMInputStream bom = new org.apache.commons.io.input.BOMInputStream(new java.io.BufferedInputStream(is, org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE), false, org.apache.commons.io.input.XmlStreamReader.BOMS);
        final org.apache.commons.io.input.BOMInputStream pis = new org.apache.commons.io.input.BOMInputStream(bom, true, org.apache.commons.io.input.XmlStreamReader.XML_GUESS_BYTES);
        this.encoding = doRawStream(bom, pis, lenient);
        this.reader = new java.io.InputStreamReader(pis, encoding);
    }

    /**
     * Creates a Reader using the InputStream of a URL.
     * <p>
     * If the URL is not of type HTTP and there is not 'content-type' header in
     * the fetched data it uses the same logic used for Files.
     * <p>
     * If the URL is a HTTP Url or there is a 'content-type' header in the
     * fetched data it uses the same logic used for an InputStream with
     * content-type.
     * <p>
     * It does a lenient charset encoding detection, check the constructor with
     * the lenient parameter for details.
     *
     * @param url
     * 		URL to create a Reader from.
     * @throws IOException
     * 		thrown if there is a problem reading the stream of
     * 		the URL.
     */
    public XmlStreamReader(final java.net.URL url) throws java.io.IOException {
        this(url.openConnection(), null);
    }

    /**
     * Creates a Reader using the InputStream of a URLConnection.
     * <p>
     * If the URLConnection is not of type HttpURLConnection and there is not
     * 'content-type' header in the fetched data it uses the same logic used for
     * files.
     * <p>
     * If the URLConnection is a HTTP Url or there is a 'content-type' header in
     * the fetched data it uses the same logic used for an InputStream with
     * content-type.
     * <p>
     * It does a lenient charset encoding detection, check the constructor with
     * the lenient parameter for details.
     *
     * @param conn
     * 		URLConnection to create a Reader from.
     * @param defaultEncoding
     * 		The default encoding
     * @throws IOException
     * 		thrown if there is a problem reading the stream of
     * 		the URLConnection.
     */
    public XmlStreamReader(final java.net.URLConnection conn, final java.lang.String defaultEncoding) throws java.io.IOException {
        this.defaultEncoding = defaultEncoding;
        final boolean lenient = true;
        final java.lang.String contentType = conn.getContentType();
        final java.io.InputStream is = conn.getInputStream();
        final org.apache.commons.io.input.BOMInputStream bom = new org.apache.commons.io.input.BOMInputStream(new java.io.BufferedInputStream(is, org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE), false, org.apache.commons.io.input.XmlStreamReader.BOMS);
        final org.apache.commons.io.input.BOMInputStream pis = new org.apache.commons.io.input.BOMInputStream(bom, true, org.apache.commons.io.input.XmlStreamReader.XML_GUESS_BYTES);
        if ((conn instanceof java.net.HttpURLConnection) || (contentType != null)) {
            this.encoding = doHttpStream(bom, pis, contentType, lenient);
        }else {
            this.encoding = doRawStream(bom, pis, lenient);
        }
        this.reader = new java.io.InputStreamReader(pis, encoding);
    }

    /**
     * Creates a Reader using an InputStream and the associated content-type
     * header.
     * <p>
     * First it checks if the stream has BOM. If there is not BOM checks the
     * content-type encoding. If there is not content-type encoding checks the
     * XML prolog encoding. If there is not XML prolog encoding uses the default
     * encoding mandated by the content-type MIME type.
     * <p>
     * It does a lenient charset encoding detection, check the constructor with
     * the lenient parameter for details.
     *
     * @param is
     * 		InputStream to create the reader from.
     * @param httpContentType
     * 		content-type header to use for the resolution of
     * 		the charset encoding.
     * @throws IOException
     * 		thrown if there is a problem reading the file.
     */
    public XmlStreamReader(final java.io.InputStream is, final java.lang.String httpContentType) throws java.io.IOException {
        this(is, httpContentType, true);
    }

    /**
     * Creates a Reader using an InputStream and the associated content-type
     * header. This constructor is lenient regarding the encoding detection.
     * <p>
     * First it checks if the stream has BOM. If there is not BOM checks the
     * content-type encoding. If there is not content-type encoding checks the
     * XML prolog encoding. If there is not XML prolog encoding uses the default
     * encoding mandated by the content-type MIME type.
     * <p>
     * If lenient detection is indicated and the detection above fails as per
     * specifications it then attempts the following:
     * <p>
     * If the content type was 'text/html' it replaces it with 'text/xml' and
     * tries the detection again.
     * <p>
     * Else if the XML prolog had a charset encoding that encoding is used.
     * <p>
     * Else if the content type had a charset encoding that encoding is used.
     * <p>
     * Else 'UTF-8' is used.
     * <p>
     * If lenient detection is indicated an XmlStreamReaderException is never
     * thrown.
     *
     * @param is
     * 		InputStream to create the reader from.
     * @param httpContentType
     * 		content-type header to use for the resolution of
     * 		the charset encoding.
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @param defaultEncoding
     * 		The default encoding
     * @throws IOException
     * 		thrown if there is a problem reading the file.
     * @throws XmlStreamReaderException
     * 		thrown if the charset encoding could not
     * 		be determined according to the specs.
     */
    public XmlStreamReader(final java.io.InputStream is, final java.lang.String httpContentType, final boolean lenient, final java.lang.String defaultEncoding) throws java.io.IOException {
        this.defaultEncoding = defaultEncoding;
        final org.apache.commons.io.input.BOMInputStream bom = new org.apache.commons.io.input.BOMInputStream(new java.io.BufferedInputStream(is, org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE), false, org.apache.commons.io.input.XmlStreamReader.BOMS);
        final org.apache.commons.io.input.BOMInputStream pis = new org.apache.commons.io.input.BOMInputStream(bom, true, org.apache.commons.io.input.XmlStreamReader.XML_GUESS_BYTES);
        this.encoding = doHttpStream(bom, pis, httpContentType, lenient);
        this.reader = new java.io.InputStreamReader(pis, encoding);
    }

    /**
     * Creates a Reader using an InputStream and the associated content-type
     * header. This constructor is lenient regarding the encoding detection.
     * <p>
     * First it checks if the stream has BOM. If there is not BOM checks the
     * content-type encoding. If there is not content-type encoding checks the
     * XML prolog encoding. If there is not XML prolog encoding uses the default
     * encoding mandated by the content-type MIME type.
     * <p>
     * If lenient detection is indicated and the detection above fails as per
     * specifications it then attempts the following:
     * <p>
     * If the content type was 'text/html' it replaces it with 'text/xml' and
     * tries the detection again.
     * <p>
     * Else if the XML prolog had a charset encoding that encoding is used.
     * <p>
     * Else if the content type had a charset encoding that encoding is used.
     * <p>
     * Else 'UTF-8' is used.
     * <p>
     * If lenient detection is indicated an XmlStreamReaderException is never
     * thrown.
     *
     * @param is
     * 		InputStream to create the reader from.
     * @param httpContentType
     * 		content-type header to use for the resolution of
     * 		the charset encoding.
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @throws IOException
     * 		thrown if there is a problem reading the file.
     * @throws XmlStreamReaderException
     * 		thrown if the charset encoding could not
     * 		be determined according to the specs.
     */
    public XmlStreamReader(final java.io.InputStream is, final java.lang.String httpContentType, final boolean lenient) throws java.io.IOException {
        this(is, httpContentType, lenient, null);
    }

    /**
     * Closes the XmlStreamReader stream.
     *
     * @throws IOException
     * 		thrown if there was a problem closing the stream.
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        reader.close();
    }

    /**
     * Process a HTTP stream.
     *
     * @param bom
     * 		BOMInputStream to detect byte order marks
     * @param pis
     * 		BOMInputStream to guess XML encoding
     * @param httpContentType
     * 		The HTTP content type
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @return the encoding to be used
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    private java.lang.String doHttpStream(final org.apache.commons.io.input.BOMInputStream bom, final org.apache.commons.io.input.BOMInputStream pis, final java.lang.String httpContentType, final boolean lenient) throws java.io.IOException {
        final java.lang.String bomEnc = bom.getBOMCharsetName();
        final java.lang.String xmlGuessEnc = pis.getBOMCharsetName();
        final java.lang.String xmlEnc = org.apache.commons.io.input.XmlStreamReader.getXmlProlog(pis, xmlGuessEnc);
        try {
            return calculateHttpEncoding(httpContentType, bomEnc, xmlGuessEnc, xmlEnc, lenient);
        } catch (final org.apache.commons.io.input.XmlStreamReaderException ex) {
            if (lenient) {
                return doLenientDetection(httpContentType, ex);
            }
            throw ex;
        }
    }

    /**
     * Do lenient detection.
     *
     * @param httpContentType
     * 		content-type header to use for the resolution of
     * 		the charset encoding.
     * @param ex
     * 		The thrown exception
     * @return the encoding
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    private java.lang.String doLenientDetection(java.lang.String httpContentType, org.apache.commons.io.input.XmlStreamReaderException ex) throws java.io.IOException {
        if ((httpContentType != null) && (httpContentType.startsWith("text/html"))) {
            httpContentType = httpContentType.substring("text/html".length());
            httpContentType = "text/xml" + httpContentType;
            try {
                return calculateHttpEncoding(httpContentType, ex.getBomEncoding(), ex.getXmlGuessEncoding(), ex.getXmlEncoding(), true);
            } catch (final org.apache.commons.io.input.XmlStreamReaderException ex2) {
                ex = ex2;
            }
        }
        java.lang.String encoding = ex.getXmlEncoding();
        if (encoding == null) {
            encoding = ex.getContentTypeEncoding();
        }
        if (encoding == null) {
            encoding = ((defaultEncoding) == null) ? org.apache.commons.io.input.XmlStreamReader.UTF_8 : defaultEncoding;
        }
        return encoding;
    }

    /**
     * Calculate the raw encoding.
     *
     * @param bomEnc
     * 		BOM encoding
     * @param xmlGuessEnc
     * 		XML Guess encoding
     * @param xmlEnc
     * 		XML encoding
     * @return the raw encoding
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    java.lang.String calculateRawEncoding(final java.lang.String bomEnc, final java.lang.String xmlGuessEnc, final java.lang.String xmlEnc) throws java.io.IOException {
        // BOM is Null
        if (bomEnc == null) {
            if ((xmlGuessEnc == null) || (xmlEnc == null)) {
                return (defaultEncoding) == null ? org.apache.commons.io.input.XmlStreamReader.UTF_8 : defaultEncoding;
            }
            if ((xmlEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16)) && ((xmlGuessEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16BE)) || (xmlGuessEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16LE)))) {
                return xmlGuessEnc;
            }
            return xmlEnc;
        }
        // BOM is UTF-8
        if (bomEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_8)) {
            if ((xmlGuessEnc != null) && (!(xmlGuessEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_8)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            if ((xmlEnc != null) && (!(xmlEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_8)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            return bomEnc;
        }
        // BOM is UTF-16BE or UTF-16LE
        if ((bomEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16BE)) || (bomEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16LE))) {
            if ((xmlGuessEnc != null) && (!(xmlGuessEnc.equals(bomEnc)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            if (((xmlEnc != null) && (!(xmlEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16)))) && (!(xmlEnc.equals(bomEnc)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            return bomEnc;
        }
        // BOM is UTF-32BE or UTF-32LE
        if ((bomEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32BE)) || (bomEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32LE))) {
            if ((xmlGuessEnc != null) && (!(xmlGuessEnc.equals(bomEnc)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            if (((xmlEnc != null) && (!(xmlEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32)))) && (!(xmlEnc.equals(bomEnc)))) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
            }
            return bomEnc;
        }
        // BOM is something else
        final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.RAW_EX_2, bomEnc, xmlGuessEnc, xmlEnc);
        throw new org.apache.commons.io.input.XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
    }

    /**
     * Calculate the HTTP encoding.
     *
     * @param httpContentType
     * 		The HTTP content type
     * @param bomEnc
     * 		BOM encoding
     * @param xmlGuessEnc
     * 		XML Guess encoding
     * @param xmlEnc
     * 		XML encoding
     * @param lenient
     * 		indicates if the charset encoding detection should be
     * 		relaxed.
     * @return the HTTP encoding
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    java.lang.String calculateHttpEncoding(final java.lang.String httpContentType, final java.lang.String bomEnc, final java.lang.String xmlGuessEnc, final java.lang.String xmlEnc, final boolean lenient) throws java.io.IOException {
        // Lenient and has XML encoding
        if (lenient && (xmlEnc != null)) {
            return xmlEnc;
        }
        // Determine mime/encoding content types from HTTP Content Type
        final java.lang.String cTMime = org.apache.commons.io.input.XmlStreamReader.getContentTypeMime(httpContentType);
        final java.lang.String cTEnc = org.apache.commons.io.input.XmlStreamReader.getContentTypeEncoding(httpContentType);
        final boolean appXml = org.apache.commons.io.input.XmlStreamReader.isAppXml(cTMime);
        final boolean textXml = org.apache.commons.io.input.XmlStreamReader.isTextXml(cTMime);
        // Mime type NOT "application/xml" or "text/xml"
        if ((!appXml) && (!textXml)) {
            final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.HTTP_EX_3, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            throw new org.apache.commons.io.input.XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
        }
        // No content type encoding
        if (cTEnc == null) {
            if (appXml) {
                return calculateRawEncoding(bomEnc, xmlGuessEnc, xmlEnc);
            }
            return (defaultEncoding) == null ? org.apache.commons.io.input.XmlStreamReader.US_ASCII : defaultEncoding;
        }
        // UTF-16BE or UTF-16LE content type encoding
        if ((cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16BE)) || (cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16LE))) {
            if (bomEnc != null) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.HTTP_EX_1, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            }
            return cTEnc;
        }
        // UTF-16 content type encoding
        if (cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_16)) {
            if ((bomEnc != null) && (bomEnc.startsWith(org.apache.commons.io.input.XmlStreamReader.UTF_16))) {
                return bomEnc;
            }
            final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.HTTP_EX_2, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            throw new org.apache.commons.io.input.XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
        }
        // UTF-32BE or UTF-132E content type encoding
        if ((cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32BE)) || (cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32LE))) {
            if (bomEnc != null) {
                final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.HTTP_EX_1, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                throw new org.apache.commons.io.input.XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            }
            return cTEnc;
        }
        // UTF-32 content type encoding
        if (cTEnc.equals(org.apache.commons.io.input.XmlStreamReader.UTF_32)) {
            if ((bomEnc != null) && (bomEnc.startsWith(org.apache.commons.io.input.XmlStreamReader.UTF_32))) {
                return bomEnc;
            }
            final java.lang.String msg = java.text.MessageFormat.format(org.apache.commons.io.input.XmlStreamReader.HTTP_EX_2, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            throw new org.apache.commons.io.input.XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
        }
        return cTEnc;
    }

    /**
     * Returns MIME type or NULL if httpContentType is NULL.
     *
     * @param httpContentType
     * 		the HTTP content type
     * @return The mime content type
     */
    static java.lang.String getContentTypeMime(final java.lang.String httpContentType) {
        java.lang.String mime = null;
        if (httpContentType != null) {
            final int i = httpContentType.indexOf(";");
            if (i >= 0) {
                mime = httpContentType.substring(0, i);
            }else {
                mime = httpContentType;
            }
            mime = mime.trim();
        }
        return mime;
    }

    /**
     * Returns charset parameter value, NULL if not present, NULL if
     * httpContentType is NULL.
     *
     * @param httpContentType
     * 		the HTTP content type
     * @return The content type encoding (upcased)
     */
    static java.lang.String getContentTypeEncoding(final java.lang.String httpContentType) {
        java.lang.String encoding = null;
        if (httpContentType != null) {
            final int i = httpContentType.indexOf(";");
            if (i > (-1)) {
                final java.lang.String postMime = httpContentType.substring((i + 1));
                final java.util.regex.Matcher m = org.apache.commons.io.input.XmlStreamReader.CHARSET_PATTERN.matcher(postMime);
                encoding = (m.find()) ? m.group(1) : null;
                encoding = (encoding != null) ? encoding.toUpperCase(java.util.Locale.ROOT) : null;
            }
        }
        return encoding;
    }

    /**
     * Returns the encoding declared in the <?xml encoding=...?>, NULL if none.
     *
     * @param is
     * 		InputStream to create the reader from.
     * @param guessedEnc
     * 		guessed encoding
     * @return the encoding declared in the <?xml encoding=...?>
     * @throws IOException
     * 		thrown if there is a problem reading the stream.
     */
    private static java.lang.String getXmlProlog(final java.io.InputStream is, final java.lang.String guessedEnc) throws java.io.IOException {
        java.lang.String encoding = null;
        if (guessedEnc != null) {
            final byte[] bytes = new byte[org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE];
            is.mark(org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE);
            int offset = 0;
            int max = org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE;
            int c = is.read(bytes, offset, max);
            int firstGT = -1;
            java.lang.String xmlProlog = "";// avoid possible NPE warning (cannot happen; this just silences the warning)
            
            while (((c != (-1)) && (firstGT == (-1))) && (offset < (org.apache.commons.io.input.XmlStreamReader.BUFFER_SIZE))) {
                offset += c;
                max -= c;
                c = is.read(bytes, offset, max);
                xmlProlog = new java.lang.String(bytes, 0, offset, guessedEnc);
                firstGT = xmlProlog.indexOf('>');
            } 
            if (firstGT == (-1)) {
                if (c == (-1)) {
                    throw new java.io.IOException("Unexpected end of XML stream");
                }
                throw new java.io.IOException((("XML prolog or ROOT element not found on first " + offset) + " bytes"));
            }
            final int bytesRead = offset;
            if (bytesRead > 0) {
                is.reset();
                final java.io.BufferedReader bReader = new java.io.BufferedReader(new java.io.StringReader(xmlProlog.substring(0, (firstGT + 1))));
                final java.lang.StringBuffer prolog = new java.lang.StringBuffer();
                java.lang.String line = bReader.readLine();
                while (line != null) {
                    prolog.append(line);
                    line = bReader.readLine();
                } 
                final java.util.regex.Matcher m = org.apache.commons.io.input.XmlStreamReader.ENCODING_PATTERN.matcher(prolog);
                if (m.find()) {
                    encoding = m.group(1).toUpperCase(java.util.Locale.ROOT);
                    encoding = encoding.substring(1, ((encoding.length()) - 1));
                }
            }
        }
        return encoding;
    }

    /**
     * Indicates if the MIME type belongs to the APPLICATION XML family.
     *
     * @param mime
     * 		The mime type
     * @return true if the mime type belongs to the APPLICATION XML family,
     * otherwise false
     */
    static boolean isAppXml(final java.lang.String mime) {
        return (mime != null) && ((((mime.equals("application/xml")) || (mime.equals("application/xml-dtd"))) || (mime.equals("application/xml-external-parsed-entity"))) || ((mime.startsWith("application/")) && (mime.endsWith("+xml"))));
    }

    /**
     * Indicates if the MIME type belongs to the TEXT XML family.
     *
     * @param mime
     * 		The mime type
     * @return true if the mime type belongs to the TEXT XML family,
     * otherwise false
     */
    static boolean isTextXml(final java.lang.String mime) {
        return (mime != null) && (((mime.equals("text/xml")) || (mime.equals("text/xml-external-parsed-entity"))) || ((mime.startsWith("text/")) && (mime.endsWith("+xml"))));
    }
}

