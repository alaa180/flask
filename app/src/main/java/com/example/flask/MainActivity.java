package com.example.flask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // private OkHttpClient okHttpClient;
    EditText edtext;
    Button btn;
    TextView tv;
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okHttpClient = new OkHttpClient();
        edtext =findViewById(R.id.ettext);
        btn =findViewById(R.id.btn);
        tv =findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtext.getText().toString();
                RequestBody formbody = new FormBody.Builder().add("chatInput", message).build();
                try {
                    Request request = new Request.Builder().url("http://192.168.1.43:5000/chat")
                            .post(formbody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        final String s=response.body().string();
                                        tv.setText(s);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });



                        }
                    });
                }
                catch (Exception e) {
                }
            }

        });
    }}