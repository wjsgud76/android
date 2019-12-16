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
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main2Activity extends AppCompatActivity {
    private class AccessDateTask extends AsyncTask<String,String,Boolean> {
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
            String date = strings[0];
            String area = strings[1];
            String uri = "http://openapi.seoul.go.kr:8088/676a754743776a7334315961555a48/xml/DailyAverageAirQuality/1/1000/"+date+"/"+area;
            try {
                Document doc = builder.parse(uri);

                Element root = doc.getDocumentElement();
                NodeList nodeList = root.getElementsByTagName("row");

                for(int i=0; i<nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        TextView NO2 = (TextView)findViewById(R.id.date_NO2);
                        TextView O3 = (TextView)findViewById(R.id.date_O3);
                        TextView CO = (TextView)findViewById(R.id.date_CO);
                        TextView SO2 = (TextView)findViewById(R.id.date_SO2);
                        TextView PM10 = (TextView)findViewById(R.id.date_fine_dust);
                        TextView PM25 = (TextView)findViewById(R.id.date_fine_particulate_matter);

                        String no2 = element.getElementsByTagName("NO2").item(0).getTextContent();
                        String o3 = element.getElementsByTagName("O3").item(0).getTextContent();
                        String co = element.getElementsByTagName("CO").item(0).getTextContent();
                        String so2 = element.getElementsByTagName("SO2").item(0).getTextContent();
                        String pm10 = element.getElementsByTagName("PM10").item(0).getTextContent();
                        String pm25 = element.getElementsByTagName("PM25").item(0).getTextContent();

                        if(no2.isEmpty())
                            NO2.setText("없음");
                        else
                            NO2.setText(no2);
                        if(o3.isEmpty())
                            O3.setText("없음");
                        else
                            O3.setText(o3);
                        if(co.isEmpty())
                            CO.setText("없음");
                        else
                            CO.setText(co);
                        if(so2.isEmpty())
                            SO2.setText("없음");
                        else
                            SO2.setText(so2);
                        if(pm10.isEmpty())
                            PM10.setText("없음");
                        else
                            PM10.setText(pm10);
                        if(pm25.isEmpty())
                            PM25.setText("없음");
                        else
                            PM25.setText(pm25);
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
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");
        String area = intent.getStringExtra("DATE_AREA");

        AccessDateTask accessDateTask = new AccessDateTask();
        accessDateTask.execute(date,area);

        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    String date = intent.getStringExtra("DATE");
                    String area = intent.getStringExtra("DATE_AREA");

                    TextView NO2 = (TextView)findViewById(R.id.date_NO2);
                    TextView O3 = (TextView)findViewById(R.id.date_O3);
                    TextView CO = (TextView)findViewById(R.id.date_CO);
                    TextView SO2 = (TextView)findViewById(R.id.date_SO2);
                    TextView PM10 = (TextView)findViewById(R.id.date_fine_dust);
                    TextView PM25 = (TextView)findViewById(R.id.date_fine_particulate_matter);

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    String uri = "http://openapi.seoul.go.kr:8088/676a754743776a7334315961555a48/xml/DailyAverageAirQuality/1/1000/"+date+"/"+area;
                    Document doc = builder.parse(uri);
                    Element root = doc.getDocumentElement();
                    NodeList nodeList = root.getElementsByTagName("row");

                    for(int i=0; i<nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if(node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;

                            String no2 = element.getElementsByTagName("NO2").item(0).getTextContent();
                            String o3 = element.getElementsByTagName("O3").item(0).getTextContent();
                            String co = element.getElementsByTagName("CO").item(0).getTextContent();
                            String so2 = element.getElementsByTagName("SO2").item(0).getTextContent();
                            String pm10 = element.getElementsByTagName("PM10").item(0).getTextContent();
                            String pm25 = element.getElementsByTagName("PM25").item(0).getTextContent();

                            if(no2.isEmpty())
                                NO2.setText("없음");
                            else
                                NO2.setText(no2);
                            if(o3.isEmpty())
                                O3.setText("없음");
                            else
                                O3.setText(o3);
                            if(co.isEmpty())
                                CO.setText("없음");
                            else
                                CO.setText(co);
                            if(so2.isEmpty())
                                SO2.setText("없음");
                            else
                                SO2.setText(so2);
                            if(pm10.isEmpty())
                                PM10.setText("없음");
                            else
                                PM10.setText(pm10);
                            if(pm25.isEmpty())
                                PM25.setText("없음");
                            else
                                PM25.setText(pm25);
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
