package com.lilium.intergratesearch.Utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHighLight {
    private static String mColor="#F44336";
    public static SpannableStringBuilder matcherSearchContent(String text, String[] keyword1) {
        String[] keyword = new String[keyword1.length];
        System.arraycopy(keyword1, 0, keyword, 0, keyword1.length);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        CharacterStyle span;
        String wordReg;
        for (int i = 0; i < keyword.length; i++) {
            String key = "";
            //  处理通配符问题
            if (keyword[i].contains("*") || keyword[i].contains("(") || keyword[i].contains(")")) {
                char[] chars = keyword[i].toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                        key = key + "\\" + String.valueOf(chars[k]);
                    } else {
                        key = key + String.valueOf(chars[k]);
                    }
                }
                keyword[i] = key;
            }

            wordReg = "(?i)" + keyword[i];   //忽略字母大小写
            Pattern pattern = Pattern.compile(wordReg);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                span = new ForegroundColorSpan(Color.parseColor(mColor));
                spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_MARK_MARK);
            }
        }

        return spannable;
    }
}
