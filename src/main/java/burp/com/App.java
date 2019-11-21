package burp.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import burp.com.org.apache.commons.codec.DecoderException;
import burp.com.org.apache.commons.codec.net.URLCodec;


public class App {
  public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
    /* JSONObject
    JSONObject json = new JSONObject("{\"name\":\"Taro Tanaka\"}");
    System.out.println(json.toString(4));
    */
    // URLCOdec
    String enc = "Shift-JIS";
    URLCodec codec = new URLCodec(enc);
    String target = "%83e%83X%83g";
    String encodedResult = codec.decode(target, "Shift-JIS");
    System.out.println("エンコード結果:" + encodedResult);

  }
}