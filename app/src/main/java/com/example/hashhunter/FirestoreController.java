package com.example.hashhunter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Map;

public class FirestoreController {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
    UserInfo collection
     */
    // read one document from UserInfo collection
    @NonNull
    public static Task<DocumentSnapshot> getUserInfo(String userId) {
        DocumentReference docRef = db.collection("UserInfo").document(userId);
        return docRef.get();
    }
    // create a new document in UserInfo collection
    @NonNull
    public static Task<Void> postUserInfo(String userId, Map<String, Object> newUser ) {
        return db.collection("UserInfo").document(userId).set(newUser);
    }
    // delete a document in UserInfo collection
    @NonNull
    public static Task<Void> deleteUserInfo(String userId) {
        return db.collection("UserInfo").document(userId).delete();
    }

    /*
    Owners collection
     */
    // read one document from Owners collection
    @NonNull
    public static Task<DocumentSnapshot> getOwners(String userId) {
        DocumentReference docRef = db.collection("Owners").document(userId);
        return docRef.get();
    }
    // create a new document in Owners collection
    @NonNull
    public static Task<Void> postOwners(String userId, Map<String, Object> newUser ) {
        return db.collection("Owners").document(userId).set(newUser);
    }
    // delete a document in Owners collection
    @NonNull
    public static Task<Void> deleteOwners(String userId) {
        return db.collection("Owners").document(userId).delete();
    }

    /*
    Usernames collection
     */
    // read one document from Usernames collection
    @NonNull
    public static Task<DocumentSnapshot> getUsernames(String username) {
        DocumentReference docRef = db.collection("Usernames").document(username);
        return docRef.get();
    }
    // create a new document in Usernames collection
    @NonNull
    public static Task<Void> postUsernames(String username, Map<String, Object> newUser) {
        return db.collection("Usernames").document(username).set(newUser);
    }
    // delete a document in Usernames collection
    @NonNull
    public static Task<Void> deleteUsernames(String username) {
        return db.collection("Usernames").document(username).delete();
    }

    /*
    Players collection
     */
    // read one document from Players collection
    @NonNull
    public static Task<DocumentSnapshot> getPlayers(String userId) {
        DocumentReference docRef = db.collection("Players").document(userId);
        return docRef.get();
    }
    // create a new document in Players collection
    @NonNull
    public static Task<Void> postPlayers(String userId, Player player) {
        return db.collection("Players").document(userId).set(player);
    }
    // delete a document in Players collection
    @NonNull
    public static Task<Void> deletePlayers(String userId) {
        return db.collection("Players").document(userId).delete();
    }
    // read all documents from Players collection
    @NonNull
    public static Task<QuerySnapshot> getPlayersList() {
        CollectionReference collRef = db.collection("Players");
        return collRef.get();
    }

    @NonNull
    public static Task<QuerySnapshot> getPlayersWithScannedCode(String gameCodeID) {
        CollectionReference colRef = (CollectionReference) db.collection("Players").whereArrayContains("gameCodeList", gameCodeID);
        return colRef.get();
    }

    /*
    GameCode collection
     */
    // read one document from GameCode collection
    @NonNull
    public static Task<DocumentSnapshot> getGameCode(String gameCodeId) {
        DocumentReference docRef = db.collection("GameCode").document(gameCodeId);
        return docRef.get();
    }
    // create a new document in GameCode collection
    @NonNull
    public static Task<Void> postGameCode(String gameCodeId, GameCode gameCode) {
        return db.collection("GameCode").document(gameCodeId).set(gameCode);
    }
    // delete a document in GameCode collection
    @NonNull
    public static Task<Void> deleteGameCode(String gameCodeId) {
        return db.collection("Players").document(gameCodeId).delete();
    }
    // read all documents from GameCode collection
    @NonNull
    public static Task<QuerySnapshot> getGameCodeList() {
        CollectionReference collRef = db.collection("GameCode");
        return collRef.get();
    }
    // get a list of document with locations from GameCode
    @NonNull
    public static Task<QuerySnapshot> getGameCodeListWithLocation() {
        Query collRef = db.collection("GameCode").whereNotEqualTo("latitude",null);
        return collRef.get();
    }
    // get a list of document of GameCode with a specified title
    @NonNull
    public static Task<QuerySnapshot> getGameCodeListWithTitle(String title) {
        Query collRef = db.collection("GameCode").whereEqualTo("title", title);
        return collRef.get();
    }
    // get a list of document of GameCode with a specified code
    @NonNull
    public static Task<QuerySnapshot> getGameCodeListWithCode(String code) {
        Query collRef = db.collection("GameCode").whereEqualTo("code", code);
        return collRef.get();
    }
    @NonNull
    public static Task<Void> updateGameCodePhoto(String gameCodeId, String photoId) {
        return db.collection("GameCode").document(gameCodeId).update("photos", FieldValue.arrayUnion(photoId));
    }

    @NonNull
    public static Task<QuerySnapshot> getGameCodeWithCodeLatLon(String code, Double lat, Double lon) {
        CollectionReference colRef = (CollectionReference) db.collection("GameCode")
                .whereEqualTo("code", code)
                .whereEqualTo("latitude", lat)
                .whereEqualTo("longitude", lon);
        return colRef.get();
    }

    @NonNull
    public static Task<QuerySnapshot> getGameCodesWithOwner(String username) {
        CollectionReference colRef = (CollectionReference) db.collection("GameCode").whereArrayContains("owners", username);
        return colRef.get();
    }


    /*
    Comment collection
     */
    // read one document from Comment collection
    @NonNull
    public static Task<DocumentSnapshot> getComment(String commentId) {
        DocumentReference docRef = db.collection("Comment").document(commentId);
        return docRef.get();
    }
    // create a new document in Comment collection
    @NonNull
    public static Task<Void> postComment(String commentId, Map<String, Object> newComment) {
        return db.collection("Comment").document(commentId).set(newComment);
    }
    // delete a document in Comment collection
    @NonNull
    public static Task<Void> deleteComment(String commentId) {
        return db.collection("Comment").document(commentId).delete();
    }
    // read all documents from Comment collection
    @NonNull
    public static Task<QuerySnapshot> getCommentList() {
        CollectionReference collRef = db.collection("Comment");
        return collRef.get();
    }

    /*
    Photo collection
     */
    // read one document from Photo collection
    @NonNull
    public static Task<DocumentSnapshot> getPhoto(String photoId) {
        DocumentReference docRef = db.collection("Photo").document(photoId);
        return docRef.get();
    }
    // create a new document in Photo collection
    @NonNull
    public static Task<Void> postPhoto(String photoId, Map<String, Object> newPhoto) {
        return db.collection("Photo").document(photoId).set(newPhoto);
    }
    // delete a document in Photo collection
    @NonNull
    public static Task<Void> deletePhoto(String photoId) {
        return db.collection("Photo").document(photoId).delete();
    }
    // read all documents from Photo collection
    @NonNull
    public static Task<QuerySnapshot> getPhotoList() {
        CollectionReference collRef = db.collection("Photo");
        return collRef.get();
    }
}
