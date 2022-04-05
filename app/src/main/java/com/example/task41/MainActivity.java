package com.example.task41;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    long elapsedTime;
    private static final String KEY_INDEX = "index";
    public int choice = 1;
    public String time;
    public long lasttime;
    public String tasktype;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.textView15);
        lasttime=SystemClock.elapsedRealtime() - elapsedTime;
        ImageButton Start = findViewById(R.id.Start);
        ImageButton Pause = findViewById(R.id.Pause);
        ImageButton Stop = findViewById(R.id.Stop);
        TextView tip2 = findViewById(R.id.tip);
        EditText Taskinput = findViewById(R.id.Taskinput);
        TextView tip = findViewById(R.id.tip);

        if (savedInstanceState != null) {
            lasttime = savedInstanceState.getLong("time");
            tasktype = savedInstanceState.getString("tasktype");
            choice = savedInstanceState.getInt("choice");
            chronometer.setBase(lasttime);
            if(choice==0)
            chronometer.start();
            if(choice==1)
            chronometer.stop();

        }
        SharedPreferences Records = getSharedPreferences("data", MODE_PRIVATE);
        time = Records.getString("time", "00:00");
        tasktype = Records.getString("tasktype", "....");


        tip.setText("You spend " + time + " on " + tasktype + " last time");

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
                chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
                chronometer.start();
                choice = 0;
            }
        });

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
                choice = 1;
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
                     time = (String) chronometer.getText();
                if (hour == 0)
                    time = time.substring(3, time.length());
                String tasktype = String.valueOf(Taskinput.getText());
                chronometer.setBase(SystemClock.elapsedRealtime());
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("time", time);
                editor.putString("tasktype", tasktype);
                if (tasktype.isEmpty()) ;//
                {
                    editor.putString("tasktype", "....");
                }
                editor.apply();
            }
        });


    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putLong("time", lasttime);
        savedInstanceState.putString("tasktype", tasktype);
        savedInstanceState.putInt("choice", choice);
        super.onSaveInstanceState(savedInstanceState);
        // Always call the superclass so it can save the view hierarchy state
    }
}