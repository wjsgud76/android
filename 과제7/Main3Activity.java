package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Main3Activity extends AppCompatActivity {
    private class AccessTodayTask extends AsyncTask<String,String,Boolean> {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        @Override
        protected void onPreExecute() {
            try {
                builder = factory.newDocumentBuilder();
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String today = strings[0];
            String todayArea = strings[1];
            String uri = "http://openapi.seoul.go.kr:8088/676a754743776a7334315961555a48/xml/DailyAverageAirQuality/1/1000/"+today+"/"+todayArea;
            try {
                Document doc = builder.parse(uri);

                Element root = doc.getDocumentElement();
                NodeList nodeList = root.getElementsByTagName("row");

                for(int i=0; i<nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        TextView todayPM10 = (TextView)findViewById(R.id.today_fine_dust);
                        TextView todayPM25 = (TextView)findViewById(R.id.today_fine_particulate_matter);

                        String todayPm10 = element.getElementsByTagName("PM10").item(0).getTextContent();
                        String todayPm25 = element.getElementsByTagName("PM25").item(0).getTextContent();

                        if(todayPm10.isEmpty())
                            todayPM10.setText("없음");
                        else
                            todayPM10.setText(todayPm10);
                        if(todayPm25.isEmpty())
                            todayPM25.setText("없음");
                        else
                            todayPM25.setText(todayPm25);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        Intent intent = getIntent();
        String todayArea = intent.getStringExtra("TODAY_AREA");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(System.currentTimeMillis());

        AccessTodayTask accessTodayTask = new AccessTodayTask();
        accessTodayTask.execute(today,todayArea);


        /*
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    String todayArea = intent.getStringExtra("TODAY_AREA");

                    TextView todayPM10 = (TextView)findViewById(R.id.today_fine_dust);
                    TextView todayPM25 = (TextView)findViewById(R.id.today_fine_particulate_matter);

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    String today = format.format(System.currentTimeMillis());

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    StringBuilder xmlStringBuilder = new StringBuilder();
                    xmlStringBuilder.append("<?xml version=\"1.0\"?> <DailyAverageAirQuality> </DailyAverageAirQuality>");

                    ByteArrayInputStream input = new ByteArrayInputStream(
                            xmlStringBuilder.toString().getBytes("UTF-8"));
                    String uri = "http://openapi.seoul.go.kr:8088/676a754743776a7334315961555a48/xml/DailyAverageAirQuality/1/1000/"+today+"/"+todayArea;
                    Document doc = builder.parse(uri);
                    Element root = doc.getDocumentElement();
                    NodeList nodeList = root.getElementsByTagName("row");

                    for(int i=0; i<nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if(node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;

                            String todayPm10 = element.getElementsByTagName("PM10").item(0).getTextContent();
                            String todayPm25 = element.getElementsByTagName("PM25").item(0).getTextContent();

                            if(todayPm10.isEmpty())
                                todayPM10.setText("없음");
                            else
                                todayPM10.setText(todayPm10);
                            if(todayPm25.isEmpty())
                                todayPM25.setText("없음");
                            else
                                todayPM25.setText(todayPm25);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

         */

    }
}
