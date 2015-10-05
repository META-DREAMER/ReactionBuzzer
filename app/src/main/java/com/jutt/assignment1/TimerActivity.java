package com.jutt.assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class TimerActivity extends AppCompatActivity {

    private StatsData data;
    private Button measureReaction;
    private ReactionTimer timer;
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int REFRESH_RATE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        data = new StatsData();
        timer = new ReactionTimer();

        measureReaction = (Button) findViewById(R.id.measure_reaction);

        measureReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerHandler.sendEmptyMessage(MSG_STOP_TIMER);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        instructions();
    }

    public void instructions(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Tap the screen when it turns green to record your reaction times. Press back to exit.");

        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(TimerActivity.this, "Timer Started", Toast.LENGTH_SHORT).show();
                startGame(0);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void startGame(Integer delay) {
        timerHandler.sendEmptyMessageDelayed(MSG_START_TIMER, delay);
    }


    private void checkReaction(Integer reaction) {
        if (reaction >= 0) {
            data.addReaction(reaction);
            saveInFile();
            measureReaction.setText("Reaction Time: " + reaction.floatValue()/1000 + "s");
        } else {
            measureReaction.setText("Too Early!");
            measureReaction.setBackgroundColor(0xffc25975);
        }
        Toast.makeText(TimerActivity.this, "Restarting...", Toast.LENGTH_SHORT).show();
        startGame(2500);
    }

    Handler timerHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    measureReaction.setText("Wait...");
                    measureReaction.setBackgroundColor(0xff505050);
                    timer.resetTimer(); //start timer
                    timerHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    if(timer.delayPassed()) {
                        measureReaction.setText("Press now!");
                        measureReaction.setBackgroundColor(0xff51b46d);
                    }
                    timerHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running
                case MSG_STOP_TIMER:
                    timerHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    int rTime = timer.getReaction();//stop timer
                    checkReaction(rTime);
                    break;

                default:
                    break;
            }
        }
    };



    //Loading and saving taken from https://github.com/joshua2ua/lonelyTwitter
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(getString(R.string.filename));
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 2015-09-22
            Type dataType = new TypeToken<StatsData>() {}.getType();
            data = gson.fromJson(in, dataType);
        } catch (FileNotFoundException e) {
            data = new StatsData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.filename), 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(data, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}