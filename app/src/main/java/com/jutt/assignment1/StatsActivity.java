package com.jutt.assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

        rows = bStatsParsed().split("\\n");
        TableLayout bsTable =(TableLayout) findViewById(R.id.bs_table);

        for(int j = 0; j < rows.length; j++) {
            String[] columns = rows[j].split(",");
            View view = bsTable.getChildAt(j);
            if (view instanceof TableRow) {
                TableRow tableRow = (TableRow) view;
                for(int i = 0; i < columns.length; i++) {
                    TextView cell=(TextView) tableRow.getChildAt(i);
                    cell.setText(columns[i]);
                }
            }
        }
    }

    public void sendEmail () {

        StringBuilder emailText = new StringBuilder();

        emailText.append("Reaction Stats:\n\n");
        emailText.append(rStatsParsed());

        emailText.append("\n\nBuzzer Stats:\n\n");
        emailText.append(rStatsParsed());

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_SUBJECT, "Reaction and Buzzer Stats");
        i.putExtra(Intent.EXTRA_TEXT   , emailText.toString());
        startActivity(Intent.createChooser(i, "Send mail..."));
    }

    public void confirmClear(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete all your stats?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                data.clearStats();
                saveInFile();
                Toast.makeText(StatsActivity.this, "Stats Deleted", Toast.LENGTH_SHORT).show();
                update();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private String bStatsParsed() {
        StringBuilder bString = new StringBuilder();
        StatsCalc calc = new StatsCalc(data);
        int[] numPlayers = {2,3,4};

        bString.append(" , 2 Player, 3 Player, 4 Player");

        for (int i = 1; i <= 4; i++){
            bString.append("\nPlayer ").append(i);
            for (int n:numPlayers) {
                bString.append(",");
                bString.append(String.valueOf(calc.countBuzzes(new Player(i), n)));
            }
        }

        return bString.toString();
    }
    private String rStatsParsed() {
        StringBuilder rString = new StringBuilder();

        StatsCalc calc = new StatsCalc(data);
        int[] ranges = {0,10,100};

        rString.append(" , All Time, Last 10, Last 100");

        rString.append("\nMin");
        for (int n:ranges) {
            rString.append(",").append(String.valueOf(calc.min(n).doubleValue() / 1000)).append(" s");
        }
        rString.append("\nMax");
        for (int n:ranges) {
            rString.append(",").append(String.valueOf(calc.max(n).doubleValue() / 1000)).append(" s");
        }
        rString.append("\nAverage");
        for (int n:ranges) {
            rString.append(",").append(String.valueOf(calc.avg(n).doubleValue() / 1000)).append(" s");
        }
        rString.append("\nMedian");
        for (int n:ranges) {
            rString.append(",").append(String.valueOf(calc.median(n).doubleValue() / 1000)).append(" s");
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
        if (id == R.id.action_clear) {
            confirmClear();
            return true;
        }

        if (id == R.id.action_email){
            sendEmail();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
