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
 * Enumeration of IO case sensitivity.
 * <p>
 * Different filing systems have different rules for case-sensitivity.
 * Windows is case-insensitive, Unix is case-sensitive.
 * <p>
 * This class captures that difference, providing an enumeration to
 * control how filename comparisons should be performed. It also provides
 * methods that use the enumeration to perform comparisons.
 * <p>
 * Wherever possible, you should use the <code>check</code> methods in this
 * class to compare filenames.
 *
 * @since 1.3
 */
/* method "checkEndsWith" was removed from this class because it was not covered by the test suite */
/* method "checkEquals" was removed from this class because it was not covered by the test suite */
/* method "checkStartsWith" was removed from this class because it was not covered by the test suite */
/* method "isCaseSensitive" was removed from this class because it was not covered by the test suite */
/* method "checkCompareTo" was removed from this class because it was not covered by the test suite */
/* method "checkIndexOf" was removed from this class because it was not covered by the test suite */
/* method "readResolve" was removed from this class because it was not covered by the test suite */
/* method "getName" was removed from this class because it was not covered by the test suite */
/* method "toString" was removed from this class because it was not covered by the test suite */
/* method "forName" was removed from this class because it was not covered by the test suite */
public enum IOCase {
SENSITIVE("Sensitive",true), INSENSITIVE("Insensitive",false), SYSTEM("System",(!(org.apache.commons.io.FilenameUtils.isSystemWindows())));
    /**
     * Serialization version.
     */
    private static final long serialVersionUID = -6343169151696340687L;

    /**
     * The enumeration name.
     */
    private final java.lang.String name;

    /**
     * The sensitivity flag.
     */
    private final transient boolean sensitive;

    // -----------------------------------------------------------------------
    /**
     * Constructs a new instance.
     *
     * @param name
     * 		the name
     * @param sensitive
     * 		the sensitivity
     */
    IOCase(final java.lang.String name, final boolean sensitive) {
        this.name = name;
        this.sensitive = sensitive;
    }

    /**
     * Checks if one string contains another at a specific index using the case-sensitivity rule.
     * <p>
     * This method mimics parts of {@link String#regionMatches(boolean, int, String, int, int)}
     * but takes case-sensitivity into account.
     *
     * @param str
     * 		the string to check, not null
     * @param strStartIndex
     * 		the index to start at in str
     * @param search
     * 		the start to search for, not null
     * @return true if equal using the case rules
     * @throws NullPointerException
     * 		if either string is null
     */
    public boolean checkRegionMatches(final java.lang.String str, final int strStartIndex, final java.lang.String search) {
        return str.regionMatches((!(sensitive)), strStartIndex, search, 0, search.length());
    }
}

