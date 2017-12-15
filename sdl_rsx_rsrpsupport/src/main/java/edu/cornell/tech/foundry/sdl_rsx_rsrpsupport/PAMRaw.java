package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import org.researchsuite.rsrp.RSRPIntermediateResult;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMRaw extends RSRPIntermediateResult {
    public static String TYPE = "PAMRaw";

    private Map<String, Serializable> pamChoice;

    public PAMRaw(
            UUID uuid,
            String taskIdentifier,
            UUID taskRunUUID,
            Map<String, Serializable>  choice) {

        super(TYPE, uuid, taskIdentifier, taskRunUUID);
        this.pamChoice = choice;

    }

    public Map<String, Serializable>  getPamChoice() {
        return pamChoice;
    }
}
