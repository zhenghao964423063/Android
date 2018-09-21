package com.example.asus.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.TypeVariable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView responseTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendrequest = (Button)findViewById(R.id.send_request);
        responseTest = (TextView)findViewById(R.id.response_test);
        sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestwithOkHttp();
            }
        });
    }
    private void sendRequestWithHttpUrlConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    ShowResponse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private  void sendRequestwithOkHttp(){
        new Thread(){
            public void run(){
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2/get_data.json").build();                   Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    ShowResponse(responseData);
                    parseJSONWithGson(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void ShowResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseTest.setText(response);
            }
        });
    }
    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while(eventType != xmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName))
                            id = xmlPullParser.nextText();
                        else if("name".equals(nodeName))
                            name = xmlPullParser.nextText();
                        else if("version".equals(nodeName))
                            version = xmlPullParser.nextText();
                    }
                    break;
                    case XmlPullParser.END_TAG :{
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is"+id);
                            Log.d("MainActivity","name is"+name);
                            Log.d("MainActivity","version is"+version);
                        }
                        break;
                    }
                    default: break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void parseXMLEithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandle contentHandle = new ContentHandle();
            xmlReader.setContentHandler(contentHandle);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i <jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity","id is "+id);
                Log.d("MainActivity","name is"+name);
                Log.d("MainActivity","version is"+version);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        private void parseJSONWithGson(String jsonData){
            Gson gson = new Gson();
            List<App>apps = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
            for(App app:apps){
                Log.d("MainActivity","id is "+app.getId());
                Log.d("MainActivity","name is"+app.getName());
                Log.d("MainActivity","version is"+app.getVersion());
            }

    }
}

