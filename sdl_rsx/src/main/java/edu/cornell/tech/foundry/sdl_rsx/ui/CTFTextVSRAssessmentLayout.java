package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.step.CTFTextVSRAssessmentStep;

/**
 * Created by jameskizer on 12/8/16.
 */
public class CTFTextVSRAssessmentLayout extends RSXMultipleImageSelectionSurveyLayout {

    //Constructors
    public CTFTextVSRAssessmentLayout(Context context)
    {
        super(context);
    }

    public CTFTextVSRAssessmentLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CTFTextVSRAssessmentLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

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
        return "Submit (0)";
    }

    protected void somethingSelectedButtonPressed() {
        this.onNextClicked();
    }
    protected void nothingSelectedButtonPressed() {
        this.onNextClicked();
    }

    protected boolean supportsMultipleSelection() {
        return ((CTFTextVSRAssessmentStep)this.getStep()).getOptions().getMaximumSelectedNumberOfItems() != 1;
    }

    @Override
    public Class<?> getAdaptorClass() {
        return CTFTextVSRAssessmentAdaptor.class;
    }
}
