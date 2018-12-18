package br.com.inaconsultoria.ireceitas.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Ingredient;
import br.com.inaconsultoria.ireceitas.data.model.Recipe;
import br.com.inaconsultoria.ireceitas.data.model.Step;
import br.com.inaconsultoria.ireceitas.ui.adapter.StepDatailPageAdapter;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EActivity(R.layout.activity_recipe_detail)
public class RecipeDetailActivity extends AppCompatActivity implements ApiCallback {

    @Extra
    boolean isDetailFragment;

    @Extra
    Recipe recipe;

    @ViewById
    Toolbar toolbarRecipeDetail;

    @ViewById
    View containerRecipeDatailTablet;

    @ViewById
    View containerSecondColumn;

    @ViewById
    View secondColumnStepDetail;

    @ViewById
    View ingredientFragmentContainer;

    @ViewById
    ViewPager viewPager;

    @ViewById
    PagerSlidingTabStrip tabs;


    @InstanceState
    List<Step> steps;

    @InstanceState
    List<Ingredient> ingredients;

    @InstanceState
    boolean isChangePosition;

    @InstanceState
    boolean isStepDetailView;

    @InstanceState
    int positionClickedStep;


    private PagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @AfterViews
    void init() {
        if (containerRecipeDatailTablet == null) {
            setSupportActionBar(toolbarRecipeDetail);
            getSupportActionBar().setTitle(R.string.recipes_detail);
        }

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (isDetailFragment) {
            callRecipeDetailFragment();
        }
    }

    private void callRecipeDetailFragment() {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment_.builder().recipe(recipe).build();

        if (!isChangePosition) {
            fragmentTransaction.add(R.id.fragmentContainer, recipeDetailFragment);
            fragmentTransaction.commit();
            isChangePosition = true;
        } else {
            // Posição foi modificada
            fragmentTransaction.replace(R.id.fragmentContainer, recipeDetailFragment);

            // Varifica se algum passo foi clicado no modo tablet, e se a view a ser
            // apresentada é e de ingredients pu passos (isStepDetailView)
            if (containerRecipeDatailTablet != null) {
                if (steps != null && steps.size() > 0 && isStepDetailView) {
                    onItemClickStepView(steps, positionClickedStep);
                }
            }
        }

    }

    @Override
    public void onItemClickStepView(List<Step> steps, int position) {
        // Seta o parâmetro passado para iniciar a chamada
        isStepDetailView = true;
        this.steps = steps;
        this.positionClickedStep = position;

        boolean isCellphone = checkIsCellphone();

        initStepDetail(isCellphone);

    }

    private boolean checkIsCellphone() {
        boolean isCellphone = true;
        if (containerRecipeDatailTablet != null) {
            isCellphone = false;
        }
        return isCellphone;
    }

    /**
     * Incia a chamada da activity ou configuração para tablet do Step Detail
     *
     * @param isCellphone
     */
    private void initStepDetail(boolean isCellphone) {

        if (isCellphone) {
            // Chamar Activty de Steps
            StepDetailActivity_.intent(this)
                    .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .position(positionClickedStep)
                    .steps(steps)
                    .isCellphone(true)
                    .start();
        } else {

            //  Apresenta os  Steps para tablet
            setTabletsColumnsVisibility(View.VISIBLE, View.GONE, View.VISIBLE);
            pagerAdapter = new StepDatailPageAdapter(getSupportFragmentManager(), steps);
            viewPager.setAdapter(pagerAdapter);
            // Informa a posição do item da lista a ViewPager para ser apresentado selecionado juntamente com a tab
            viewPager.setCurrentItem(positionClickedStep);
            tabs.setViewPager(viewPager);
        }
    }


    private void setTabletsColumnsVisibility(
            int containerSecondColumnVisivlility,
            int ingredientVisibility,
            int secondColumnVisibility) {

        containerSecondColumn.setVisibility(containerSecondColumnVisivlility);
        ingredientFragmentContainer.setVisibility(ingredientVisibility);
        secondColumnStepDetail.setVisibility(secondColumnVisibility);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
