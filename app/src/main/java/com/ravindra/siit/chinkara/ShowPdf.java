package com.ravindra.siit.chinkara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ShowPdf extends AppCompatActivity {
    WebView webView;
    int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        code = getIntent().getIntExtra("syllabus",0);
        String url = null;
        switch(code){
            case 1:
                toastPrint("Loading Police Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fpolice%2Fpolice.pdf?alt=media&token=5429e571-0e01-40ab-bca2-6e199b919d0d";
                break;
            case 2:
                toastPrint("Loading Army Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Farmy%2Farmy.pdf?alt=media&token=77e94113-aa31-4264-b056-2ca30e61c84f";
                break;
            case 3:
                toastPrint("Loading NDA Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fnda%2Fnda.pdf?alt=media&token=ac656303-56ec-44d5-892f-c25006b84fb8";
                break;
            case 4:
                toastPrint("Loading Navy AA Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fnavy%2Faa%2Fnavyaa.pdf?alt=media&token=cc331672-23cd-46b2-804a-5f2b25ad17be";
                break;
            case 5:
                toastPrint("Loading Navy SSR Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fnavy%2Fssr%2Fnavyssr.pdf?alt=media&token=6fb03b59-6214-43e7-996b-5f7f99922308";
                break;
            case 6:
                toastPrint("Loading AirForce Y-Group Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fairforce%2Fygroup%2Fairforceygroup.pdf?alt=media&token=53236da8-fca2-4036-87db-10b0ead9efcd";
                break;
            case 7:
                toastPrint("Loading AirForce X-Group Syllabus! Please Wait ");
                url = "https://firebasestorage.googleapis.com/v0/b/chinkara-c947c.appspot.com/o/syllabus%2Fairforce%2Fxgroup%2Fairforcexgroup.pdf?alt=media&token=f6d15ae4-8392-4239-a618-c52b226a7ebc";
                break;
        }
        try {
            url = URLEncoder.encode(url,"UTF-8");
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void toastPrint(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}