/**
 * PermissionsEx
 * Copyright (C) zml and PermissionsEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ninja.leaping.permissionsex.backends;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LegacyConversionUtils {
    private static final Pattern MATCHER_GROUP_PATTERN = Pattern.compile("\\((.*?)\\)");
    public static String convertPermission(String permission) {
        final StringBuffer ret = new StringBuffer();
        Matcher matcher = MATCHER_GROUP_PATTERN.matcher(permission); // Convert regex multimatches to shell globs
        while (matcher.find()) {
            matcher.appendReplacement(ret, "{" + matcher.group(1).replace('|', ',') + "}");
        }
        matcher.appendTail(ret);
        if (ret.length() > 2 && ret.substring(ret.length() - 2).equals(".*")) { // Delete .* from nodes -- this is implied now
            ret.delete(ret.length() - 2, ret.length());
        }
        return ret.toString();
    }
}
