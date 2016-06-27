package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.ui.MEDLSpotAssessmentLayout;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLSpotAssessmentStep extends RSXMultipleImageSelectionSurveyStep{

    @Override
    public Class getStepLayoutClass()
    {
        return MEDLSpotAssessmentLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return null;
    }

    public MEDLSpotAssessmentStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
    }
}
