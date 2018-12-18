package br.com.inaconsultoria.ireceitas.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.db.repository.ReceitaRepository;
import br.com.inaconsultoria.ireceitas.data.model.Ingredient;
import br.com.inaconsultoria.ireceitas.data.model.Recipe;
import br.com.inaconsultoria.ireceitas.data.model.Step;
import br.com.inaconsultoria.ireceitas.service.IngredientIntentService;
import br.com.inaconsultoria.ireceitas.ui.ApiCallback;
import br.com.inaconsultoria.ireceitas.ui.IngredientItemView;
import br.com.inaconsultoria.ireceitas.ui.IngredientItemView_;
import br.com.inaconsultoria.ireceitas.ui.IngredientPreference_;
import br.com.inaconsultoria.ireceitas.ui.RecipeDetailActivity;
import br.com.inaconsultoria.ireceitas.ui.adapter.StepAdapter;


/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EFragment(R.layout.fragment_recipe_detail)
public class RecipeDetailFragment extends Fragment {

    @Pref
    public  static IngredientPreference_ ingredientPreference;


    @ViewById
    NestedScrollView nestedScrollView;

    @FragmentArg
    Recipe recipe;

    @ViewById
    RecyclerView stepRecyclerView;

    @ViewById
    LinearLayout listViewIngredients;

    @Bean
    StepAdapter stepAdapter;

    @InstanceState
    protected List<Step> steps;

    @InstanceState
    protected Parcelable listStatePosition;

    protected boolean loadViewsLandscape;

    ApiCallback apiCallback;

    ReceitaRepository ingredientDao;


    @AfterViews
    void init() {
        ingredientDao = new ReceitaRepository(getContext().getContentResolver());
        stepRecyclerView.setNestedScrollingEnabled(false);
        setupAcionBar();
        fillLayoutIngredientItemView();
        initRecyclerView();

        if (CollectionUtils.isNotEmpty(steps)) {
            fillRecyclerViewInSavePosition();
            loadViewsLandscape = true;
        } else {
            showStepList(recipe.getSteps());
        }
    }

    private void setupAcionBar() {
        ActionBar actionBar = ((RecipeDetailActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            // Apresenta o botão de voltar no menu
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void fillLayoutIngredientItemView() {
        if (recipe != null && CollectionUtils.isNotEmpty(recipe.getIngredients())) {
            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {
                IngredientItemView view = IngredientItemView_.build(getContext());
                view.bind(ingredient);
                listViewIngredients.addView(view);
            }

            insertIngredientsDontSaved(ingredients);
        }
    }

    private void insertIngredientsDontSaved(List<Ingredient> ingredients) {
        int recipeId = recipe.getId();
        Cursor cursor = ingredientDao.countIngredientSaved(recipeId);
        int count = cursor.getCount();
        if (count == 0) {
            ingredientDao.insertIngredients(ingredients, recipeId);
        }
        ingredientPreference.recipeId().put(recipeId);
        IngredientIntentService.startActiontWidgetIngredients(getContext(), recipeId);
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        stepRecyclerView.setLayoutManager(mLayoutManager);
        stepRecyclerView.setAdapter(stepAdapter);

        stepAdapter.bindApiCallBack(new ApiCallback() {

            @Override
            public void onItemClickStepView(List<Step> steps, int position) {
                apiCallback.onItemClickStepView(steps, position);
            }
        });
    }


    private void fillRecyclerViewInSavePosition() {
        stepRecyclerView.getLayoutManager().onRestoreInstanceState(listStatePosition);
        showStepList(steps);
    }

    private void showStepList(List<Step> steps) {

        if (CollectionUtils.isNotEmpty(stepAdapter.getItems())) {
            int size = steps.size();
            stepAdapter.notifyItemRangeRemoved(0, size);
        }

        stepAdapter.setItems(steps);
        stepAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            apiCallback = (ApiCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // Usa o método apenas para setar asa propriedaes a serem salvas. Esses itens serão salvos
        // no RecipeDetailFragment_

        // Informa a lista a ser salva
        steps = stepAdapter.getItems();

        // Informa a posição onde se está parada a lista
        listStatePosition = stepRecyclerView.getLayoutManager().onSaveInstanceState();

        super.onSaveInstanceState(outState);
    }


//    @Click(R.id.ingredientCardView)
//    void showIngredientsList() {
//        apiCallback.onItemClickIngrendientView(recipe.getIngredients());
//    }


}
