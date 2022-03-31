package com.example.hashhunter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreController {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
    UserInfo collection
     */
    // read one document from UserInfo collection
    @NonNull
    public static Task<DocumentSnapshot> getUserInfo(String userId) {
        DocumentReference userDocRef = db.collection("UserInfo").document(userId);
        return userDocRef.get();
    }
    // create a new document in UserInfo collection
    @NonNull
    public static Task<Void> postUserInfo(String userId, Map<String, Object> newUser ) {
        return db.collection("UserInfo").document(userId).set(newUser);
    }
    /*
    Owners collection
     */
    // read one document from Owners collection
    @NonNull
    public static Task<DocumentSnapshot> getOwners(String userId) {
        DocumentReference userDocRef = db.collection("Owners").document(userId);
        return userDocRef.get();
    }
    /*
    Usernames collection
     */
    // read one document from Usernames collection
    @NonNull
    public static Task<DocumentSnapshot> getUsernames(String username) {
        DocumentReference userDocRef = db.collection("Usernames").document(username);
        return userDocRef.get();
    }
    // create a new document in Usernames collection
    @NonNull
    public static Task<Void> postUsernames(String username, Map<String, Object> newUser ) {
        return db.collection("Usernames").document(username).set(newUser);
    }
    /*
    Players collection
     */
    // create a new document in Players collection
    @NonNull
    public static Task<Void> postPlayers(String userId, Player player) {
        return db.collection("Players").document(userId).set(player);
    }
}
