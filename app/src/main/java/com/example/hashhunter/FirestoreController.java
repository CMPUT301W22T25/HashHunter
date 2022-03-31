package com.example.hashhunter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreController {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
    UserInfo collection
     */
    public static Task<DocumentSnapshot> getUserInfo(String userId) {
        DocumentReference userDocRef = db.collection("UserInfo").document(userId);
        return userDocRef.get();
    }
    /*
    Owners collection
     */
    public static Task<DocumentSnapshot> getOwnerInfo(String userId) {
        DocumentReference userDocRef = db.collection("Owners").document(userId);
        return userDocRef.get();
    }
}
