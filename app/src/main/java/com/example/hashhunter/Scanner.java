package com.example.hashhunter;

import android.os.Message;
import android.util.Log;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @References SHA-256 hashing from GeeksforGeeks; Aug 7th, 2019; https://www.geeksforgeeks.org/sha-256-hash-in-java/
 */
public class Scanner {
    public static void calculatePoints(String code) {
        String result;
        try {
            result = toHexString(getHashedCode(code));
            Log.d("SCANNER_DEBUG", result); // print output
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("SCANNER_ERROR", "Hashing algorithm invalid");
        }
    }
    /**
     *
     * @param code code from QR/bar
     * @return hashed code in byte array
     * @throws NoSuchAlgorithmException if hashing algorithm doesn't exist
     */
    public static byte[] getHashedCode(String code) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(code.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * convert hashed code in byte array into hexadecimal string
     * @param hash hashed code in byte array
     * @return resulting hexadecimal string
     */
    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
