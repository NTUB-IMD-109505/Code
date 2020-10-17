package com.example.barcodenum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView textView3;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout =(LinearLayout)findViewById(R.id.layout);
        textView3 =(TextView)findViewById(R.id.textView3);
        getPC();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean j = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                String jj = j + "";
                textView3.setText(jj);
                if (jj.equals("true")) {
                    go();
                }else {
                    textView3.setText("請重啟app,給予相機權限後,以繼續使用");
                }
            }
        }
        );
    }

    public void getPC(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }

    public void go(){
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView3.setText(millisUntilFinished/1000 + "秒");
            }
            public void onFinish() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        }.start();
    }
}
