package com.nurdancaliskan.videomeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class MainActivity extends AppCompatActivity {
    EditText Name,MN,MP,Email,Password;
    Button Join,Login;
    private ZoomSDKAuthenticationListener authListener = new ZoomSDKAuthenticationListener() {
        @Override
        public void onZoomSDKLoginResult(long l) {
            if (l == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
                Home();
            }

        }

        @Override
        public void onZoomSDKLogoutResult(long l) {

        }

        @Override
        public void onZoomIdentityExpired() {

        }

        @Override
        public void onZoomAuthIdentityExpired() {

        }
    };

    private void Home() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        if (zoomSDK.isLoggedIn()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.txt_name);
        MN = findViewById(R.id.txt_number);
        MP = findViewById(R.id.txt_password);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);

        Login = findViewById(R.id.btn_login);
        Join = findViewById(R.id.btnJoinMeeting);


      initializeZoom(this);

      Join.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String MeetingNumber=MN.getText().toString();
              String MeetingPassword=MP.getText().toString();
              String UserName=Name.getText().toString();

              if(MeetingNumber.trim().length()>0 && MeetingPassword.trim().length()>0 && UserName.trim().length()>0){
                  joinMeeting(MainActivity.this,MeetingNumber,MeetingPassword,UserName);
              }else {
                  Toast.makeText(MainActivity.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
              }
          }
      });

      Login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(Email != null && Email.getText() != null && Password != null && Password.getText() !=null) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                  if(email.trim().length()>0 && password.trim().length()>0 ){
                      login(email,password);
                  }

              }
          }
      });

    }

    private void login(String email, String password) {
        int result = ZoomSDK.getInstance().tryAutoLoginZoom();
        if (result == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
            ZoomSDK.getInstance().addAuthenticationListener(authListener);
        }
    }
    

    private void initializeZoom(Context context){
        ZoomSDK sdk = ZoomSDK.getInstance();
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        params.appKey="yIiEZ5VhaZ0H7MoyOKVRVCZExm24v3EDPD0C";
        params.appSecret="BohNGbuIuGqdrIOnc705Jm4jQ6mP2HeRgw5I";
        params.domain="zoom.us";
        params.enableLog=true;

        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            @Override
            public void onZoomSDKInitializeResult(int i, int i1) {

            }

            @Override
            public void onZoomAuthIdentityExpired() {

            }
        };
        sdk.initialize(context,listener,params);

    }

    private void joinMeeting(Context context,String meetingNumber,String meetingPassword,String userName){
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();
        JoinMeetingParams params = new JoinMeetingParams();
        params.displayName = userName;
        params.meetingNo = meetingNumber;
        params.password = meetingPassword;
        meetingService.joinMeetingWithParams(context,params,options);
    }

}
