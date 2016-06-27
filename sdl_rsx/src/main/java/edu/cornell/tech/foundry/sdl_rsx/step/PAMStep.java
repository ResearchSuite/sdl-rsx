package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.ui.PAMLayout;

/**
 * Created by jk on 6/18/16.
 */
public class PAMStep extends RSXMultipleImageSelectionSurveyStep {
    @Override
    public Class getStepLayoutClass()
    {
        return PAMLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return null;
    }

    public PAMStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
    }
}
