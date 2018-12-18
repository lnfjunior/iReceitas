package br.com.inaconsultoria.ireceitas.data.bo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONArray;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Recipe;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RecipeBO {

    private static final String TAG  = RecipeBO.class.getSimpleName();

    @RootContext
    Context context;

    @Background
    public void RequestMovieVolley(final ApiCallBack callBack) {
        Uri uri = Uri.parse(context.getString(R.string.recipes_url)).buildUpon().build();
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, uri.toString(), null,
                response -> {
                    try {
                            List<Recipe> recipes = new Gson().fromJson(response.toString(), new TypeToken<List<Recipe>>(){}.getType());
                            callBack.onSuccess(recipes);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());

                    }
                }, error -> callBack.onError(error.getMessage()));

        VolleyRequest.getInstance().addToRequestQueue(request, context);
    }
}
