package burp.com.burp.util;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/*
* Do Like about Pbcopy with Burp provided function.
*/
public class Pbcopy{

    public static void pbcopy(String str){
        Toolkit tool = Toolkit.getDefaultToolkit();
		Clipboard clip = tool.getSystemClipboard();

        StringSelection ss = new StringSelection(str);
        clip.setContents(ss, ss);
    }
}