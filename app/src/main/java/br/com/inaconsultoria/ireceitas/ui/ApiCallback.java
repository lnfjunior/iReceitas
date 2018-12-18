package br.com.inaconsultoria.ireceitas.ui;

import java.util.List;

import br.com.inaconsultoria.ireceitas.data.model.Step;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public interface ApiCallback {
     void onItemClickStepView(List<Step> steps, int position);
}
