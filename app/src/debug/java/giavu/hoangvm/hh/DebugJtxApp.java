package giavu.hoangvm.hh;

import android.webkit.WebView;
import giavu.hoangvm.hh.hoi.HOIApp;

public class DebugJtxApp extends HOIApp {

    @Override
    public void onCreate() {
        super.onCreate();
        WebView.setWebContentsDebuggingEnabled(true);
    }
}
