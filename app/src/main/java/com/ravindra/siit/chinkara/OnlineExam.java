package com.ravindra.siit.chinkara;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OnlineExam extends AppCompatActivity {
    Intent intent;
    FirebaseDatabase database;
    DatabaseReference reference;
    long currentTime;
    long serverTime;
    Button technical;
    Button nonTechnical;
    TextView textView;
    long leftTime;
    TextView time;
    TextView textView1;
    PhoneActivity pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_exam);
        technical = findViewById(R.id.onlineExamTechnicalBtn);
        nonTechnical = findViewById(R.id.onlineExamNonTechnicalBtn);
        textView = findViewById(R.id.onlineTextMsgTV);
        time = findViewById(R.id.onlineExamTimeLeft);
        database = FirebaseDatabase.getInstance();
        textView1 = findViewById(R.id.textView9);
        pa = new PhoneActivity();
        pa.setCustomFontTV(this,textView);
        pa.setCustomFontTV(this,textView1);
        pa.setCustomFontTV(this,time);
        pa.setCustomFontBTN(this,technical);
        pa.setCustomFontBTN(this,nonTechnical);
        reference = database.getReference("online_exam").child("start_exam_time_stamp_in_sec");
        loadTimeStamp();
    }

    public void technicalBtn(View view) {
        intent = new Intent(this,OnlineExamPageDrower.class);
        intent.putExtra("exam","technical");
        startActivity(intent);
        finish();
    }

    public void nonTechnicalBtn(View view) {
        intent = new Intent(this,OnlineExamPageDrower.class);
        intent.putExtra("exam","nontechnical");
        startActivity(intent);
        finish();
    }

    public void startTime(int milisec) {
        textView1.setVisibility(View.VISIBLE);
        new CountDownTimer(milisec, 1000) {
            public void onTick(long millisUntilFinished) {
//                time.setText("seconds remaining: " + millisUntilFinished / 1000);
                int sec = (int) (millisUntilFinished / 1000);
                int hour = sec / 3600;
                sec = sec % 3600;
                int min = sec / 60;
                sec = sec % 60;
//                time.setText(hour + ":" + min + ":" + sec + "LEFT");

                time.setText(parseDateTohhmmss(hour + ":" + min + ":" + sec ));
            }

            public void onFinish() {
                textView.setVisibility(View.GONE);
                technical.setVisibility(View.VISIBLE);
                nonTechnical.setVisibility(View.VISIBLE);
                time.setVisibility(View.GONE);
                loadTimeStamp();
            }
        }.start();
    }
    public static String parseDateTohhmmss(String time) {
        String inputPattern = "h:m:s";
        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void compareTime(){
        if (leftTime<300&&leftTime>0){
            technical.setVisibility(View.VISIBLE);
            nonTechnical.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
//            time.setVisibility(View.GONE);
            time.setVisibility(View.VISIBLE);
            startTime1((int) ((serverTime - currentTime)*1000));
//            startTime(600000);

        }else if (leftTime<=0){
            technical.setVisibility(View.GONE);
            nonTechnical.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else {
            textView.setVisibility(View.GONE);
            technical.setVisibility(View.GONE);
            nonTechnical.setVisibility(View.GONE);
            time.setVisibility(View.VISIBLE);
            startTime((int)leftTime*1000-300000);
        }
    }

    private void startTime1(int i) {
        textView1.setVisibility(View.VISIBLE);
        textView1.setText("Remaining Time");
        new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {
//                time.setText("seconds remaining: " + millisUntilFinished / 1000);
                int sec = (int) (millisUntilFinished / 1000);
                int hour = sec / 3600;
                sec = sec % 3600;
                int min = sec / 60;
                sec = sec % 60;
//                time.setText(hour + ":" + min + ":" + sec + "LEFT");
                time.setText(parseDateTohhmmss(hour + ":" + min + ":" + sec ));
            }

            public void onFinish() {
                textView.setVisibility(View.VISIBLE);
                technical.setVisibility(View.GONE);
                nonTechnical.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                loadTimeStamp();
            }
        }.start();
    }

    public void loadTimeStamp(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentTime = System.currentTimeMillis() / 1000;
                    serverTime = Long.parseLong(dataSnapshot.getValue().toString());
                    leftTime = serverTime-currentTime;
                    compareTime();
                }
                else {
                    Toast.makeText(OnlineExam.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OnlineExam.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}