package br.com.inaconsultoria.ireceitas.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import br.com.inaconsultoria.ireceitas.data.model.Recipe;
import br.com.inaconsultoria.ireceitas.ui.RecipeItemView;
import br.com.inaconsultoria.ireceitas.ui.RecipeItemView_;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EBean
public class RecipeAdapter extends RecyclerViewAdapterBase<Recipe, RecipeItemView> {

    @RootContext
    Context context;

    @Override
    protected RecipeItemView onCreateItemView(ViewGroup parent, int viewType) {

        return RecipeItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RecipeItemView> holder, int position) {
          RecipeItemView view = holder.getView();
          Recipe recipe = items.get(position);
          view.bind(recipe);
    }

    @Override
    public void setItems(List<Recipe> items) {
        super.setItems(items);
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
