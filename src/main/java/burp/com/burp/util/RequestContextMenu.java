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

import java.util.Calendar;
import java.util.Date;

public class RequestContextMenu implements MouseListener {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IHttpRequestResponse[] iHttpRequestResponseArray;
    private RequestResponseUtils requestResponseUtils;
    private ExtentionTab extentionTab;
    public ITab iTab;

    public RequestContextMenu(IBurpExtenderCallbacks callbacks, IHttpRequestResponse[] requestResponseArray,
            ExtentionTab extentionTab) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iHttpRequestResponseArray = requestResponseArray;
        this.requestResponseUtils = new RequestResponseUtils(callbacks);
        this.extentionTab = extentionTab;
    }

    /**
     * Call events, mouseReleased on ContextMenu.
     * 
     * @param event
     */
    @Override
    public void mouseReleased(MouseEvent event) {

        PrintWriter stdout = new PrintWriter(iBurpExtenderCallbacks.getStdout(), true);
        PrintWriter stderr = new PrintWriter(iBurpExtenderCallbacks.getStderr(), true);
        for (IHttpRequestResponse iHttpRequestResponse : this.iHttpRequestResponseArray) {
            RequestParser requestParser = null;

            String memoFormatText = this.extentionTab.getFormatMemoTextArea();
            // [TODO] To declare argument "decodeset" is undesirable.
            byte decodeSet = this.extentionTab.decodeChecked();

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2021, 8, 1);
            Date useLimitDate = calendar.getTime();

            if (date.before(useLimitDate)) {
                stdout.println("[Notice]: You should update. Let Producer know this.\n");
                Pbcopy.pbcopy("[Notice]: You should update. Let Producer know this.\n");
            }

            try {
                requestParser = new RequestParser(iHttpRequestResponse, requestResponseUtils, memoFormatText,
                        decodeSet);

                stdout.println("[Success]: Parsed 1 request, then pasted to Clipboard.\n");
                Pbcopy.pbcopy(requestParser.getParsedRequest());
            } catch (Exception e) {
                stdout.println("[Error]: Unexpected Request.\n");
                stderr.println(e);
                Pbcopy.pbcopy(requestParser.getParsedRequest());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
