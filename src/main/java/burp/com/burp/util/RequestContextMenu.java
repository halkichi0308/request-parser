package burp.com.burp.util;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.com.burp.util.RequestResponseUtils;
import burp.com.burp.util.Pbcopy;
import burp.com.burp.ui.ExtentionTab;
import burp.com.burp.RequestParser;
import burp.ITab;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;


public class RequestContextMenu implements MouseListener {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IHttpRequestResponse[] iHttpRequestResponseArray;
    private RequestResponseUtils requestResponseUtils;
    private ExtentionTab extentionTab;
    public ITab iTab;

    public RequestContextMenu(IBurpExtenderCallbacks callbacks, IHttpRequestResponse[] requestResponseArray, ExtentionTab extentionTab) {  
        this.iBurpExtenderCallbacks = callbacks;
        this.iHttpRequestResponseArray = requestResponseArray;
        this.requestResponseUtils = new RequestResponseUtils(callbacks);
        this.extentionTab = extentionTab;
    }

    /**
     * Call events, mouseReleased on ContextMenu.
     * @param event 
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        
        PrintWriter stdout = new PrintWriter(iBurpExtenderCallbacks.getStdout(), true);
        PrintWriter stderr = new PrintWriter(iBurpExtenderCallbacks.getStderr(), true);
        for (IHttpRequestResponse iHttpRequestResponse : this.iHttpRequestResponseArray) {
            RequestParser requestParser = null;
            
                String memoFormatText = this.extentionTab.getFormatMemoTextArea();
                // TODO In fact, I don't want to send arg "decodeset".
                byte decodeSet = this.extentionTab.decodeChecked();
                
            try{       
                requestParser = new RequestParser(  iHttpRequestResponse,
                                                    requestResponseUtils,
                                                    memoFormatText,
                                                    decodeSet ); 

                stdout.println("[Success]: Parsed 1 request, then pasted to Clipboard.\n");
                Pbcopy.pbcopy(requestParser.getParsedRequest());                
            }catch(Exception e){
                stdout.println("[Error]: Unexpected Request.\n");
                stderr.println(e);
                Pbcopy.pbcopy(requestParser.getParsedRequest());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
