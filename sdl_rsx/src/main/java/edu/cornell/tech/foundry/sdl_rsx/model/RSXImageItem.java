package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONObject;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public abstract class RSXImageItem extends RSXItem {

    public RSXImageItem(JSONObject json, Context context) {
        super(json, context);
    }

    abstract public RSXImageChoice getImageChoice();
}
