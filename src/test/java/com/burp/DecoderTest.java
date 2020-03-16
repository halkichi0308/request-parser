package com.burp;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import burp.com.burp.util.Decoder;
import burp.com.org.apache.commons.codec.DecoderException;

/**
 * Unit test for simple App.
 */
public class DecoderTest {

    @Test
    public void normal_DecodeSpecialChars() {
        assertEquals(Decoder.decodeUTF8("%40%27"), "@'");
    }

    @Test
    public void normal_DecodeMultibytes() {
        assertEquals(Decoder.decodeUTF8("%E3%83%86%E3%82%B9%E3%83%88%E6%96%87%E5%AD%97"), "テスト文字");
        assertEquals(Decoder.decodeUTF8("%E3%83%861%E3%82%B92%E3%83%883%E6%96%874%E5%AD%975"), "テ1ス2ト3文4字5");
        assertEquals(Decoder.decodeUTF8("Yahoo!%E3%83%A1%E3%83%BC%E3%83%AB"), "Yahoo!メール");
    }

    @Test
    public void err_returnRawChar_with_inValidFormat() {
        assertEquals(Decoder.decodeUTF8("%E3%83%%E3%82%B9%E3%83%88"), "%E3%83%%E3%82%B9%E3%83%88");
        assertEquals(Decoder.decodeUTF8("%08"), "%08");
    }

    @Test
    public void err_notException_with_largeBuffer() {
        String arg_string = new String(new char[65536]).replace("\0", "A");
        assertEquals(Decoder.decodeUTF8(arg_string).length(), 65536);
    }

    @Test
    public void normal_decodeShiftJIS() throws UnsupportedEncodingException, DecoderException {
        String sJisStr = new String("%83e%83X%83g");
        assertEquals(
            Decoder.decodeSJIS(sJisStr),
            "テスト"
        );
    }

}
