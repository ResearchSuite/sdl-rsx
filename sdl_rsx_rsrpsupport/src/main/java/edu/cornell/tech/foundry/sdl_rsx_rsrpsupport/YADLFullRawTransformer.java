package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.RSRPIntermediateResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by jameskizer on 4/19/17.
 */

public class YADLFullRawTransformer implements RSRPFrontEnd {

    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {

        Object schemaID = parameters.get("schemaID");
        if (schemaID == null || !(schemaID instanceof Map)) {
            return null;
        }

        StepResult firstResult = null;
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() instanceof StepResult) {
                StepResult result = (StepResult) entry.getValue();
                if (firstResult==null) { firstResult = result; }
                        Object answer = result.getResult();
                if (answer instanceof String) {
                    String[] parts = result.getIdentifier().split("\\.");
                    String identifier = parts[parts.length-1];
                    resultMap.put(identifier, (String)answer);
                }
            }
        }

        YADLFullRaw yadlFull = new YADLFullRaw(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                (Map<String, Object>)schemaID,
                resultMap);

        yadlFull.setStartDate( firstResult != null ? firstResult.getStartDate() : new Date() );
        yadlFull.setEndDate(firstResult != null ? firstResult.getEndDate() : new Date());

        return yadlFull;
    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(YADLFullRaw.TYPE)) return true;
        else return false;
    }
}
