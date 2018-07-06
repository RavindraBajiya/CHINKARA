package com.ravindra.siit.chinkara;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import static com.ravindra.siit.chinkara.OnlineExam.parseDateTohhmmss;

public class PhoneActivity extends AppCompatActivity{
    private static final String TAG = "rkbb";
    TextView phoneTV;
    private PhoneAuthProvider.ForceResendingToken code;
    private String verificationId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthCredential credential;
    private FirebaseAuth mAuth;
    boolean flag = false;
    Button resendBt;
    boolean flag1 = false;
    TextView textView;
    Button sendOtp;
    AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        mAuth = FirebaseAuth.getInstance();
        phoneTV = findViewById(R.id.phoneNumberEditText);
        textView = findViewById(R.id.textView);
        sendOtp = findViewById(R.id.sendOtpButton);
        setCustomFontTV(this,phoneTV);
        setCustomFontTV(this,textView);
        setCustomFontBTN(this,sendOtp);
        avi = findViewById(R.id.avi);
        avi.hide();
    }
    public void setCustomFontTV(Context context, TextView view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        view.setTypeface(typeface);
    }
    public void setCustomFontBTN(Context context, Button view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        view.setTypeface(typeface);
    }

    public void sendOtp(View view) {
        avi.show();
        sendOtp();
    }

    private void sendOtp() {
        String phone;
        phone = phoneTV.getText().toString().trim();
        setUpVerificationCallBacks();
        String mobile = "+91" + phone;
        if (!flag){
            if(verifyPhone(phone)) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mobile,
                        60,
                        TimeUnit.SECONDS,
                        this,
                        mCallbacks
                );
            }
        }
        else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    mobile,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks,
                    code
            );
        }
    }

    private boolean verifyPhone(String mobile) {
        if (mobile.equals("")){
            Toast.makeText(this, "Enter 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (mobile.length()==10){
            Toast.makeText(this, "Sending Otp On +91 "+mobile, Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void setUpVerificationCallBacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(PhoneActivity.this, "onVerificationCompleted", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.d(TAG, "onverfi");
                avi.hide();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d(TAG, "onVerificationFailed");
                avi.hide();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    //Invalid Request
                    Log.d(TAG, "Invalid Credential : " + e.getLocalizedMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(PhoneActivity.this, "Too Many Requests...Please Try Again after Some time.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "SMS Quota exceeded");
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d("akak", "onCodeSent");

                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                code = forceResendingToken;
                avi.hide();
                setContentView(R.layout.activity_otp_verify);
                resendBt = findViewById(R.id.resendBtn);
                Button verify = findViewById(R.id.otpVerifyButton);
                setCustomFontBTN(PhoneActivity.this,verify);
                setCustomFontTV(PhoneActivity.this,(TextView) findViewById(R.id.otpVerifyEditText));
                setCustomFontBTN(PhoneActivity.this,resendBt);
                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
//                time.setText("seconds remaining: " + millisUntilFinished / 1000);
                        int sec = (int) (millisUntilFinished / 1000);
                        int hour = sec / 3600;
                        sec = sec % 3600;
                        int min = sec / 60;
                        sec = sec % 60;

//                time.setText(hour + ":" + min + ":" + sec + "LEFT");
                        resendBt.setText(parseDateTohhmmss(hour + ":" + min + ":" + sec ));
//                        resendBt.setText("ddfgg");
                    }

                    public void onFinish() {
                        resendBt.setText("RESEND");
                        flag1 = true;
                    }
                }.start();

            }
        };
    }


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            credential = phoneAuthCredential;
                            updateUI();
                            avi.hide();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            avi.hide();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void updateUI() {
        if (getData()){
            Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(PhoneActivity.this, UserDetail.class);
            intent.putExtra("from_home",false);
            startActivity(intent);
            finish();
        }
    }

    private boolean getData() {
        final boolean[] flag = {false};
        FirebaseDatabase database = MainActivity.getDatabaseObject();
        DatabaseReference mRef = database.getReference("userDetail/"+mAuth.getCurrentUser().getUid()+"/name");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    flag[0] = true;
                }
                else {
                    flag[0] = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (flag[0]){
            return true;
        }
        else {
            return false;
        }
    }

    public void resendOtp(View view) {
//        setContentView(R.layout.activity_phone);
        if (flag1) {
            flag = true;
            sendOtp();
        }

    }

    public void verifyOtp(View view) {
        avi.show();
        TextView textView = findViewById(R.id.otpVerifyEditText);
        String otp = textView.getText().toString().trim();
        if (otp.length()==6){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otp);
            signInWithPhoneAuthCredential(credential);
        }
        else {
            Toast.makeText(this, "Enter 6 digit otp.", Toast.LENGTH_SHORT).show();
        }
    }
}
