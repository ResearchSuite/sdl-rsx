package edu.cornell.tech.foundry.sdl_rsx.ui;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;

import edu.cornell.tech.foundry.sdl_rsx.choice.PAMImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyResult;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXMultipleImageSelectionSurveyStep;

/**
 * Created by jameskizer on 3/16/17.
 */

public class PAMAdaptor<T> extends RSXMultipleImageSelectionSurveyAdapter {

    public PAMAdaptor(
            RSXMultipleImageSelectionSurveyStep step,
            StepResult<RSXMultipleImageSelectionSurveyResult> result) {
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

//    @Nullable
//    public T getValueForChoice(Choice choice) {
//
//        PAMImageChoice pamChoice = (PAMImageChoice) choice;
//
//        try {
//            JSONObject json = new JSONObject((String)pamChoice.getValue());
//            json.put("image", pamChoice.getCurrentImageName());
//            return (T)json.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return (T)pamChoice.getValue();
//        }
//
//    }
}
