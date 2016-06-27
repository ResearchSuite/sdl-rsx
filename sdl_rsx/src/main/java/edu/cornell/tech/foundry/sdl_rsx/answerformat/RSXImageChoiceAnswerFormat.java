package edu.cornell.tech.foundry.sdl_rsx.answerformat;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.step.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;

/**
 * Created by jk on 6/1/16.
 */
public class RSXImageChoiceAnswerFormat extends ChoiceAnswerFormat {

    public RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle answerStyle, RSXImageChoice... choices)
    {
        super(answerStyle, choices);
    }

    public RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle answerStyle, List<RSXImageChoice> choices) {
        this(answerStyle, choices.toArray(new RSXImageChoice[choices.size()]));
    }

    public RSXImageChoice[] getImageChoices()
    {
        Choice[] choices = this.getChoices();
        return Arrays.copyOf(choices, choices.length, RSXImageChoice[].class);
    }
}
