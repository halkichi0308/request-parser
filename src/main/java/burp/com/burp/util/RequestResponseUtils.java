package burp.com.burp.util;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.IResponseInfo;
import burp.IParameter;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import burp.com.burp.util.Decoder;
import burp.com.burp.util.RegexParser;
import burp.com.burp.type.ParamType;
import burp.com.org.apache.commons.codec.DecoderException;
import java.io.PrintWriter;
import burp.IBurpExtenderCallbacks;

public class RequestResponseUtils {

    private static final String NEW_LINE = System.lineSeparator();

    private static IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private static IExtensionHelpers iExtensionHelpers;
    private Byte decodeSet = 0;

    /*
     * String url; String referer; String method; String cookie; String query;
     * String body;
     */

    public RequestResponseUtils(IBurpExtenderCallbacks callbacks) {
        iBurpExtenderCallbacks = callbacks;
        iExtensionHelpers = callbacks.getHelpers();
    }

    /**
     * リクエスト情報を取得
     *
     * @param iHttpRequestResponse
     * @return String
     */
    public String showRequest(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // リクエスト情報を取得
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        List<String> headers = iRequestInfo.getHeaders();
        stringBuilder.append(this.createHeaderRaw(headers));

        // リクエストボディ情報を取得
        byte[] requestBytes = iHttpRequestResponse.getRequest();
        stringBuilder.append(this.createBodyRaw(requestBytes));

        return stringBuilder.toString();
    }

    /**
     * レスポンス情報を取得
     *
     * @param iHttpRequestResponse
     * @return String
     */
    public String showResponse(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // レスポンス情報を取得
        IResponseInfo iResponseInfo = iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse());

        // レスポンスヘッダ情報を取得
        List<String> headers = iResponseInfo.getHeaders();
        stringBuilder.append(this.createHeaderRaw(headers));

        // レスポンスボディ情報を取得
        byte[] responseBytes = iHttpRequestResponse.getResponse();
        stringBuilder.append(this.createBodyRaw(responseBytes));

        return stringBuilder.toString();
    }

    /**
     * @param iHttpRequestResponse
     * 
     * @return Short
     */
    public Short getStatusCode(IHttpRequestResponse iHttpRequestResponse) {
        Short statusCode = 0;
        try {
            IResponseInfo iResponseInfo = iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse());
            statusCode = iResponseInfo.getStatusCode();
        } catch (Exception e) {

        }
        return statusCode;
    }

    /**
     * ヘッダーリストを文字列に変換
     *
     * @param headers
     * @return
     */
    private String createHeaderRaw(List<String> headers) {
        StringBuilder stringBuilder = new StringBuilder();
        // リクエストヘッダ情報を取得
        for (String header : headers) {
            stringBuilder.append(header);
            stringBuilder.append(NEW_LINE);
        }
        return stringBuilder.toString();
    }

    /**
     * ボディのバイト配列を文字列に変換
     *
     * @param bodyBytes
     * @return
     */
    private String createBodyRaw(byte[] bodyBytes) {
        String response = "";
        try {
            response = new String(bodyBytes, "UTF-8");
            response = response.substring(iExtensionHelpers.analyzeResponse(bodyBytes).getBodyOffset());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error converting string");
        }

        if (response.length() > 0) {
            response = NEW_LINE + response;
            return response;
        } else {
            return "";
        }
    }

    /**
     * リクエスト情報を取得, IRequestInfoのメソッドを取得
     *
     * @param method
     * @return String
     */
    public String getMethod(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // リクエスト情報を取得
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        String method = iRequestInfo.getMethod();
        stringBuilder.append(method);

        return stringBuilder.toString();
    }

    public String getUrl(IHttpRequestResponse iHttpRequestResponse) {

        // リクエスト情報を取得
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        URL url = iRequestInfo.getUrl();
        String port = ":" + String.valueOf(url.getPort());
        return url.toString().replace(port, "");
    }

    public String getRequestOrigin(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // リクエスト情報を取得
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        URL url = iRequestInfo.getUrl();
        String rtnURLString = url.toString();
        String port = ":" + String.valueOf(url.getPort());
        if (url.getQuery() != null) {

            rtnURLString = rtnURLString.replace(url.getQuery(), "").replace("?", "");
        }
        rtnURLString = rtnURLString.replace(port, "");
        stringBuilder.append(rtnURLString);

        return stringBuilder.toString();
    }

    public String getReferer(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        String requestString = this.showRequest(iHttpRequestResponse);
        RegexParser parseRequest = new RegexParser(requestString);
        stringBuilder.append(parseRequest.matcher("^Referer.\\s(http.:.*$)"));
        return stringBuilder.toString();
    }

    public String getRefererOrigin(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        String referer = this.getReferer(iHttpRequestResponse);
        RegexParser parseRequest = new RegexParser(referer);
        stringBuilder.append(parseRequest.matcher("^(http.+?((?=\\?)|$))"));
        return stringBuilder.toString();
    }

    public String getQueryString(IHttpRequestResponse iHttpRequestResponse) {
        return getParamString(iHttpRequestResponse, (byte) 0);
    }

    public String getBodyString(IHttpRequestResponse iHttpRequestResponse) {
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);
        if (iRequestInfo.getContentType() == 5) {
            return getParamString(iHttpRequestResponse, (byte) 5);
        } else if (iRequestInfo.getContentType() == 4) {
            return getParamString(iHttpRequestResponse, (byte) 4);
        } else {
            return getParamString(iHttpRequestResponse, (byte) 1);
        }
    }

    public String getCookieString(IHttpRequestResponse iHttpRequestResponse) {
        return getParamString(iHttpRequestResponse, (byte) 2);
    }

    public byte getContentType(IHttpRequestResponse iHttpRequestResponse) {
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);
        return iRequestInfo.getContentType();
    }

    public int countParams(IHttpRequestResponse iHttpRequestResponse) {
        int rtnCountParams = 0;
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);
        List<IParameter> parametors = iRequestInfo.getParameters();
        for (IParameter parametor : parametors) {
            if (parametor.getType() != 2 /* Cookie */) {
                rtnCountParams++;
            }
        }
        return rtnCountParams;
    }

    /**
     * Count paramator exclude cookie from all paramators.
     */
    public int countParamsWithoutCookie(IHttpRequestResponse iHttpRequestResponse) {
        int rtnCountParams = 0;
        PrintWriter stdout = new PrintWriter(iBurpExtenderCallbacks.getStdout(), true);
        stdout.println("Wrote1\n");

        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);
        List<IParameter> parametors = iRequestInfo.getParameters();
        for (IParameter parametor : parametors) {
            rtnCountParams++;
        }
        return rtnCountParams;
    }

    /**
     * set decodeType 1->UTF-8, 2->Shift-JIS, 3->EUC-JP
     * 
     * @param decodeSet
     */
    public void setDecodeType(Byte decodeSet) {
        this.decodeSet = decodeSet;
    }

    /**
     * Following: Util private Methods
     * 
     * 
     */
    private String getParamString(IHttpRequestResponse iHttpRequestResponse, byte type) throws RuntimeException {
        StringBuilder stringBuilder = new StringBuilder();
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);
        // リクエストヘッダ情報を取得
        List<IParameter> parametors = iRequestInfo.getParameters();

        String param_string = "";
        if (type == 2) {
            param_string = this.iterateIParameter(extractCookieList(parametors), (byte) 2);
        } else if (type == 1) {
            param_string = this.iterateIParameter(extractBodyList(parametors), (byte) 1);
        } else if (type == 0) {
            param_string = this.iterateIParameter(extractQueryList(parametors), (byte) 0);
        } else if (type == 5) {
            param_string = this.iterateIParameter(extractMultipartList(parametors), (byte) 5);
        } else if (type == 4) {// JSON object
            byte[] requestBytes = iHttpRequestResponse.getRequest();
            // JSONObject is thrid party library, however burp extender can't include it.
            // String bodyRaw = this.createBodyRaw(requestBytes);
            // JSONObject json = new JSONObject();
            // param_string = json.toString(4);

            param_string = this.createBodyRaw(requestBytes);
        } else {
            // Pattern exception headers
            byte[] requestBytes = iHttpRequestResponse.getRequest();
            param_string = this.createBodyRaw(requestBytes);
        }

        stringBuilder.append(param_string);

        return stringBuilder.toString();
    }

    private List<IParameter> extractList(List<IParameter> parametors, byte type) {
        List<IParameter> parametorList = new ArrayList<IParameter>();
        for (IParameter parameter : parametors) {
            if (parameter.getType() == type) {
                parametorList.add(parameter);
            }
        }
        return parametorList;
    }

    private List<IParameter> extractQueryList(List<IParameter> parametors) {
        return extractList(parametors, (byte) 0);
    }

    private List<IParameter> extractBodyList(List<IParameter> parametors) {
        return extractList(parametors, (byte) 1);
    }

    private List<IParameter> extractCookieList(List<IParameter> parametors) {
        return extractList(parametors, (byte) 2);
    }

    private List<IParameter> extractMultipartList(List<IParameter> parametors) {
        return extractList(parametors, (byte) 5);
    }

    private String iterateIParameter(List<IParameter> parametors, byte type) {
        final ParamType paramType = ParamType.typeOf(Byte.toUnsignedInt(type));

        String iterateString = "";
        String _getParametorName = "";
        String _getParametorValue = "";

        for (IParameter parametor : parametors) {
            if (parametor.getName().length() > 0) {
                // TODO witch charset selected.
                try {
                    _getParametorName = Decoder.decode(parametor.getName(), this.decodeSet);
                    _getParametorValue = Decoder.decode(parametor.getValue(), this.decodeSet);
                    iterateString += paramType + "\t" + _getParametorName + "\t" + _getParametorValue + "\n";
                } catch (Exception e) {
                }
            }
        }
        ;
        return iterateString;
    }
}
