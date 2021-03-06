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
package org.apache.commons.io.monitor;


/**
 * FileAlterationObserver represents the state of files below a root directory,
 * checking the filesystem and notifying listeners of create, change or
 * delete events.
 * <p>
 * To use this implementation:
 * <ul>
 *   <li>Create {@link FileAlterationListener} implementation(s) that process
 *      the file/directory create, change and delete events</li>
 *   <li>Register the listener(s) with a {@link FileAlterationObserver} for
 *       the appropriate directory.</li>
 *   <li>Either register the observer(s) with a {@link FileAlterationMonitor} or
 *       run manually.</li>
 * </ul>
 *
 * <h2>Basic Usage</h2>
 * Create a {@link FileAlterationObserver} for the directory and register the listeners:
 * <pre>
 *      File directory = new File(new File("."), "src");
 *      FileAlterationObserver observer = new FileAlterationObserver(directory);
 *      observer.addListener(...);
 *      observer.addListener(...);
 * </pre>
 * To manually observe a directory, initialize the observer and invoked the
 * {@link #checkAndNotify()} method as required:
 * <pre>
 *      // initialize
 *      observer.init();
 *      ...
 *      // invoke as required
 *      observer.checkAndNotify();
 *      ...
 *      observer.checkAndNotify();
 *      ...
 *      // finished
 *      observer.finish();
 * </pre>
 * Alternatively, register the observer(s) with a {@link FileAlterationMonitor},
 * which creates a new thread, invoking the observer at the specified interval:
 * <pre>
 *      long interval = ...
 *      FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
 *      monitor.addObserver(observer);
 *      monitor.start();
 *      ...
 *      monitor.stop();
 * </pre>
 *
 * <h2>File Filters</h2>
 * This implementation can monitor portions of the file system
 * by using {@link FileFilter}s to observe only the files and/or directories
 * that are of interest. This makes it more efficient and reduces the
 * noise from <i>unwanted</i> file system events.
 * <p>
 * <a href="http://commons.apache.org/io/">Commons IO</a> has a good range of
 * useful, ready made
 * <a href="../filefilter/package-summary.html">File Filter</a>
 * implementations for this purpose.
 * <p>
 * For example, to only observe 1) visible directories and 2) files with a ".java" suffix
 * in a root directory called "src" you could set up a {@link FileAlterationObserver} in the following
 * way:
 * <pre>
 *      // Create a FileFilter
 *      IOFileFilter directories = FileFilterUtils.and(
 *                                      FileFilterUtils.directoryFileFilter(),
 *                                      HiddenFileFilter.VISIBLE);
 *      IOFileFilter files       = FileFilterUtils.and(
 *                                      FileFilterUtils.fileFileFilter(),
 *                                      FileFilterUtils.suffixFileFilter(".java"));
 *      IOFileFilter filter = FileFilterUtils.or(directories, files);
 *
 *      // Create the File system observer and register File Listeners
 *      FileAlterationObserver observer = new FileAlterationObserver(new File("src"), filter);
 *      observer.addListener(...);
 *      observer.addListener(...);
 * </pre>
 *
 * <h2>FileEntry</h2>
 * {@link FileEntry} represents the state of a file or directory, capturing
 * {@link File} attributes at a point in time. Custom implementations of
 * {@link FileEntry} can be used to capture additional properties that the
 * basic implementation does not support. The {@link FileEntry#refresh(File)}
 * method is used to determine if a file or directory has changed since the last
 * check and stores the current state of the {@link File}'s properties.
 *
 * @see FileAlterationListener
 * @see FileAlterationMonitor
 * @version $Id$
 * @since 2.0
 */
/* method "getFileFilter" was removed from this class because it was not covered by the test suite */
/* method "destroy" was removed from this class because it was not covered by the test suite */
public class FileAlterationObserver implements java.io.Serializable {
    private static final long serialVersionUID = 1185122225658782848L;

    private final java.util.List<org.apache.commons.io.monitor.FileAlterationListener> listeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    private final org.apache.commons.io.monitor.FileEntry rootEntry;

    private final java.io.FileFilter fileFilter;

    private final java.util.Comparator<java.io.File> comparator;

    /**
     * Construct an observer for the specified directory.
     *
     * @param directoryName
     * 		the name of the directory to observe
     */
    public FileAlterationObserver(final java.lang.String directoryName) {
        this(new java.io.File(directoryName));
    }

    /**
     * Construct an observer for the specified directory and file filter.
     *
     * @param directoryName
     * 		the name of the directory to observe
     * @param fileFilter
     * 		The file filter or null if none
     */
    public FileAlterationObserver(final java.lang.String directoryName, final java.io.FileFilter fileFilter) {
        this(new java.io.File(directoryName), fileFilter);
    }

    /**
     * Construct an observer for the specified directory, file filter and
     * file comparator.
     *
     * @param directoryName
     * 		the name of the directory to observe
     * @param fileFilter
     * 		The file filter or null if none
     * @param caseSensitivity
     * 		what case sensitivity to use comparing file names, null means system sensitive
     */
    public FileAlterationObserver(final java.lang.String directoryName, final java.io.FileFilter fileFilter, final org.apache.commons.io.IOCase caseSensitivity) {
        this(new java.io.File(directoryName), fileFilter, caseSensitivity);
    }

    /**
     * Construct an observer for the specified directory.
     *
     * @param directory
     * 		the directory to observe
     */
    public FileAlterationObserver(final java.io.File directory) {
        this(directory, null);
    }

    /**
     * Construct an observer for the specified directory and file filter.
     *
     * @param directory
     * 		the directory to observe
     * @param fileFilter
     * 		The file filter or null if none
     */
    public FileAlterationObserver(final java.io.File directory, final java.io.FileFilter fileFilter) {
        this(directory, fileFilter, null);
    }

    /**
     * Construct an observer for the specified directory, file filter and
     * file comparator.
     *
     * @param directory
     * 		the directory to observe
     * @param fileFilter
     * 		The file filter or null if none
     * @param caseSensitivity
     * 		what case sensitivity to use comparing file names, null means system sensitive
     */
    public FileAlterationObserver(final java.io.File directory, final java.io.FileFilter fileFilter, final org.apache.commons.io.IOCase caseSensitivity) {
        this(new org.apache.commons.io.monitor.FileEntry(directory), fileFilter, caseSensitivity);
    }

    /**
     * Construct an observer for the specified directory, file filter and
     * file comparator.
     *
     * @param rootEntry
     * 		the root directory to observe
     * @param fileFilter
     * 		The file filter or null if none
     * @param caseSensitivity
     * 		what case sensitivity to use comparing file names, null means system sensitive
     */
    protected FileAlterationObserver(final org.apache.commons.io.monitor.FileEntry rootEntry, final java.io.FileFilter fileFilter, final org.apache.commons.io.IOCase caseSensitivity) {
        if (rootEntry == null) {
            throw new java.lang.IllegalArgumentException("Root entry is missing");
        }
        if ((rootEntry.getFile()) == null) {
            throw new java.lang.IllegalArgumentException("Root directory is missing");
        }
        this.rootEntry = rootEntry;
        this.fileFilter = fileFilter;
        if ((caseSensitivity == null) || (caseSensitivity.equals(org.apache.commons.io.IOCase.SYSTEM))) {
            this.comparator = org.apache.commons.io.comparator.NameFileComparator.NAME_SYSTEM_COMPARATOR;
        }else
            if (caseSensitivity.equals(org.apache.commons.io.IOCase.INSENSITIVE)) {
                this.comparator = org.apache.commons.io.comparator.NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
            }else {
                this.comparator = org.apache.commons.io.comparator.NameFileComparator.NAME_COMPARATOR;
            }
        
    }

    /**
     * Return the directory being observed.
     *
     * @return the directory being observed
     */
    public java.io.File getDirectory() {
        return rootEntry.getFile();
    }

    /**
     * Add a file system listener.
     *
     * @param listener
     * 		The file system listener
     */
    public void addListener(final org.apache.commons.io.monitor.FileAlterationListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a file system listener.
     *
     * @param listener
     * 		The file system listener
     */
    public void removeListener(final org.apache.commons.io.monitor.FileAlterationListener listener) {
        if (listener != null) {
            while (listeners.remove(listener)) {
            } 
        }
    }

    /**
     * Returns the set of registered file system listeners.
     *
     * @return The file system listeners
     */
    public java.lang.Iterable<org.apache.commons.io.monitor.FileAlterationListener> getListeners() {
        return listeners;
    }

    /**
     * Initialize the observer.
     *
     * @throws Exception
     * 		if an error occurs
     */
    public void initialize() throws java.lang.Exception {
        rootEntry.refresh(rootEntry.getFile());
        final org.apache.commons.io.monitor.FileEntry[] children = doListFiles(rootEntry.getFile(), rootEntry);
        rootEntry.setChildren(children);
    }

    /**
     * Check whether the file and its children have been created, modified or deleted.
     */
    public void checkAndNotify() {
        /* fire onStart() */
        for (final org.apache.commons.io.monitor.FileAlterationListener listener : listeners) {
            listener.onStart(this);
        }
        /* fire directory/file events */
        final java.io.File rootFile = rootEntry.getFile();
        // Didn't exist and still doesn't
        if (rootFile.exists()) {
            checkAndNotify(rootEntry, rootEntry.getChildren(), listFiles(rootFile));
        }// Didn't exist and still doesn't
        else
            if (rootEntry.isExists()) {
                checkAndNotify(rootEntry, rootEntry.getChildren(), org.apache.commons.io.FileUtils.EMPTY_FILE_ARRAY);
            }else {
            }
        
        /* fire onStop() */
        for (final org.apache.commons.io.monitor.FileAlterationListener listener : listeners) {
            listener.onStop(this);
        }
    }

    /**
     * Compare two file lists for files which have been created, modified or deleted.
     *
     * @param parent
     * 		The parent entry
     * @param previous
     * 		The original list of files
     * @param files
     * 		The current list of files
     */
    private void checkAndNotify(final org.apache.commons.io.monitor.FileEntry parent, final org.apache.commons.io.monitor.FileEntry[] previous, final java.io.File[] files) {
        int c = 0;
        final org.apache.commons.io.monitor.FileEntry[] current = ((files.length) > 0) ? new org.apache.commons.io.monitor.FileEntry[files.length] : org.apache.commons.io.monitor.FileEntry.EMPTY_ENTRIES;
        for (final org.apache.commons.io.monitor.FileEntry entry : previous) {
            while ((c < (files.length)) && ((comparator.compare(entry.getFile(), files[c])) > 0)) {
                current[c] = createFileEntry(parent, files[c]);
                doCreate(current[c]);
                c++;
            } 
            if ((c < (files.length)) && ((comparator.compare(entry.getFile(), files[c])) == 0)) {
                doMatch(entry, files[c]);
                checkAndNotify(entry, entry.getChildren(), listFiles(files[c]));
                current[c] = entry;
                c++;
            }else {
                checkAndNotify(entry, entry.getChildren(), org.apache.commons.io.FileUtils.EMPTY_FILE_ARRAY);
                doDelete(entry);
            }
        }
        for (; c < (files.length); c++) {
            current[c] = createFileEntry(parent, files[c]);
            doCreate(current[c]);
        }
        parent.setChildren(current);
    }

    /**
     * Create a new file entry for the specified file.
     *
     * @param parent
     * 		The parent file entry
     * @param file
     * 		The file to create an entry for
     * @return A new file entry
     */
    private org.apache.commons.io.monitor.FileEntry createFileEntry(final org.apache.commons.io.monitor.FileEntry parent, final java.io.File file) {
        final org.apache.commons.io.monitor.FileEntry entry = parent.newChildInstance(file);
        entry.refresh(file);
        final org.apache.commons.io.monitor.FileEntry[] children = doListFiles(file, entry);
        entry.setChildren(children);
        return entry;
    }

    /**
     * List the files
     *
     * @param file
     * 		The file to list files for
     * @param entry
     * 		the parent entry
     * @return The child files
     */
    private org.apache.commons.io.monitor.FileEntry[] doListFiles(final java.io.File file, final org.apache.commons.io.monitor.FileEntry entry) {
        final java.io.File[] files = listFiles(file);
        final org.apache.commons.io.monitor.FileEntry[] children = ((files.length) > 0) ? new org.apache.commons.io.monitor.FileEntry[files.length] : org.apache.commons.io.monitor.FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < (files.length); i++) {
            children[i] = createFileEntry(entry, files[i]);
        }
        return children;
    }

    /**
     * Fire directory/file created events to the registered listeners.
     *
     * @param entry
     * 		The file entry
     */
    private void doCreate(final org.apache.commons.io.monitor.FileEntry entry) {
        for (final org.apache.commons.io.monitor.FileAlterationListener listener : listeners) {
            if (entry.isDirectory()) {
                listener.onDirectoryCreate(entry.getFile());
            }else {
                listener.onFileCreate(entry.getFile());
            }
        }
        final org.apache.commons.io.monitor.FileEntry[] children = entry.getChildren();
        for (final org.apache.commons.io.monitor.FileEntry aChildren : children) {
            doCreate(aChildren);
        }
    }

    /**
     * Fire directory/file change events to the registered listeners.
     *
     * @param entry
     * 		The previous file system entry
     * @param file
     * 		The current file
     */
    private void doMatch(final org.apache.commons.io.monitor.FileEntry entry, final java.io.File file) {
        if (entry.refresh(file)) {
            for (final org.apache.commons.io.monitor.FileAlterationListener listener : listeners) {
                if (entry.isDirectory()) {
                    listener.onDirectoryChange(file);
                }else {
                    listener.onFileChange(file);
                }
            }
        }
    }

    /**
     * Fire directory/file delete events to the registered listeners.
     *
     * @param entry
     * 		The file entry
     */
    private void doDelete(final org.apache.commons.io.monitor.FileEntry entry) {
        for (final org.apache.commons.io.monitor.FileAlterationListener listener : listeners) {
            if (entry.isDirectory()) {
                listener.onDirectoryDelete(entry.getFile());
            }else {
                listener.onFileDelete(entry.getFile());
            }
        }
    }

    /**
     * List the contents of a directory
     *
     * @param file
     * 		The file to list the contents of
     * @return the directory contents or a zero length array if
     * the empty or the file is not a directory
     */
    private java.io.File[] listFiles(final java.io.File file) {
        java.io.File[] children = null;
        if (file.isDirectory()) {
            children = ((fileFilter) == null) ? file.listFiles() : file.listFiles(fileFilter);
        }
        if (children == null) {
            children = org.apache.commons.io.FileUtils.EMPTY_FILE_ARRAY;
        }
        if (((comparator) != null) && ((children.length) > 1)) {
            java.util.Arrays.sort(children, comparator);
        }
        return children;
    }

    /**
     * Provide a String representation of this observer.
     *
     * @return a String representation of this observer
     */
    @java.lang.Override
    public java.lang.String toString() {
        final java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append("[file='");
        builder.append(getDirectory().getPath());
        builder.append('\'');
        if ((fileFilter) != null) {
            builder.append(", ");
            builder.append(fileFilter.toString());
        }
        builder.append(", listeners=");
        builder.append(listeners.size());
        builder.append("]");
        return builder.toString();
    }
}

