package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }


    }

    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.date_info_check_btn: {
                EditText dateEditText = (EditText) findViewById(R.id.date_edit);
                EditText dateAreaEditText = (EditText)findViewById(R.id.date_area_edit);
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("DATE",dateEditText.getText().toString());
                intent.putExtra("DATE_AREA",dateAreaEditText.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.today_info_check_btn: {
                EditText todayAreaEditText = (EditText)findViewById(R.id.today_area_edit);
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("TODAY_AREA",todayAreaEditText.getText().toString());
                startActivity(intent);
                break;
            }
        }
    }
}
