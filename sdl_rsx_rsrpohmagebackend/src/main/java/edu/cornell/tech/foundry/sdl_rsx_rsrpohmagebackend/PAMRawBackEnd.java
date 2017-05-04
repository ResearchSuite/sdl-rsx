package edu.cornell.tech.foundry.sdl_rsx_rsrpohmagebackend;

import android.content.Context;

import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.omhclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;
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
