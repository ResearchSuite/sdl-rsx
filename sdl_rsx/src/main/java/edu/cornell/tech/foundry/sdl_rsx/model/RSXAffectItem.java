package edu.cornell.tech.foundry.sdl_rsx.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.choice.PAMImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;

/**
 * Created by jk on 6/1/16.
 */
public class RSXAffectItem extends RSXImageItem implements Serializable {


    private String[] imageTitles;
//    private JSONObject value;
    private String value;

    public RSXAffectItem(JSONObject json) {
        super(json);

        try {
            List<String> imageTitles = new ArrayList<String>();

            JSONArray imageTitlesJSON= json.getJSONArray("imageTitles");

            for (int i = 0; i < imageTitlesJSON.length(); i++) {
                String imageTitle = imageTitlesJSON.getString(i);
                imageTitles.add(imageTitle);
            }

            this.imageTitles = imageTitles.toArray(new String[imageTitles.size()]);

            this.value = json.getJSONObject("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RSXImageChoice getImageChoice() {
        return new PAMImageChoice<>(this.imageTitles, this.value);
    }


}
