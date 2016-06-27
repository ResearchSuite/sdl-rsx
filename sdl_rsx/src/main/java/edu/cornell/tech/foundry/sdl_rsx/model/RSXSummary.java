package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONObject;

/**
 * Created by jk on 6/1/16.
 */
public class RSXSummary {

    private String identifier;
    private String title;
    private String text;
    public RSXSummary(JSONObject json) {
        try {
            this.identifier = json.getString("identifier");
            this.text = json.getString("text");
            this.title = json.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }
}
