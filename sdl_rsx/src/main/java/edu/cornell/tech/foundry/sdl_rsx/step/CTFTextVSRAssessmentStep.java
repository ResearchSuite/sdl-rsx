package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.ui.CTFTextVSRAssessmentLayout;
import edu.cornell.tech.foundry.sdl_rsx.ui.YADLSpotAssessmentLayout;

/**
 * Created by jameskizer on 12/8/16.
 */
public class CTFTextVSRAssessmentStep extends RSXMultipleImageSelectionSurveyStep {
    @Override
    public Class getStepLayoutClass()
    {
        return CTFTextVSRAssessmentLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return null;
    }




    public CTFTextVSRAssessmentStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
    }

}
