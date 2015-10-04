package com.jutt.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    private StatsData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update() {
        data = new StatsData();
        loadFromFile();

        String[] rows = rStatsParsed().split("\\n");

        TableLayout rsTable =(TableLayout) findViewById(R.id.rs_table);

        for(int j = 0; j < rows.length; j++) {
            String[] columns = rows[j].split(",");
            View view = rsTable.getChildAt(j);
            if (view instanceof TableRow) {
                TableRow tableRow = (TableRow) view;
                for(int i = 0; i < columns.length; i++) {
                    TextView cell=(TextView) tableRow.getChildAt(i);
                    cell.setText(columns[i]);
                }
            }
        }
    }
    private String rStatsParsed() {
        StringBuilder rString = new StringBuilder();

        StatsCalc calc = new StatsCalc(data);
        int[] ranges = {0,10,100};

        rString.append(" , All Time, Last 10, Last 100");

        rString.append("\nMin");
        for (int n:ranges) {
            rString.append(",");
            rString.append(String.valueOf(calc.min(n).doubleValue()/1000));
        }
        rString.append("\nMax");
        for (int n:ranges) {
            rString.append(",");
            rString.append(String.valueOf(calc.max(n).doubleValue()/1000));
        }
        rString.append("\nAverage");
        for (int n:ranges) {
            rString.append(",");
            rString.append(String.valueOf(calc.avg(n).doubleValue()/1000));
        }
        rString.append("\nMedian");
        for (int n:ranges) {
            rString.append(",");
            rString.append(String.valueOf(calc.median(n).doubleValue()/1000));
        }
        return rString.toString();
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
            data.clearStats();
            update();
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
