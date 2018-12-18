package br.com.inaconsultoria.ireceitas.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.bo.ApiCallBack;
import br.com.inaconsultoria.ireceitas.data.bo.RecipeBO;
import br.com.inaconsultoria.ireceitas.data.model.Recipe;
import br.com.inaconsultoria.ireceitas.ui.adapter.RecipeAdapter;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    RecipeBO recipeBO;

    @Bean
    RecipeAdapter recipeAdapter;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    View progress;

    @ViewById
    View areaErro;

    @ViewById
    TextView textMsgErroView;

    @ViewById
    View mainContainerTablet;

    @InstanceState
    protected List<Recipe> recipes;

    @AfterViews
     void init(){
        textMsgErroView.setText(getString(R.string.erro_listar_receitas));
        initRecyclerView();

     if(recipes != null && recipes.size() >0){
            showList(recipes);
        } else {
            updateRecipes();
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        int countColumn = 1;
        if(mainContainerTablet != null){
            countColumn = 2;
            // Instancia o GridLayoutManeger informado que o recycler terá 2 colunas
            mLayoutManager = new GridLayoutManager(recyclerView.getContext(), countColumn);
            // Seta o actionBar padrão para o título
            getSupportActionBar().setTitle(R.string.app_name);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recipeAdapter);
    }

    private void updateRecipes() {
        showView(progress);
        recipeBO.RequestMovieVolley(new ApiCallBack() {
            @Override
            public void onSuccess(Object response) {
                showList((List<Recipe>) response);
            }

            @Override
            public void onError(String error) {
                showError(error);
            }
        });
    }

    void showView(View view){
        recyclerView.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        areaErro.setVisibility(View.GONE);

        if(view != null){
            view.setVisibility(View.VISIBLE);
        }
    }


    @UiThread
    protected void showList(List<Recipe> recipes){
        showView(recyclerView);
        this.recipes = recipes;
        recipeAdapter.setItems(recipes);
        recipeAdapter.notifyDataSetChanged();
    }

    @UiThread
   protected void showError(String error){
        showView(areaErro);
    }

  @Click(R.id.areaErro)
  protected void reloadRecipes(){
      updateRecipes();
  }
}
