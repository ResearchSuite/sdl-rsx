package edu.cornell.tech.foundry.sdl_rsx_rsrpohmagebackend;

import android.content.Context;

import org.researchsuite.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import org.researchsuite.omhclient.OMHDataPoint;
import org.researchsuite.rsrp.RSRPIntermediateResult;

import edu.cornell.tech.foundry.sdl_rsx_rsrpsupport.PAMRaw;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMRawBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        return new PAMRawOMHDatapoint(context, (PAMRaw) intermediateResult);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof PAMRaw) {
            return true;
        }
        else {
            return false;
        }
    }
}
