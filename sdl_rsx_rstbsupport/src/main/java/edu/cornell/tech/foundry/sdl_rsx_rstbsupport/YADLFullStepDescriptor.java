package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBElementDescriptor;

import java.util.List;

/**
 * Created by jameskizer on 4/13/17.
 */

public class YADLFullStepDescriptor extends RSTBElementDescriptor {

    public List<YADLDifficultyChoiceDescriptor> choices;
    public List<YADLItemDescriptor> items;
    public String text;
    public boolean optional = true;
    public String imagePath = "images/yadl";
    public String imageExtension = "jpeg";

    public YADLFullStepDescriptor() {

    }

}
