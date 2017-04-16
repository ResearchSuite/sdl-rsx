package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONObject;
import org.researchstack.backbone.utils.TextUtils;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXActivityItem extends RSXImageItem {

    private String activityDescription;
    private String imageTitle;

    public RSXActivityItem(String identifier, String activityDescription, String imageTitle) {
        super(identifier);
        this.activityDescription = activityDescription;
        this.imageTitle = imageTitle;
    }

    public RSXActivityItem(JSONObject json, Context context) {
        super(json, context);

        try {
            this.activityDescription = json.getString("description");
            StringBuilder imageTitleStringBuilder = new StringBuilder();
            int imagePathResId = context.getResources().getIdentifier("yadl_image_path", "string", context.getPackageName());
            if (imagePathResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imagePathResId))) {
                imageTitleStringBuilder.append(context.getResources().getString(imagePathResId));
                imageTitleStringBuilder.append("/");
            }
            imageTitleStringBuilder.append(json.getString("imageTitle"));
            int imageExtResId = context.getResources().getIdentifier("yadl_image_ext", "string", context.getPackageName());
            if (imageExtResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imageExtResId))) {
                imageTitleStringBuilder.append(".");
                imageTitleStringBuilder.append(context.getResources().getString(imageExtResId));
            }
            this.imageTitle = imageTitleStringBuilder.toString();

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
