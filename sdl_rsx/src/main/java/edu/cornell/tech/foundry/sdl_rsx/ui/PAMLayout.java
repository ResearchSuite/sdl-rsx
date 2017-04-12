package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.util.AttributeSet;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;

import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jk on 6/18/16.
 */
public class PAMLayout extends RSXMultipleImageSelectionSurveyLayout {
    //Constructors
    public PAMLayout(Context context)
    {
        super(context);
    }

    public PAMLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PAMLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //Pam always clears step results
    @Override
    public void initialize(Step step, StepResult result)
    {
        super.initialize(step, null);
    }

    //Step Layout Methods

    @Override
    protected boolean transitionOnSelection() {
        return true;
    }

    @Override
    protected String getSomethingSelectedButtonText() {
        return "";
    }
    protected String getNothingSelectedButtonText() {
        return "Reload Images";
    }

    protected void somethingSelectedButtonPressed() {

    }
    protected void nothingSelectedButtonPressed() {
        this.updateUI();
    }

    protected boolean supportsMultipleSelection() {
        return false;
    }

    @Override
    public Class<?> getAdaptorClass() {
        return PAMAdaptor.class;
    }
}
