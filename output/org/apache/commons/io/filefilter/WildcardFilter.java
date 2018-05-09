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
package org.apache.commons.io.filefilter;


/**
 * Filters files using the supplied wildcards.
 * <p>
 * This filter selects files, but not directories, based on one or more wildcards
 * and using case-sensitive comparison.
 * <p>
 * The wildcard matcher uses the characters '?' and '*' to represent a
 * single or multiple wildcard characters.
 * This is the same as often found on Dos/Unix command lines.
 * The extension check is case-sensitive.
 * See {@link FilenameUtils#wildcardMatch(String, String)} for more information.
 * <p>
 * For example:
 * <pre>
 * File dir = new File(".");
 * FileFilter fileFilter = new WildcardFilter("*test*.java~*~");
 * File[] files = dir.listFiles(fileFilter);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @since 1.1
 * @deprecated Use WildcardFileFilter. Deprecated as this class performs directory
 * filtering which it shouldn't do, but that can't be removed due to compatibility.
 */
@java.lang.Deprecated
public class WildcardFilter extends org.apache.commons.io.filefilter.AbstractFileFilter implements java.io.Serializable {
    private static final long serialVersionUID = -5037645902506953517L;

    /**
     * The wildcards that will be used to match filenames.
     */
    private final java.lang.String[] wildcards;

    /**
     * Construct a new case-sensitive wildcard filter for a single wildcard.
     *
     * @param wildcard
     * 		the wildcard to match
     * @throws IllegalArgumentException
     * 		if the pattern is null
     */
    public WildcardFilter(final java.lang.String wildcard) {
        if (wildcard == null) {
            throw new java.lang.IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new java.lang.String[]{ wildcard };
    }

    /**
     * Construct a new case-sensitive wildcard filter for an array of wildcards.
     *
     * @param wildcards
     * 		the array of wildcards to match
     * @throws IllegalArgumentException
     * 		if the pattern array is null
     */
    public WildcardFilter(final java.lang.String[] wildcards) {
        if (wildcards == null) {
            throw new java.lang.IllegalArgumentException("The wildcard array must not be null");
        }
        this.wildcards = new java.lang.String[wildcards.length];
        java.lang.System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
    }

    /**
     * Construct a new case-sensitive wildcard filter for a list of wildcards.
     *
     * @param wildcards
     * 		the list of wildcards to match
     * @throws IllegalArgumentException
     * 		if the pattern list is null
     * @throws ClassCastException
     * 		if the list does not contain Strings
     */
    public WildcardFilter(final java.util.List<java.lang.String> wildcards) {
        if (wildcards == null) {
            throw new java.lang.IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = wildcards.toArray(new java.lang.String[wildcards.size()]);
    }

    // -----------------------------------------------------------------------
    /**
     * Checks to see if the filename matches one of the wildcards.
     *
     * @param dir
     * 		the file directory
     * @param name
     * 		the filename
     * @return true if the filename matches one of the wildcards
     */
    @java.lang.Override
    public boolean accept(final java.io.File dir, final java.lang.String name) {
        if ((dir != null) && (new java.io.File(dir, name).isDirectory())) {
            return false;
        }
        for (final java.lang.String wildcard : wildcards) {
            if (org.apache.commons.io.FilenameUtils.wildcardMatch(name, wildcard)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the filename matches one of the wildcards.
     *
     * @param file
     * 		the file to check
     * @return true if the filename matches one of the wildcards
     */
    @java.lang.Override
    public boolean accept(final java.io.File file) {
        if (file.isDirectory()) {
            return false;
        }
        for (final java.lang.String wildcard : wildcards) {
            if (org.apache.commons.io.FilenameUtils.wildcardMatch(file.getName(), wildcard)) {
                return true;
            }
        }
        return false;
    }
}

