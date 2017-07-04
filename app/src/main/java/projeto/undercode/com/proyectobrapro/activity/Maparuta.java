package projeto.undercode.com.proyectobrapro.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.fragments.MapaSectoresFragment;

/**
 * Created by Level on 06/12/2016.
 */

public class Maparuta extends BaseController {


    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @BindView(R.id.mapawebView)
    WebView webView;


    @Override
    public int getLayout() {
        return R.layout.fragment_sectores_webview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("datos");
        String ids = data.replace("[", "").replace("]","").replace(" ","");

        Log.d("datos ids",ids);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        Log.d("Direccion: ", "http://201.24.26.23/braproweb/paginas/mapa_ruta.asp?acao=C&codigo=" + ids);

        webView.loadUrl("http://201.24.26.23/braproweb/paginas/mapa_ruta.asp?acao=C&codigo=" + ids);

    }


}
