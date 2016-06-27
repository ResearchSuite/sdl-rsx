package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONObject;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXCopingMechanismItem extends RSXImageItem {

    private String generalDescription;
    private String specificDescription;
    private String imageTitle;
    private String category;

    public RSXCopingMechanismItem(JSONObject json) {
        super(json);

        try {
            this.generalDescription = json.getString("generalDescription");
            this.specificDescription = json.getString("specificDescription");
            this.imageTitle = json.getString("imageTitle");
            this.category = json.getString("category");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSXImageChoice getImageChoice() {
        return new RSXImageChoice<String>(this.imageTitle, null, this.specificDescription, this.getIdentifier(), this.generalDescription);
    }

    public String getCategory() {
        return this.category;
    }

}
