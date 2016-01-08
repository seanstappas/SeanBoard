package com.seanstappas.seanboard;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import android.os.Handler;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName1 = null;
    private static String mFileName2 = null;
    private static String mFileName3 = null;
    public static String[] arrFileNames = null;

    private RecordButton recordButton1 = null;
    private RecordButton recordButton2 = null;
    private RecordButton recordButton3 = null;

    private PlayButton playButton1 = null;
    private PlayButton playButton2 = null;
    private PlayButton playButton3 = null;

    private static RecordButton[] arrRecordBtns = null;
    private PlayButton[] arrPlayBtns = null;

    private SeekBar seekBar1 = null;
    private SeekBar seekBar2 = null;
    private SeekBar seekBar3 = null;

    Handler seekHandler = new Handler();

    public MainActivity() {
        mFileName1 = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName2 = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName3 = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName1 += "/sound1.3gp";
        mFileName2 += "/sound2.3gp";
        mFileName3 += "/sound3.3gp";

        arrFileNames = new String[3];
        arrFileNames[0] = mFileName1;
        arrFileNames[1] = mFileName2;
        arrFileNames[2] = mFileName3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton1 = (RecordButton) findViewById(R.id.recordButton1);
        recordButton2 = (RecordButton) findViewById(R.id.recordButton2);
        recordButton3 = (RecordButton) findViewById(R.id.recordButton3);
        arrRecordBtns = new RecordButton[3];
        arrRecordBtns[0] = recordButton1;
        arrRecordBtns[1] = recordButton2;
        arrRecordBtns[2] = recordButton3;

        playButton1 = (PlayButton) findViewById(R.id.playBtn1);
        playButton2 = (PlayButton) findViewById(R.id.playBtn2);
        playButton3 = (PlayButton) findViewById(R.id.playBtn3);
        arrPlayBtns = new PlayButton[3];
        arrPlayBtns[0] = playButton1;
        arrPlayBtns[1] = playButton2;
        arrPlayBtns[2] = playButton3;

        for (int i = 0; i < arrFileNames.length; i++) {
            arrRecordBtns[i].setFileName(arrFileNames[i]);
            arrPlayBtns[i].setFileName(arrFileNames[i]);
        }

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);

        playButton1.setSeekBar(seekBar1);
        playButton2.setSeekBar(seekBar2);
        playButton3.setSeekBar(seekBar3);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (PlayButton playButton : arrPlayBtns) {
                    SeekBar seekBar = playButton.getSeekBar();
                    if (playButton.isPlaying()) {
                        MediaPlayer mediaPlayer = playButton.getMediaPlayer();
                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                    else {
                        seekBar.setProgress(0);
                    }
                }
                seekHandler.postDelayed(this, 50);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        MediaRecorder mRecorder = null;
        for (RecordButton recButton : arrRecordBtns) {
            if (recButton != null) {
                mRecorder = recButton.getMediaRecorder();
                if (mRecorder != null) {
                    mRecorder.release();
                    recButton.setMediaRecorder(null);
                }
            }
        }

        MediaPlayer mPlayer = null;
        for (PlayButton playButton : arrPlayBtns) {
            if (playButton != null) {
                mPlayer = playButton.getMediaPlayer();
                if (mPlayer != null) {
                    mPlayer.release();
                    playButton.setMediaRecorder(null);
                }
            }
        }
    }

    public static void beginRecord(int recordBtnNumber) {
        for (int i = 0; i < arrRecordBtns.length; i++) {
            if (i != recordBtnNumber) {
                RecordButton currentButton = arrRecordBtns[i];
                if (currentButton.isRecording()) {
                    currentButton.stopRecording();
                }
            }
        }
    }

}
