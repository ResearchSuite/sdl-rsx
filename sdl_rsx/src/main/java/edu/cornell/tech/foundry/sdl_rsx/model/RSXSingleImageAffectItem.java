package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.utils.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.PAMImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jameskizer on 12/7/16.
 */
public class RSXSingleImageAffectItem extends RSXImageItem implements Serializable {
    private String[] imageTitles;
    private String value;

    public RSXSingleImageAffectItem(JSONObject json, Context context) {
        super(json, context);

        try {
            List<String> imageTitles = new ArrayList<String>();

            JSONArray imageTitlesJSON= json.getJSONArray("imageTitles");

            for (int i = 0; i < imageTitlesJSON.length(); i++) {
                StringBuilder imageTitleStringBuilder = new StringBuilder();
                int imagePathResId = context.getResources().getIdentifier("pam_image_path", "string", context.getPackageName());
                if (imagePathResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imagePathResId))) {
                    imageTitleStringBuilder.append(context.getResources().getString(imagePathResId));
                    imageTitleStringBuilder.append("/");
                }
                imageTitleStringBuilder.append(imageTitlesJSON.getString(i));
                int imageExtResId = context.getResources().getIdentifier("pam_image_ext", "string", context.getPackageName());
                if (imageExtResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imageExtResId))) {
                    imageTitleStringBuilder.append(".");
                    imageTitleStringBuilder.append(context.getResources().getString(imageExtResId));
                }
                imageTitles.add(imageTitleStringBuilder.toString());
            }

            this.imageTitles = imageTitles.toArray(new String[imageTitles.size()]);

            this.value = json.getJSONObject("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSXImageChoice getImageChoice() {
        return new RSXImageChoice(this.imageTitles[0], null, null, this.value, null);
    }
}
