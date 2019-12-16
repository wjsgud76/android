package com.example.hw5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver SMSReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.INTERNET}, 1);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        SMSReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                Object pdus[] = (Object[])bundle.get("pdus");

                String format = bundle.getString("format");
                /*
                SmsMessage [] messages = new SmsMessage[pdus.length];

                for(int i=0; i<messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i], format);

                    String contents = messages[i].getMessageBody().toString();

                    Toast.makeText(getApplicationContext(), "SMS가 도착하였습니다.\n"+contents,Toast.LENGTH_SHORT).show();
                }
                */
                SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[0],format);
                String contents = message.getMessageBody().toString();
                Toast.makeText(getApplicationContext(), "SMS가 도착하였습니다.\n"+contents,Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(SMSReceiver,intentFilter);
    }
}
