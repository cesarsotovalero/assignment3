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
 * Convenience {@link FileAlterationListener} implementation that does nothing.
 *
 * @see FileAlterationObserver
 * @version $Id$
 * @since 2.0
 */
public class FileAlterationListenerAdaptor implements org.apache.commons.io.monitor.FileAlterationListener {
    /**
     * File system observer started checking event.
     *
     * @param observer
     * 		The file system observer (ignored)
     */
    @java.lang.Override
    public void onStart(final org.apache.commons.io.monitor.FileAlterationObserver observer) {
    }

    /**
     * Directory created Event.
     *
     * @param directory
     * 		The directory created (ignored)
     */
    @java.lang.Override
    public void onDirectoryCreate(final java.io.File directory) {
    }

    /**
     * Directory changed Event.
     *
     * @param directory
     * 		The directory changed (ignored)
     */
    @java.lang.Override
    public void onDirectoryChange(final java.io.File directory) {
    }

    /**
     * Directory deleted Event.
     *
     * @param directory
     * 		The directory deleted (ignored)
     */
    @java.lang.Override
    public void onDirectoryDelete(final java.io.File directory) {
    }

    /**
     * File created Event.
     *
     * @param file
     * 		The file created (ignored)
     */
    @java.lang.Override
    public void onFileCreate(final java.io.File file) {
    }

    /**
     * File changed Event.
     *
     * @param file
     * 		The file changed (ignored)
     */
    @java.lang.Override
    public void onFileChange(final java.io.File file) {
    }

    /**
     * File deleted Event.
     *
     * @param file
     * 		The file deleted (ignored)
     */
    @java.lang.Override
    public void onFileDelete(final java.io.File file) {
    }

    /**
     * File system observer finished checking event.
     *
     * @param observer
     * 		The file system observer (ignored)
     */
    @java.lang.Override
    public void onStop(final org.apache.commons.io.monitor.FileAlterationObserver observer) {
    }
}

