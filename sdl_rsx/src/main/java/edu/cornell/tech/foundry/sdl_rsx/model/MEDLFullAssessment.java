package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jk on 6/23/16.
 */
public class MEDLFullAssessment extends RSXAssessment {

    private RSXMultipleImageSelectionSurveyOptions options;

    public MEDLFullAssessment(JSONObject assessmentJSON, JSONArray itemsJSON) {

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
        return RSXCopingMechanismItem.class;
    }

    public RSXMultipleImageSelectionSurveyOptions getOptions() {
        return this.options;
    }
}
