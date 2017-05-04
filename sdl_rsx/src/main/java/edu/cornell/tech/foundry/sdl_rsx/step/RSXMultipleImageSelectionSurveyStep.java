package edu.cornell.tech.foundry.sdl_rsx.step;

import android.support.annotation.Nullable;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.QuestionStep;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;

/**
 * Created by jk on 6/1/16.
 */
abstract public class RSXMultipleImageSelectionSurveyStep extends QuestionStep {

    interface VisibilityFilter<T> {
        public boolean isVisible(T value);
    }



    private RSXMultipleImageSelectionSurveyOptions options;
//    private VisibilityFilter visibilityFilter;
    @Nullable
    private String[] excludedIdentifiers;

    /**
     * Returns a new question step that includes the specified identifier, title, image, and answer
     * format.
     *
     * @param identifier The identifier of the step (a step identifier should be unique within the
     *                   task).
     * @param title      A localized string that represents the primary text of the question.
     * @param answerFormat The format in which the answer is expected.
     */
    public RSXMultipleImageSelectionSurveyStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options
    )
    {
        super(identifier, title, answerFormat);
        this.options = options;
    }

    public RSXMultipleImageSelectionSurveyStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options,
            VisibilityFilter visibilityFilter
    )
    {
        super(identifier, title, answerFormat);
        this.options = options;
//        this.visibilityFilter = visibilityFilter;
    }

    public RSXMultipleImageSelectionSurveyStep(
            String identifier,
            String title,
            AnswerFormat answerFormat,
            RSXMultipleImageSelectionSurveyOptions options,
            final String[] excludedIdentifiers
    )
    {
        super(identifier, title, answerFormat);
        this.options = options;
        this.excludedIdentifiers = excludedIdentifiers;
        //create visibility filter that leaves out excluded identitifiers
//        this.visibilityFilter = new VisibilityFilter() {
//            @Override
//            public boolean isVisible(Object value) {
//
//                for (int i=0; i<excludedIdentifiers.length; i++) {
//                    if (excludedIdentifiers[i].equals(value)) {
//                        return false;
//                    }
//                }
//
//                return true;
//            }
//        };
    }

    public RSXMultipleImageSelectionSurveyOptions getOptions() {
        return this.options;
    }

    @Nullable
    public String[] getExcludedIdentifiers() {
        return excludedIdentifiers;
    }
}