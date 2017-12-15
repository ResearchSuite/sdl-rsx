package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.RSRPIntermediateResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyResult;

/**
 * Created by jameskizer on 4/19/17.
 */

public class YADLSpotRawTransformer implements RSRPFrontEnd {

    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {

        Object schemaID = parameters.get("schemaID");
        if (schemaID == null || !(schemaID instanceof Map)) {
            return null;
        }

        Object resultObject = parameters.get("result");
        if (resultObject == null ||
                !(resultObject instanceof StepResult) ||
                !(((StepResult)resultObject).getResult() instanceof RSXMultipleImageSelectionSurveyResult)) {
            return null;
        }

        RSXMultipleImageSelectionSurveyResult yadlSpotResult = (RSXMultipleImageSelectionSurveyResult)((StepResult)resultObject).getResult();

        Map<String, String> resultMap = new HashMap<>();

        for (int i=0; i<yadlSpotResult.getSelectedIdentifiers().length; i++) {
            resultMap.put(yadlSpotResult.getSelectedIdentifiers()[i], "selected");
        }

        for (int i=0; i<yadlSpotResult.getNotSelectedIdentifiers().length; i++) {
            resultMap.put(yadlSpotResult.getNotSelectedIdentifiers()[i], "notSelected");
        }

        for (int i=0; i<yadlSpotResult.getExcludedIdentifiers().length; i++) {
            resultMap.put(yadlSpotResult.getExcludedIdentifiers()[i], "excluded");
        }

        YADLSpotRaw yadlSpot = new YADLSpotRaw(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                (Map<String, Object>)schemaID,
                yadlSpotResult.getSelectedIdentifiers(),
                yadlSpotResult.getNotSelectedIdentifiers(),
                yadlSpotResult.getExcludedIdentifiers(),
                resultMap
        );

        yadlSpot.setStartDate(((StepResult)resultObject).getStartDate());
        yadlSpot.setEndDate(((StepResult)resultObject).getEndDate());

        return yadlSpot;
    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(YADLSpotRaw.TYPE)) return true;
        else return false;
    }
}
