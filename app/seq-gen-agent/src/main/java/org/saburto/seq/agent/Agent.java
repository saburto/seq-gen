package org.saburto.seq.agent;

import java.lang.instrument.Instrumentation;

import org.saburto.seq.agent.transformer.CallTraceTransformer;

public class Agent {
	
public static void premain(String agentArgs, Instrumentation inst) {
        
        // registers the transformer
		inst.addTransformer(new CallTraceTransformer());
    }

}
