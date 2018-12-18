package br.com.inaconsultoria.ireceitas.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;


/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class IngredientContentProvider extends ContentProvider {

    private static final String TAG = IngredientContentProvider.class.getName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int INGREDIENTS = 100;
    public static final int INGREDIENT = 101;

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Caso queira retornar/acessar um registro de filmes acessa esse diretório aqui
        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENT + "/#", INGREDIENT);

        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENT, INGREDIENTS);

        return uriMatcher;
    }

    private IngredientDbHelper ingredientDbHelper;


    @Override
    public boolean onCreate() {
        ingredientDbHelper = new IngredientDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        // Obtendo uma instancia para inserir os dados
        final SQLiteDatabase db = ingredientDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int insertCount = 0;
        switch (match) {
            case INGREDIENTS:
                try {
                    db.beginTransaction();

                    for (ContentValues value : values) {
                        long id = db.insert(IngredientContract.IngredientEntry.TABLE_NAME, null, value);
                        if (id > 0) {
                            insertCount++;
                        }
                    }

                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    throw new SQLException("Failed to insert row  into" + uri);
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new UnsupportedOperationException(" Unknow uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return insertCount;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = ingredientDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            // Query for the plants directory
            case INGREDIENTS:
                retCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.


        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
