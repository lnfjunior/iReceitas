package br.com.inaconsultoria.ireceitas.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Recipe;

import static br.com.inaconsultoria.ireceitas.utils.Constants.BROWNIES;
import static br.com.inaconsultoria.ireceitas.utils.Constants.CHEESECAKE;
import static br.com.inaconsultoria.ireceitas.utils.Constants.NUTELLA_PIE;
import static br.com.inaconsultoria.ireceitas.utils.Constants.YELLOW_CAKE;


/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EViewGroup(R.layout.view_recipe_item)
public class RecipeItemView extends FrameLayout {

     @ViewById
    protected CardView cardViewContainer;

    @ViewById
    protected TextView nameRecipeTxt;

    @ViewById
    protected ImageView ivRecipe;

    @ViewById
    protected RelativeLayout rlRecipe;


    public RecipeItemView(Context context) {
        super(context);
    }

    public RecipeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecipeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
    }

    public void bind(Recipe recipe) {
        if (recipe != null) {
            String resourceBackgroundName = recipe.getName().toLowerCase().replace(" ", "_");
            switch (resourceBackgroundName) {
                case NUTELLA_PIE:
                    ivRecipe.setBackground(getResources().getDrawable(R.drawable.nutela_pie));
                    break;
                case BROWNIES:
                    ivRecipe.setBackground(getResources().getDrawable(R.drawable.brownies));
                    break;
                case YELLOW_CAKE:
                    ivRecipe.setBackground(getResources().getDrawable(R.drawable.yellow_cake));
                    break;
                case CHEESECAKE:
                    ivRecipe.setBackground(getResources().getDrawable(R.drawable.cheesecake));
                    break;
            }
            nameRecipeTxt.setText(recipe.getName());
            nameRecipeTxt.setTag(recipe);
        }
    }

    @Click(R.id.cardViewContainer)
    void showRecipeDetail() {
        RecipeDetailActivity_.intent(getContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .recipe((Recipe) nameRecipeTxt.getTag())
                .isDetailFragment(true)
                .start();
    }

}
