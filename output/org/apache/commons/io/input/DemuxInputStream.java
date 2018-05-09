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
 * Data written to this stream is forwarded to a stream that has been associated
 * with this thread.
 */
public class DemuxInputStream extends java.io.InputStream {
    private final java.lang.InheritableThreadLocal<java.io.InputStream> m_streams = new java.lang.InheritableThreadLocal<>();

    /**
     * Bind the specified stream to the current thread.
     *
     * @param input
     * 		the stream to bind
     * @return the InputStream that was previously active
     */
    public java.io.InputStream bindStream(final java.io.InputStream input) {
        final java.io.InputStream oldValue = m_streams.get();
        m_streams.set(input);
        return oldValue;
    }

    /**
     * Closes stream associated with current thread.
     *
     * @throws IOException
     * 		if an error occurs
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        final java.io.InputStream input = m_streams.get();
        if (null != input) {
            input.close();
        }
    }

    /**
     * Read byte from stream associated with current thread.
     *
     * @return the byte read from stream
     * @throws IOException
     * 		if an error occurs
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        final java.io.InputStream input = m_streams.get();
        if (null != input) {
            return input.read();
        }
        return org.apache.commons.io.IOUtils.EOF;
    }
}

