package br.com.inaconsultoria.ireceitas.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class IngredientDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ireceitas.db";
    private static final  int DATABASE_VERSION = 1;

    public IngredientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INGRDIENTS_TABLE = "CREATE TABLE " + IngredientContract.IngredientEntry.TABLE_NAME + "( " +
                IngredientContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IngredientContract.IngredientEntry.COLUMN_ID_RECIPE + " INTEGER NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_QUANTITY  + " REAL NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_INGRDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientContract.IngredientEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
