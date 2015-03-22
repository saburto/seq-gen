package org.saburto.seq.gen;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.xml.bind.UnmarshalException;

import org.junit.Before;
import org.junit.Test;
import org.saburto.seq.gen.GeneratorEA;
import org.saburto.seq.gen.input.SequenceInfo;

public class TestGeneratorEA {
	
	GeneratorEA generator;
	
	@Before
	public void setup(){
		generator = new GeneratorEA(getClass().getResource("/testRunSeq.xml").getFile(), "nuevoFile.xml"); 
	}
	
	@Test
	public void test_GenerateEA_Read_FileInput_to_Object() throws Exception {
		SequenceInfo info = generator.parseInputFile();
		assertNotNull(info);
	}
	
	@Test(expected=UnmarshalException.class)
	public void test_GenerateEA_Read_FileInput_No_Exists() throws Exception {
		generator = new GeneratorEA("noexistexml", "");
		generator.parseInputFile();
		fail("Must Fail");
	}
	
	@Test(expected=UnmarshalException.class)
	public void test_GenerateEA_Read_FileInput_Bad_Format() throws Exception {
		generator = new GeneratorEA("noexistexml", "");
		generator.parseInputFile();
		fail("Must Fail");
	}

}
