package br.com.inaconsultoria.ireceitas.data.db.repository;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.inaconsultoria.ireceitas.data.db.IngredientContract;
import br.com.inaconsultoria.ireceitas.data.model.Ingredient;
import br.com.inaconsultoria.ireceitas.data.db.IngredientContract.IngredientEntry;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class ReceitaRepository {
    private static final String LOG_TAG = ReceitaRepository.class.getName();

    private ContentResolver contentResolver;

    public ReceitaRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Cursor countIngredientSaved(int recipeId) {
        return contentResolver.query(IngredientEntry.CONTENT_URI, new String[]{IngredientEntry.COLUMN_INGREDIENT_NAME},
                IngredientEntry.COLUMN_ID_RECIPE + " = ?", new String[]{String.valueOf(recipeId)}, null);
    }

    public void insertIngredients(List<Ingredient> ingredients, int idRecipe) {
        int sizeList = ingredients.size();
        ContentValues[] contentValuesArray = new ContentValues[sizeList];
        for (int i = 0; i < sizeList; i++) {
            Ingredient ingredient = ingredients.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(IngredientEntry.COLUMN_ID_RECIPE, idRecipe);
            contentValues.put(IngredientEntry.COLUMN_QUANTITY, ingredient.getQuantity());
            contentValues.put(IngredientEntry.COLUMN_INGREDIENT_NAME, ingredient.getIngredient());
            contentValuesArray[i] = contentValues;
        }

        try {
            int result = contentResolver.bulkInsert(IngredientEntry.CONTENT_URI, contentValuesArray);

            if (result != 0) {
                Log.v(LOG_TAG, "Quantidade de linhas inseridas : " + result);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public List<Ingredient> getIngredients(int recipeId) {
        List<Ingredient> ingredients = null;

        // Obtem os ingredientes a partir do id da receita
        Cursor mCursor = contentResolver.query(IngredientEntry.CONTENT_URI,
                new String[]{IngredientEntry.COLUMN_ID_RECIPE, IngredientEntry.COLUMN_QUANTITY, IngredientEntry.COLUMN_INGREDIENT_NAME},
                IngredientEntry.COLUMN_ID_RECIPE + " = ?",
                new String[]{String.valueOf(recipeId)},
                null);

        if (mCursor == null || mCursor.getCount() == 0) {
            return ingredients;
        }
        ingredients = new ArrayList<Ingredient>();

        int sizeList = mCursor.getCount();
        for (int i= 0; i < sizeList; i++) {
            Ingredient ingredient = new Ingredient();

            mCursor.moveToPosition(i);
            int recipeIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_ID_RECIPE);
            int quantityIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY);
            int NameIngredientIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME);

            Double quantity = mCursor.getDouble(quantityIndex);
            String nameIgredient = mCursor.getString(NameIngredientIndex);

            ingredient.setQuantity(quantity);
            ingredient.setIngredient(nameIgredient);
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
