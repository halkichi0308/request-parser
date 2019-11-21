package com.burp;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import burp.com.org.apache.commons.codec.DecoderException;
import burp.com.org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

public class URLCodecTest {


  @Test
  public void decodeTestForSJIS() throws UnsupportedEncodingException, DecoderException{
    String enc = "Shift-JIS";
    URLCodec codec = new URLCodec(enc);
    String target = "%83e%83X%83g";
    String encodedResult = codec.decode(target, enc);
    
    assertEquals(encodedResult, "テスト");
  }

  @Test
  public void decodeTestForEUC() throws UnsupportedEncodingException, DecoderException{
    String enc = "EUC-JP";
    URLCodec codec = new URLCodec(enc);
    String target = "%A5%C6%A5%B9%A5%C8";
    String encodedResult = codec.decode(target, enc);

    assertEquals(encodedResult, "テスト");
  }
  
}