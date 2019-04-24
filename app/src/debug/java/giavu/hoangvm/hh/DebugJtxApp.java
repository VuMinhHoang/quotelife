package giavu.hoangvm.hh;

import android.webkit.WebView;
import com.facebook.stetho.Stetho;
import giavu.hoangvm.hh.hoi.HOIApp;

public class DebugJtxApp extends HOIApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        WebView.setWebContentsDebuggingEnabled(true);
    }
}
