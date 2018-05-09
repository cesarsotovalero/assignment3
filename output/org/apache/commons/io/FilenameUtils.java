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
 * General file name and file path manipulation utilities.
 * <p>
 * When dealing with file names you can hit problems when moving from a Windows
 * based development machine to a Unix based production machine.
 * This class aims to help avoid those problems.
 * <p>
 * <b>NOTE</b>: You may be able to avoid using this class entirely simply by
 * using JDK {@link java.io.File File} objects and the two argument constructor
 * {@link java.io.File#File(java.io.File, java.lang.String) File(File,String)}.
 * <p>
 * Most methods on this class are designed to work the same on both Unix and Windows.
 * Those that don't include 'System', 'Unix' or 'Windows' in their name.
 * <p>
 * Most methods recognise both separators (forward and back), and both
 * sets of prefixes. See the javadoc of each method for details.
 * <p>
 * This class defines six components within a file name
 * (example C:\dev\project\file.txt):
 * <ul>
 * <li>the prefix - C:\</li>
 * <li>the path - dev\project\</li>
 * <li>the full path - C:\dev\project\</li>
 * <li>the name - file.txt</li>
 * <li>the base name - file</li>
 * <li>the extension - txt</li>
 * </ul>
 * Note that this class works best if directory file names end with a separator.
 * If you omit the last separator, it is impossible to determine if the file name
 * corresponds to a file or a directory. As a result, we have chosen to say
 * it corresponds to a file.
 * <p>
 * This class only supports Unix and Windows style names.
 * Prefixes are matched as follows:
 * <pre>
 * Windows:
 * a\b\c.txt           --&gt; ""          --&gt; relative
 * \a\b\c.txt          --&gt; "\"         --&gt; current drive absolute
 * C:a\b\c.txt         --&gt; "C:"        --&gt; drive relative
 * C:\a\b\c.txt        --&gt; "C:\"       --&gt; absolute
 * \\server\a\b\c.txt  --&gt; "\\server\" --&gt; UNC
 *
 * Unix:
 * a/b/c.txt           --&gt; ""          --&gt; relative
 * /a/b/c.txt          --&gt; "/"         --&gt; absolute
 * ~/a/b/c.txt         --&gt; "~/"        --&gt; current user
 * ~                   --&gt; "~/"        --&gt; current user (slash added)
 * ~user/a/b/c.txt     --&gt; "~user/"    --&gt; named user
 * ~user               --&gt; "~user/"    --&gt; named user (slash added)
 * </pre>
 * Both prefix styles are matched always, irrespective of the machine that you are
 * currently running on.
 * <p>
 * Origin of code: Excalibur, Alexandria, Tomcat, Commons-Utils.
 *
 * @since 1.1
 */
/* method "directoryContains" was removed from this class because it was not covered by the test suite */
/* method "equals" was removed from this class because it was not covered by the test suite */
/* method "equals" was removed from this class because it was not covered by the test suite */
/* method "equalsNormalized" was removed from this class because it was not covered by the test suite */
/* method "equalsNormalizedOnSystem" was removed from this class because it was not covered by the test suite */
/* method "equalsOnSystem" was removed from this class because it was not covered by the test suite */
/* method "isExtension" was removed from this class because it was not covered by the test suite */
/* method "isExtension" was removed from this class because it was not covered by the test suite */
/* method "isExtension" was removed from this class because it was not covered by the test suite */
/* method "isSeparator" was removed from this class because it was not covered by the test suite */
/* method "isSystemWindows" was removed from this class because it was not covered by the test suite */
/* method "wildcardMatchOnSystem" was removed from this class because it was not covered by the test suite */
/* method "getAdsCriticalOffset" was removed from this class because it was not covered by the test suite */
/* method "getPrefixLength" was removed from this class because it was not covered by the test suite */
/* method "indexOfExtension" was removed from this class because it was not covered by the test suite */
/* method "indexOfLastSeparator" was removed from this class because it was not covered by the test suite */
/* method "concat" was removed from this class because it was not covered by the test suite */
/* method "doGetFullPath" was removed from this class because it was not covered by the test suite */
/* method "doGetPath" was removed from this class because it was not covered by the test suite */
/* method "doNormalize" was removed from this class because it was not covered by the test suite */
/* method "getBaseName" was removed from this class because it was not covered by the test suite */
/* method "getExtension" was removed from this class because it was not covered by the test suite */
/* method "getFullPath" was removed from this class because it was not covered by the test suite */
/* method "getFullPathNoEndSeparator" was removed from this class because it was not covered by the test suite */
/* method "getName" was removed from this class because it was not covered by the test suite */
/* method "getPath" was removed from this class because it was not covered by the test suite */
/* method "getPathNoEndSeparator" was removed from this class because it was not covered by the test suite */
/* method "getPrefix" was removed from this class because it was not covered by the test suite */
/* method "normalize" was removed from this class because it was not covered by the test suite */
/* method "normalize" was removed from this class because it was not covered by the test suite */
/* method "normalizeNoEndSeparator" was removed from this class because it was not covered by the test suite */
/* method "normalizeNoEndSeparator" was removed from this class because it was not covered by the test suite */
/* method "removeExtension" was removed from this class because it was not covered by the test suite */
/* method "separatorsToSystem" was removed from this class because it was not covered by the test suite */
/* method "separatorsToUnix" was removed from this class because it was not covered by the test suite */
/* method "separatorsToWindows" was removed from this class because it was not covered by the test suite */
/* method "failIfNullBytePresent" was removed from this class because it was not covered by the test suite */
public class FilenameUtils {
    private static final java.lang.String EMPTY_STRING = "";

    private static final int NOT_FOUND = -1;

    /**
     * The extension separator character.
     *
     * @since 1.4
     */
    public static final char EXTENSION_SEPARATOR = '.';

    /**
     * The extension separator String.
     *
     * @since 1.4
     */
    public static final java.lang.String EXTENSION_SEPARATOR_STR = java.lang.Character.toString(org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR);

    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * The system separator character.
     */
    private static final char SYSTEM_SEPARATOR = java.io.File.separatorChar;

    /**
     * The separator character that is the opposite of the system separator.
     */
    private static final char OTHER_SEPARATOR;

    static {
        if (org.apache.commons.io.FilenameUtils.isSystemWindows()) {
            OTHER_SEPARATOR = org.apache.commons.io.FilenameUtils.UNIX_SEPARATOR;
        }else {
            OTHER_SEPARATOR = org.apache.commons.io.FilenameUtils.WINDOWS_SEPARATOR;
        }
    }

    /**
     * Instances should NOT be constructed in standard programming.
     */
    public FilenameUtils() {
        super();
    }

    // -----------------------------------------------------------------------
    /**
     * Checks a fileName to see if it matches the specified wildcard matcher,
     * always testing case-sensitive.
     * <p>
     * The wildcard matcher uses the characters '?' and '*' to represent a
     * single or multiple (zero or more) wildcard characters.
     * This is the same as often found on Dos/Unix command lines.
     * The check is case-sensitive always.
     * <pre>
     * wildcardMatch("c.txt", "*.txt")      --&gt; true
     * wildcardMatch("c.txt", "*.jpg")      --&gt; false
     * wildcardMatch("a/b/c.txt", "a/b/*")  --&gt; true
     * wildcardMatch("c.txt", "*.???")      --&gt; true
     * wildcardMatch("c.txt", "*.????")     --&gt; false
     * </pre>
     * N.B. the sequence "*?" does not work properly at present in match strings.
     *
     * @param fileName
     * 		the fileName to match on
     * @param wildcardMatcher
     * 		the wildcard string to match against
     * @return true if the fileName matches the wildcard string
     * @see IOCase#SENSITIVE
     */
    public static boolean wildcardMatch(final java.lang.String fileName, final java.lang.String wildcardMatcher) {
        return org.apache.commons.io.FilenameUtils.wildcardMatch(fileName, wildcardMatcher, org.apache.commons.io.IOCase.SENSITIVE);
    }

    /**
     * Checks a fileName to see if it matches the specified wildcard matcher
     * allowing control over case-sensitivity.
     * <p>
     * The wildcard matcher uses the characters '?' and '*' to represent a
     * single or multiple (zero or more) wildcard characters.
     * N.B. the sequence "*?" does not work properly at present in match strings.
     *
     * @param fileName
     * 		the fileName to match on
     * @param wildcardMatcher
     * 		the wildcard string to match against
     * @param caseSensitivity
     * 		what case sensitivity rule to use, null means case-sensitive
     * @return true if the fileName matches the wildcard string
     * @since 1.3
     */
    public static boolean wildcardMatch(final java.lang.String fileName, final java.lang.String wildcardMatcher, org.apache.commons.io.IOCase caseSensitivity) {
        if ((fileName == null) && (wildcardMatcher == null)) {
            return true;
        }
        if ((fileName == null) || (wildcardMatcher == null)) {
            return false;
        }
        if (caseSensitivity == null) {
            caseSensitivity = org.apache.commons.io.IOCase.SENSITIVE;
        }
        final java.lang.String[] wcs = org.apache.commons.io.FilenameUtils.splitOnTokens(wildcardMatcher);
        boolean anyChars = false;
        int textIdx = 0;
        int wcsIdx = 0;
        final java.util.Stack<int[]> backtrack = new java.util.Stack<>();
        // loop around a backtrack stack, to handle complex * matching
        do {
            if ((backtrack.size()) > 0) {
                final int[] array = backtrack.pop();
                wcsIdx = array[0];
                textIdx = array[1];
                anyChars = true;
            }
            // loop whilst tokens and text left to process
            while (wcsIdx < (wcs.length)) {
                // set any chars status
                // matching text token
                // any chars then try to locate text token
                // token not found
                // matching from current position
                // couldnt match token
                // matched text token, move text index to end of matched token
                if (wcs[wcsIdx].equals("?")) {
                    // ? so move to next text char
                    textIdx++;
                    if (textIdx > (fileName.length())) {
                        break;
                    }
                    anyChars = false;
                }// set any chars status
                // matching text token
                // any chars then try to locate text token
                // token not found
                // matching from current position
                // couldnt match token
                // matched text token, move text index to end of matched token
                else
                    if (wcs[wcsIdx].equals("*")) {
                        anyChars = true;
                        if (wcsIdx == ((wcs.length) - 1)) {
                            textIdx = fileName.length();
                        }
                    }else {
                        if (anyChars) {
                            textIdx = caseSensitivity.checkIndexOf(fileName, textIdx, wcs[wcsIdx]);
                            if (textIdx == (org.apache.commons.io.FilenameUtils.NOT_FOUND)) {
                                break;
                            }
                            final int repeat = caseSensitivity.checkIndexOf(fileName, (textIdx + 1), wcs[wcsIdx]);
                            if (repeat >= 0) {
                                backtrack.push(new int[]{ wcsIdx , repeat });
                            }
                        }else {
                            if (!(caseSensitivity.checkRegionMatches(fileName, textIdx, wcs[wcsIdx]))) {
                                break;
                            }
                        }
                        textIdx += wcs[wcsIdx].length();
                        anyChars = false;
                    }
                
                wcsIdx++;
            } 
            // full match
            if ((wcsIdx == (wcs.length)) && (textIdx == (fileName.length()))) {
                return true;
            }
        } while ((backtrack.size()) > 0 );
        return false;
    }

    /**
     * Splits a string into a number of tokens.
     * The text is split by '?' and '*'.
     * Where multiple '*' occur consecutively they are collapsed into a single '*'.
     *
     * @param text
     * 		the text to split
     * @return the array of tokens, never null
     */
    static java.lang.String[] splitOnTokens(final java.lang.String text) {
        // used by wildcardMatch
        // package level so a unit test may run on this
        if (((text.indexOf('?')) == (org.apache.commons.io.FilenameUtils.NOT_FOUND)) && ((text.indexOf('*')) == (org.apache.commons.io.FilenameUtils.NOT_FOUND))) {
            return new java.lang.String[]{ text };
        }
        final char[] array = text.toCharArray();
        final java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
        final java.lang.StringBuilder buffer = new java.lang.StringBuilder();
        char prevChar = 0;
        for (final char ch : array) {
            if ((ch == '?') || (ch == '*')) {
                if ((buffer.length()) != 0) {
                    list.add(buffer.toString());
                    buffer.setLength(0);
                }
                // ch == '*' here; check if previous char was '*'
                if (ch == '?') {
                    list.add("?");
                }// ch == '*' here; check if previous char was '*'
                else
                    if (prevChar != '*') {
                        list.add("*");
                    }
                
            }else {
                buffer.append(ch);
            }
            prevChar = ch;
        }
        if ((buffer.length()) != 0) {
            list.add(buffer.toString());
        }
        return list.toArray(new java.lang.String[list.size()]);
    }
}

