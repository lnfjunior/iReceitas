package br.com.inaconsultoria.ireceitas.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import br.com.inaconsultoria.ireceitas.data.model.Step;
import br.com.inaconsultoria.ireceitas.ui.StepDetailFragment_;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
public class StepDatailPageAdapter extends FragmentStatePagerAdapter {

    private List<Step> steps;

    public StepDatailPageAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Step step = null;
        if (steps != null && steps.size() > 0) {
            step = steps.get(position);
        }
        return StepDetailFragment_.builder().step(step).build();
    }

    @Override
    public int getCount() {
        return steps != null && steps.size() > 0 ? steps.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Intro";
        } else {
            return "Step " + position;
        }
    }
}
