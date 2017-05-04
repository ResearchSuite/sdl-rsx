package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import org.researchstack.backbone.result.StepResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMRawTransformer implements RSRPFrontEnd {
    @Nullable
    public static Map<String, Serializable> convertPAMChoiceToMap(String pamChoice) {

        try {
            JSONObject pamJSON = new JSONObject(pamChoice);

            HashMap<String, Serializable> pamMap = new HashMap<>();


            Iterator<String> keys = pamJSON.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                pamMap.put(key, pamJSON.optString(key));
            }

            return pamMap;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

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
        if(responses.length > 0 && responses[0] instanceof String) {

            Map<String, Serializable> pamMap = convertPAMChoiceToMap((String)responses[0]);

            if (pamMap != null) {
                PAMRaw pamRaw = new PAMRaw(
                        UUID.randomUUID(),
                        taskIdentifier,
                        taskRunUUID,
                        pamMap
                );

                pamRaw.setStartDate(stepResult.getStartDate());
                pamRaw.setEndDate(stepResult.getEndDate());
                pamRaw.setParameters(parameters);

                return pamRaw;
            }

        }

        return null;

    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(PAMRaw.TYPE)) return true;
        else return false;
    }
}
