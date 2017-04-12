package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jameskizer on 3/16/17.
 */

public class PAMMultipleAdaptor <T> extends RSXMultipleImageSelectionSurveyAdapter {
    public PAMMultipleAdaptor(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<T[]> result) {
        super(step, result);
    }

    @Nullable
    public Choice getChoiceForValue(Object value, Choice[] choices) {

        try {
            JSONObject json = new JSONObject((String)value);
            json.remove("image");
            value = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        for(int i=0; i<choices.length; i++) {
            if (value.equals(choices[i].getValue())) {
                return choices[i];
            }
        }
        return null;
    }

    @Nullable
    public T getValueForChoice(Choice choice) {

        RSXImageChoice imageChoice = (RSXImageChoice)choice;

        try {
            JSONObject json = new JSONObject((String)imageChoice.getValue());
            json.put("image", imageChoice.getNormalStateImage());
            return (T)json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return (T)imageChoice.getValue();
        }

    }
}
