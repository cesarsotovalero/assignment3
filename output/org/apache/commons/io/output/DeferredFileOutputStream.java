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
 * An output stream which will retain data in memory until a specified
 * threshold is reached, and only then commit it to disk. If the stream is
 * closed before the threshold is reached, the data will not be written to
 * disk at all.
 * <p>
 * This class originated in FileUpload processing. In this use case, you do
 * not know in advance the size of the file being uploaded. If the file is small
 * you want to store it in memory (for speed), but if the file is large you want
 * to store it to file (to avoid memory issues).
 */
public class DeferredFileOutputStream extends org.apache.commons.io.output.ThresholdingOutputStream {
    // ----------------------------------------------------------- Data members
    /**
     * The output stream to which data will be written prior to the threshold
     * being reached.
     */
    private org.apache.commons.io.output.ByteArrayOutputStream memoryOutputStream;

    /**
     * The output stream to which data will be written at any given time. This
     * will always be one of <code>memoryOutputStream</code> or
     * <code>diskOutputStream</code>.
     */
    private java.io.OutputStream currentOutputStream;

    /**
     * The file to which output will be directed if the threshold is exceeded.
     */
    private java.io.File outputFile;

    /**
     * The temporary file prefix.
     */
    private final java.lang.String prefix;

    /**
     * The temporary file suffix.
     */
    private final java.lang.String suffix;

    /**
     * The directory to use for temporary files.
     */
    private final java.io.File directory;

    /**
     * True when close() has been called successfully.
     */
    private boolean closed = false;

    // ----------------------------------------------------------- Constructors
    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data to a file beyond that point.
     * The initial buffer size will default to 1024 bytes which is ByteArrayOutputStream's default buffer size.
     *
     * @param threshold
     * 		The number of bytes at which to trigger an event.
     * @param outputFile
     * 		The file to which data is saved beyond the threshold.
     */
    public DeferredFileOutputStream(final int threshold, final java.io.File outputFile) {
        this(threshold, outputFile, null, null, null, org.apache.commons.io.output.ByteArrayOutputStream.DEFAULT_SIZE);
    }

    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data to a file beyond that point.
     *
     * @param threshold
     * 		The number of bytes at which to trigger an event.
     * @param initialBufferSize
     * 		The initial size of the in memory buffer.
     * @param outputFile
     * 		The file to which data is saved beyond the threshold.
     * @since 2.5
     */
    public DeferredFileOutputStream(final int threshold, final int initialBufferSize, final java.io.File outputFile) {
        this(threshold, outputFile, null, null, null, initialBufferSize);
        if (initialBufferSize < 0) {
            throw new java.lang.IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }

    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data to a temporary file beyond that point.
     * The initial buffer size will default to 32 bytes which is ByteArrayOutputStream's default buffer size.
     *
     * @param threshold
     * 		The number of bytes at which to trigger an event.
     * @param prefix
     * 		Prefix to use for the temporary file.
     * @param suffix
     * 		Suffix to use for the temporary file.
     * @param directory
     * 		Temporary file directory.
     * @since 1.4
     */
    public DeferredFileOutputStream(final int threshold, final java.lang.String prefix, final java.lang.String suffix, final java.io.File directory) {
        this(threshold, null, prefix, suffix, directory, org.apache.commons.io.output.ByteArrayOutputStream.DEFAULT_SIZE);
        if (prefix == null) {
            throw new java.lang.IllegalArgumentException("Temporary file prefix is missing");
        }
    }

    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data to a temporary file beyond that point.
     *
     * @param threshold
     * 		The number of bytes at which to trigger an event.
     * @param initialBufferSize
     * 		The initial size of the in memory buffer.
     * @param prefix
     * 		Prefix to use for the temporary file.
     * @param suffix
     * 		Suffix to use for the temporary file.
     * @param directory
     * 		Temporary file directory.
     * @since 2.5
     */
    public DeferredFileOutputStream(final int threshold, final int initialBufferSize, final java.lang.String prefix, final java.lang.String suffix, final java.io.File directory) {
        this(threshold, null, prefix, suffix, directory, initialBufferSize);
        if (prefix == null) {
            throw new java.lang.IllegalArgumentException("Temporary file prefix is missing");
        }
        if (initialBufferSize < 0) {
            throw new java.lang.IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }

    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data either to a file beyond that point.
     *
     * @param threshold
     * 		The number of bytes at which to trigger an event.
     * @param outputFile
     * 		The file to which data is saved beyond the threshold.
     * @param prefix
     * 		Prefix to use for the temporary file.
     * @param suffix
     * 		Suffix to use for the temporary file.
     * @param directory
     * 		Temporary file directory.
     * @param initialBufferSize
     * 		The initial size of the in memory buffer.
     */
    private DeferredFileOutputStream(final int threshold, final java.io.File outputFile, final java.lang.String prefix, final java.lang.String suffix, final java.io.File directory, final int initialBufferSize) {
        super(threshold);
        this.outputFile = outputFile;
        this.prefix = prefix;
        this.suffix = suffix;
        this.directory = directory;
        memoryOutputStream = new org.apache.commons.io.output.ByteArrayOutputStream(initialBufferSize);
        currentOutputStream = memoryOutputStream;
    }

    // --------------------------------------- ThresholdingOutputStream methods
    /**
     * Returns the current output stream. This may be memory based or disk
     * based, depending on the current state with respect to the threshold.
     *
     * @return The underlying output stream.
     * @throws IOException
     * 		if an error occurs.
     */
    @java.lang.Override
    protected java.io.OutputStream getStream() throws java.io.IOException {
        return currentOutputStream;
    }

    /**
     * Switches the underlying output stream from a memory based stream to one
     * that is backed by disk. This is the point at which we realise that too
     * much data is being written to keep in memory, so we elect to switch to
     * disk-based storage.
     *
     * @throws IOException
     * 		if an error occurs.
     */
    @java.lang.Override
    protected void thresholdReached() throws java.io.IOException {
        if ((prefix) != null) {
            outputFile = java.io.File.createTempFile(prefix, suffix, directory);
        }
        org.apache.commons.io.FileUtils.forceMkdirParent(outputFile);
        final java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile);
        try {
            memoryOutputStream.writeTo(fos);
        } catch (final java.io.IOException e) {
            fos.close();
            throw e;
        }
        currentOutputStream = fos;
        memoryOutputStream = null;
    }

    // --------------------------------------------------------- Public methods
    /**
     * Determines whether or not the data for this output stream has been
     * retained in memory.
     *
     * @return {@code true} if the data is available in memory;
     * {@code false} otherwise.
     */
    public boolean isInMemory() {
        return !(isThresholdExceeded());
    }

    /**
     * Returns the data for this output stream as an array of bytes, assuming
     * that the data has been retained in memory. If the data was written to
     * disk, this method returns {@code null}.
     *
     * @return The data for this output stream, or {@code null} if no such
     * data is available.
     */
    public byte[] getData() {
        if ((memoryOutputStream) != null) {
            return memoryOutputStream.toByteArray();
        }
        return null;
    }

    /**
     * Returns either the output file specified in the constructor or
     * the temporary file created or null.
     * <p>
     * If the constructor specifying the file is used then it returns that
     * same output file, even when threshold has not been reached.
     * <p>
     * If constructor specifying a temporary file prefix/suffix is used
     * then the temporary file created once the threshold is reached is returned
     * If the threshold was not reached then {@code null} is returned.
     *
     * @return The file for this output stream, or {@code null} if no such
     * file exists.
     */
    public java.io.File getFile() {
        return outputFile;
    }

    /**
     * Closes underlying output stream, and mark this as closed
     *
     * @throws IOException
     * 		if an error occurs.
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        super.close();
        closed = true;
    }

    /**
     * Writes the data from this output stream to the specified output stream,
     * after it has been closed.
     *
     * @param out
     * 		output stream to write to.
     * @throws IOException
     * 		if this stream is not yet closed or an error occurs.
     */
    public void writeTo(final java.io.OutputStream out) throws java.io.IOException {
        // we may only need to check if this is closed if we are working with a file
        // but we should force the habit of closing wether we are working with
        // a file or memory.
        if (!(closed)) {
            throw new java.io.IOException("Stream not closed");
        }
        if (isInMemory()) {
            memoryOutputStream.writeTo(out);
        }else {
            try (java.io.FileInputStream fis = new java.io.FileInputStream(outputFile)) {
                org.apache.commons.io.IOUtils.copy(fis, out);
            }
        }
    }
}

