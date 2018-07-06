package com.ravindra.siit.chinkara;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.ravindra.siit.chinkara.Adapter.NotificationAdapter;
import com.ravindra.siit.chinkara.DataObj.NotificationData;
import com.ravindra.siit.chinkara.DataObj.TotalObj;

import java.util.ArrayList;


public class Notification extends AppCompatActivity implements OneSignal.NotificationReceivedHandler {
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NotificationData data;
    int total;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle("Notifications");
        listView = findViewById(R.id.notificationListView);
        loadNotifications();
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(this)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
//        getTotal("Notification Title", "Notification Body");
//        setTotalToNext(2);
    }

    private void loadNotifications() {
        database = MainActivity.getDatabaseObject();
        reference = database.getReference("notification_list");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<NotificationData> list = new ArrayList<NotificationData>();
                    for (int i = 1; dataSnapshot.child("n" + i).exists(); i++) {
//                        arr[i-1] = dataSnapshot.child("title").getValue().toString();
                        NotificationData temp = new NotificationData();
                        temp.setTitle(dataSnapshot.child("n"+i).child("title").getValue().toString().trim());
                        temp.setBody(dataSnapshot.child("n"+i).child("body").getValue().toString().trim());
                        temp.setUrl(dataSnapshot.child("n"+i).child("url").getValue().toString().trim());
                        temp.setTimestamp(Long.parseLong(dataSnapshot.child("n"+i).child("timestamp").getValue().toString().trim()));
                        list.add(temp);
                    }
                    NotificationAdapter adapter = new NotificationAdapter(Notification.this,list);
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        data = new NotificationData();
        data.setBody(notification.payload.body);
        if (notification.payload.bigPicture!=null){
            data.setUrl(notification.payload.bigPicture);
        }
        else{
            data.setUrl("null");
        }
        data.setTitle(notification.payload.title);
        data.setTimestamp(System.currentTimeMillis() / 1000);
        getTotal(data.getTitle(),data.getBody(),data.getUrl());

    }

    private void getTotal(final String title, final String body, final String url) {
        db.collection("notifications").document("tot").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("rkbb", documentSnapshot.get("total").toString());
                        TotalObj temp = documentSnapshot.toObject(TotalObj.class);
//                        total = Integer.parseInt(documentSnapshot.get("total").toString());
                        total = temp.getTotal();
                        setNotification(title, body, url);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("rkbb", e.getMessage());
                    }
                });
    }

    private void setNotification(String title, String body,String url) {
        NotificationData temp = new NotificationData();
        temp.setTitle(title);
        temp.setBody(body);
        temp.setTimestamp(System.currentTimeMillis() / 1000);
        temp.setUrl(url);
        database = MainActivity.getDatabaseObject();
        reference = database.getReference("notification_list");
        reference.child("n" + (total + 1)).setValue(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setTotalToNext(total + 1);
            }
        });
    }

    private void setTotalToNext(int i) {
        TotalObj tot = new TotalObj(i);
        db.collection("notifications").document("tot").set(tot)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Notification.this, "success", Toast.LENGTH_SHORT).show();
                    }
                });
        Toast.makeText(this, "new Tot " + i, Toast.LENGTH_SHORT).show();
    }
}
