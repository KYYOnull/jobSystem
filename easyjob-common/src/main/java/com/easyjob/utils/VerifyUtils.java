package com.easyjob.utils;

import com.easyjob.enums.VerifyRegexEnum;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class VerifyUtils {

    public static boolean verify(String regex, String value) {
        if (StringTools.isEmpty(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean verify(VerifyRegexEnum regex, String value) {

        return verify(regex.getRegex(), value);
    }
}
