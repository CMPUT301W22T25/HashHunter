package com.example.hashhunter;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Test to check functionalities of firestore controller
 */
public class FirestoreControllerTest {
    @Test
    public void checkGetUserInfo() {
        FirestoreController.getUserInfo("01f940ae-9b3d-4f80-9921-1f1774a5e149").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                // log them in and start another activity
                if (document.exists()) {
                    String username = (String) document.getData().get("com.example.hashhunter.username");
                    assertEquals("Test1", username);
                } else {
                    // document doesn't exist
                    assertTrue(false);
                }
            } else {
                // task failed
                assertTrue(false);
            }
        });
    }
}
