package edu.cornell.tech.foundry.sdl_rsx_rsrpohmagebackend;

import android.content.Context;

import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.omhclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;
import edu.cornell.tech.foundry.sdl_rsx_rsrpsupport.PAMMultipleRaw;

/**
 * Created by jameskizer on 4/19/17.
 */

public class PAMMultipleRawBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        return new PAMMultipleRawOMHDatapoint(context, (PAMMultipleRaw) intermediateResult);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof PAMMultipleRaw) {
            return true;
        }
        else {
            return false;
        }
    }
}
