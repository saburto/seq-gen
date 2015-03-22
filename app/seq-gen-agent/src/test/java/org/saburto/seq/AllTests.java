package org.saburto.seq;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.saburto.seq.transformer.TestCallTraceTransformer;

@RunWith(Suite.class)
@SuiteClasses({ TestClassSeq.class, TestCallTraceTransformer.class })
public class AllTests {

}
