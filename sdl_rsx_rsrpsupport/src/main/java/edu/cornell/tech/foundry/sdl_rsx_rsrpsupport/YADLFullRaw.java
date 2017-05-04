package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 4/19/17.
 */

public class YADLFullRaw extends RSRPIntermediateResult {
    public static String TYPE = "YADLFullRaw";

    private Map<String, String> resultMap;
    private Map<String, Object> schemaID;

    public YADLFullRaw(
            UUID uuid,
            String taskIdentifier,
            UUID taskRunUUID,
            Map<String, Object> schemaID,
            Map<String, String> resultMap) {

        super(TYPE, uuid, taskIdentifier, taskRunUUID);
        this.schemaID = schemaID;
        this.resultMap = resultMap;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public Map<String, Object> getSchemaID() {
        return schemaID;
    }
}
