package br.com.inaconsultoria.ireceitas.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.db.ReceitasWidgetProvider;

import static br.com.inaconsultoria.ireceitas.utils.Constants.EXTRA_RECIPE_ID;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class IngredientIntentService extends IntentService {

    public IngredientIntentService() {
        super("IngredientIntentService");
    }

    public static void startActiontWidgetIngredients(Context context, int recipeId) {
        Intent intent = new Intent(context, IngredientIntentService.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        context.startService(intent);
    }

    public static void startActiontWidget(Context context) {
        Intent intent = new Intent(context, IngredientIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
                int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0);
                handleActionWidgetIngredients(recipeId);
        }
    }

    private void handleActionWidgetIngredients(int recipeId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ReceitasWidgetProvider.class));
          appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredientWidgetList);
        ReceitasWidgetProvider.updateWidgetList(this, appWidgetManager, recipeId, appWidgetIds);
    }


}
