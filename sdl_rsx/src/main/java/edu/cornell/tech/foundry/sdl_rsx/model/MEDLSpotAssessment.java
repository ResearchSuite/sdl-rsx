package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLSpotAssessment extends RSXAssessment{

    private RSXMultipleImageSelectionSurveyOptions options;

    public MEDLSpotAssessment(JSONObject assessmentJSON, JSONArray itemsJSON, Context context) {

        super(assessmentJSON, itemsJSON, context);

        if (assessmentJSON.has("options")) {
            try {
                this.options = new RSXMultipleImageSelectionSurveyOptions(assessmentJSON.getJSONObject("options"), context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Class<?> getItemClass() {
        return RSXCopingMechanismItem.class;
    }

    public RSXMultipleImageSelectionSurveyOptions getOptions() {
        return this.options;
    }
}
