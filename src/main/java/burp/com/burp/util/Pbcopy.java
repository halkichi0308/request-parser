package burp.com.burp.util;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/*
* function like a pbcopy.
*/
public class Pbcopy{

    public static void pbcopy(String str){
        Toolkit tool = Toolkit.getDefaultToolkit();
        Clipboard clip = tool.getSystemClipboard();
        
        StringSelection ss = new StringSelection(str);
        clip.setContents(ss, ss);
    }
}