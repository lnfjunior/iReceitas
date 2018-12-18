package br.com.inaconsultoria.ireceitas.ui;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Ingredient;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EViewGroup(R.layout.view_ingredient_item)
public class IngredientItemView extends LinearLayout {

    @ViewById
    TextView description;

    public IngredientItemView(Context context) {
        super(context);
    }


    public void bind(Ingredient item) {
        if (item != null) {
            description.setText(item.getConcatQuantityIngradient());
        }
    }
}
