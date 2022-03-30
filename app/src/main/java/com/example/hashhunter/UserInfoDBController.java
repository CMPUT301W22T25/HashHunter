package com.example.hashhunter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoDBController {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Task<DocumentSnapshot> getUserInfo(String userId) {
        DocumentReference userDocRef = db.collection("UserInfo").document(userId);
        return userDocRef.get();
    }
}
