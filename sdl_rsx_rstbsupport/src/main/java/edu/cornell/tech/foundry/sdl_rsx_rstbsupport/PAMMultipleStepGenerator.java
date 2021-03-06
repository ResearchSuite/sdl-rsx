package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import com.google.gson.JsonObject;

import org.researchstack.backbone.step.Step;
import org.researchsuite.rstb.DefaultStepGenerators.RSTBBaseStepGenerator;
import org.researchsuite.rstb.DefaultStepGenerators.descriptors.RSTBCustomStepDescriptor;
import org.researchsuite.rstb.RSTBTaskBuilderHelper;

import java.util.Arrays;

import edu.cornell.tech.foundry.sdl_rsx.step.PAMMultipleSelectionStep;

/**
 * Created by jameskizer on 4/13/17.
 */

public class PAMMultipleStepGenerator extends RSTBBaseStepGenerator {
    public PAMMultipleStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "PAMMultipleSelection"
        );
    }

    @Override
    public Step generateStep(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        RSTBCustomStepDescriptor stepDescriptor = helper.getCustomStepDescriptor(jsonObject);

        String jsonPath = helper.getResourcePathManager().generatePath(helper.getDefaultResourceType(), stepDescriptor.parameterFileName);
        PAMMultipleSelectionStep step = PAMMultipleSelectionStep.create(stepDescriptor.identifier, jsonPath, helper.getContext());

        step.setOptional(stepDescriptor.optional);

        return step;
    }
}
