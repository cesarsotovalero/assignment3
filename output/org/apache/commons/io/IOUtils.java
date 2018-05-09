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
 * General IO stream manipulation utilities.
 * <p>
 * This class provides static utility methods for input/output operations.
 * <ul>
 * <li><b>[Deprecated]</b> closeQuietly - these methods close a stream ignoring nulls and exceptions
 * <li>toXxx/read - these methods read data from a stream
 * <li>write - these methods write data to a stream
 * <li>copy - these methods copy all the data from one stream to another
 * <li>contentEquals - these methods compare the content of two streams
 * </ul>
 * <p>
 * The byte-to-char methods and char-to-byte methods involve a conversion step.
 * Two methods are provided in each case, one that uses the platform default
 * encoding and the other which allows you to specify an encoding. You are
 * encouraged to always specify an encoding because relying on the platform
 * default can lead to unexpected results, for example when moving from
 * development to production.
 * <p>
 * All the methods in this class that read a stream are buffered internally.
 * This means that there is no cause to use a <code>BufferedInputStream</code>
 * or <code>BufferedReader</code>. The default buffer size of 4K has been shown
 * to be efficient in tests.
 * <p>
 * The various copy methods all delegate the actual copying to one of the following methods:
 * <ul>
 * <li>{@link #copyLarge(InputStream, OutputStream, byte[])}</li>
 * <li>{@link #copyLarge(InputStream, OutputStream, long, long, byte[])}</li>
 * <li>{@link #copyLarge(Reader, Writer, char[])}</li>
 * <li>{@link #copyLarge(Reader, Writer, long, long, char[])}</li>
 * </ul>
 * For example, {@link #copy(InputStream, OutputStream)} calls {@link #copyLarge(InputStream, OutputStream)}
 * which calls {@link #copy(InputStream, OutputStream, int)} which creates the buffer and calls
 * {@link #copyLarge(InputStream, OutputStream, byte[])}.
 * <p>
 * Applications can re-use buffers by using the underlying methods directly.
 * This may improve performance for applications that need to do a lot of copying.
 * <p>
 * Wherever possible, the methods in this class do <em>not</em> flush or close
 * the stream. This is to avoid making non-portable assumptions about the
 * streams' origin and further use. Thus the caller is still responsible for
 * closing streams after use.
 * <p>
 * Origin of code: Excalibur.
 */
/* method "contentEquals" was removed from this class because it was not covered by the test suite */
/* method "contentEquals" was removed from this class because it was not covered by the test suite */
/* method "contentEqualsIgnoreEOL" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "resourceToByteArray" was removed from this class because it was not covered by the test suite */
/* method "resourceToByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toByteArray" was removed from this class because it was not covered by the test suite */
/* method "toCharArray" was removed from this class because it was not covered by the test suite */
/* method "toCharArray" was removed from this class because it was not covered by the test suite */
/* method "toCharArray" was removed from this class because it was not covered by the test suite */
/* method "toCharArray" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "read" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "toBufferedReader" was removed from this class because it was not covered by the test suite */
/* method "toBufferedReader" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "buffer" was removed from this class because it was not covered by the test suite */
/* method "toBufferedInputStream" was removed from this class because it was not covered by the test suite */
/* method "toBufferedInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "toInputStream" was removed from this class because it was not covered by the test suite */
/* method "resourceToString" was removed from this class because it was not covered by the test suite */
/* method "resourceToString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "resourceToURL" was removed from this class because it was not covered by the test suite */
/* method "resourceToURL" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "copyLarge" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "skip" was removed from this class because it was not covered by the test suite */
/* method "lineIterator" was removed from this class because it was not covered by the test suite */
/* method "lineIterator" was removed from this class because it was not covered by the test suite */
/* method "lineIterator" was removed from this class because it was not covered by the test suite */
/* method "close" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "copy" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "readFully" was removed from this class because it was not covered by the test suite */
/* method "skipFully" was removed from this class because it was not covered by the test suite */
/* method "skipFully" was removed from this class because it was not covered by the test suite */
/* method "skipFully" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "writeChunked" was removed from this class because it was not covered by the test suite */
/* method "writeChunked" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
public class IOUtils {
    // NOTE: This class is focused on InputStream, OutputStream, Reader and
    // Writer. Each method should take at least one of these as a parameter,
    // or return one of them.
    /**
     * Represents the end-of-file (or stream).
     *
     * @since 2.5 (made public)
     */
    public static final int EOF = -1;

    /**
     * The Unix directory separator character.
     */
    public static final char DIR_SEPARATOR_UNIX = '/';

    /**
     * The Windows directory separator character.
     */
    public static final char DIR_SEPARATOR_WINDOWS = '\\';

    /**
     * The system directory separator character.
     */
    public static final char DIR_SEPARATOR = java.io.File.separatorChar;

    /**
     * The Unix line separator string.
     */
    public static final java.lang.String LINE_SEPARATOR_UNIX = "\n";

    /**
     * The Windows line separator string.
     */
    public static final java.lang.String LINE_SEPARATOR_WINDOWS = "\r\n";

    /**
     * The system line separator string.
     */
    public static final java.lang.String LINE_SEPARATOR;

    static {
        // avoid security issues
        try (final org.apache.commons.io.output.StringBuilderWriter buf = new org.apache.commons.io.output.StringBuilderWriter(4);final java.io.PrintWriter out = new java.io.PrintWriter(buf)) {
            out.println();
            LINE_SEPARATOR = buf.toString();
        }
    }

    /**
     * The default buffer size ({@value}) to use for
     * {@link #copyLarge(InputStream, OutputStream)}
     * and
     * {@link #copyLarge(Reader, Writer)}
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * The default buffer size to use for the skip() methods.
     */
    private static final int SKIP_BUFFER_SIZE = 2048;

    // Allocated in the relevant skip method if necessary.
    /* These buffers are static and are shared between threads.
    This is possible because the buffers are write-only - the contents are never read.
    
    N.B. there is no need to synchronize when creating these because:
    - we don't care if the buffer is created multiple times (the data is ignored)
    - we always use the same size buffer, so if it it is recreated it will still be OK
    (if the buffer size were variable, we would need to synch. to ensure some other thread
    did not create a smaller one)
     */
    private static char[] SKIP_CHAR_BUFFER;

    private static byte[] SKIP_BYTE_BUFFER;

    /**
     * Instances should NOT be constructed in standard programming.
     */
    public IOUtils() {
        super();
    }

    /**
     * Closes an <code>Reader</code> unconditionally.
     * <p>
     * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   char[] data = new char[1024];
     *   Reader in = null;
     *   try {
     *       in = new FileReader("foo.txt");
     *       in.read(data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     *
     * @param input
     * 		the Reader to close, may be null or already closed
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.Reader input) {
        org.apache.commons.io.IOUtils.closeQuietly(((java.io.Closeable) (input)));
    }

    /**
     * Closes an <code>Writer</code> unconditionally.
     * <p>
     * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Writer out = null;
     *   try {
     *       out = new StringWriter();
     *       out.write("Hello World");
     *       out.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(out);
     *   }
     * </pre>
     *
     * @param output
     * 		the Writer to close, may be null or already closed
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.Writer output) {
        org.apache.commons.io.IOUtils.closeQuietly(((java.io.Closeable) (output)));
    }

    /**
     * Closes an <code>InputStream</code> unconditionally.
     * <p>
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   byte[] data = new byte[1024];
     *   InputStream in = null;
     *   try {
     *       in = new FileInputStream("foo.txt");
     *       in.read(data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     *
     * @param input
     * 		the InputStream to close, may be null or already closed
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.InputStream input) {
        org.apache.commons.io.IOUtils.closeQuietly(((java.io.Closeable) (input)));
    }

    /**
     * Closes an <code>OutputStream</code> unconditionally.
     * <p>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     * byte[] data = "Hello, World".getBytes();
     *
     * OutputStream out = null;
     * try {
     *     out = new FileOutputStream("foo.txt");
     *     out.write(data);
     *     out.close(); //close errors are handled
     * } catch (IOException e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(out);
     * }
     * </pre>
     *
     * @param output
     * 		the OutputStream to close, may be null or already closed
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.OutputStream output) {
        org.apache.commons.io.IOUtils.closeQuietly(((java.io.Closeable) (output)));
    }

    /**
     * Closes a <code>Closeable</code> unconditionally.
     * <p>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored. This is typically used in
     * finally blocks.
     * <p>
     * Example code:
     * </p>
     * <pre>
     * Closeable closeable = null;
     * try {
     *     closeable = new FileReader(&quot;foo.txt&quot;);
     *     // process closeable
     *     closeable.close();
     * } catch (Exception e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(closeable);
     * }
     * </pre>
     * <p>
     * Closing all streams:
     * </p>
     * <pre>
     * try {
     *     return IOUtils.copy(inputStream, outputStream);
     * } finally {
     *     IOUtils.closeQuietly(inputStream);
     *     IOUtils.closeQuietly(outputStream);
     * }
     * </pre>
     *
     * @param closeable
     * 		the objects to close, may be null or already closed
     * @since 2.0
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final java.io.IOException ioe) {
            // ignore
        }
    }

    /**
     * Closes a <code>Closeable</code> unconditionally.
     * <p>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored.
     * <p>
     * This is typically used in finally blocks to ensure that the closeable is closed
     * even if an Exception was thrown before the normal close statement was reached.
     * <br>
     * <b>It should not be used to replace the close statement(s)
     * which should be present for the non-exceptional case.</b>
     * <br>
     * It is only intended to simplify tidying up where normal processing has already failed
     * and reporting close failure as well is not necessary or useful.
     * <p>
     * Example code:
     * </p>
     * <pre>
     * Closeable closeable = null;
     * try {
     *     closeable = new FileReader(&quot;foo.txt&quot;);
     *     // processing using the closeable; may throw an Exception
     *     closeable.close(); // Normal close - exceptions not ignored
     * } catch (Exception e) {
     *     // error handling
     * } finally {
     *     <b>IOUtils.closeQuietly(closeable); // In case normal close was skipped due to Exception</b>
     * }
     * </pre>
     * <p>
     * Closing all streams:
     * <br>
     * <pre>
     * try {
     *     return IOUtils.copy(inputStream, outputStream);
     * } finally {
     *     IOUtils.closeQuietly(inputStream, outputStream);
     * }
     * </pre>
     *
     * @param closeables
     * 		the objects to close, may be null or already closed
     * @see #closeQuietly(Closeable)
     * @since 2.5
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.io.Closeable... closeables) {
        if (closeables == null) {
            return ;
        }
        for (final java.io.Closeable closeable : closeables) {
            org.apache.commons.io.IOUtils.closeQuietly(closeable);
        }
    }

    /**
     * Closes a <code>Socket</code> unconditionally.
     * <p>
     * Equivalent to {@link Socket#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Socket socket = null;
     *   try {
     *       socket = new Socket("http://www.foo.com/", 80);
     *       // process socket
     *       socket.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(socket);
     *   }
     * </pre>
     *
     * @param sock
     * 		the Socket to close, may be null or already closed
     * @since 2.0
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.net.Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final java.io.IOException ioe) {
                // ignored
            }
        }
    }

    /**
     * Closes a <code>Selector</code> unconditionally.
     * <p>
     * Equivalent to {@link Selector#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Selector selector = null;
     *   try {
     *       selector = Selector.open();
     *       // process socket
     *
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(selector);
     *   }
     * </pre>
     *
     * @param selector
     * 		the Selector to close, may be null or already closed
     * @since 2.2
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.nio.channels.Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (final java.io.IOException ioe) {
                // ignored
            }
        }
    }

    /**
     * Closes a <code>ServerSocket</code> unconditionally.
     * <p>
     * Equivalent to {@link ServerSocket#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   ServerSocket socket = null;
     *   try {
     *       socket = new ServerSocket();
     *       // process socket
     *       socket.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(socket);
     *   }
     * </pre>
     *
     * @param sock
     * 		the ServerSocket to close, may be null or already closed
     * @since 2.2
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final java.net.ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final java.io.IOException ioe) {
                // ignored
            }
        }
    }
}

