package dev.negativekb.lemonkitpvp.core.util;

import java.util.TreeMap;

public class RomanNumeral {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(20, "XX");
        map.put(19, "XIX");
        map.put(18, "XVIII");
        map.put(17, "XVII");
        map.put(16, "XVI");
        map.put(15, "XV");
        map.put(14, "XIV");
        map.put(13, "XIII");
        map.put(12, "XII");
        map.put(11, "XI");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(8, "VIII");
        map.put(7, "VII");
        map.put(6, "VI");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(3, "III");
        map.put(2, "II");
        map.put(1, "I");
    }

    public static String convert(int number) {
        int l = map.floorKey(number);
        if (number == l)
            return map.get(number);
        else
            return map.get(l) + convert(number - l);
    }

}
