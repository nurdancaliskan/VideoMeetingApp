package com.nurdancaliskan.videomeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import us.zoom.androidlib.utils.ZmMimeTypeUtils;
import us.zoom.sdk.MeetingParameter;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomSDK;

public class WaitJoinActivity extends AppCompatActivity  implements MeetingServiceListener, View.OnClickListener {
    TextView MeetingTopic, MeetingDate, MeetingTime, MeetingID;
    String topic, date, time;
    Button LeaveMeeting;
    long meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_join);

        Intent intent = getIntent();

        topic = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_TOPIC);
        date = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_DATE);
        time = intent.getStringExtra(ZmMimeTypeUtils.EXTRA_TIME);

        meetingId = intent.getLongExtra(ZmMimeTypeUtils.EXTRA_MEETING_ID, 0);

        MeetingTopic = findViewById(R.id.tvMTP);
        MeetingDate = findViewById(R.id.tvMD);
        MeetingTime = findViewById(R.id.tvMT);
        MeetingID = findViewById(R.id.tvMID);


        LeaveMeeting = findViewById(R.id.btnLM);

        if (topic != null) {
            MeetingTopic.setText("Meeting Topic: " + topic);
        }
        if (date != null) {
            MeetingDate.setText("Meeting Topic: " + date);
        }
        if (time != null) {
            MeetingTime.setText("Meeting Time: " + time);

        }
        if (meetingId > 0) {
            MeetingID.setText("Meeting ID: " + meetingId);
        }

        LeaveMeeting.setOnClickListener(this);

        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();

        if (meetingService != null){
            meetingService.addListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLM) {
            OnClick();

        }
    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {
        if (meetingStatus != MeetingStatus.MEETING_STATUS_WAITINGFORHOST) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(zoomSDK.isInitialized()){
            MeetingService meetingService = zoomSDK.getMeetingService();
            meetingService.removeListener(this);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        OnClick();

    }

    @Override
    public void onMeetingParameterNotification(MeetingParameter meetingParameter) {

    }

    private void OnClick(){
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();

        if(meetingService != null){
            meetingService.leaveCurrentMeeting(false);
        }
        finish();

    }
}