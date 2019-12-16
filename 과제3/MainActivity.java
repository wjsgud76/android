package com.example.hw32015726053;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSubmitClick(View view) {
        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();

        EditText editTextID = (EditText)findViewById(R.id.idEdit);
        EditText editTextPW = (EditText)findViewById(R.id.pwEdit);
        EditText editTextEmail = (EditText)findViewById(R.id.emailEdit);
        EditText editTextPhone = (EditText)findViewById(R.id.phoneEdit);
        TextView textViewThx = (TextView)findViewById(R.id.thxText);

        editTextID.setText("");
        editTextPW.setText("");
        editTextEmail.setText("");
        editTextPhone.setText("");
        textViewThx.setText("회원가입 해주셔서 감사합니다.");
    }
}
