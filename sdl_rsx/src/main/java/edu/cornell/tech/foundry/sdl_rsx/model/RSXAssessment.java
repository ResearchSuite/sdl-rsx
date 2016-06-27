package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.step.body.StepBody;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.task.RSXSingleImageClassificationSurveyTask;

/**
 * Created by jk on 6/1/16.
 */
abstract public class RSXAssessment {
    private String identifier;
    private String prompt;
    private RSXSummary summary;
    private RSXSummary noItemsSummary;
    private List<RSXItem> items;

    public RSXAssessment(JSONObject assessmentJSON, JSONArray itemsJSON) {
        try {
            this.identifier = assessmentJSON.getString("identifier");
            this.prompt = assessmentJSON.getString("prompt");
            this.summary = new RSXSummary(assessmentJSON.getJSONObject("summary"));
            if (assessmentJSON.has("noItemsSummary")) {
                this.noItemsSummary = new RSXSummary(assessmentJSON.getJSONObject("noItemsSummary"));
            }

            this.items = new ArrayList<>();

            Class cls = this.getItemClass();
            Constructor constructor = cls.getConstructor(JSONObject.class);

            for (int i = 0; i < itemsJSON.length(); i++) {
                RSXItem item = (RSXItem) constructor.newInstance(itemsJSON.getJSONObject(i));
                this.items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public RSXSummary getSummary() {
        return this.summary;
    }

    public RSXSummary getNoItemsSummary() {
        return this.noItemsSummary;
    }

    abstract public Class<?> getItemClass();

    public List<RSXItem> getItems() {
        return this.items;
    }
}
