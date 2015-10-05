package com.jutt.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button launchTimerBtn;
    private Button launchStatsBtn;
    private Button launchBuzzerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        launchTimerBtn = (Button) findViewById(R.id.launch_timer_btn);
        launchTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTimer();
            }
        });
        launchStatsBtn = (Button) findViewById(R.id.launch_stats_btn);
        launchStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStats();
            }
        });
        launchBuzzerBtn = (Button) findViewById(R.id.launch_buzzer_btn);
        launchBuzzerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchBuzzer();
            }
        });
    }

    private void launchStats() {
        Intent intent = new Intent(this,StatsActivity.class);
        startActivity(intent);
    }

    private void launchTimer() {
        Intent intent = new Intent(this,TimerActivity.class);
        startActivity(intent);
    }

    private void launchBuzzer() {
        Intent intent = new Intent(this,BuzzerActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
