package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Set;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLSpotAssessmentLayout extends RSXMultipleImageSelectionSurveyLayout {
    //Constructors
    public MEDLSpotAssessmentLayout(Context context)
    {
        super(context);
    }

    public MEDLSpotAssessmentLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MEDLSpotAssessmentLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
        return MEDLAdapter.class;
    }
}
