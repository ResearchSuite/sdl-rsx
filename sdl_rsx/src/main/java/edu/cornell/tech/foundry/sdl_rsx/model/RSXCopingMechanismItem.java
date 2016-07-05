package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONObject;
import org.researchstack.backbone.utils.TextUtils;

import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXCopingMechanismItem extends RSXImageItem {

    private String generalDescription;
    private String specificDescription;
    private String imageTitle;
    private String category;

    public RSXCopingMechanismItem(JSONObject json, Context context) {
        super(json, context);

        try {
            this.generalDescription = json.getString("generalDescription");
            this.specificDescription = json.getString("specificDescription");
            this.category = json.getString("category");

            StringBuilder imageTitleStringBuilder = new StringBuilder();
            int imagePathResId = context.getResources().getIdentifier("medl_image_path", "string", context.getPackageName());
            if (imagePathResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imagePathResId))) {
                imageTitleStringBuilder.append(context.getResources().getString(imagePathResId));
                imageTitleStringBuilder.append("/");
            }
            imageTitleStringBuilder.append(json.getString("imageTitle"));
            int imageExtResId = context.getResources().getIdentifier("medl_image_ext", "string", context.getPackageName());
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
        return new RSXImageChoice<String>(this.imageTitle, null, this.specificDescription, this.getIdentifier(), this.generalDescription);
    }

    public String getCategory() {
        return this.category;
    }

}
