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
    @NonNull
    public static DocumentReference getPlayerDoc(String userId){
        return db.collection("Players").document(userId);
    }

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
    //Delete gamecode reference from a certain player


    // read all documents from Players collection
    @NonNull
    public static Task<QuerySnapshot> getPlayersList() {
        CollectionReference collRef = db.collection("Players");
        return collRef.get();
    }

    public static void deletePlayerGameCodeReference(String playerId, String gameCodeId){
        //Delete username from the owners list of gamecodes
        Map<String, Object> elementToDelete = new HashMap<>();

        elementToDelete.put("gameCodeList", FieldValue.arrayRemove(gameCodeId));
        db.collection("Players")
                .document(playerId)
                .update(elementToDelete);
        //Subtract points from the player
        getGameCode(gameCodeId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                GameCode myCode = doc.toObject(GameCode.class);
                Integer points = myCode.getPoints();
                subtractPlayerTotalPoints(playerId, points);
                subtractGameCodeCount(playerId);
            }
        });


    }

    public static void subtractGameCodeCount(String playerId){

        getPlayers(playerId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Player player = task.getResult().toObject(Player.class);
                HashMap<String , Object> objectToUpdate = new HashMap<>();

                objectToUpdate.put("totalGameCode",player.getTotalGameCode()-1);
                db.collection("Players")
                        .document(playerId)
                        .update(objectToUpdate);
                }
        });

    }

    public static void subtractPlayerTotalPoints(String playerId, Integer pointToSubtract){
        getPlayers(playerId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Player player = task.getResult().toObject(Player.class);
                HashMap<String , Object> objectToUpdate = new HashMap<>();
                Integer previousPoints = player.getTotalPoints();
                objectToUpdate.put("totalPoints",previousPoints-pointToSubtract);
                db.collection("Players")
                        .document(playerId)
                        .update(objectToUpdate);
                Integer maxPoints = player.getMaxGameCodePoints();
                System.out.println(player.getGameCodeList().size());
                System.out.println("--------------------Max Points-----------------------");

                System.out.println(maxPoints);
                System.out.println(pointToSubtract);
                if (pointToSubtract.equals(maxPoints)) {
                    System.out.println("--------------------Went here Points-----------------------");

                    getGameCodeList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            System.out.println("--------------------Went here Complete-----------------------");

                            Integer max = 0;
                            if (player.getGameCodeList().size() >0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    if (player.getGameCodeList().contains(id)) {
                                        GameCode code = document.toObject(GameCode.class);
                                        Integer points = code.getPoints();
                                        if (points > max) {
                                            max = points;
                                        }

                                    }
                                }
                            }
                            System.out.println("--------------------Max-----------------------");
                            System.out.print(max);
                            System.out.println("--------------------Max-----------------------");

                            objectToUpdate.put("maxGameCodePoints",max);
                            db.collection("Players")
                                    .document(playerId)
                                    .update(objectToUpdate);

                        }
                    });
                }

            }
        });



    }


    @NonNull
    public static Task<QuerySnapshot> getPlayersWithScannedCode(String gameCodeID) {
        Query colRef =  db.collection("Players").whereArrayContains("gameCodeList", gameCodeID);
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

    public static Task<Void> updateGameCodeComment(String gameCodeId, String commentId) {

        return db.collection("GameCode").document(gameCodeId).update("comments", FieldValue.arrayUnion(commentId));

    }
    public void deleteGameCodeUsernameReference(String gameCodeId, String usernameId){

        Map<String, Object> elementToDelete = new HashMap<>();

        elementToDelete.put("owners", FieldValue.arrayRemove(usernameId));
        db.collection("GameCode")
                .document(gameCodeId)
                .update(elementToDelete);
    }


    @NonNull
    public static Task<QuerySnapshot> getGameCodeWithCodeLatLon(String code, Double lat, Double lon) {
        Query colRef = db.collection("GameCode")
                .whereEqualTo("code", code)
                .whereEqualTo("latitude", lat)
                .whereEqualTo("longitude", lon);
        return colRef.get();
    }

    @NonNull
    public static Task<QuerySnapshot> getGameCodesWithOwner(String uniqueID) {
        Query colRef = db.collection("GameCode").whereArrayContains("owners", uniqueID);
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
    public static String postComment(Comment newComment) {
        DocumentReference theDoc = db.collection("Comment").document();
        String commentCode = theDoc.getId();

        theDoc.set(newComment);
        return commentCode;
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
