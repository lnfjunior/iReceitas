package br.com.inaconsultoria.ireceitas.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import br.com.inaconsultoria.ireceitas.data.model.Step;
import br.com.inaconsultoria.ireceitas.ui.ApiCallback;
import br.com.inaconsultoria.ireceitas.ui.StepItemView;
import br.com.inaconsultoria.ireceitas.ui.StepItemView_;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EBean
public class StepAdapter extends RecyclerViewAdapterBase<Step,StepItemView> {

    @RootContext
    Context context;

    ApiCallback apiCallback;

    @Override
    protected StepItemView onCreateItemView(ViewGroup parent, int viewType) {
        return StepItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StepItemView> holder, int position) {
        StepItemView view = holder.getView();
        Step step = items.get(position);
        view.bind(items, position, new ApiCallback() {

            @Override
            public void onItemClickStepView(List<Step> steps, int position) {
                apiCallback.onItemClickStepView(steps, position);
            }
        });
    }

    @Override
    public void setItems(List<Step> items) {
        super.setItems(items);
    }

    public void bindApiCallBack(ApiCallback apiCallBack){
        this.apiCallback = apiCallBack;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
