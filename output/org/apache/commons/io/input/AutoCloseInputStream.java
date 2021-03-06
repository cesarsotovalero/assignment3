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
 * Proxy stream that closes and discards the underlying stream as soon as the
 * end of input has been reached or when the stream is explicitly closed.
 * Not even a reference to the underlying stream is kept after it has been
 * closed, so any allocated in-memory buffers can be freed even if the
 * client application still keeps a reference to the proxy stream.
 * <p>
 * This class is typically used to release any resources related to an open
 * stream as soon as possible even if the client application (by not explicitly
 * closing the stream when no longer needed) or the underlying stream (by not
 * releasing resources once the last byte has been read) do not do that.
 *
 * @since 1.4
 */
/* method "afterRead" was removed from this class because it was not covered by the test suite */
public class AutoCloseInputStream extends org.apache.commons.io.input.ProxyInputStream {
    /**
     * Creates an automatically closing proxy for the given input stream.
     *
     * @param in
     * 		underlying input stream
     */
    public AutoCloseInputStream(final java.io.InputStream in) {
        super(in);
    }

    /**
     * Closes the underlying input stream and replaces the reference to it
     * with a {@link ClosedInputStream} instance.
     * <p>
     * This method is automatically called by the read methods when the end
     * of input has been reached.
     * <p>
     * Note that it is safe to call this method any number of times. The original
     * underlying input stream is closed and discarded only once when this
     * method is first called.
     *
     * @throws IOException
     * 		if the underlying input stream can not be closed
     */
    @java.lang.Override
    public void close() throws java.io.IOException {
        in.close();
        in = new org.apache.commons.io.input.ClosedInputStream();
    }

    /**
     * Ensures that the stream is closed before it gets garbage-collected.
     * As mentioned in {@link #close()}, this is a no-op if the stream has
     * already been closed.
     *
     * @throws Throwable
     * 		if an error occurs
     */
    @java.lang.Override
    protected void finalize() throws java.lang.Throwable {
        close();
        super.finalize();
    }
}

