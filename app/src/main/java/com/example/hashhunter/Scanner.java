package com.example.hashhunter;

import android.util.Log;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import java.lang.Math;

/**
 * @References SHA-256 hashing from GeeksforGeeks; Aug 7th, 2019; https://www.geeksforgeeks.org/sha-256-hash-in-java/
 * @References hex to decimal from javatpoint; accessed Feb 22th, 2022; https://www.javatpoint.com/java-hex-to-decimal
 */
public class Scanner {
    /**
     *
     * @param code string representaiton of QR or bar code
     */
    public static void getCodePoints(String code) {
        int res;
        try {
            String hashedCode = toHexString(getHashedCode(code)); // get hashed code in hexadecimal
            Log.d("SCANNER_DEBUG", hashedCode);
            res = calculatePoints(hashedCode); // convert hashed code to points
            Log.d("SCANNER_DEBUG", Integer.toString(res)); // DEBUG print output
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("SCANNER_ERROR", "Hashing algorithm invalid");
        }
    }
    public static int calculatePoints(String code) {
        Vector<String> repeatedNums = new Vector<>();
        int points = 0;
        // locate repeating numbers
        String cache = code.substring(0, 1); // cache the first number
        for (int i=1; i<code.length(); i++) {
            if (code.charAt(i) == cache.charAt(0)) {
                // number matches with number in the cache
                cache += cache.charAt(0); // append the same number
            } else { // numbers are different
                if (cache.length() > 1) {
                    repeatedNums.add(cache); // cache is repeated numbers, store it
                }
                cache = code.substring(i, i+1); // reset cache to the current number
            }
        }
        // accumulate points based on the repeated numbers
        for (String nums : repeatedNums) {
            int decimal = Integer.parseInt(nums.substring(0, 1), 16); // convert hex to decimal
            points += Math.pow(decimal, nums.length()-1); // raise decimal by how many time number is repeated
        }
        return points;
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
