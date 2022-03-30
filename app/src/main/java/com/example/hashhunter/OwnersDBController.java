package com.example.hashhunter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OwnersDBController {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Task<DocumentSnapshot> getOwnerInfo(String userId) {
        DocumentReference userDocRef = db.collection("Owners").document(userId);
        return userDocRef.get();
    }
}
