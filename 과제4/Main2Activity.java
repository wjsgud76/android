package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView main2TextView = (TextView)findViewById(R.id.main2TextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int count = bundle.getInt("COUNT");

        String str = "입력받은 시간은 "+count+"초입니다.";
        main2TextView.setText(str);
    }
}
