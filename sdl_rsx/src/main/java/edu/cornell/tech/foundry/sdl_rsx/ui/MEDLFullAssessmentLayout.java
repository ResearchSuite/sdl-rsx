package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.step.MEDLFullAssessmentStep;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLFullAssessmentLayout extends RSXMultipleImageSelectionSurveyLayout {

    //Constructors
    public MEDLFullAssessmentLayout(Context context)
    {
        super(context);
    }

    public MEDLFullAssessmentLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MEDLFullAssessmentLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean transitionOnSelection() {
        return false;
    }

    @Override
    protected String getSomethingSelectedButtonText() {
        return "Next";
    }
    protected String getNothingSelectedButtonText() {
        return "Next";
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

    @Override
    protected String getAdditionalText() {
        return ((MEDLFullAssessmentStep) this.getStep()).getCategory();
    }
}
