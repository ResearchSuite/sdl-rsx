package edu.cornell.tech.foundry.sdl_rsx.model;

import android.graphics.Color;

import org.json.JSONObject;

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


    public RSXMultipleImageSelectionSurveyOptions() {}
    public RSXMultipleImageSelectionSurveyOptions(JSONObject json) {
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
                this.itemCellSelectedOverlayImageTitle = json.getString("itemCellSelectedOverlayImageTitle");
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


}
