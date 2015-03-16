package com.totop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.totop.utils.ShareUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class GoodsDetailActivity extends Activity {

    public static final String EXTRA_IMAGE_URL = "extra_image_url";

    @InjectView(R.id.webView)WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.inject(this);

        String url = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @OnClick({R.id.text_back,R.id.webview_back,R.id.webview_forward,R.id.webview_share,R.id.webview_favor,R.id.webview_close})
    public void bkClick(View view){
        switch (view.getId()){
            case R.id.text_back:
            case R.id.webview_close:
                finish();
                break;
            case R.id.webview_back:
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }else{
                    Toast.makeText(this,"不能再后退了！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.webview_forward:
                if(mWebView.canGoForward()){
                    mWebView.goForward();
                }else{
                    Toast.makeText(this,"不能再往前了！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.webview_share:
                ShareUtils.share(this);
                break;
            case R.id.webview_favor:
                //TODO 收藏
                break;
        }
    }

}