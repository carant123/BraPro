package projeto.undercode.com.proyectobrapro.base;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Level on 16/09/2016.
 */
public abstract class BaseRequest {

    private String url;
    private JSONObject parameters;
    private Context context;
    private String option;

    public BaseRequest (Context context, String url, JSONObject params, String option) {
        setContext(context);
        setUrl(url);
        setparameters(params);
        setOption(option);
    }

    public String getUrlQuery() {
        String aux = this.url + "?";
        String key;

        if(this.parameters != null) {
            for(Iterator<String> keys = this.parameters.keys(); keys.hasNext();) {
                try {
                    key = keys.next();
                    aux = aux + key + "=" + this.parameters.getString(key) + "&";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return aux;
    }

    // GET'S
    public Context getContext(){
        return this.context;
    }

    public String getOption(){
        return this.option;
    }

    public String getUrl(){
        return this.url;
    }

    public JSONObject getParameters(){
        return this.parameters;
    }

    // SET'S
    public void setUrl(String url) {
        this.url = url;
    }

    public void setparameters(JSONObject parameters) {
        this.parameters = parameters;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setOption(String option) {
        this.option = option;
    }

}
