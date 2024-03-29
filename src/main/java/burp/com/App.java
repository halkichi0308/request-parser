package burp.com;

import java.io.UnsupportedEncodingException;
import burp.com.org.apache.commons.codec.DecoderException;
import burp.com.org.apache.commons.codec.net.URLCodec;

import java.util.Date;
import java.util.Calendar;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * Sandbox during development. You can use IDE's "run".
 */
public class App {
  public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
    /*
     * JSONObject JSONObject json = new JSONObject("{\"name\":\"Taro Tanaka\"}");
     * System.out.println(json.toString(4));
     */
    // JavaCompiler c = ToolProvider.getSystemJavaCompiler();
    // int r = c.run(null, null, null, "-version"); // javac -version
    // System.out.println("戻り値：" + r);

    try {
      URLCodec codec = new URLCodec("ISO-8859-1");
      String target = "%83e%83X%83g"; // sjis
      String encodedResult = codec.decode(target);
      System.out.println("encodedResult:" + encodedResult);
    } catch (Exception e) {
      // System.out.println(target);
    }
  }
}
