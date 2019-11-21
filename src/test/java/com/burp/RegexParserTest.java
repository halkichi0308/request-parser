package com.burp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import burp.com.burp.util.RegexParser;

public class RegexParserTest {
  String LINE_SEPARATOR_PATTERN =  "\r\n|[\n\r\u2028\u2029\u0085]";
  /*
  * normal Test.
  * Using RegexParser() after do new RegexParser(rew_txt)
  */

  @Test
  public void narmal_regexMatch(){
    String target_str = "POST /dir?q=123 HTTP/1.1";
    RegexParser regexParser = new RegexParser(target_str);

    assertEquals( 
      regexParser.matcher("\\/.*HTTP\\/", 0),
      "/dir?q=123 HTTP/"
    );
  }
  public void simple_match(){
    String target_str = "";
    RegexParser regexParser = new RegexParser(target_str);
    assertEquals(regexParser.matcher(""), "");
  }
  @Test
  public void normal_groupMatch(){
    String target_str = "POST /dir?q=123 HTTP/1.1";
    RegexParser regexParser = new RegexParser(target_str);
    assertEquals(
      regexParser.matcher("dir(.+?)\\s(HTTP)", 0),
      "dir?q=123 HTTP"
    );
    assertEquals(
      regexParser.matcher("dir(.+?)\\s", 1),
      "?q=123"
    );
    assertEquals(
      regexParser.matcher("dir(.+?)\\s(HTTP)", 2),
      "HTTP"
    );
  }
  @Test
  public void normal_regex_multiLine(){
    String target_str = "Accept-Language: ja,en-US;q=0.9,en;q=0.8\n" +
                        "Cookie: SESSIONID=!1234567; jsession=!7654321;";
    RegexParser regexParser = new RegexParser(target_str);
    assertEquals(
      regexParser.matcher("\\A[\\s\\S]*\\z", 0),
      target_str
    );
    assertEquals(
      regexParser.matcher("^Cookie\\: (.+?);", 1),
      "SESSIONID=!1234567"
    );
    assertEquals(
      regexParser.matcher("^Cookie\\: (.+?); (.+?);", 2),
      "jsession=!7654321"
    );
  }
}