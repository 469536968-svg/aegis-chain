package dev.aegischain.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 唯一 Activity：把网关页面装进 WebView。
 *
 * 三个构建变体（dev / test / prod）的代码完全相同，只有 BuildConfig.DEFAULT_BASE_URL
 * 与包名/应用名不同——功能由服务端同一套代码提供，所以功能严格一致。
 * 自建网关时，长按顶部标题可改地址（存在 SharedPreferences）。
 */
public class MainActivity extends Activity {

    private static final String PREFS = "aegis";
    private static final String KEY_BASE = "base_url";

    private WebView web;
    private ProgressBar bar;
    private TextView title;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        baseUrl = sp.getString(KEY_BASE, BuildConfig.DEFAULT_BASE_URL);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#080d13"));

        // 顶部条：标题（长按可改网关地址）
        title = new TextView(this);
        title.setText(BuildConfig.APP_LABEL);
        title.setTextColor(Color.parseColor("#e7eef7"));
        title.setTextSize(15);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(dp(16), dp(12), dp(16), dp(12));
        title.setBackgroundColor(Color.parseColor("#111a24"));
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showBaseUrlDialog();
                return true;
            }
        });
        root.addView(title, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 进度条
        bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        bar.setMax(100);
        root.addView(bar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(2)));

        // WebView
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(false);
        s.setMediaPlaybackRequiresUserGesture(true);
        s.setSupportZoom(false);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        // dev/test 走自建 http 网关；prod 走 https。混合内容只放行"页面自身"的 http 资源。
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        web.setBackgroundColor(Color.parseColor("#080d13"));
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                bar.setVisibility(View.GONE);
                // 网关连不上时给一个可操作的离线页，而不是系统错误页
                if (failingUrl != null && failingUrl.startsWith("file://")) return;
                view.loadUrl("file:///android_asset/www/offline.html");
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bar.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
                bar.setProgress(newProgress);
            }
        });

        root.addView(web, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        setContentView(root);

        getWindow().setNavigationBarColor(Color.parseColor("#080d13"));
        getWindow().setStatusBarColor(Color.parseColor("#111a24"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        loadGateway();
    }

    private void loadGateway() {
        web.loadUrl(baseUrl + "/?app=" + BuildConfig.APP_ENV);
    }

    /** 长按标题：修改网关地址（自建/内网调试用），功能集合不变。 */
    private void showBaseUrlDialog() {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        input.setText(baseUrl);
        input.setSingleLine(true);

        new AlertDialog.Builder(this)
            .setTitle("Gateway URL")
            .setMessage("dev: http://127.0.0.1:8787 · test/prod: your https endpoint")
            .setView(input)
            .setPositiveButton("Save", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(android.content.DialogInterface d, int w) {
                    String v = input.getText().toString().trim();
                    if (v.length() == 0) return;
                    if (!v.startsWith("http")) v = "http://" + v;
                    baseUrl = v.endsWith("/") ? v.substring(0, v.length() - 1) : v;
                    getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString(KEY_BASE, baseUrl).apply();
                    loadGateway();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) { web.goBack(); return; }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (web != null) web.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (web != null) web.onResume();
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }
}
