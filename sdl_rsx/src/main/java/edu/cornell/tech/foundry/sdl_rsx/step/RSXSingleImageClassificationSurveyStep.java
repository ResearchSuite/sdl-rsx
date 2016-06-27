package edu.cornell.tech.foundry.sdl_rsx.step;

import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.layout.SurveyStepLayout;

import edu.cornell.tech.foundry.sdl_rsx.ui.RSXSingleImageClassificationSurveyLayout;

/**
 * Created by jk on 5/26/16.
 */

/**
 * The {@link QuestionStep} class is a concrete subclass of {@link Step} that represents a step in
 * which a single question is presented to the user.
 * <p>
 * To use a question step, instantiate an QuestionStep object, fill in its properties, and include
 * it in a task. When the task completes, the user's answer is encoded in the result hierarchy
 * returned from the {@link org.researchstack.backbone.ui.ViewTaskActivity}.
 * <p>
 * When the ViewTaskActivity presents a QuestionStep object, it instantiates a {@link
 * SurveyStepLayout} object to present the step. The actual visual presentation depends on the
 * answer format and its {@link org.researchstack.backbone.ui.step.body.StepBody}.
 * <p>
 * When you need to present more than one question at the same time, it can be appropriate to use
 * {@link FormStep} instead of QuestionStep.
 * <p>
 * The result of a question step is an {@link org.researchstack.backbone.result.StepResult} object.
 */

public class RSXSingleImageClassificationSurveyStep extends QuestionStep {


    @Override
    public Class getStepLayoutClass()
    {
        return RSXSingleImageClassificationSurveyLayout.class;
    }

//    @Override
//    public Class<?> getStepBodyClass()
//    {
//        return RSXSingleImageClassificationSurveyBody.class;
//    }
    /**
     * The base64-encoded image.
     */
//    private String image;
    private String text;
    private String image;



    /**
     * Returns a new question step that includes the specified identifier, title, image, and answer
     * format.
     *
     * @param identifier The identifier of the step (a step identifier should be unique within the
     *                   task).
     * @param title      A localized string that represents the primary text of the question.
     * @param image
     * @param answerFormat The format in which the answer is expected.
     */
    public RSXSingleImageClassificationSurveyStep(
            String identifier,
            String title,
            String text,
            String image,
            AnswerFormat answerFormat
    )
    {
        super(identifier, title, answerFormat);
        this.image = image;
        this.text = text;
    }

    public String getImage() { return this.image; }
    public void setImage(String image) { this.image = image; }

    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }


}
