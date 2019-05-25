package giavu.co.jp;

import android.webkit.WebView;
import giavu.co.jp.hoi.HOIApp;

public class DebugJtxApp extends HOIApp {

    @Override
    public void onCreate() {
        super.onCreate();
        WebView.setWebContentsDebuggingEnabled(true);
    }
}
