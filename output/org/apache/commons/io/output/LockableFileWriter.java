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
 * FileWriter that will create and honor lock files to allow simple
 * cross thread file lock handling.
 * <p>
 * This class provides a simple alternative to <code>FileWriter</code>
 * that will use a lock file to prevent duplicate writes.
 * <p>
 * <b>N.B.</b> the lock file is deleted when {@link #close()} is called
 * - or if the main file cannot be opened initially.
 * In the (unlikely) event that the lockfile cannot be deleted,
 * this is not reported, and subsequent requests using
 * the same lockfile will fail.
 * <p>
 * By default, the file will be overwritten, but this may be changed to append.
 * The lock directory may be specified, but defaults to the system property
 * <code>java.io.tmpdir</code>.
 * The encoding may also be specified, and defaults to the platform default.
 */
/* method "flush" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
public class LockableFileWriter extends java.io.Writer {
    // Cannot extend ProxyWriter, as requires writer to be
    // known when super() is called
    /**
     * The extension for the lock file.
     */
    private static final java.lang.String LCK = ".lck";

    /**
     * The writer to decorate.
     */
    private final java.io.Writer out;

    /**
     * The lock file.
     */
    private final java.io.File lockFile;

    /**
     * Constructs a LockableFileWriter.
     * If the file exists, it is overwritten.
     *
     * @param fileName
     * 		the file to write to, not null
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     */
    public LockableFileWriter(final java.lang.String fileName) throws java.io.IOException {
        this(fileName, false, null);
    }

    /**
     * Constructs a LockableFileWriter.
     *
     * @param fileName
     * 		file to write to, not null
     * @param append
     * 		true if content should be appended, false to overwrite
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     */
    public LockableFileWriter(final java.lang.String fileName, final boolean append) throws java.io.IOException {
        this(fileName, append, null);
    }

    /**
     * Constructs a LockableFileWriter.
     *
     * @param fileName
     * 		the file to write to, not null
     * @param append
     * 		true if content should be appended, false to overwrite
     * @param lockDir
     * 		the directory in which the lock file should be held
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     */
    public LockableFileWriter(final java.lang.String fileName, final boolean append, final java.lang.String lockDir) throws java.io.IOException {
        this(new java.io.File(fileName), append, lockDir);
    }

    /**
     * Constructs a LockableFileWriter.
     * If the file exists, it is overwritten.
     *
     * @param file
     * 		the file to write to, not null
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     */
    public LockableFileWriter(final java.io.File file) throws java.io.IOException {
        this(file, false, null);
    }

    /**
     * Constructs a LockableFileWriter.
     *
     * @param file
     * 		the file to write to, not null
     * @param append
     * 		true if content should be appended, false to overwrite
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     */
    public LockableFileWriter(final java.io.File file, final boolean append) throws java.io.IOException {
        this(file, append, null);
    }

    /**
     * Constructs a LockableFileWriter.
     *
     * @param file
     * 		the file to write to, not null
     * @param append
     * 		true if content should be appended, false to overwrite
     * @param lockDir
     * 		the directory in which the lock file should be held
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     * @deprecated 2.5 use {@link #LockableFileWriter(File, Charset, boolean, String)} instead
     */
    @java.lang.Deprecated
    public LockableFileWriter(final java.io.File file, final boolean append, final java.lang.String lockDir) throws java.io.IOException {
        this(file, java.nio.charset.Charset.defaultCharset(), append, lockDir);
    }

    /**
     * Constructs a LockableFileWriter with a file encoding.
     *
     * @param file
     * 		the file to write to, not null
     * @param encoding
     * 		the encoding to use, null means platform default
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     * @since 2.3
     */
    public LockableFileWriter(final java.io.File file, final java.nio.charset.Charset encoding) throws java.io.IOException {
        this(file, encoding, false, null);
    }

    /**
     * Constructs a LockableFileWriter with a file encoding.
     *
     * @param file
     * 		the file to write to, not null
     * @param encoding
     * 		the encoding to use, null means platform default
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     * @throws java.nio.charset.UnsupportedCharsetException
     * 		thrown instead of {@link java.io.UnsupportedEncodingException} in version 2.2 if the encoding is not
     * 		supported.
     */
    public LockableFileWriter(final java.io.File file, final java.lang.String encoding) throws java.io.IOException {
        this(file, encoding, false, null);
    }

    /**
     * Constructs a LockableFileWriter with a file encoding.
     *
     * @param file
     * 		the file to write to, not null
     * @param encoding
     * 		the encoding to use, null means platform default
     * @param append
     * 		true if content should be appended, false to overwrite
     * @param lockDir
     * 		the directory in which the lock file should be held
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     * @since 2.3
     */
    public LockableFileWriter(java.io.File file, final java.nio.charset.Charset encoding, final boolean append, java.lang.String lockDir) throws java.io.IOException {
        super();
        // init file to create/append
        file = file.getAbsoluteFile();
        if ((file.getParentFile()) != null) {
            org.apache.commons.io.FileUtils.forceMkdir(file.getParentFile());
        }
        if (file.isDirectory()) {
            throw new java.io.IOException("File specified is a directory");
        }
        // init lock file
        if (lockDir == null) {
            lockDir = java.lang.System.getProperty("java.io.tmpdir");
        }
        final java.io.File lockDirFile = new java.io.File(lockDir);
        org.apache.commons.io.FileUtils.forceMkdir(lockDirFile);
        testLockDir(lockDirFile);
        lockFile = new java.io.File(lockDirFile, ((file.getName()) + (org.apache.commons.io.output.LockableFileWriter.LCK)));
        // check if locked
        createLock();
        // init wrapped writer
        out = initWriter(file, encoding, append);
    }

    /**
     * Constructs a LockableFileWriter with a file encoding.
     *
     * @param file
     * 		the file to write to, not null
     * @param encoding
     * 		the encoding to use, null means platform default
     * @param append
     * 		true if content should be appended, false to overwrite
     * @param lockDir
     * 		the directory in which the lock file should be held
     * @throws NullPointerException
     * 		if the file is null
     * @throws IOException
     * 		in case of an I/O error
     * @throws java.nio.charset.UnsupportedCharsetException
     * 		thrown instead of {@link java.io.UnsupportedEncodingException} in version 2.2 if the encoding is not
     * 		supported.
     */
    public LockableFileWriter(final java.io.File file, final java.lang.String encoding, final boolean append, final java.lang.String lockDir) throws java.io.IOException {
        this(file, org.apache.commons.io.Charsets.toCharset(encoding), append, lockDir);
    }

    // -----------------------------------------------------------------------
    /**
     * Tests that we can write to the lock directory.
     *
     * @param lockDir
     * 		the File representing the lock directory
     * @throws IOException
     * 		if we cannot write to the lock directory
     * @throws IOException
     * 		if we cannot find the lock file
     */
    private void testLockDir(final java.io.File lockDir) throws java.io.IOException {
        if (!(lockDir.exists())) {
            throw new java.io.IOException(("Could not find lockDir: " + (lockDir.getAbsolutePath())));
        }
        if (!(lockDir.canWrite())) {
            throw new java.io.IOException(("Could not write to lockDir: " + (lockDir.getAbsolutePath())));
        }
    }

    /**
     * Creates the lock file.
     *
     * @throws IOException
     * 		if we cannot create the file
     */
    private void createLock() throws java.io.IOException {
        synchronized(org.apache.commons.io.output.LockableFileWriter.class) {
            if (!(lockFile.createNewFile())) {
                throw new java.io.IOException((("Can't write file, lock " + (lockFile.getAbsolutePath())) + " exists"));
            }
            lockFile.deleteOnExit();
        }
    }

    /**
     * Initialise the wrapped file writer.
     * Ensure that a cleanup occurs if the writer creation fails.
     *
     * @param file
     * 		the file to be accessed
     * @param encoding
     * 		the encoding to use
     * @param append
     * 		true to append
     * @return The initialised writer
     * @throws IOException
     * 		if an error occurs
     */
    private java.io.Writer initWriter(final java.io.File file, final java.nio.charset.Charset encoding, final boolean append) throws java.io.IOException {
        final boolean fileExistedAlready = file.exists();
        try {
            return new java.io.OutputStreamWriter(new java.io.FileOutputStream(file.getAbsolutePath(), append), org.apache.commons.io.Charsets.toCharset(encoding));
        } catch (java.io.IOException | java.lang.RuntimeException ex) {
            org.apache.commons.io.FileUtils.deleteQuietly(lockFile);
            if (fileExistedAlready == false) {
                org.apache.commons.io.FileUtils.deleteQuietly(file);
            }
            throw ex;
        }
    }

    // -----------------------------------------------------------------------
    /**
     * Closes the file writer and deletes the lockfile (if possible).
     *
     * @throws IOException
     * 		if an I/O error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        try {
            out.close();
        } finally {
            lockFile.delete();
        }
    }
}

