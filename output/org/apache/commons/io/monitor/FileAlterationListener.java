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
 * A listener that receives events of file system modifications.
 * <p>
 * Register {@link FileAlterationListener}s with a {@link FileAlterationObserver}.
 *
 * @see FileAlterationObserver
 * @version $Id$
 * @since 2.0
 */
public interface FileAlterationListener {
    /**
     * File system observer started checking event.
     *
     * @param observer
     * 		The file system observer
     */
    void onStart(final org.apache.commons.io.monitor.FileAlterationObserver observer);

    /**
     * Directory created Event.
     *
     * @param directory
     * 		The directory created
     */
    void onDirectoryCreate(final java.io.File directory);

    /**
     * Directory changed Event.
     *
     * @param directory
     * 		The directory changed
     */
    void onDirectoryChange(final java.io.File directory);

    /**
     * Directory deleted Event.
     *
     * @param directory
     * 		The directory deleted
     */
    void onDirectoryDelete(final java.io.File directory);

    /**
     * File created Event.
     *
     * @param file
     * 		The file created
     */
    void onFileCreate(final java.io.File file);

    /**
     * File changed Event.
     *
     * @param file
     * 		The file changed
     */
    void onFileChange(final java.io.File file);

    /**
     * File deleted Event.
     *
     * @param file
     * 		The file deleted
     */
    void onFileDelete(final java.io.File file);

    /**
     * File system observer finished checking event.
     *
     * @param observer
     * 		The file system observer
     */
    void onStop(final org.apache.commons.io.monitor.FileAlterationObserver observer);
}

