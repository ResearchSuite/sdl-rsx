package edu.cornell.tech.foundry.sdl_rsx_rsrpohmagebackend;

import android.content.Context;

import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.omhclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;
import edu.cornell.tech.foundry.sdl_rsx_rsrpsupport.YADLFullRaw;

/**
 * Created by jameskizer on 4/19/17.
 */

public class YADLFullRawBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        return new YADLFullRawOMHDatapoint(context, (YADLFullRaw)intermediateResult);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof YADLFullRaw) {
            return true;
        }
        else {
            return false;
        }
    }
}
