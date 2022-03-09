package com.example.hashhunter;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.security.NoSuchAlgorithmException;

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
public class ScannerTest {
    @Test
    public void checkCodeToPoints() {
        assertEquals(111, Scanner.getCodePoints("BFG5DGW54\n"));
        assertEquals(94, Scanner.getCodePoints("reddit.com"));
        assertEquals(14, Scanner.getCodePoints("reddit.com"));
    }

    @Test
    public void checkHashedCodeToPoints() {
        assertEquals(111, Scanner.calculatePoints("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6"));
        assertEquals(94, Scanner.calculatePoints("90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644"));
        assertEquals(14, Scanner.calculatePoints("c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a"));
    }

    @Test
    public void checkCodeToHash() {
        try {
            String hashedCode;
            // CASE 1
            hashedCode = Scanner.toHexString(Scanner.getHashedCode("BFG5DGW54\n"));
            assertEquals("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6", hashedCode);
            // CASE 2
            hashedCode = Scanner.toHexString(Scanner.getHashedCode("reddit.com"));
            assertEquals("90ce957778d13b453b7416c943e1720b3379d7c77814c6b70028cbbf7aecb644", hashedCode);
            // CASE 3
            hashedCode = Scanner.toHexString(Scanner.getHashedCode("7801141456"));
            assertEquals("c6438656c4aeec951c2a012964623054d3e2f45d5e3c5e7c01faf347c9a5f51a", hashedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

