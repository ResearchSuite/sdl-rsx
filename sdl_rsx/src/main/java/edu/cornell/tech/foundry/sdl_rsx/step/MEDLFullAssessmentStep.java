package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.answerformat.AnswerFormat;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.ui.MEDLFullAssessmentLayout;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLFullAssessmentStep extends RSXMultipleImageSelectionSurveyStep  {

    private String category;
    @Override
    public Class getStepLayoutClass()
    {
        return MEDLFullAssessmentLayout.class;
    }

    @Override
    public Class<?> getStepBodyClass()
    {
        return null;
    }

    public MEDLFullAssessmentStep(
            String identifier,
            String title,
            String category,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat, options);
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
