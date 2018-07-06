package com.ravindra.siit.chinkara;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ravindra.siit.chinkara.Adapter.SyllabusAdapter;
import com.ravindra.siit.chinkara.DataObj.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Syllabus extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Button aa, ssr, xGroup, yGroup, navy, army, nda, police, aforce;
    FirebaseStorage storage;
    PhoneActivity pa;
    GridView sview;
    ArrayList<DataModel> list;
    String[] name = {"Air Force","Navy","NDA","Army","Police"};
    String[] url = {"https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fairforce.png?alt=media&token=fe3f5037-51d7-49e4-b481-d15f0ea64362",
    "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fnavy.png?alt=media&token=d2013fc7-33a4-400b-8607-016b2059a396",
    "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fnda.jpg?alt=media&token=03f2b50e-c274-4760-8195-360176463593",
    "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Farmy.png?alt=media&token=26a73150-326f-49c9-8e3b-0bf87194c7f8",
    "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fpolice.jpg?alt=media&token=032104ce-bf64-4db5-98f4-a302ddd44413"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        setTitle("Syllabus");
        list = new ArrayList<>();
        sview = findViewById(R.id.syllabusGrid);
       /* aa = findViewById(R.id.syllabusAA);
        ssr = findViewById(R.id.syllabusSSR);
        xGroup = findViewById(R.id.syllabusXGroup);
        yGroup = findViewById(R.id.syllabusYGroup);
        navy = findViewById(R.id.syllabusNavy);
        aforce = findViewById(R.id.syllabusAirForce);
        army = findViewById(R.id.syllabusArmy);
        nda = findViewById(R.id.syllabusNDA);
        police = findViewById(R.id.syllabusPolice);*/
       /* pa = new PhoneActivity();
        pa.setCustomFontBTN(this,army);
        pa.setCustomFontBTN(this,nda);
        pa.setCustomFontBTN(this,yGroup);
        pa.setCustomFontBTN(this,xGroup);
        pa.setCustomFontBTN(this,aforce);
        pa.setCustomFontBTN(this,navy);
        pa.setCustomFontBTN(this,ssr);
        pa.setCustomFontBTN(this,aa);
        pa.setCustomFontBTN(this,police);*/
        storage = FirebaseStorage.getInstance();
        DataModel temp;
        for (int i = 0;i<url.length;i++){
            temp = new DataModel(name[i],url[i]);
            list.add(temp);
        }
        SyllabusAdapter adapter = new SyllabusAdapter(this,list);
        sview.setAdapter(adapter);
        sview.setOnItemClickListener(this);
    }

    public void airForceBtn() {
        Intent intent = new Intent(this,Syllabus2.class);
        intent.putExtra("navy",false);
        startActivity(intent);
    }

    public void navyBtn() {
        Intent intent = new Intent(this,Syllabus2.class);
        intent.putExtra("navy",true);
        startActivity(intent);
    }

    public void ndaBtn() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 3);
        startActivity(intent);
    }

    public void armyBtn() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 2);
        startActivity(intent);
    }

    public void policeBtn() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 1);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                airForceBtn();
                break;
            case 1:
                navyBtn();
                break;
            case 2:
                ndaBtn();
                break;
            case 3:
                armyBtn();
                break;
            case 4:
                policeBtn();
                break;
        }
    }
}
