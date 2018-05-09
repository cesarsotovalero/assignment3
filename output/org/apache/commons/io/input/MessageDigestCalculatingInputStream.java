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
 * This class is an example for using an {@link ObservableInputStream}. It
 * creates its own {@link org.apache.commons.io.input.ObservableInputStream.Observer},
 * which calculates a checksum using a MessageDigest, for example an MD5 sum.
 * <em>Note</em>: Neither {@link ObservableInputStream}, nor {@link MessageDigest},
 * are thread safe. So is {@link MessageDigestCalculatingInputStream}.
 */
public class MessageDigestCalculatingInputStream extends org.apache.commons.io.input.ObservableInputStream {
    /**
     * Maintains the message digest.
     */
    public static class MessageDigestMaintainingObserver extends org.apache.commons.io.input.ObservableInputStream.Observer {
        private final java.security.MessageDigest md;

        /**
         * Creates an MessageDigestMaintainingObserver for the given MessageDigest.
         *
         * @param pMd
         * 		the message digest to use
         */
        public MessageDigestMaintainingObserver(final java.security.MessageDigest pMd) {
            md = pMd;
        }

        @java.lang.Override
        void data(final int pByte) throws java.io.IOException {
            md.update(((byte) (pByte)));
        }

        @java.lang.Override
        void data(final byte[] pBuffer, final int pOffset, final int pLength) throws java.io.IOException {
            md.update(pBuffer, pOffset, pLength);
        }
    }

    private final java.security.MessageDigest messageDigest;

    /**
     * Creates a new instance, which calculates a signature on the given stream,
     * using the given {@link MessageDigest}.
     *
     * @param pStream
     * 		the stream to calculate the message digest for
     * @param pDigest
     * 		the message digest to use
     */
    public MessageDigestCalculatingInputStream(final java.io.InputStream pStream, final java.security.MessageDigest pDigest) {
        super(pStream);
        messageDigest = pDigest;
        add(new org.apache.commons.io.input.MessageDigestCalculatingInputStream.MessageDigestMaintainingObserver(pDigest));
    }

    /**
     * Creates a new instance, which calculates a signature on the given stream,
     * using a {@link MessageDigest} with the given algorithm.
     *
     * @param pStream
     * 		the stream to calculate the message digest for
     * @param pAlgorithm
     * 		the name of the algorithm to use
     * @throws NoSuchAlgorithmException
     * 		if no Provider supports a MessageDigestSpi implementation for the specified algorithm.
     */
    public MessageDigestCalculatingInputStream(final java.io.InputStream pStream, final java.lang.String pAlgorithm) throws java.security.NoSuchAlgorithmException {
        this(pStream, java.security.MessageDigest.getInstance(pAlgorithm));
    }

    /**
     * Creates a new instance, which calculates a signature on the given stream,
     * using a {@link MessageDigest} with the "MD5" algorithm.
     *
     * @param pStream
     * 		the stream to calculate the message digest for
     * @throws NoSuchAlgorithmException
     * 		if no Provider supports a MessageDigestSpi implementation for the specified algorithm.
     */
    public MessageDigestCalculatingInputStream(final java.io.InputStream pStream) throws java.security.NoSuchAlgorithmException {
        this(pStream, java.security.MessageDigest.getInstance("MD5"));
    }

    /**
     * Returns the {@link MessageDigest}, which is being used for generating the
     * checksum.
     * <em>Note</em>: The checksum will only reflect the data, which has been read so far.
     * This is probably not, what you expect. Make sure, that the complete data has been
     * read, if that is what you want. The easiest way to do so is by invoking
     * {@link #consume()}.
     *
     * @return the message digest used
     */
    public java.security.MessageDigest getMessageDigest() {
        return messageDigest;
    }
}

