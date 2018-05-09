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
 * An Iterator over the lines in a <code>Reader</code>.
 * <p>
 * <code>LineIterator</code> holds a reference to an open <code>Reader</code>.
 * When you have finished with the iterator you should close the reader
 * to free internal resources. This can be done by closing the reader directly,
 * or by calling the {@link #close()} or {@link #closeQuietly(LineIterator)}
 * method on the iterator.
 * <p>
 * The recommended usage pattern is:
 * <pre>
 * LineIterator it = FileUtils.lineIterator(file, "UTF-8");
 * try {
 *   while (it.hasNext()) {
 *     String line = it.nextLine();
 *     // do something with line
 *   }
 * } finally {
 *   it.close();
 * }
 * </pre>
 *
 * @since 1.2
 */
public class LineIterator implements java.io.Closeable , java.util.Iterator<java.lang.String> {
    // N.B. This class deliberately does not implement Iterable, see https://issues.apache.org/jira/browse/IO-181
    /**
     * The reader that is being read.
     */
    private final java.io.BufferedReader bufferedReader;

    /**
     * The current line.
     */
    private java.lang.String cachedLine;

    /**
     * A flag indicating if the iterator has been fully read.
     */
    private boolean finished = false;

    /**
     * Constructs an iterator of the lines for a <code>Reader</code>.
     *
     * @param reader
     * 		the <code>Reader</code> to read from, not null
     * @throws IllegalArgumentException
     * 		if the reader is null
     */
    public LineIterator(final java.io.Reader reader) throws java.lang.IllegalArgumentException {
        if (reader == null) {
            throw new java.lang.IllegalArgumentException("Reader must not be null");
        }
        if (reader instanceof java.io.BufferedReader) {
            bufferedReader = ((java.io.BufferedReader) (reader));
        }else {
            bufferedReader = new java.io.BufferedReader(reader);
        }
    }

    // -----------------------------------------------------------------------
    /**
     * Indicates whether the <code>Reader</code> has more lines.
     * If there is an <code>IOException</code> then {@link #close()} will
     * be called on this instance.
     *
     * @return {@code true} if the Reader has more lines
     * @throws IllegalStateException
     * 		if an IO exception occurs
     */
    @java.lang.Override
    public boolean hasNext() {
        if ((cachedLine) != null) {
            return true;
        }else
            if (finished) {
                return false;
            }else {
                try {
                    while (true) {
                        final java.lang.String line = bufferedReader.readLine();
                        if (line == null) {
                            finished = true;
                            return false;
                        }else
                            if (isValidLine(line)) {
                                cachedLine = line;
                                return true;
                            }
                        
                    } 
                } catch (final java.io.IOException ioe) {
                    try {
                        close();
                    } catch (final java.io.IOException e) {
                        ioe.addSuppressed(e);
                    }
                    throw new java.lang.IllegalStateException(ioe);
                }
            }
        
    }

    /**
     * Overridable method to validate each line that is returned.
     * This implementation always returns true.
     *
     * @param line
     * 		the line that is to be validated
     * @return true if valid, false to remove from the iterator
     */
    protected boolean isValidLine(final java.lang.String line) {
        return true;
    }

    /**
     * Returns the next line in the wrapped <code>Reader</code>.
     *
     * @return the next line from the input
     * @throws NoSuchElementException
     * 		if there is no line to return
     */
    @java.lang.Override
    public java.lang.String next() {
        return nextLine();
    }

    /**
     * Returns the next line in the wrapped <code>Reader</code>.
     *
     * @return the next line from the input
     * @throws NoSuchElementException
     * 		if there is no line to return
     */
    public java.lang.String nextLine() {
        if (!(hasNext())) {
            throw new java.util.NoSuchElementException("No more lines");
        }
        final java.lang.String currentLine = cachedLine;
        cachedLine = null;
        return currentLine;
    }

    /**
     * Closes the underlying {@code Reader}.
     * This method is useful if you only want to process the first few
     * lines of a larger file. If you do not close the iterator
     * then the {@code Reader} remains open.
     * This method can safely be called multiple times.
     *
     * @throws IOException
     * 		if closing the underlying {@code Reader} fails.
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        finished = true;
        cachedLine = null;
        if ((this.bufferedReader) != null) {
            this.bufferedReader.close();
        }
    }

    /**
     * Unsupported.
     *
     * @throws UnsupportedOperationException
     * 		always
     */
    @java.lang.Override
    public void remove() {
        throw new java.lang.UnsupportedOperationException("Remove unsupported on LineIterator");
    }

    // -----------------------------------------------------------------------
    /**
     * Closes a {@code LineIterator} quietly.
     *
     * @param iterator
     * 		The iterator to close, or {@code null}.
     * @deprecated As of 2.6 removed without replacement. Please use the try-with-resources statement or handle
     * suppressed exceptions manually.
     * @see Throwable#addSuppressed(java.lang.Throwable)
     */
    @java.lang.Deprecated
    public static void closeQuietly(final org.apache.commons.io.LineIterator iterator) {
        try {
            if (iterator != null) {
                iterator.close();
            }
        } catch (final java.io.IOException e) {
            // Suppressed.
        }
    }
}

