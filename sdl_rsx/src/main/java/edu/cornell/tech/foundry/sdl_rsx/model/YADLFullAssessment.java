package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;

/**
 * Created by jk on 6/1/16.
 */
public class YADLFullAssessment extends RSXAssessment {

    private List<RSXTextChoiceWithColor> choices;

    public YADLFullAssessment(JSONObject assessmentJSON, JSONArray itemsJSON) {

        super(assessmentJSON, itemsJSON);

        this.choices = new ArrayList<RSXTextChoiceWithColor>();

        try {
            JSONArray choicesJSON = assessmentJSON.getJSONArray("choices");

            for (int i = 0; i < choicesJSON.length(); i++) {

                RSXTextChoiceWithColor<Object> choice = new RSXTextChoiceWithColor<Object>(
                        choicesJSON.getJSONObject(i).getString("text"),
                        choicesJSON.getJSONObject(i).get("value"),
                        Color.parseColor(choicesJSON.getJSONObject(i).getString("color"))
                );

                this.choices.add(choice);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> getItemClass() {
        return RSXActivityItem.class;
    }

    public List<RSXTextChoiceWithColor> getChoices() {
        return this.choices;
    }

}
