/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class Vietnamese {
    private static final Pattern DIACRITICS
            = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String unaccent(String s) {
        if (s == null) {
            return "";
        }
        String tmp = Normalizer.normalize(s, Normalizer.Form.NFD);
        tmp = DIACRITICS.matcher(tmp).replaceAll("");
        tmp = tmp.replace('đ', 'd').replace('Đ', 'D');
        return tmp;
    }
}
