package edu.cornell.tech.foundry.sdl_rsx_rsrpsupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMMultipleRaw extends RSRPIntermediateResult {
    public static String TYPE = "PAMMultipleRaw";

    private ArrayList<Map<String, Serializable>> pamChoices;

    public PAMMultipleRaw(
            UUID uuid,
            String taskIdentifier,
            UUID taskRunUUID,
            ArrayList<Map<String, Serializable>>  choices) {

        super(TYPE, uuid, taskIdentifier, taskRunUUID);
        this.pamChoices = choices;

    }

    public ArrayList<Map<String, Serializable>>  getPamChoices() {
        return pamChoices;
    }
}
