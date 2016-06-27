package edu.cornell.tech.foundry.sdl_rsx.choice;

import org.researchstack.backbone.model.Choice;

/**
 * Created by jk on 5/31/16.
 */

public class RSXImageChoice <T> extends Choice {

    private String normalStateImage;
    private String selectedStateImage;


    public RSXImageChoice(String normalImage, String selectedImage, String text, T value, String detailText) {

        super(text, value, detailText);
        this.normalStateImage = normalImage;
        this.selectedStateImage = selectedImage;
    }

    public String getNormalStateImage() {
        return this.normalStateImage;
    }

    public void setNormalStateImage(String normalStateImage) {
        this.normalStateImage = normalStateImage;
    }

    public String getSelectedStateImage() {
        return this.selectedStateImage;
    }

    public void setSelectedStateImage(String selectedStateImage) {
        this.selectedStateImage = selectedStateImage;
    }

}
