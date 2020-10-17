package com.example.barcodenum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView textView2;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textView2 = (TextView)findViewById(R.id.textView2);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.EAN_13).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(100,100).build();
        cameraSource = new CameraSource.Builder(this,barcodeDetector).setAutoFocusEnabled(setAutoFocusEnabled(true)).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try{
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {

            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    String show = qrCodes.valueAt(0).displayValue;
                    barcodeDetector.release();
                    Intent intent = new Intent();
                    intent.setClass(Main3Activity.this , Main2Activity.class);
                    Bundle bundle =new Bundle();
                    bundle.putString("show" , show);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean setAutoFocusEnabled(boolean b) {
        return true;
    }
}
