package com.ravindra.siit.chinkara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ravindra.siit.chinkara.Adapter.SyllabusAdapter;
import com.ravindra.siit.chinkara.DataObj.DataModel;

import java.util.ArrayList;

public class Syllabus2 extends AppCompatActivity implements AdapterView.OnItemClickListener{
    GridView gridView;
    ArrayList<DataModel> list;
    boolean a;
    String namenavy[] = {"AA","SSR"};
    String nameairf[] = {"X Group","Y Group"};
    String urlnavy[] = {"https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fnavy.png?alt=media&token=d2013fc7-33a4-400b-8607-016b2059a396","https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fnavy.png?alt=media&token=d2013fc7-33a4-400b-8607-016b2059a396"};
    String urlairf[] = {"https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fairforce.png?alt=media&token=fe3f5037-51d7-49e4-b481-d15f0ea64362","https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/pics%2Fairforce.png?alt=media&token=fe3f5037-51d7-49e4-b481-d15f0ea64362"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus2);
        gridView = findViewById(R.id.syllabusGrid2);
        a = getIntent().getBooleanExtra("navy",true);
        list = new ArrayList<>();
        if (a){
            DataModel temp;
            for(int i = 0;i<urlnavy.length;i++){
               temp = new DataModel(namenavy[i],urlnavy[i]);
               list.add(temp);
            }
        }

        else {
            DataModel temp;
            for (int i = 0;i<urlairf.length;i++){
                temp = new DataModel(nameairf[i],urlairf[i]);
                list.add(temp);
            }
        }
        SyllabusAdapter adapter = new SyllabusAdapter(this,list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (a){
            if (position == 0){
                aANavy();
            }
            else if (position == 1){
                sSRNavy();
            }
        }
        else {
            if (position == 0){
                xGroupAir();
            }
            else if (position == 1){
                yGroupAir();
            }
        }
    }
    public void aANavy() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 4);
        startActivity(intent);
    }

    public void yGroupAir() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 6);
        startActivity(intent);
    }

    public void sSRNavy() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 5);
        startActivity(intent);
    }

    public void xGroupAir() {
        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("syllabus", 7);
        startActivity(intent);
    }
}
