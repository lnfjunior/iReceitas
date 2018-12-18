package br.com.inaconsultoria.ireceitas.ui;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@SharedPref
public interface IngredientPreference {
    int recipeId();
}
