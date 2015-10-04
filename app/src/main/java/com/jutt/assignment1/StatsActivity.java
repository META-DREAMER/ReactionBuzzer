package com.jutt.assignment1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private StatsData data;
    private StatsCalc calc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        data = new StatsData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        calc = new StatsCalc(data);

        double[][] rStats = rStatsParsed();

        TableLayout tv=(TableLayout) findViewById(R.id.r_stats_table);
        tv.removeAllViewsInLayout();
        int flag=1;

        // when i=-1, loop will display heading of each column
        // then usually data will be display from i=0 to jArray.length()
        for(int i=-1;i<3;i++){

            TableRow tr=new TableRow(StatsActivity.this);

            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // this will be executed once
            if(flag==1){

                TextView b3=new TextView(StatsActivity.this);
                b3.setText("Min");
                tr.addView(b3);

                TextView b4=new TextView(StatsActivity.this);
                b4.setPadding(10, 0, 0, 0);
                b4.setText("Max");
                tr.addView(b4);

                TextView b5=new TextView(StatsActivity.this);
                b5.setPadding(10, 0, 0, 0);
                b5.setText("Average");
                tr.addView(b5);

                TextView b6=new TextView(StatsActivity.this);
                b6.setPadding(10, 0, 0, 0);
                b6.setText("Median");
                tr.addView(b6);
                tv.addView(tr);

                final View vline = new View(StatsActivity.this);
                vline.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tv.addView(vline); // add line below heading
                flag=0;
            } else {

                TextView b=new TextView(StatsActivity.this);
                String str=String.valueOf(rStats[i][0]);
                b.setText(str);
                tr.addView(b);

                TextView b1=new TextView(StatsActivity.this);
                b1.setPadding(10, 0, 0, 0);
                String str1= String.valueOf(rStats[i][1]);
                b1.setText(str1);
                tr.addView(b1);

                TextView b2=new TextView(StatsActivity.this);
                b2.setPadding(10, 0, 0, 0);
                String str2=String.valueOf(rStats[i][2]);
                b2.setText(str2);
                tr.addView(b2);

                TextView b21=new TextView(StatsActivity.this);
                b21.setPadding(10, 0, 0, 0);
                String str3=String.valueOf(rStats[i][3]);
                b21.setText(str3);
                tr.addView(b21);

                tv.addView(tr);
                final View vline1 = new View(StatsActivity.this);
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.WHITE);
                tv.addView(vline1);  // add line below each row
            }
        }

    }

    private double[][] rStatsParsed() {
        double[][] rStats = new double[3][4];

        for (int i = 0, j = 0; i < rStats.length; i++, j = j*10) {
            rStats[i][0] = calc.min(j).doubleValue() / 1000;
            rStats[i][1] = calc.max(j).doubleValue() / 1000;
            rStats[i][2] = calc.avg(j).doubleValue() / 1000;
            rStats[i][3] = calc.median(j).doubleValue() / 1000;
            if(j == 0)
                j = 1;
        }
        return rStats;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
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
