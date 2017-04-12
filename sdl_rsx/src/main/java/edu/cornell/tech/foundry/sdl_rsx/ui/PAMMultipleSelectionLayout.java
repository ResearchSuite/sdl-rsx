package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.util.AttributeSet;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;

import java.util.Set;

/**
 * Created by jameskizer on 12/7/16.
 */
public class PAMMultipleSelectionLayout extends RSXMultipleImageSelectionSurveyLayout {
    //Constructors
    public PAMMultipleSelectionLayout(Context context)
    {
        super(context);
    }

    public PAMMultipleSelectionLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PAMMultipleSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //Pam always clears step results
    @Override
    public void initialize(Step step, StepResult result)
    {
        super.initialize(step, result);
    }

    //Step Layout Methods

    @Override
    protected boolean transitionOnSelection() {
        return false;
    }

    @Override
    protected String getSomethingSelectedButtonText() {
        Set<?> selectedAnswers = this.getCurrentSelected();
        if (selectedAnswers != null) {
            return String.format("Submit (%s)", selectedAnswers.size());
        }
        else {
            return "Submit (0)";
        }
    }

    protected String getNothingSelectedButtonText() {
        return "Nothing To Report";
    }

    protected void somethingSelectedButtonPressed() {
        this.onNextClicked();
    }
    protected void nothingSelectedButtonPressed() {
        this.onNextClicked();
    }

    protected boolean supportsMultipleSelection() {
        return true;
    }

    @Override
    public Class<?> getAdaptorClass() {
        return PAMMultipleAdaptor.class;
    }
}
