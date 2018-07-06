package com.ravindra.siit.chinkara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.ravindra.siit.chinkara.DataObj.QuestionStudentResponse;
import com.ravindra.siit.chinkara.DataObj.QuestionsObj;
import com.ravindra.siit.chinkara.DataObj.ResultObj;

import java.util.ArrayList;

public class OnlineResult extends AppCompatActivity {
    ArrayList<QuestionsObj> questionsObjs;
    ArrayList<QuestionStudentResponse> questionStudentResponses;
    float positive;
    float negative;
    float getMarks;
    float minusMarks;
    float oveMarks;
    int question;
    FirebaseDatabase database;
    DatabaseReference reference;
    float result;
    String type;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_result);
        Intent intent = getIntent();
        questionsObjs = intent.getParcelableArrayListExtra("totQues");
        question = intent.getIntExtra("question", 0);
        questionStudentResponses = intent.getParcelableArrayListExtra("questionList");
        type = intent.getStringExtra("exam");
        database = FirebaseDatabase.getInstance();
        positive = intent.getFloatExtra("positive",0);
        negative = intent.getFloatExtra("negative",0);
        textView = findViewById(R.id.textView5);
        setResultData();
        getData();
    }

    private void getData() {
        DatabaseReference reference = database.getReference("show_msg_after_exam");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    textView.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setResultData() {

        int attempt=0;
        for (int i=0;questionStudentResponses.size()>i;i++){
            if (questionStudentResponses.get(i).getAttempt()==1){
                attempt++;
            }
        }
        int right = 0;
        int wrong = 0;
        for (int i=0;i<attempt;i++){
            if (questionStudentResponses.get(i).getAttempt()==1) {
                if (questionStudentResponses.get(i).getRealAns().trim().equals(questionStudentResponses.get(i).getUserAns().trim())) {
                    right++;
                }
            }
        }
        wrong = attempt-right;
        getMarks = positive*right;
        minusMarks = negative*wrong;
        oveMarks = getMarks-minusMarks;
        result = oveMarks*100/(question*positive);
        Log.d("rkbb","result="+result);
        ResultObj temp = new  ResultObj(question,attempt,right,wrong,getMarks,minusMarks,oveMarks,result+"");
        Log.d("rkbb",temp.getAttemptQuestions()+"");
        Log.d("rkbb",temp.getMaximumQuestions()+"");
        Log.d("rkbb",temp.getRightQuestions()+"");
        Log.d("rkbb",temp.getPositiveMarking()+"");
        Log.d("rkbb",temp.getNegativeMarking()+"");
        Log.d("rkbb",temp.getWrongQuestions()+"");
        Log.d("rkbb",temp.getTotalMarks()+"");
        Log.d("rkbb",temp.getOverAllResultInPercentage()+"");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = database.getReference("online_exam/result/"+type+"/"+user.getUid()+"_"+user.getPhoneNumber());
        reference.setValue(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(OnlineResult.this, "data saved", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OnlineResult.this, "Data Saving Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}