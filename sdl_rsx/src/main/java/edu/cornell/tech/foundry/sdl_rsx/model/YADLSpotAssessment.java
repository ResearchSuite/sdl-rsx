package edu.cornell.tech.foundry.sdl_rsx.model;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;

/**
 * Created by jk on 6/1/16.
 */
public class YADLSpotAssessment extends RSXAssessment {

    private RSXMultipleImageSelectionSurveyOptions options;

    public YADLSpotAssessment(JSONObject assessmentJSON, JSONArray itemsJSON) {

        super(assessmentJSON, itemsJSON);

        if (assessmentJSON.has("options")) {
            try {
                this.options = new RSXMultipleImageSelectionSurveyOptions(assessmentJSON.getJSONObject("options"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Class<?> getItemClass() {
        return RSXActivityItem.class;
    }

    public RSXMultipleImageSelectionSurveyOptions getOptions() {
        return this.options;
    }

}
