package br.com.inaconsultoria.ireceitas.data.bo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class VolleyRequest {

    private static VolleyRequest instance;
    private RequestQueue requestQueue;

    private VolleyRequest(){}

    public  static synchronized VolleyRequest getInstance() {
        if(instance == null ){
             instance = new VolleyRequest();
        }
        return instance;
    }

    public RequestQueue getRequestQueue(Context context){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, Context context){
        getRequestQueue(context).add(request);
    }
}
