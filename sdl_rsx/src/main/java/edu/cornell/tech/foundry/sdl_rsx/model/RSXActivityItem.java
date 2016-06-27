package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONObject;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXActivityItem extends RSXImageItem {

    private String activityDescription;
    private String imageTitle;

    public RSXActivityItem(JSONObject json) {
        super(json);

        try {
            this.activityDescription = json.getString("description");
            this.imageTitle = json.getString("imageTitle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSXImageChoice getImageChoice() {
        return new RSXImageChoice<String>(this.imageTitle, null, this.activityDescription, this.getIdentifier(), null);
    }

    public String getActivityDescription() {
        return this.activityDescription;
    }

    public String getImageTitle() {
        return this.imageTitle;
    }

}
