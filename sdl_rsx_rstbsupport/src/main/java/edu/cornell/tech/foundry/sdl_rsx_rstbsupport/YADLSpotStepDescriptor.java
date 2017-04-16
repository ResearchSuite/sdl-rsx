package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.support.annotation.Nullable;

import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBStepDescriptor;


/**
 * Created by jameskizer on 4/14/17.
 */

public class YADLSpotStepDescriptor extends RSTBStepDescriptor {
    @Nullable
    public String filterKey;
    public List<YADLItemDescriptor> items;
    public String imagePath = "images/yadl";
    public String imageExtension = "jpeg";

    public YADLSpotStepDescriptor() {

    }
}
