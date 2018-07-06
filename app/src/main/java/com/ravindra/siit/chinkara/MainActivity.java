package com.ravindra.siit.chinkara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;
import com.ravindra.siit.chinkara.Adapter.MenuListAdapter;
import com.ravindra.siit.chinkara.DataObj.MenuListData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static FirebaseDatabase database;
    ListView menuList;
//    int imgs[] = new int[]{R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    ArrayList<MenuListData> arrayList;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new Notification())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        mAuth = FirebaseAuth.getInstance();
        menuList = findViewById(R.id.menuListView);
        String arr[] = getResources().getStringArray(R.array.menus);
        String imgs[] = getResources().getStringArray(R.array.menu_icons);
        arrayList = new ArrayList<>();
        MenuListData temp;
        for (int i = 0; i < arr.length; i++) {
            temp = new MenuListData(imgs[i], arr[i]);
            arrayList.add(temp);
        }
        MenuListAdapter adapter = new MenuListAdapter(arrayList, this);
        Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        menuList.setOnItemClickListener(this);
        menuList.setAdapter(adapter);
        menuList.setAnimation(fadeIn);
    }


    public static FirebaseDatabase getDatabaseObject() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, ExaminationList.class);
                break;
            case 1:
                intent = new Intent(this, OnlineExam.class);
                break;
            case 2:
                intent = new Intent(this, Syllabus.class);
                break;
            case 3:
                intent = new Intent(this, UserDetail.class);
                intent.putExtra("from_home", true);
                break;
            case 4:
                intent = new Intent(this, Notification.class);
                break;
            case 5:
                mAuth.signOut();
                intent = new Intent(this, PhoneActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finishAffinity();
                break;

        }
        startActivity(intent);
    }
}
