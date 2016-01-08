package com.seanstappas.seanboard;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by Sean on 12/23/2015.
 */
public class PlayButton extends Button {
    private static final String LOG_TAG = "AudioRecordTest";
    private String fileName;
    private MediaPlayer mPlayer = null;
    private SeekBar seekBar;
    private boolean playing = false;

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }
    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            startPlaying();
        }
    };

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(clicker);
    }

    private void startPlaying() {
        playing = true;
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                playing = false;
                mp.release();
            }

            ;
        });
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

    }

    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    public void setMediaRecorder(MediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}