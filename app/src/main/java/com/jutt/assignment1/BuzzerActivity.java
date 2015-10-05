package com.jutt.assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class BuzzerActivity extends AppCompatActivity {

    private Button b1, b2, b3, b4;
    private Integer numPlayers;
    private StatsData data;
    private static final Player p1 = new Player(1);
    private static final Player p2 = new Player(2);
    private static final Player p3 = new Player(3);
    private static final Player p4 = new Player(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzzer);

        data = new StatsData();
        b1 = (Button) findViewById(R.id.buzzer_1);
        b2 = (Button) findViewById(R.id.buzzer_2);
        b3 = (Button) findViewById(R.id.buzzer_3);
        b4 = (Button) findViewById(R.id.buzzer_4);
        b1.setOnClickListener(buzzHandler);
        b2.setOnClickListener(buzzHandler);
        b3.setOnClickListener(buzzHandler);
        b4.setOnClickListener(buzzHandler);
        numPlayers = 2;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        promptPlayers();
    }

    private void setPlayers(Integer num) {
        this.numPlayers = num;
        switch (this.numPlayers) {
            case(2):
                findViewById(R.id.buzzer_1).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_2).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_3).setVisibility(View.GONE);
                findViewById(R.id.buzzer_4).setVisibility(View.GONE);
                break;
            case(3):
                findViewById(R.id.buzzer_1).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_2).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_3).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_4).setVisibility(View.GONE);
                break;
            case(4):
                findViewById(R.id.buzzer_1).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_2).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_3).setVisibility(View.VISIBLE);
                findViewById(R.id.buzzer_4).setVisibility(View.VISIBLE);
                break;
        }
    }

    View.OnClickListener buzzHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Player winner = null;
            view.setBackgroundColor(0xff51b46d);
            switch(view.getId()) {
                case R.id.buzzer_1:
                    winner = p1;
                    break;
                case R.id.buzzer_2:
                    winner = p2;
                    break;
                case R.id.buzzer_3:
                    winner = p3;
                    break;
                case R.id.buzzer_4:
                    winner = p4;
                    break;
            }
            if(winner != null){
                saveWinner(winner);
                displayWinner(winner);
            }
            view.setBackgroundColor(0x20ffffff);
        }
    };

    private void saveWinner (Player winner) {
        BuzzerResult br = new BuzzerResult(winner, numPlayers);
        data.addBuzzer(br);
        saveInFile();
    }
    public void displayWinner(Player winner){
        AlertDialog.Builder winnerPrompt = new AlertDialog.Builder(this);
        winnerPrompt.setMessage("Player " + winner.getNumber().toString() + " Pressed it first!");
        winnerPrompt.setCancelable(false);

        winnerPrompt.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = winnerPrompt.create();
        alertDialog.show();
    }

    public void promptPlayers(){
        AlertDialog.Builder selectPlayers = new AlertDialog.Builder(this);
        selectPlayers.setTitle("Select a number of Players");
        selectPlayers.setCancelable(false);
        String[] types = {"Two", "Three", "Four"};
        selectPlayers.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        setPlayers(2);
                        break;
                    case 1:
                        setPlayers(3);
                        break;
                    case 2:
                        setPlayers(4);
                        break;
                }
            }
        });

        selectPlayers.show();
    }

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
