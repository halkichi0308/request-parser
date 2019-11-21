package burp.com.burp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {

    protected String raw_req;
    public RegexParser(String raw_req){
        this.raw_req = raw_req;
    }
    /*
    *  You can use specify chars with following.
    *  \A ... First of character of your input.
    *  [\\s\\S]... Full & half spaces.
    */
    public String matcher(String comment, String patternStr, int groupMatch) {
        
        /*                       example:("^POST.*" ,/m) */
        Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(this.raw_req);
        if(matcher.find() == false){
            System.out.println(patternStr + " is not match.");
            return "";
        }
        return matcher.group(groupMatch);
    }

    /* @Overload ...without groupMatch */
    public String matcher(String comment, String patternStr){
        return matcher(comment, patternStr, 1);
    }
    /* @Overload ...without comment, groupMatch */
    public String matcher(String patternStr){
        return matcher("", patternStr, 1);
    }
    /* @Overload ...without comment */
    public String matcher(String patternStr, int groupMatch){
        return matcher("", patternStr, groupMatch);
    }
}