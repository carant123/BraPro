package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;

/**
 * Created by Level on 22/09/2016.
 */
public class MapaSectoresFragment extends BaseFragment {

    @BindView(R.id.mapawebView) WebView webView;


    public MapaSectoresFragment(){}

    public static MapaSectoresFragment newInstance(){
        MapaSectoresFragment pf = new MapaSectoresFragment();
        return pf;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_sectores_webview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

       //webView.loadUrl("http://192.168.25.38/BraPro/paginas/sectortest.asp?usuario=9");
        webView.loadUrl("http://201.24.26.23/braproweb/paginas/sectortest.asp?usuario=9");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
