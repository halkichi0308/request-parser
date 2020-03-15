package burp.com.burp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import burp.com.org.apache.commons.codec.DecoderException;
import burp.com.org.apache.commons.codec.net.URLCodec;

public class Decoder {

    public static String decode(String str, Byte decodeSet) throws UnsupportedEncodingException, DecoderException {
        switch(decodeSet){
            case 1:
                return Decoder.decode(str, "UTF-8");
            case 2:
                return Decoder.decode(str, "Shift-JIS");
            case 3:
                return Decoder.decode(str, "EUC-JP");
            default:
                return str;
        }
    }
    
    /**
     * @param str 
     * @param enc <UTF-8|Shift-JIS|EUC-JP>
     * @throws UnsupportedEncodingException
     * 
     */
    public static String decode(String str, String enc) throws DecoderException, UnsupportedEncodingException{
        
        RegexParser regexParser = new RegexParser(str);
    
        boolean hasBadCodeChar = (regexParser.matcher("%0\\d").length() > 1);
        if(hasBadCodeChar){
            return str;
        };
        String decodedString = "";

        URLCodec codec = new URLCodec(enc);
        switch(enc){
            case "UTF-8":
            try {
                decodedString = URLDecoder.decode(str, enc);
            //} catch (UnsupportedEncodingException e1) {
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                decodedString = str;
            }
                break;

            case "Shift-JIS":
            case "EUC-JP":
                // INFO: Shift-JIS & EUC-JP strings can't decode with java.net.URLDecoder.
                
                try{ 
                    decodedString = codec.decode(str);
                } catch (Exception e) {
                    String exceptionMessage = e.getClass().getName() + ": " + e.getMessage();
                    //decodedString = exceptionMessage + str;
                    decodedString = str;
                }
                break;

            default:
                decodedString = str;
        }
        return decodedString;
    }

    /**
     * Decode UTF8 chars from raw string buffer.
     *
     * @param str raw text
     */
    public static String decodeUTF8(String str){
        String rtnString;
        try{
            rtnString = decode(str, "UTF-8");
        }catch(Exception e){
            rtnString = str;
        }
        return rtnString;
    }
    
    /**
     * Decode Shift-JIS chars from raw string buffer.
     *
     * @param str raw text
     * @throws UnsupportedEncodingException
     */
    public static String decodeSJIS(String str) throws UnsupportedEncodingException, DecoderException {
        return decode(str, "Shift-JIS");
    }
    /**
     * Decode EUC-JP chars from raw string buffer.
     *
     * @param str raw text
     * @throws UnsupportedEncodingException
     */
    public static String decodeEUC(String str) throws UnsupportedEncodingException, DecoderException {
        return decode(str, "EUC-JP");
    }
    
}