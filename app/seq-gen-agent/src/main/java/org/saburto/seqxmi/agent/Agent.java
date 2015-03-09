package org.saburto.seqxmi.agent;

import java.lang.instrument.Instrumentation;

import org.saburto.seqxmi.agent.transformer.CallTraceTransformer;

public class Agent {
	
public static void premain(String agentArgs, Instrumentation inst) {
        
        // registers the transformer
		inst.addTransformer(new CallTraceTransformer());
    }

}
