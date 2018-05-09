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
 * General file manipulation utilities.
 * <p>
 * Facilities are provided in the following areas:
 * <ul>
 * <li>writing to a file
 * <li>reading from a file
 * <li>make a directory including parent directories
 * <li>copying files and directories
 * <li>deleting files and directories
 * <li>converting to and from a URL
 * <li>listing files and directories by filter and extension
 * <li>comparing file content
 * <li>file last changed date
 * <li>calculating a checksum
 * </ul>
 * <p>
 * Note that a specific charset should be specified whenever possible.
 * Relying on the platform default means that the code is Locale-dependent.
 * Only use the default if the files are known to always use the platform default.
 * <p>
 * Origin of code: Excalibur, Alexandria, Commons-Utils
 */
/* method "contentEquals" was removed from this class because it was not covered by the test suite */
/* method "contentEqualsIgnoreEOL" was removed from this class because it was not covered by the test suite */
/* method "deleteQuietly" was removed from this class because it was not covered by the test suite */
/* method "directoryContains" was removed from this class because it was not covered by the test suite */
/* method "isFileNewer" was removed from this class because it was not covered by the test suite */
/* method "isFileNewer" was removed from this class because it was not covered by the test suite */
/* method "isFileNewer" was removed from this class because it was not covered by the test suite */
/* method "isFileOlder" was removed from this class because it was not covered by the test suite */
/* method "isFileOlder" was removed from this class because it was not covered by the test suite */
/* method "isFileOlder" was removed from this class because it was not covered by the test suite */
/* method "isSymlink" was removed from this class because it was not covered by the test suite */
/* method "waitFor" was removed from this class because it was not covered by the test suite */
/* method "readFileToByteArray" was removed from this class because it was not covered by the test suite */
/* method "getFile" was removed from this class because it was not covered by the test suite */
/* method "getFile" was removed from this class because it was not covered by the test suite */
/* method "getTempDirectory" was removed from this class because it was not covered by the test suite */
/* method "getUserDirectory" was removed from this class because it was not covered by the test suite */
/* method "toFile" was removed from this class because it was not covered by the test suite */
/* method "openInputStream" was removed from this class because it was not covered by the test suite */
/* method "openOutputStream" was removed from this class because it was not covered by the test suite */
/* method "openOutputStream" was removed from this class because it was not covered by the test suite */
/* method "convertFileCollectionToFileArray" was removed from this class because it was not covered by the test suite */
/* method "toFiles" was removed from this class because it was not covered by the test suite */
/* method "verifiedListFiles" was removed from this class because it was not covered by the test suite */
/* method "byteCountToDisplaySize" was removed from this class because it was not covered by the test suite */
/* method "byteCountToDisplaySize" was removed from this class because it was not covered by the test suite */
/* method "decodeUrl" was removed from this class because it was not covered by the test suite */
/* method "getTempDirectoryPath" was removed from this class because it was not covered by the test suite */
/* method "getUserDirectoryPath" was removed from this class because it was not covered by the test suite */
/* method "readFileToString" was removed from this class because it was not covered by the test suite */
/* method "readFileToString" was removed from this class because it was not covered by the test suite */
/* method "readFileToString" was removed from this class because it was not covered by the test suite */
/* method "toSuffixes" was removed from this class because it was not covered by the test suite */
/* method "sizeOfAsBigInteger" was removed from this class because it was not covered by the test suite */
/* method "sizeOfBig0" was removed from this class because it was not covered by the test suite */
/* method "sizeOfDirectoryAsBigInteger" was removed from this class because it was not covered by the test suite */
/* method "sizeOfDirectoryBig0" was removed from this class because it was not covered by the test suite */
/* method "toURLs" was removed from this class because it was not covered by the test suite */
/* method "innerListFilesOrDirectories" was removed from this class because it was not covered by the test suite */
/* method "listFiles" was removed from this class because it was not covered by the test suite */
/* method "listFiles" was removed from this class because it was not covered by the test suite */
/* method "listFilesAndDirs" was removed from this class because it was not covered by the test suite */
/* method "iterateFiles" was removed from this class because it was not covered by the test suite */
/* method "iterateFiles" was removed from this class because it was not covered by the test suite */
/* method "iterateFilesAndDirs" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "readLines" was removed from this class because it was not covered by the test suite */
/* method "checksum" was removed from this class because it was not covered by the test suite */
/* method "checksumCRC32" was removed from this class because it was not covered by the test suite */
/* method "copyFile" was removed from this class because it was not covered by the test suite */
/* method "sizeOf" was removed from this class because it was not covered by the test suite */
/* method "sizeOf0" was removed from this class because it was not covered by the test suite */
/* method "sizeOfDirectory" was removed from this class because it was not covered by the test suite */
/* method "sizeOfDirectory0" was removed from this class because it was not covered by the test suite */
/* method "lineIterator" was removed from this class because it was not covered by the test suite */
/* method "lineIterator" was removed from this class because it was not covered by the test suite */
/* method "setUpEffectiveDirFilter" was removed from this class because it was not covered by the test suite */
/* method "setUpEffectiveFileFilter" was removed from this class because it was not covered by the test suite */
/* method "checkDirectory" was removed from this class because it was not covered by the test suite */
/* method "checkFileRequirements" was removed from this class because it was not covered by the test suite */
/* method "cleanDirectory" was removed from this class because it was not covered by the test suite */
/* method "cleanDirectoryOnExit" was removed from this class because it was not covered by the test suite */
/* method "copyDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyDirectoryToDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyFile" was removed from this class because it was not covered by the test suite */
/* method "copyFile" was removed from this class because it was not covered by the test suite */
/* method "copyFileToDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyFileToDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyInputStreamToFile" was removed from this class because it was not covered by the test suite */
/* method "copyToDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyToDirectory" was removed from this class because it was not covered by the test suite */
/* method "copyToFile" was removed from this class because it was not covered by the test suite */
/* method "copyURLToFile" was removed from this class because it was not covered by the test suite */
/* method "copyURLToFile" was removed from this class because it was not covered by the test suite */
/* method "deleteDirectory" was removed from this class because it was not covered by the test suite */
/* method "deleteDirectoryOnExit" was removed from this class because it was not covered by the test suite */
/* method "doCopyDirectory" was removed from this class because it was not covered by the test suite */
/* method "doCopyFile" was removed from this class because it was not covered by the test suite */
/* method "forceDelete" was removed from this class because it was not covered by the test suite */
/* method "forceDeleteOnExit" was removed from this class because it was not covered by the test suite */
/* method "forceMkdirParent" was removed from this class because it was not covered by the test suite */
/* method "innerListFiles" was removed from this class because it was not covered by the test suite */
/* method "moveDirectory" was removed from this class because it was not covered by the test suite */
/* method "moveDirectoryToDirectory" was removed from this class because it was not covered by the test suite */
/* method "moveFile" was removed from this class because it was not covered by the test suite */
/* method "moveFileToDirectory" was removed from this class because it was not covered by the test suite */
/* method "moveToDirectory" was removed from this class because it was not covered by the test suite */
/* method "touch" was removed from this class because it was not covered by the test suite */
/* method "validateListFilesParameters" was removed from this class because it was not covered by the test suite */
/* method "validateMoveParameters" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "write" was removed from this class because it was not covered by the test suite */
/* method "writeByteArrayToFile" was removed from this class because it was not covered by the test suite */
/* method "writeByteArrayToFile" was removed from this class because it was not covered by the test suite */
/* method "writeByteArrayToFile" was removed from this class because it was not covered by the test suite */
/* method "writeByteArrayToFile" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeLines" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
/* method "writeStringToFile" was removed from this class because it was not covered by the test suite */
public class FileUtils {
    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_KB_BI = java.math.BigInteger.valueOf(org.apache.commons.io.FileUtils.ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = (org.apache.commons.io.FileUtils.ONE_KB) * (org.apache.commons.io.FileUtils.ONE_KB);

    /**
     * The number of bytes in a megabyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_MB_BI = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_KB_BI);

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = (org.apache.commons.io.FileUtils.ONE_MB) * 30;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = (org.apache.commons.io.FileUtils.ONE_KB) * (org.apache.commons.io.FileUtils.ONE_MB);

    /**
     * The number of bytes in a gigabyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_GB_BI = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = (org.apache.commons.io.FileUtils.ONE_KB) * (org.apache.commons.io.FileUtils.ONE_GB);

    /**
     * The number of bytes in a terabyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_TB_BI = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_GB_BI);

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = (org.apache.commons.io.FileUtils.ONE_KB) * (org.apache.commons.io.FileUtils.ONE_TB);

    /**
     * The number of bytes in a petabyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_PB_BI = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = (org.apache.commons.io.FileUtils.ONE_KB) * (org.apache.commons.io.FileUtils.ONE_PB);

    /**
     * The number of bytes in an exabyte.
     *
     * @since 2.4
     */
    public static final java.math.BigInteger ONE_EB_BI = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_PB_BI);

    /**
     * The number of bytes in a zettabyte.
     */
    public static final java.math.BigInteger ONE_ZB = java.math.BigInteger.valueOf(org.apache.commons.io.FileUtils.ONE_KB).multiply(java.math.BigInteger.valueOf(org.apache.commons.io.FileUtils.ONE_EB));

    /**
     * The number of bytes in a yottabyte.
     */
    public static final java.math.BigInteger ONE_YB = org.apache.commons.io.FileUtils.ONE_KB_BI.multiply(org.apache.commons.io.FileUtils.ONE_ZB);

    /**
     * An empty array of type <code>File</code>.
     */
    public static final java.io.File[] EMPTY_FILE_ARRAY = new java.io.File[0];

    /**
     * Instances should NOT be constructed in standard programming.
     */
    public FileUtils() {
        super();
    }

    /**
     * Makes a directory, including any necessary but nonexistent parent
     * directories. If a file already exists with specified name but it is
     * not a directory then an IOException is thrown.
     * If the directory cannot be created (or does not already exist)
     * then an IOException is thrown.
     *
     * @param directory
     * 		directory to create, must not be {@code null}
     * @throws NullPointerException
     * 		if the directory is {@code null}
     * @throws IOException
     * 		if the directory cannot be created or the file already exists but is not a directory
     */
    public static void forceMkdir(final java.io.File directory) throws java.io.IOException {
        if (directory.exists()) {
            if (!(directory.isDirectory())) {
                final java.lang.String message = (("File " + directory) + " exists and is ") + "not a directory. Unable to create directory.";
                throw new java.io.IOException(message);
            }
        }else {
            if (!(directory.mkdirs())) {
                // Double-check that some other thread or process hasn't made
                // the directory in the background
                if (!(directory.isDirectory())) {
                    final java.lang.String message = "Unable to create directory " + directory;
                    throw new java.io.IOException(message);
                }
            }
        }
    }
}

