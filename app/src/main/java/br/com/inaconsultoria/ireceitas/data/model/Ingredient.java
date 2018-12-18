package br.com.inaconsultoria.ireceitas.data.model;

import org.parceler.Parcel;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@Parcel
public class Ingredient {
    protected Double quantity;
    protected String measure;
    protected String ingredient;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getConcatQuantityIngradient() {
        return this.quantity + " " + this.ingredient;
    }
}
