package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int count;
    String[] target = {"hello","android","ILoveSabit","DoHomework","F"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        TextView targetTextView = (TextView)findViewById(R.id.targetTextView);
        final TextView timeTextView = (TextView)findViewById(R.id.timeTextView);
        EditText targetEditText = (EditText)findViewById(R.id.targetEditText);

        Random rand = new Random();
        int rIndex = rand.nextInt(5);
        targetTextView.setText(target[rIndex]);

        Thread countThread = new Thread() {
            @Override
            public void run() {
                count = 0;
                for(int i=0; i<Integer.MAX_VALUE; i++) {
                    count++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timeTextView.setText("Time:"+ count);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        countThread.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        count = 0;

        TextView targetTextView = (TextView)findViewById(R.id.targetTextView);
        Random rand = new Random();
        int rIndex = rand.nextInt(5);
        targetTextView.setText(target[rIndex]);

        EditText targetEditText = (EditText)findViewById(R.id.targetEditText);
        targetEditText.setText("");
    }

    public void onClick(View v) {
        TextView targetTextView = (TextView)findViewById(R.id.targetTextView);
        EditText targetEditText = (EditText)findViewById(R.id.targetEditText);

        if(targetTextView.getText().toString().equals(targetEditText.getText().toString())) {
            Intent intent = new Intent();
            intent.setClass(this, Main2Activity.class);
            intent.putExtra("COUNT", count);
            startActivityForResult(intent, 0);
        }
        else {
            Toast.makeText(this.getApplicationContext(),"틀렸습니다",Toast.LENGTH_LONG).show();
        }
    }
}
