package br.com.inaconsultoria.ireceitas.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.db.IngredientContract.IngredientEntry;
import br.com.inaconsultoria.ireceitas.ui.RecipeDetailFragment_;
import br.com.inaconsultoria.ireceitas.utils.Constants;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class IngredientsRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewsFactory(this.getApplication(), intent);
    }

    class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private Cursor mCursor;
        private int recipeId;
        private boolean isNewInstance;

        public IngredientsRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            recipeId = intent.getIntExtra(Constants.EXTRA_RECIPE_ID, 0);
            isNewInstance = true;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            if (!isNewInstance) {
                //Obtem as preferências armazenadas do.
                SharedPreferences prefs = RecipeDetailFragment_.ingredientPreference.getSharedPreferences();
                // Obtem o recipeId armazenado
                recipeId = prefs.getInt("recipeId", 0);
            }
            isNewInstance = false;

            // Obtem os ingredientes a partir do id da receita
            mCursor = getContentResolver().query(IngredientEntry.CONTENT_URI,
                    new String[]{IngredientEntry.COLUMN_ID_RECIPE, IngredientEntry.COLUMN_QUANTITY, IngredientEntry.COLUMN_INGREDIENT_NAME},
                    IngredientEntry.COLUMN_ID_RECIPE + " = ?",
                    new String[]{String.valueOf(recipeId)},
                    null);
        }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mCursor == null) {
                return 0;
            }
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (mCursor == null || mCursor.getCount() == 0) {
                return null;
            }
            mCursor.moveToPosition(i);
            int recipeIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_ID_RECIPE);
            int quantityIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY);
            int NameIngredientIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME);

            int recipeId = mCursor.getInt(recipeIndex);
            Double quantity = mCursor.getDouble(quantityIndex);
            String nameIgredient = mCursor.getString(NameIngredientIndex);

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_widget_ingredient_item);
            remoteViews.setTextViewText(R.id.widgetIngredientItemTxt, quantity + " " + nameIgredient);

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(Constants.EXTRA_RECIPE_ID, recipeId);

            remoteViews.setOnClickFillInIntent(R.id.widgetIngredientItemTxt, fillInIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

    }
}
