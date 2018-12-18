package br.com.inaconsultoria.ireceitas.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Step;

/**
 * iReceitas
 * Created by Luiz Nogueira on 16/12/2018
 * All rights reserved 2018.
 */
@EViewGroup(R.layout.view_step_item)
public class StepItemView extends FrameLayout {

    @ViewById
    View cardView;

    @ViewById
    TextView stepDescription;

    ApiCallback apiCallBack;

    public StepItemView(Context context) {
        super(context);
    }

    public StepItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StepItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
    }

    public void bind(List<Step> items, int position, ApiCallback apiCallBack) {
        this.apiCallBack = apiCallBack;

        if (items != null && items.size() > 0) {
            Step step = items.get(position);
            Object[] stepArray = {items, position};
            stepDescription.setText(step.getShortDescription());
            stepDescription.setTag(stepArray);
        }
    }

    @Click(R.id.cardView)
    void showStepVideosOrImages() {
       Object[] stepArray = (Object[]) stepDescription.getTag();
          apiCallBack.onItemClickStepView((List<Step>) stepArray[0], ((Integer) stepArray[1]).intValue());
    }

}
