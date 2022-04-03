package com.example.hashhunter;

import org.junit.Test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * List of cases:
 * 1. BFG5DGW54\n:
 *      - hashed: 696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6
 *      - repeated digits: bb, 999, ee, 55
 *      - total points: 11 + 81 + 14 + 5 = 111
 * 2. reddit.com:
 *      - hashed: 90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644
 *      - repeated digits: 777, 33, 77, 00, bb, 44
 *      - total points: 49 + 3 + 7 + 20 + 11 + 4 = 94
 * 3. 7801141456
 *      - hashed: c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a
 *      - repeated digits: ee
 *      - total points: 14 = 14
 * How to manually calculate points to create test cases
 * 1. hash the code using online converter as the terminal method append \n to the code: https://emn178.github.io/online-tools/sha256.html
 * 2. calculate the points by hands according to the rule described in the project description: https://eclass.srv.ualberta.ca/mod/page/view.php?id=5825439
 * Notes on generating QR code for testing point calculation
 * - use https://www.the-qrcode-generator.com/ to generate the BFG5DGW54 and press enter to add \n
 * - other website may ignore \n
 */
public class GameCodePointsControllerTest {
    /**
     * test if overall conversion from code string to points work
     */
    @Test
    public void checkCodeToPoints() {
        assertEquals(111, GameCodePointsController.getCodePoints("BFG5DGW54\n"));
        assertEquals(94, GameCodePointsController.getCodePoints("reddit.com"));
        assertEquals(14, GameCodePointsController.getCodePoints("7801141456"));
    }

    /**
     * test if conversion from hashed code string to points work
     */
    @Test
    public void checkHashedCodeToPoints() {
        assertEquals(111, GameCodePointsController.calculatePoints("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6"));
        assertEquals(94, GameCodePointsController.calculatePoints("90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644"));
        assertEquals(14, GameCodePointsController.calculatePoints("c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a"));
    }

    /**
     * test methods that calculate what digits is being repeated from a string
     */
    @Test
    public void checkCodeToRepeatedDigits() {
        ArrayList<String> repeatedDigits;
        // CASE 1
        repeatedDigits = new ArrayList<String>(Arrays.asList("bb", "999", "ee", "55"));
        assertEquals(repeatedDigits, GameCodePointsController.getRepeatedDigits("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6"));
        // CASE 2
        repeatedDigits = new ArrayList<String>(Arrays.asList("777", "33", "77", "00", "bb", "44"));
        assertEquals(repeatedDigits, GameCodePointsController.getRepeatedDigits("90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644"));
        // CASE 3
        repeatedDigits = new ArrayList<String>(Arrays.asList("ee"));
        assertEquals(repeatedDigits, GameCodePointsController.getRepeatedDigits("c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a"));

    }

    /**
     * test hashing result from scanned qr code
     */
    @Test
    public void checkCodeToHash() {
        try {
            String hashedCode;
            // CASE 1
            hashedCode = GameCodePointsController.toHexString(GameCodePointsController.getHashedCode("BFG5DGW54\n"));
            assertEquals("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6", hashedCode);
            // CASE 2
            hashedCode = GameCodePointsController.toHexString(GameCodePointsController.getHashedCode("reddit.com"));
            assertEquals("90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644", hashedCode);
            // CASE 3
            hashedCode = GameCodePointsController.toHexString(GameCodePointsController.getHashedCode("7801141456"));
            assertEquals("c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a", hashedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

