package edu.cornell.tech.foundry.sdl_rsx_rsrpohmagebackend;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONObject;
import org.researchsuite.omhclient.OMHAcquisitionProvenance;
import org.researchsuite.omhclient.OMHDataPoint;
import org.researchsuite.omhclient.OMHDataPointBuilder;
import org.researchsuite.omhclient.OMHSchema;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.cornell.tech.foundry.sdl_rsx_rsrpsupport.PAMMultipleRaw;
import edu.cornell.tech.foundry.sdl_rsx_rsrpsupport.PAMRaw;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMMultipleRawOMHDatapoint extends OMHDataPointBuilder {
    private PAMMultipleRaw pamMultipleRaw;
    private OMHAcquisitionProvenance acquisitionProvenance;

    public PAMMultipleRawOMHDatapoint(Context context, PAMMultipleRaw pamMultipleRaw) {
        this.pamMultipleRaw = pamMultipleRaw;
        this.acquisitionProvenance = new OMHAcquisitionProvenance(
                context.getPackageName(),
                pamMultipleRaw.getStartDate(),
                OMHAcquisitionProvenance.OMHAcquisitionProvenanceModality.SELF_REPORTED
        );
    }

    @Override
    public String getDataPointID() {
        return this.pamMultipleRaw.getUuid().toString();
    }

    @Override
    public Date getCreationDateTime() {
        return this.pamMultipleRaw.getStartDate() != null ? this.pamMultipleRaw.getStartDate() : new Date();
    }

    @Override
    public OMHSchema getSchema() {

        return new OMHSchema(
                "PAMMultipleRaw",
                "Cornell",
                "1.0"
        );
    }

    @Nullable
    @Override
    public OMHAcquisitionProvenance getAcquisitionProvenance() {
        return this.acquisitionProvenance;
    }

    @Override
    public JSONObject getBody() {
        Map<String, Serializable> map = new HashMap<>();
        HashMap<String, Serializable> effectiveTimeMap = new HashMap<>();
        effectiveTimeMap.put("date_time", OMHDataPoint.stringFromDate(this.getCreationDateTime()));
        map.put("effective_time_frame", effectiveTimeMap);
        map.put("selected", this.pamMultipleRaw.getPamChoices());
        return new JSONObject(map);
    }
}
