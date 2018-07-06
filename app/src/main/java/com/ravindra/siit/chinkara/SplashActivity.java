package com.ravindra.siit.chinkara;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ravindra.siit.chinkara.DataObj.TotalObj;
import com.ravindra.siit.chinkara.DataObj.UserData;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SplashActivity extends AppCompatActivity {
    public static final int SPLASH_TIME = 1000;
    public static final int SPLASH_TIME2 = 2000;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseFirestore db;
    String currentVersion, latestVersion;
    // Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        database = MainActivity.getDatabaseObject();
        db =FirebaseFirestore.getInstance();
        getLatestVersion();
    }

    private void getLatestVersion() {
        db.collection("app").document("app_version").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("rkbb", documentSnapshot.get("latest_version").toString());
                        latestVersion = documentSnapshot.get("latest_version").toString();
                                getCurrentVersion();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("rkbb", e.getMessage());
                    }
                });
    }

    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {

            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void showUpdateDialog() {
        Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You are using old version of application");
        alertDialogBuilder.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(SplashActivity.this, "send market", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialogBuilder.setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void updateUI(final FirebaseUser currentUser) {
        final Intent[] intent = new Intent[1];
//        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeIn = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
                FrameLayout frameLayout = findViewById(R.id.splashLayout);
                frameLayout.setBackground(SplashActivity.this.getResources().getDrawable(R.drawable.secondsplash));
                frameLayout.setAnimation(fadeIn);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentVersion.equals(latestVersion)){
                            if (currentUser == null) {
                                intent[0] = new Intent(SplashActivity.this, PhoneActivity.class);
                                finish();
                                startActivity(intent[0]);
                            } else {
                                loadData(currentUser);
                                Log.d("rkbb", "else");
                            }
                        }
                        else {
                            showUpdateDialog();
                        }
                    }
                }, SPLASH_TIME2);
            }
        }, SPLASH_TIME);
    }

    private void loadData(FirebaseUser currentUser) {
        final DatabaseReference reference = database.getReference("user_detail/" + currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intent;
                if (dataSnapshot.child("name").exists()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, UserDetail.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
