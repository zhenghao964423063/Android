package com.example.asus.networktest;

import android.util.Log;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.jar.Attributes;

/**
 * Created by ASUS on 2018/5/5.
 */

public class ContentHandle extends DefaultHandler {
    private String nodename;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    public void startDocument()throws SAXException{
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes){
        nodename = localName;
    }
    public void characters(char[] ch,int start,int length)throws SAXException{
        if("id".equals(nodename))
            id.append(ch,start,length);
        else if("name".equals(nodename))
            name.append(ch,start,length);
        else if("version".equals(nodename))
            version.append(ch,start,length);
    }
    public void endElement(String uri,String localName,String qName)throws SAXException{
        if("app".equals(localName)){
            Log.d("MainActivity","id is"+id.toString().trim());
            Log.d("MainActivity","name is"+name.toString().trim());
            Log.d("MainActivity","version is"+version.toString().trim());
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }
    public void endDocument()throws SAXException{
        super.endDocument();
    }
}
