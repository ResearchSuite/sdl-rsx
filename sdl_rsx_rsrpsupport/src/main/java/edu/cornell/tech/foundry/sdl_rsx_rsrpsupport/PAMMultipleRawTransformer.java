package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMMultipleRawTransformer implements RSRPFrontEnd {
    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {
        Object param = parameters.get("result");
        if (param == null || !(param instanceof StepResult)) {
            return null;
        }

        StepResult stepResult = (StepResult)param;
        Object result = stepResult.getResult();
        if(! (result instanceof Object[])) {
            return null;
        }

        Object[] responses = (Object[]) result;
        if(responses.length > 0) {

            ArrayList<Map<String, Serializable>> pamChoices = new ArrayList<>();
            for (Object response : responses) {
                if (response instanceof String) {
                    Map<String, Serializable> pamChoice = PAMRawTransformer.convertPAMChoiceToMap((String)response);
                    if (pamChoice != null) {
                        pamChoices.add(pamChoice);
                    }
                }
            }

            PAMMultipleRaw pamMultipleRaw = new PAMMultipleRaw(
                    UUID.randomUUID(),
                    taskIdentifier,
                    taskRunUUID,
                    pamChoices
            );

            pamMultipleRaw.setStartDate(stepResult.getStartDate());
            pamMultipleRaw.setEndDate(stepResult.getEndDate());
            pamMultipleRaw.setParameters(parameters);

            return pamMultipleRaw;

        }

        return null;

    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(PAMMultipleRaw.TYPE)) return true;
        else return false;
    }
}
