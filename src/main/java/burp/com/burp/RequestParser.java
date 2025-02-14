package burp.com.burp;

import burp.com.burp.util.MemoFormatter;
import burp.com.burp.util.RequestResponseUtils;
import burp.IHttpRequestResponse;

public class RequestParser {

  private MemoFormatter memoFormatter;
  private String parsedRequestString = "";

  /**
   * Constructor
   *
   * @param IHttpRequestResponse -> in Burp interface
   * @param RequestResponseUtils -> util Class
   * @param memoFormatText       -> from User input string in Burp Tab.
   */
  public RequestParser(IHttpRequestResponse iHttpRequestResponse, RequestResponseUtils requestResponseUtils,
      String memoFormatText, Byte decodeSet) {
    requestResponseUtils.setDecodeType(decodeSet);
    // Request
    String requestString = requestResponseUtils.showRequest(iHttpRequestResponse);
    String method = requestResponseUtils.getMethod(iHttpRequestResponse);
    String url = requestResponseUtils.getUrl(iHttpRequestResponse);
    String request_origin = requestResponseUtils.getRequestOrigin(iHttpRequestResponse);
    String referer = requestResponseUtils.getReferer(iHttpRequestResponse);
    String refererOrigin = requestResponseUtils.getRefererOrigin(iHttpRequestResponse);
    String cookies = requestResponseUtils.getCookieString(iHttpRequestResponse);
    String queries = requestResponseUtils.getQueryString(iHttpRequestResponse);
    String bodies = requestResponseUtils.getBodyString(iHttpRequestResponse);
    int countParametorWithoutCookie = requestResponseUtils.countParamsWithoutCookie(iHttpRequestResponse);
    int countParametor = requestResponseUtils.countParams(iHttpRequestResponse);

    // Response
    Short statusCode = requestResponseUtils.getStatusCode(iHttpRequestResponse);
    memoFormatter = new MemoFormatter(requestString, memoFormatText);
    memoFormatter.appendReferer(referer);
    memoFormatter.appendRefererOrigin(refererOrigin);
    memoFormatter.appendUrl(url);
    memoFormatter.appendRequestOrigin(request_origin);
    memoFormatter.appendMethod(method);
    memoFormatter.appendCookies(cookies);
    memoFormatter.appendQueries(queries);
    memoFormatter.appendBodies(bodies);
    memoFormatter.appendParamsCountWithoutCookie(countParametorWithoutCookie);
    memoFormatter.appendParamsCount(countParametor);
    memoFormatter.appendStatusCode(statusCode);

    this.parsedRequestString = memoFormatter.single_memorandom;
  }

  public String getParsedRequest() {
    return parsedRequestString;
  }

}
