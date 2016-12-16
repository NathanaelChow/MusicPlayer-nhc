package com.example.student.musicplayer_nhc;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer song1;

    private Button pauseButton;
    private Button playButton;
    private Button stopButton;
    private Button rewindButton;
    private Button forwardButton;

    private TextView currentTimeView;
    private TextView totalTimeView;

    private double currentTimeMS;
    private double totalTimeMS;

    private Handler time = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Song Config
        song1 = MediaPlayer.create(getApplicationContext(), R.raw.arduous_task);

        //Total time related items
        totalTimeMS = song1.getDuration();

        int totalMinutes = (int) (totalTimeMS / 1000 / 60);
        int totalSeconds = ((int) (totalTimeMS / 1000)) % 60;

        //Sets text total time view
        totalTimeView = (TextView) findViewById(R.id.totalTime);
        totalTimeView.setText(totalMinutes + " min, " + totalSeconds + " sec");

        //Button Config
        pauseButton = (Button) findViewById(R.id.pause);
        playButton = (Button) findViewById(R.id.play);
        stopButton = (Button) findViewById(R.id.stop);
        rewindButton = (Button) findViewById(R.id.rewind);
        forwardButton = (Button) findViewById(R.id.forward);

        //Time Handler for current time view
        time.postDelayed(UpdateSongTime, 100);

    }

    //Time Updater
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            currentTimeMS = song1.getCurrentPosition();

            int currentMinutes = (int) (currentTimeMS / 1000 / 60);
            int currentSeconds = ((int) (currentTimeMS / 1000)) % 60;

            currentTimeView = (TextView) findViewById(R.id.currentTime);
            currentTimeView.setText(currentMinutes + " min, " + currentSeconds + " sec");

            //For Rewind / Forward Buttons
            if(currentTimeMS > 5000) {
                rewindButton.setEnabled(true);
            }
            else {
                rewindButton.setEnabled(false);
            }

            if(currentTimeMS < totalTimeMS - 5000) {
                forwardButton.setEnabled(true);
            }
            else {
                forwardButton.setEnabled(false);
            }

            time.postDelayed(this, 100);
        }
    };

    //Function Section of Button Config
    public void play(View view) {
        song1.start();

        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
        playButton.setEnabled(false);

        Context context = getApplicationContext();
        CharSequence text = "The song is now playing.";
        int duration = Toast.LENGTH_SHORT;
        Toast playMessage= Toast.makeText(context, text, duration);
        playMessage.show();
    }

    public void pause(View view) {
        song1.pause();

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);

        Context context = getApplicationContext();
        CharSequence text = "The song is now paused.";
        int duration = Toast.LENGTH_SHORT;
        Toast pauseMessage= Toast.makeText(context, text, duration);
        pauseMessage.show();
    }

    public void stop(View view) {
        song1.seekTo(0);
        song1.pause();

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);

        Context context = getApplicationContext();
        CharSequence text = "The song is now stopped.";
        int duration = Toast.LENGTH_SHORT;
        Toast stopMessage= Toast.makeText(context, text, duration);
        stopMessage.show();
    }

    public void rewind(View view) {
        song1.seekTo((int) (currentTimeMS - 5000));
    }

    public void forward(View view) {
        song1.seekTo((int) (currentTimeMS + 5000));
    }
}