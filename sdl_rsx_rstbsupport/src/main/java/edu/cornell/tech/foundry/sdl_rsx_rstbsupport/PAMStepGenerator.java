package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.util.Log;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBBaseStepGenerator;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBCustomStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

import java.util.Arrays;

import edu.cornell.tech.foundry.sdl_rsx.step.PAMStep;

/**
 * Created by jameskizer on 4/13/17.
 */

public class PAMStepGenerator extends RSTBBaseStepGenerator {
    public PAMStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "PAM"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        try {
            RSTBCustomStepDescriptor customStepDescriptor = helper.getGson().fromJson(jsonObject, RSTBCustomStepDescriptor.class);
            return PAMStep.create(customStepDescriptor.identifier, helper.getContext());
        }
        catch(Exception e) {
            Log.w("PAM GENERATOR!!!", "malformed element: " + jsonObject.getAsString(), e);
            return null;
        }
    }
}
