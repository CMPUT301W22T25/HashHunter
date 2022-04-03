package com.example.hashhunter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteGameCodeActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.DeleteGameCodeActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView gameCodeList;
    ArrayAdapter<GameCode> gameCodeAdapter;
    ArrayList<GameCode> gameCodeDataList = new ArrayList<GameCode>();
    Integer selectedGameCodeIndex = -1;

//    public interface OnArrFilledListener {
//        void onArrFilled(ArrayList<Long> arr);
//        void onError(Exception taskException);
//    }
//
//    public void getMaxPoints(String playerID, OnArrFilledListener listener) {
//        FirestoreController.getGameCodesWithOwner(playerID).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    ArrayList<Long> pointsArr = new ArrayList<>();
//
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Long points = document.getLong("points");
//                        pointsArr.add(points);
//                    }
//
//                    listener.onArrFilled(pointsArr);
//                } else {
//                    listener.onError(task.getException());
//                }
//            }
//        });
//    }

    // this handles the result from the scan activity
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;

                        String scannedCode = intent.getStringExtra(ScanActivity.EXTRA_SCANNED_UNAME);

                        //check which gamecodes have the same title
                        //https://stackoverflow.com/questions/62675759/how-to-remove-an-element-from-an-array-in-multiple-documents-in-firestore
                        //https://cloud.google.com/firestore/docs/query-data/get-data#javaandroid_4
                        FirestoreController.getGameCodeListWithCode(scannedCode)
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                // put each code in the qr list
                                                GameCode code = document.toObject(GameCode.class);
                                                gameCodeDataList.add(code);
                                            }

                                            gameCodeAdapter.notifyDataSetChanged();
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(DeleteGameCodeActivity.this, "result not ok", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private ActivityResultLauncher<String> requestCameraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(DeleteGameCodeActivity.this, ScanActivity.class);
                    mStartForResult.launch(intent);
                } else {
                    Toast.makeText(DeleteGameCodeActivity.this, "Permission denied to access your camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_game_code);
        gameCodeList = findViewById(R.id.game_code_list_view);
        gameCodeAdapter = new GameCodeList(this, gameCodeDataList);

        gameCodeList.setAdapter(gameCodeAdapter);

        requestCameraLauncher.launch(Manifest.permission.CAMERA);

        gameCodeAdapter.notifyDataSetChanged();

        gameCodeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGameCodeIndex = i;
                GameCode code = gameCodeDataList.get(i);
                FirestoreController.getGameCodeWithCodeLatLon(code.getCode(), code.getLatitude(), code.getLongitude())
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document: task.getResult()) {
                                        String gameCodeID = document.getId();
                                        Integer pointsToSubtract = code.getPoints();
                                        // delete the gameCode from the gameCodeList of the players that have scanned it
                                        FirestoreController.getPlayersWithScannedCode(gameCodeID)
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document: task.getResult()) {
                                                                document.getReference().update("gameCodeList", FieldValue.arrayRemove(gameCodeID));
                                                                document.getReference().update("totalPoints", FieldValue.increment(Long.valueOf(-1*pointsToSubtract)));
                                                                document.getReference().update("totalGameCode", FieldValue.increment(Long.valueOf(-1)));

                                                                // see if max points need to be changed
                                                                // https://stackoverflow.com/questions/53064375/how-can-i-return-data-and-not-0-value-from-firestore-through-java-method
//                                                                Long playerMaxGameCodePoints = (Long) document.get("maxGameCodePoints");

                                                                Player player = document.toObject(Player.class);
                                                                String playerID = document.getId();
                                                                HashMap<String, Object> objectToUpdate = new HashMap<>();
                                                                Integer maxPoints = player.getMaxGameCodePoints();
                                                                if (pointsToSubtract.equals(maxPoints)) {
                                                                    FirestoreController.getGameCodeList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            Integer max = 0;
                                                                            if (player.getGameCodeList().size() >0) {
                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                    String id = document.getId();
                                                                                    if (!id.equals(gameCodeID) && player.getGameCodeList().contains(id)) {
                                                                                        GameCode code = document.toObject(GameCode.class);
                                                                                        Integer points = code.getPoints();
                                                                                        if (points > max) {
                                                                                            max = points;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            objectToUpdate.put("maxGameCodePoints",max);
                                                                            db.collection("Players")
                                                                                    .document(playerID)
                                                                                    .update(objectToUpdate);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        } else {
                                                            Log.d(TAG, "Error: ", task.getException());
                                                        }
                                                    }
                                                });

                                        // delete the comments associated with the gameCode
                                        try {
                                            ArrayList<String> gameCodeComments = (ArrayList<String>) document.get("comments");
                                            for (int i = 0; i < gameCodeComments.size(); i++) {
                                                String commentID = gameCodeComments.get(i);
                                                FirestoreController.deleteComment(commentID);
                                            }
                                        } catch (NullPointerException e) {
                                            // no existing comments for that I assume
                                        }

                                        // delete the photos associated with the gameCode
                                        try {
                                            ArrayList<String> gameCodePhotos = (ArrayList<String>) document.get("photos");
                                            for (int i = 0; i < gameCodePhotos.size(); i++) {
                                                String photoID = gameCodePhotos.get(i);
                                                FirestoreController.deletePhoto(photoID);
                                            }
                                        } catch (NullPointerException e) {
                                            // no existing photos for the game code I assume
                                        }

                                        // delete the gameCode itself
                                        document.getReference().delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Log.d(TAG, "onComplete: successfully deleted gamecode");
                                                    }
                                                });

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
    }
}