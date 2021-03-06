package com.example.hashhunter;

import android.util.Log;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;
import java.lang.Math;

/**
 * Controller to hash strings and obtain points
 * References: SHA-256 hashing from GeeksforGeeks; Aug 7th, 2019; https://www.geeksforgeeks.org/sha-256-hash-in-java/
 * References: hex to decimal from javatpoint; accessed Feb 22th, 2022; https://www.javatpoint.com/java-hex-to-decimal
 */
public class GameCodePointsController {
    /**
     * calculate points from string representation of QR or bar code
     * @param code string representation of QR or bar code
     * @return res calculated points
     */
    public static int getCodePoints(String code) {
        int res;
        try {
            String hashedCode = toHexString(getHashedCode(code)); // get hashed code in hexadecimal
            res = calculatePoints(hashedCode); // convert hashed code to points
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("SCANNER_ERROR", "Hashing algorithm invalid");
            return -1; // indicates error
        }
    }

    /**
     * calculate points as specified by requirement from repeated digits
     * @param code
     * @return total points of the code
     */
    public static int calculatePoints(String code) {
        ArrayList<String> repeatedDigits = getRepeatedDigits(code);
        int points = 0;
        // accumulate points based on the repeated numbers
        for (String nums : repeatedDigits) {
            int decimal = Integer.parseInt(nums.substring(0, 1), 16); // convert hex to decimal
            if (decimal == 0) {
                points += Math.pow(20, nums.length()-1); // special case for
            } else {
                points += Math.pow(decimal, nums.length()-1); // raise decimal by how many time number is repeated
            }
        }
        return points;
    }
    /**
     * collect repeated digits in the hashed code
     * @param code
     * @return array of repeated digits of string
     */
    public static ArrayList<String> getRepeatedDigits(String code) {
        ArrayList<String> repeatedDigits = new ArrayList<>();
        // locate repeating numbers
        String cache = code.substring(0, 1); // cache the first number
        for (int i=1; i<code.length(); i++) {
            if (code.charAt(i) == cache.charAt(0)) {
                // number matches with number in the cache
                cache += cache.charAt(0); // append the same number
            } else { // numbers are different
                if (cache.length() > 1) {
                    repeatedDigits.add(cache); // cache is repeated numbers, store it
                }
                cache = code.substring(i, i+1); // reset cache to the current number
            }
        }
        // cache last repeated digits if exists
        if (cache.length() > 1) {
            repeatedDigits.add(cache);
        }
        return repeatedDigits;
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
