package com.example.googlesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        final String i = "";
        final String j = "https://project109505-1.herokuapp.com/product/query?barcode_num=2671022826268";
        final String uri = j+i;
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                        .build();
                /**設置傳送需求*/
                Request request = new Request.Builder().url(uri).build();
                /**設置回傳*/
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        /**如果傳送過程有發生錯誤*/
                        textView.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String responseData = response.body().string();
                        /**取得回傳*/
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String [] i = null;
                                String show = responseData;
                                i = show.split("\"");
                                show ="";
                                int sum = 0;
                                for(String j : i){
                                    sum +=1;
                                    if(sum%4 == 0) {
                                        show += j + "\n";
                                    }
                                }
                                textView.setText(show);                            }
                        });
                    }
                });
    }
}
