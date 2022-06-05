package com.nurdancaliskan.videomeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import us.zoom.sdk.ZoomSDK;

public class HomeActivity extends AppCompatActivity {
    Button StartMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StartMeeting = findViewById(R.id.btnSIM);
        StartMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstantMeeting();
                
            }
        });
    }

    private void InstantMeeting() {
        ZoomSDK sdk = ZoomSDK.getInstance();
    }
}