package edu.cornell.tech.foundry.sdl_rsx.model;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.researchstack.backbone.utils.TextUtils;

import java.io.Serializable;

/**
 * Created by jk on 6/1/16.
 */
public class RSXMultipleImageSelectionSurveyOptions implements Serializable {

    private int somethingSelectedButtonColor;
    private int nothingSelectedButtonColor;
    private int itemCellSelectedColor;
    private String itemCellSelectedOverlayImageTitle;
    private int itemCellTextBackgroundColor;
    private int itemCollectionViewBackgroundColor;
    private int itemsPerRow;
    private int itemMinSpacing;
    private int maximumSelectedNumberOfItems;


    public RSXMultipleImageSelectionSurveyOptions() {}
    public RSXMultipleImageSelectionSurveyOptions(JSONObject json, Context context) {
        try {
            if (json.has("somethingSelectedButtonColor")) {
                this.somethingSelectedButtonColor = Color.parseColor(json.getString("somethingSelectedButtonColor"));
            }
            if (json.has("nothingSelectedButtonColor")) {
                this.nothingSelectedButtonColor = Color.parseColor(json.getString("nothingSelectedButtonColor"));
            }
            if (json.has("itemCellSelectedColor")) {
                this.itemCellSelectedColor = Color.parseColor(json.getString("itemCellSelectedColor"));
            }
            if (json.has("itemCellSelectedOverlayImageTitle")) {
                StringBuilder imageTitleStringBuilder = new StringBuilder();
                int imagePathResId = context.getResources().getIdentifier("overlay_image_path", "string", context.getPackageName());
                if (imagePathResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imagePathResId))) {
                    imageTitleStringBuilder.append(context.getResources().getString(imagePathResId));
                    imageTitleStringBuilder.append("/");
                }
                imageTitleStringBuilder.append(json.getString("itemCellSelectedOverlayImageTitle"));
                int imageExtResId = context.getResources().getIdentifier("overlay_image_ext", "string", context.getPackageName());
                if (imageExtResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imageExtResId))) {
                    imageTitleStringBuilder.append(".");
                    imageTitleStringBuilder.append(context.getResources().getString(imageExtResId));
                }
                this.itemCellSelectedOverlayImageTitle  = imageTitleStringBuilder.toString();
            }
            if (json.has("itemCellTextBackgroundColor")) {
                this.itemCellTextBackgroundColor = Color.parseColor(json.getString("itemCellTextBackgroundColor"));
            }
            if (json.has("itemCollectionViewBackgroundColor")) {
                this.itemCollectionViewBackgroundColor = Color.parseColor(json.getString("itemCollectionViewBackgroundColor"));
            }
            if (json.has("itemsPerRow")) {
                this.itemsPerRow = json.getInt("itemsPerRow");
            }
            if (json.has("itemMinSpacing")) {
                this.itemMinSpacing = json.getInt("itemMinSpacing");
            }
            if (json.has("maximumSelectedNumberOfItems")) {
                this.maximumSelectedNumberOfItems = json.getInt("maximumSelectedNumberOfItems");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RSXMultipleImageSelectionSurveyOptions(JsonObject json, Context context) {
        try {
            if (json.has("somethingSelectedButtonColor")) {
                this.somethingSelectedButtonColor = Color.parseColor(json.get("somethingSelectedButtonColor").getAsString());
            }
            if (json.has("nothingSelectedButtonColor")) {
                this.nothingSelectedButtonColor = Color.parseColor(json.get("nothingSelectedButtonColor").getAsString());
            }
            if (json.has("itemCellSelectedColor")) {
                this.itemCellSelectedColor = Color.parseColor(json.get("itemCellSelectedColor").getAsString());
            }
            if (json.has("itemCellSelectedOverlayImageTitle")) {
                StringBuilder imageTitleStringBuilder = new StringBuilder();
                int imagePathResId = context.getResources().getIdentifier("overlay_image_path", "string", context.getPackageName());
                if (imagePathResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imagePathResId))) {
                    imageTitleStringBuilder.append(context.getResources().getString(imagePathResId));
                    imageTitleStringBuilder.append("/");
                }
                imageTitleStringBuilder.append(json.get("itemCellSelectedOverlayImageTitle").getAsString());
                int imageExtResId = context.getResources().getIdentifier("overlay_image_ext", "string", context.getPackageName());
                if (imageExtResId != 0 && !TextUtils.isEmpty(context.getResources().getString(imageExtResId))) {
                    imageTitleStringBuilder.append(".");
                    imageTitleStringBuilder.append(context.getResources().getString(imageExtResId));
                }
                this.itemCellSelectedOverlayImageTitle  = imageTitleStringBuilder.toString();
            }
            if (json.has("itemCellTextBackgroundColor")) {
                this.itemCellTextBackgroundColor = Color.parseColor(json.get("itemCellTextBackgroundColor").getAsString());
            }
            if (json.has("itemCollectionViewBackgroundColor")) {
                this.itemCollectionViewBackgroundColor = Color.parseColor(json.get("itemCollectionViewBackgroundColor").getAsString());
            }
            if (json.has("itemsPerRow")) {
                this.itemsPerRow = json.get("itemsPerRow").getAsInt();
            }
            if (json.has("itemMinSpacing")) {
                this.itemMinSpacing = json.get("itemMinSpacing").getAsInt();
            }
            if (json.has("maximumSelectedNumberOfItems")) {
                this.maximumSelectedNumberOfItems = json.get("maximumSelectedNumberOfItems").getAsInt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSomethingSelectedButtonColor() {
        return this.somethingSelectedButtonColor;
    }
    public void setSomethingSelectedButtonColor(int color) {
        this.somethingSelectedButtonColor = color;
    }

    public int getNothingSelectedButtonColor() {
        return this.nothingSelectedButtonColor;
    }
    public void setNothingSelectedButtonColor(int color) {
        this.nothingSelectedButtonColor = color;
    }

    public int getItemCellSelectedColor() {
        return this.itemCellSelectedColor;
    }
    public void setItemCellSelectedColor(int color) {
        this.itemCellSelectedColor = color;
    }

    public String getItemCellSelectedOverlayImageTitle() {
        return this.itemCellSelectedOverlayImageTitle;
    }
    public void setItemCellSelectedOverlayImageTitle(String title) {
        this.itemCellSelectedOverlayImageTitle = title;
    }

    public int getItemCellTextBackgroundColor() {
        return this.itemCellTextBackgroundColor;
    }
    public void setItemCellTextBackgroundColor(int color) {
        this.itemCellTextBackgroundColor = color;
    }

    public int getItemCollectionViewBackgroundColor() {
        return this.itemCollectionViewBackgroundColor;
    }
    public void setItemCollectionViewBackgroundColor(int color) {
        this.itemCollectionViewBackgroundColor = color;
    }

    public int getItemsPerRow() {
        return this.itemsPerRow;
    }
    public void setItemsPerRow(int numItems) {
        this.itemsPerRow = numItems;
    }

    public int getItemMinSpacing() {
        return this.itemMinSpacing;
    }
    public void setItemMinSpacing(int spacing) {
        this.itemMinSpacing = spacing;
    }

    public int getMaximumSelectedNumberOfItems() {
        return this.maximumSelectedNumberOfItems;
    }
    public void setMaximumSelectedNumberOfItems(int items) {
        this.maximumSelectedNumberOfItems = items;
    }


}
