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
 * Simple implementation of the unix "tail -f" functionality.
 *
 * <h2>1. Create a TailerListener implementation</h2>
 * <p>
 * First you need to create a {@link TailerListener} implementation
 * ({@link TailerListenerAdapter} is provided for convenience so that you don't have to
 * implement every method).
 * </p>
 *
 * <p>For example:</p>
 * <pre>
 *  public class MyTailerListener extends TailerListenerAdapter {
 *      public void handle(String line) {
 *          System.out.println(line);
 *      }
 *  }</pre>
 *
 * <h2>2. Using a Tailer</h2>
 *
 * <p>
 * You can create and use a Tailer in one of three ways:
 * </p>
 * <ul>
 *   <li>Using one of the static helper methods:
 *     <ul>
 *       <li>{@link Tailer#create(File, TailerListener)}</li>
 *       <li>{@link Tailer#create(File, TailerListener, long)}</li>
 *       <li>{@link Tailer#create(File, TailerListener, long, boolean)}</li>
 *     </ul>
 *   </li>
 *   <li>Using an {@link java.util.concurrent.Executor}</li>
 *   <li>Using an {@link Thread}</li>
 * </ul>
 *
 * <p>
 * An example of each of these is shown below.
 * </p>
 *
 * <h3>2.1 Using the static helper method</h3>
 *
 * <pre>
 *      TailerListener listener = new MyTailerListener();
 *      Tailer tailer = Tailer.create(file, listener, delay);</pre>
 *
 * <h3>2.2 Using an Executor</h3>
 *
 * <pre>
 *      TailerListener listener = new MyTailerListener();
 *      Tailer tailer = new Tailer(file, listener, delay);
 *
 *      // stupid executor impl. for demo purposes
 *      Executor executor = new Executor() {
 *          public void execute(Runnable command) {
 *              command.run();
 *           }
 *      };
 *
 *      executor.execute(tailer);
 * </pre>
 *
 *
 * <h3>2.3 Using a Thread</h3>
 * <pre>
 *      TailerListener listener = new MyTailerListener();
 *      Tailer tailer = new Tailer(file, listener, delay);
 *      Thread thread = new Thread(tailer);
 *      thread.setDaemon(true); // optional
 *      thread.start();</pre>
 *
 * <h2>3. Stopping a Tailer</h2>
 * <p>Remember to stop the tailer when you have done with it:</p>
 * <pre>
 *      tailer.stop();
 * </pre>
 *
 * <h2>4. Interrupting a Tailer</h2>
 * <p>You can interrupt the thread a tailer is running on by calling {@link Thread#interrupt()}.</p>
 * <pre>
 *      thread.interrupt();
 * </pre>
 * <p>If you interrupt a tailer, the tailer listener is called with the {@link InterruptedException}.</p>
 *
 * <p>The file is read using the default charset; this can be overridden if necessary</p>
 *
 * @see TailerListener
 * @see TailerListenerAdapter
 * @version $Id$
 * @since 2.0
 * @since 2.5 Updated behavior and documentation for {@link Thread#interrupt()}
 */
/* method "getFile" was removed from this class because it was not covered by the test suite */
/* method "getDelay" was removed from this class because it was not covered by the test suite */
public class Tailer implements java.lang.Runnable {
    private static final int DEFAULT_DELAY_MILLIS = 1000;

    private static final java.lang.String RAF_MODE = "r";

    private static final int DEFAULT_BUFSIZE = 4096;

    // The default charset used for reading files
    private static final java.nio.charset.Charset DEFAULT_CHARSET = java.nio.charset.Charset.defaultCharset();

    /**
     * Buffer on top of RandomAccessFile.
     */
    private final byte[] inbuf;

    /**
     * The file which will be tailed.
     */
    private final java.io.File file;

    /**
     * The character set that will be used to read the file.
     */
    private final java.nio.charset.Charset charset;

    /**
     * The amount of time to wait for the file to be updated.
     */
    private final long delayMillis;

    /**
     * Whether to tail from the end or start of file
     */
    private final boolean end;

    /**
     * The listener to notify of events when tailing.
     */
    private final org.apache.commons.io.input.TailerListener listener;

    /**
     * Whether to close and reopen the file whilst waiting for more input.
     */
    private final boolean reOpen;

    /**
     * The tailer will run as long as this value is true.
     */
    private volatile boolean run = true;

    /**
     * Creates a Tailer for the given file, starting from the beginning, with the default delay of 1.0s.
     *
     * @param file
     * 		The file to follow.
     * @param listener
     * 		the TailerListener to use.
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener) {
        this(file, listener, org.apache.commons.io.input.Tailer.DEFAULT_DELAY_MILLIS);
    }

    /**
     * Creates a Tailer for the given file, starting from the beginning.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis) {
        this(file, listener, delayMillis, false);
    }

    /**
     * Creates a Tailer for the given file, with a delay other than the default 1.0s.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end) {
        this(file, listener, delayMillis, end, org.apache.commons.io.input.Tailer.DEFAULT_BUFSIZE);
    }

    /**
     * Creates a Tailer for the given file, with a delay other than the default 1.0s.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		if true, close and reopen the file between reading chunks
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen) {
        this(file, listener, delayMillis, end, reOpen, org.apache.commons.io.input.Tailer.DEFAULT_BUFSIZE);
    }

    /**
     * Creates a Tailer for the given file, with a specified buffer size.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param bufSize
     * 		Buffer size
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final int bufSize) {
        this(file, listener, delayMillis, end, false, bufSize);
    }

    /**
     * Creates a Tailer for the given file, with a specified buffer size.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		if true, close and reopen the file between reading chunks
     * @param bufSize
     * 		Buffer size
     */
    public Tailer(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int bufSize) {
        this(file, org.apache.commons.io.input.Tailer.DEFAULT_CHARSET, listener, delayMillis, end, reOpen, bufSize);
    }

    /**
     * Creates a Tailer for the given file, with a specified buffer size.
     *
     * @param file
     * 		the file to follow.
     * @param charset
     * 		the Charset to be used for reading the file
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		if true, close and reopen the file between reading chunks
     * @param bufSize
     * 		Buffer size
     */
    public Tailer(final java.io.File file, final java.nio.charset.Charset charset, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int bufSize) {
        this.file = file;
        this.delayMillis = delayMillis;
        this.end = end;
        this.inbuf = new byte[bufSize];
        // Save and prepare the listener
        this.listener = listener;
        listener.init(this);
        this.reOpen = reOpen;
        this.charset = charset;
    }

    /**
     * Creates and starts a Tailer for the given file.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param bufSize
     * 		buffer size.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final int bufSize) {
        return org.apache.commons.io.input.Tailer.create(file, listener, delayMillis, end, false, bufSize);
    }

    /**
     * Creates and starts a Tailer for the given file.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		whether to close/reopen the file between chunks
     * @param bufSize
     * 		buffer size.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int bufSize) {
        return org.apache.commons.io.input.Tailer.create(file, org.apache.commons.io.input.Tailer.DEFAULT_CHARSET, listener, delayMillis, end, reOpen, bufSize);
    }

    /**
     * Creates and starts a Tailer for the given file.
     *
     * @param file
     * 		the file to follow.
     * @param charset
     * 		the character set to use for reading the file
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		whether to close/reopen the file between chunks
     * @param bufSize
     * 		buffer size.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final java.nio.charset.Charset charset, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int bufSize) {
        final org.apache.commons.io.input.Tailer tailer = new org.apache.commons.io.input.Tailer(file, charset, listener, delayMillis, end, reOpen, bufSize);
        final java.lang.Thread thread = new java.lang.Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    /**
     * Creates and starts a Tailer for the given file with default buffer size.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end) {
        return org.apache.commons.io.input.Tailer.create(file, listener, delayMillis, end, org.apache.commons.io.input.Tailer.DEFAULT_BUFSIZE);
    }

    /**
     * Creates and starts a Tailer for the given file with default buffer size.
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @param end
     * 		Set to true to tail from the end of the file, false to tail from the beginning of the file.
     * @param reOpen
     * 		whether to close/reopen the file between chunks
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen) {
        return org.apache.commons.io.input.Tailer.create(file, listener, delayMillis, end, reOpen, org.apache.commons.io.input.Tailer.DEFAULT_BUFSIZE);
    }

    /**
     * Creates and starts a Tailer for the given file, starting at the beginning of the file
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @param delayMillis
     * 		the delay between checks of the file for new content in milliseconds.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener, final long delayMillis) {
        return org.apache.commons.io.input.Tailer.create(file, listener, delayMillis, false);
    }

    /**
     * Creates and starts a Tailer for the given file, starting at the beginning of the file
     * with the default delay of 1.0s
     *
     * @param file
     * 		the file to follow.
     * @param listener
     * 		the TailerListener to use.
     * @return The new tailer
     */
    public static org.apache.commons.io.input.Tailer create(final java.io.File file, final org.apache.commons.io.input.TailerListener listener) {
        return org.apache.commons.io.input.Tailer.create(file, listener, org.apache.commons.io.input.Tailer.DEFAULT_DELAY_MILLIS, false);
    }

    /**
     * Gets whether to keep on running.
     *
     * @return whether to keep on running.
     * @since 2.5
     */
    protected boolean getRun() {
        return run;
    }

    /**
     * Follows changes in the file, calling the TailerListener's handle method for each new line.
     */
    @java.lang.Override
    public void run() {
        java.io.RandomAccessFile reader = null;
        try {
            long last = 0;// The last time the file was checked for changes
            
            long position = 0;// position within the file
            
            // Open the file
            while ((getRun()) && (reader == null)) {
                try {
                    reader = new java.io.RandomAccessFile(file, org.apache.commons.io.input.Tailer.RAF_MODE);
                } catch (final java.io.FileNotFoundException e) {
                    listener.fileNotFound();
                }
                if (reader == null) {
                    java.lang.Thread.sleep(delayMillis);
                }else {
                    // The current position in the file
                    position = (end) ? file.length() : 0;
                    last = file.lastModified();
                    reader.seek(position);
                }
            } 
            while (getRun()) {
                final boolean newer = org.apache.commons.io.FileUtils.isFileNewer(file, last);// IO-279, must be done first
                
                // Check the file length to see if it was rotated
                final long length = file.length();
                if (length < position) {
                    // File was rotated
                    listener.fileRotated();
                    // Reopen the reader after rotation ensuring that the old file is closed iff we re-open it
                    // successfully
                    try (java.io.RandomAccessFile save = reader) {
                        reader = new java.io.RandomAccessFile(file, org.apache.commons.io.input.Tailer.RAF_MODE);
                        // At this point, we're sure that the old file is rotated
                        // Finish scanning the old file and then we'll start with the new one
                        try {
                            readLines(save);
                        } catch (final java.io.IOException ioe) {
                            listener.handle(ioe);
                        }
                        position = 0;
                    } catch (final java.io.FileNotFoundException e) {
                        // in this case we continue to use the previous reader and position values
                        listener.fileNotFound();
                        java.lang.Thread.sleep(delayMillis);
                    }
                    continue;
                }
                // File was not rotated
                // See if the file needs to be read again
                /* This can happen if the file is truncated or overwritten with the exact same length of
                information. In cases like this, the file position needs to be reset
                 */
                // cannot be null here
                // Now we can read new lines
                if (length > position) {
                    // The file has more content than it did last time
                    position = readLines(reader);
                    last = file.lastModified();
                }/* This can happen if the file is truncated or overwritten with the exact same length of
                information. In cases like this, the file position needs to be reset
                 */
                // cannot be null here
                // Now we can read new lines
                else
                    if (newer) {
                        position = 0;
                        reader.seek(position);
                        position = readLines(reader);
                        last = file.lastModified();
                    }
                
                if ((reOpen) && (reader != null)) {
                    reader.close();
                }
                java.lang.Thread.sleep(delayMillis);
                if ((getRun()) && (reOpen)) {
                    reader = new java.io.RandomAccessFile(file, org.apache.commons.io.input.Tailer.RAF_MODE);
                    reader.seek(position);
                }
            } 
        } catch (final java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            listener.handle(e);
        } catch (final java.lang.Exception e) {
            listener.handle(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (final java.io.IOException e) {
                listener.handle(e);
            }
            stop();
        }
    }

    /**
     * Allows the tailer to complete its current loop and return.
     */
    public void stop() {
        this.run = false;
    }

    /**
     * Read new lines.
     *
     * @param reader
     * 		The file to read
     * @return The new position after the lines have been read
     * @throws java.io.IOException
     * 		if an I/O error occurs.
     */
    private long readLines(final java.io.RandomAccessFile reader) throws java.io.IOException {
        try (java.io.ByteArrayOutputStream lineBuf = new java.io.ByteArrayOutputStream(64)) {
            long pos = reader.getFilePointer();
            long rePos = pos;// position to re-read
            
            int num;
            boolean seenCR = false;
            while ((getRun()) && ((num = reader.read(inbuf)) != (org.apache.commons.io.IOUtils.EOF))) {
                for (int i = 0; i < num; i++) {
                    final byte ch = inbuf[i];
                    switch (ch) {
                        case '\n' :
                            seenCR = false;// swallow CR before LF
                            
                            listener.handle(new java.lang.String(lineBuf.toByteArray(), charset));
                            lineBuf.reset();
                            rePos = (pos + i) + 1;
                            break;
                        case '\r' :
                            if (seenCR) {
                                lineBuf.write('\r');
                            }
                            seenCR = true;
                            break;
                        default :
                            // swallow final CR
                            if (seenCR) {
                                seenCR = false;
                                listener.handle(new java.lang.String(lineBuf.toByteArray(), charset));
                                lineBuf.reset();
                                rePos = (pos + i) + 1;
                            }
                            lineBuf.write(ch);
                    }
                }
                pos = reader.getFilePointer();
            } 
            reader.seek(rePos);// Ensure we can re-read if necessary
            
            if ((listener) instanceof org.apache.commons.io.input.TailerListenerAdapter) {
                ((org.apache.commons.io.input.TailerListenerAdapter) (listener)).endOfFileReached();
            }
            return rePos;
        }
    }
}

