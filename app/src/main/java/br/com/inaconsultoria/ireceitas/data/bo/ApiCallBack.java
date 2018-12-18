package br.com.inaconsultoria.ireceitas.data.bo;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public interface ApiCallBack<T> {

    void onSuccess(T response);

    void onError(String error);
}
