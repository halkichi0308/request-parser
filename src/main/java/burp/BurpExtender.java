package burp;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.ITab;
import burp.com.burp.util.RequestContextMenu;
import burp.com.burp.util.RequestResponseUtils;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import burp.com.burp.ui.ExtentionTab;

/* 
interface is package one of jar
It can pack full interfaces then `mvn install`
 */
public class BurpExtender implements IBurpExtender, IContextMenuFactory, ITab {

    public static IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private static IExtensionHelpers helpers;

    private static final String EXTENSION_NAME = "Request Parser";

    private ExtentionTab extentionTab;
    private static RequestResponseUtils requestResponseUtils;


    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName(EXTENSION_NAME);
        iBurpExtenderCallbacks = callbacks;
        helpers = callbacks.getHelpers();
        this.requestResponseUtils = new RequestResponseUtils(this.iBurpExtenderCallbacks);

        //UI Component
        SwingUtilities.invokeLater(() -> {
            extentionTab = new ExtentionTab(requestResponseUtils);
            extentionTab.render();
            this.iBurpExtenderCallbacks.addSuiteTab(BurpExtender.this);
        });

        // In order to regist Context Menu.
        callbacks.registerContextMenuFactory(this);
    }

    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    public Component getUiComponent() {
        return extentionTab;
    }

    public static IBurpExtenderCallbacks getCallbacks() {
        return iBurpExtenderCallbacks;
    }
    public static IExtensionHelpers getHelpers() {
        return helpers;
    }

    /**
     * コンテキストメニューの作成
     *
     * @param iContextMenuInvocation
     * @return
     */
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        /*
         * リクエストを選択した状態で、コンテキストメニューがクリックされた際に、取得される情報は「IHttpRequestResponse」クラスに格納されます
         * リクエストを複数選択した状態で、コンテキストメニュークリックすることも可能であるため、配列で取得されます。
         */
        IHttpRequestResponse[] httpRequestResponseArray = iContextMenuInvocation.getSelectedMessages();

        if (null == httpRequestResponseArray) {
            return null;
        }

        List<JMenuItem> jMenuItemList = new LinkedList<>();

        // リクエスト表示
        // コンテキストに表示するテキスト
        JMenuItem requestJMenuItem = new JMenuItem("[Request Parser]: Parse"/* + httpRequestResponseArray.length*/);
        // 右クリック時の動作を設定
        requestJMenuItem.addMouseListener(new RequestContextMenu(this.iBurpExtenderCallbacks, httpRequestResponseArray, extentionTab));
        // コンテキストを追加
        jMenuItemList.add(requestJMenuItem);

        return jMenuItemList;
    }
}
