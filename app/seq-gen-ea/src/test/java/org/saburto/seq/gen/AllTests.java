package org.saburto.seq.gen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.saburto.seq.gen.input.TestSequenceInfoInput;
import org.saburto.seq.gen.output.TestSequenceEntryBuilder;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestGenerator.class, 
	TestGeneratorEA.class,
	TestSequenceEntryBuilder.class,
	TestSequenceInfoInput.class})
public class AllTests {

}
