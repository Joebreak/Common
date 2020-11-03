package org.joe.utils;

public class Numbertool {

    public static int parseIntNotNull(String s, int defaultInt) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
        }
        return defaultInt;
    }

    public static int parseIntNotNull(String s) {
        return parseIntNotNull(s, 0);
    }
}
