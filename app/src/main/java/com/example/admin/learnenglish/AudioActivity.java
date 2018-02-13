package com.example.admin.learnenglish;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class AudioActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    ImageButton btnPlay, skipNext, skipPrev;
    boolean started;
    TextView audioInfo, startTime, endTime, audioTitle;
    SeekBar seekBar;
    int audioLength;
    String title  = "Fifty famous stories retold";
    String[] subTitles = {"Story #1", "Story #2", "Story #3", "Story #4", "Story #5"};
    int idNum;
    @Override
    public void onBackPressed() {
        mPlayer.stop();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        btnPlay = (ImageButton) findViewById(R.id.playMusic);
        audioInfo = (TextView) findViewById(R.id.audioInfo);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        audioTitle = (TextView) findViewById(R.id.titleAudio);
        skipNext = (ImageButton) findViewById(R.id.skipNext);
        skipPrev = (ImageButton) findViewById(R.id.skipPrev);

        idNum = getIntent().getExtras().getInt("id");

        init();
        checkForClickable();

        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int pos = mPlayer.getCurrentPosition() / 1000;
                int min = pos / 60;
                int s = pos - min * 60;
                String sStr = (s > 9) ? "" + s : ("0" + s);
                startTime.setText(min + ":" + sStr);
                handler.postDelayed(this, delay);
                seekBar.setProgress(pos);
                System.out.println("Handler : " + pos);
            }
        }, delay);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    int min = progress / 60;
                    int s = progress - min * 60;
                    String sStr = (s > 9) ? "" + s : ("0" + s);
                    startTime.setText(min + ":" + sStr);
                    mPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!started){
                    mPlayer.start();
                    started = true;
                    btnPlay.setImageResource(R.drawable.ic_pause_black_40dp);
                }
                else{
                    mPlayer.pause();
                    started = false;
                    btnPlay.setImageResource(R.drawable.ic_play_arrow_black_40dp);
                }

            }
        });
        skipPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idNum > 1){
                    mPlayer.stop();
                    idNum--;
                    init();
                    checkForClickable();
                }
            }
        });
        skipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idNum < subTitles.length){
                    mPlayer.stop();
                    idNum++;
                    init();
                    checkForClickable();
                }
            }
        });
    }



    private void init() {
        switch(idNum) {
            case 1: mPlayer = MediaPlayer.create(AudioActivity.this, R.raw.fiftyfamous01);break;
            case 2: mPlayer = MediaPlayer.create(AudioActivity.this, R.raw.fiftyfamous02);break;
            case 3: mPlayer = MediaPlayer.create(AudioActivity.this, R.raw.fiftyfamous03);break;
            case 4: mPlayer = MediaPlayer.create(AudioActivity.this, R.raw.fiftyfamous04);break;
            default: mPlayer = MediaPlayer.create(AudioActivity.this, R.raw.fiftyfamous05);break;
        }
        audioLength = mPlayer.getDuration() / 1000;
        int min = audioLength / 60;
        int s = audioLength - min * 60;
        String sStr = (s > 9) ? "" + s : ("0" + s);
        endTime.setText(min + ":" + sStr);
        seekBar.setMax(audioLength);
        started = false;
        btnPlay.setImageResource(R.drawable.ic_play_arrow_black_40dp);

    }

    void checkForClickable(){
        if(idNum == 1)
            skipPrev.setClickable(false);
        else if(idNum == subTitles.length)
            skipNext.setClickable(false);
        else{
            skipNext.setClickable(true);
            skipPrev.setClickable(true);
        }
        audioTitle.setText(title + "\n" + subTitles[idNum-1]);
    }
}
