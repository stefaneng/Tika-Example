package com.example;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by stefan on 4/21/14.
 */
public class ExampleTika {
    public static void main(String[] args) {
        File file = new File("src/main/resources/test.pdf");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Metadata metadata = new Metadata();
        BodyContentHandler ch = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();

        String mimeType = null;
        try {
            mimeType = new Tika().detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        metadata.set(Metadata.CONTENT_TYPE, mimeType);

        try {
            parser.parse(is, ch, metadata, new ParseContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String name : metadata.names()) {
            System.out.println(name + " -- " + metadata.get(name));
        }

        System.out.println(ch.toString());
    }
}
