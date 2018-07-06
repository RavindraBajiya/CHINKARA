package com.ravindra.siit.chinkara;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.ravindra.siit.chinkara.DataObj.UserData;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserDetail extends AppCompatActivity {
    TextView nameTV;
    TextView fNameTV;
    TextView dobTV;
    TextView addressTV;
    TextView mobileTV;
    String name;
    String fname;
    String dob;
    String address;
    String mobile;
    FirebaseDatabase database;
    FirebaseUser user;
    boolean from_home;
    Calendar myCalendar;
    PhoneActivity f;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        nameTV = findViewById(R.id.userDetailName);
        fNameTV = findViewById(R.id.userDetailFname);
        dobTV = findViewById(R.id.userDetailDOB);
        addressTV = findViewById(R.id.userDetailAddress);
        mobileTV = findViewById(R.id.userDetailMobile);
        database = MainActivity.getDatabaseObject();
        user = FirebaseAuth.getInstance().getCurrentUser();
        avi = findViewById(R.id.avi);
        from_home = getIntent().getBooleanExtra("from_home",true);
//        if(from_home){
            loadData();
            f = new PhoneActivity();
            f.setCustomFontBTN(this,(Button) findViewById(R.id.userDetailSubmit));
            f.setCustomFontTV(this,(TextView) findViewById(R.id.textView3));
            f.setCustomFontTV(this,(TextView) findViewById(R.id.textView4));
            f.setCustomFontTV(this,(TextView) findViewById(R.id.textView6));
            f.setCustomFontTV(this,(TextView) findViewById(R.id.textView7));
            f.setCustomFontTV(this,(TextView) findViewById(R.id.textView8));
            f.setCustomFontTV(this,nameTV);
            f.setCustomFontTV(this,fNameTV);
            f.setCustomFontTV(this,addressTV);
            f.setCustomFontTV(this,dobTV);
            f.setCustomFontTV(this,mobileTV);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dobTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(UserDetail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobTV.setText(sdf.format(myCalendar.getTime()));
    }

    private void loadData() {
        avi.show();
        final DatabaseReference reference = database.getReference("user_detail/"+user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = new UserData();
                if (dataSnapshot.exists()) {
                 userData.setName(dataSnapshot.child("name").getValue().toString().trim());
                 userData.setfName(dataSnapshot.child("fName").getValue().toString().trim());
                 userData.setAddress(dataSnapshot.child("address").getValue().toString().trim());
                 userData.setDob(dataSnapshot.child("dob").getValue().toString().trim());
                 userData.setMobile(dataSnapshot.child("mobile").getValue().toString().trim());
                }
//                Log.d("rkbb",dataSnapshot.child("mobile").getValue().toString().trim()+"");
               nameTV.setText(userData.getName());
               fNameTV.setText(userData.getfName());
               addressTV.setText(userData.getAddress());
               mobileTV.setText(userData.getMobile());
               dobTV.setText(userData.getDob());
               avi.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                avi.hide();
            }
        });
    }

    public void submitData(View view) {
       if (verifyData()){
           UserData userData = new UserData(name,fname,dob,address,mobile);
           saveData(userData);
           avi.show();
//           Toast.makeText(this, "Verified", Toast.LENGTH_SHORT).show();
       }
    }

    private void saveData(UserData userData) {
        DatabaseReference reference = database.getReference("user_detail/"+user.getUid());
        reference.setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(UserDetail.this, "Data Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserDetail.this,MainActivity.class);
                avi.hide();
                startActivity(intent);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserDetail.this, "Data Saving Failed", Toast.LENGTH_SHORT).show();
                        avi.hide();
                    }
                });
    }

    private boolean verifyData() {
        name = nameTV.getText().toString().trim();
        fname = fNameTV.getText().toString().trim();
        dob = dobTV.getText().toString().trim();
        address = addressTV.getText().toString().trim();
        mobile = mobileTV.getText().toString().trim();
        if (!name.equals("")){
            if (!fname.equals("")){
                if (!dob.equals("")){
                    if (!address.equals("")){
                        if (verifyPhone(mobile)){
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        Toast.makeText(this, "Address Can't be Empty", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                else {
                    Toast.makeText(this, "DOB can't be Empty", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else {
                Toast.makeText(this, "Father's Name Can't be Empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Name Can't be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean verifyPhone(String mobile) {
        if (mobile.equals("")){
            Toast.makeText(this, "Enter 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mobile.length()==10){
            return true;
        }
        else {
            Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
