package burp.com;

import java.io.UnsupportedEncodingException;
import burp.com.org.apache.commons.codec.DecoderException;
import burp.com.org.apache.commons.codec.net.URLCodec;

import java.util.Date;
import java.util.Calendar;



/**
 * Sandbox during development.
 * You can use IDE's "run".
 */
public class App {
  public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
    /* JSONObject
    JSONObject json = new JSONObject("{\"name\":\"Taro Tanaka\"}");
    System.out.println(json.toString(4));
    */
    // URLCodec
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2021, 2, 1);
    Date date2 = calendar.getTime();
    System.out.println(date.before(date2));

    
    String enc = "Shift-JIS";

    try{
      URLCodec codec = new URLCodec("ISO-8859-1");
      String target = "%83e%83X%83g"; //sjis
      String encodedResult = codec.decode(target);
      System.out.println("encodedResult:" + encodedResult);
    }catch(Exception e){
      //System.out.println(target);
    }
  }
}