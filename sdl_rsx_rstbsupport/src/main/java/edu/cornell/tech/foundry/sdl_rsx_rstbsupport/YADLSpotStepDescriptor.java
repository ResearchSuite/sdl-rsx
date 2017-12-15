package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.support.annotation.Nullable;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBStepDescriptor;

import java.util.List;

/**
 * Created by jameskizer on 4/14/17.
 */

public class YADLSpotStepDescriptor extends RSTBStepDescriptor {
    @Nullable
    public String filterKey;
    public List<YADLItemDescriptor> items;
    public String imagePath = "images/yadl";
    public String imageExtension = "jpeg";
    public String itemCellSelectedOverlayImageTitle;
    public String itemCellSelectedOverlayImagePath = "images";
    public String itemCellSelectedOverlayImageExtension = "png";

    public YADLSpotStepDescriptor() {

    }
}
