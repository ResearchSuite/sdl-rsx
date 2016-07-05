package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by jk on 6/1/16.
 */
abstract public class RSXItem {

    private String identifier;
    public RSXItem(JSONObject json, Context context) {
        try {
            this.identifier = json.getString("identifier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
