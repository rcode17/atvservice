package com.bancolombia.pocatv.utils;

public class AtvffUserUtils {

    public static boolean isValidDomain(String domain) {
        return "00".equals(domain) || "02".equals(domain) || "03".equals(domain);
    }

    public static boolean isValidAccessLevel(int accessLevel) {
        return accessLevel >= 1 && accessLevel <= 3;
    }
}