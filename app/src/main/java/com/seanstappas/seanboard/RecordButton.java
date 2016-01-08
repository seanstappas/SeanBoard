package com.seanstappas.seanboard;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by Sean on 12/23/2015.
 */
public class RecordButton extends ImageButton {
    private static final String LOG_TAG = "AudioRecordTest";
    private boolean isRecording = false;
    private boolean isSafeToPress = true;
    private String fileName;
    private MediaRecorder mRecorder = null;
    private int buttonNumber;

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(clicker);
        buttonNumber = Integer.parseInt(this.getTag().toString());
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean mStartRecording) {
        this.isRecording = mStartRecording;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            if (isSafeToPress) {
                toggleRecording(isRecording);
            }
        }

    };

    private void toggleRecording(boolean isRecording) {
        isSafeToPress = false;
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
        }
        isSafeToPress = true;
    }

    private void startRecording() {
        MainActivity.beginRecord(buttonNumber);
        isRecording = true;
        setImageResource(R.mipmap.stop);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        isRecording = false;
        setImageResource(R.mipmap.record);
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public MediaRecorder getMediaRecorder() {
        return mRecorder;
    }

    public void setMediaRecorder(MediaRecorder mRecorder) {
        this.mRecorder = mRecorder;
    }
}
