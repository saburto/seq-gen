package org.saburto.seq.gen;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.saburto.seq.gen.GeneratorCommandLine;

public class TestGenerator {
	
	
	ByteArrayOutputStream bytes;
	
	@Before
	public void setup(){
		bytes = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bytes);
		System.setOut(out);
		System.setErr(out);
	}
	
	@Test
	public void test_Generator_No_Input_Arguments_Message_No_Success() throws Exception {
		executeGeneration(null, "nuevoFile.xml");	
		assertThat(bytes.toString(), not(containsString("GENERATION SUCCESSFUL")));
		assertThat(bytes.toString(), containsString("usage: java -jar generator.jar [inputFile] [outputFile]"));
	}
	
	@Test
	public void test_Generator_No_Output_Arguments_Message_No_Success() throws Exception {
		executeGeneration("/testRunSeq.xml", null);	
		assertThat(bytes.toString(), not(containsString("GENERATION SUCCESSFUL")));
		assertThat(bytes.toString(), containsString("usage: java -jar generator.jar [inputFile] [outputFile]"));
	}
	
	@Test
	public void test_Generator_Arguments_Message_Success() throws Exception {
		String fileOutput = "nuevoFile.xml";
		executeGeneration("/testRunSeq.xml", fileOutput);	
		assertThat(bytes.toString(), containsString("GENERATION SUCCESSFUL"));
	}
	
	@Test
	public void test_Generator_Out_put_exists() throws Exception {
		String fileOutput = "nuevoFile.xml";
		executeGeneration("/testRunSeq.xml", fileOutput);	
		assertTrue(new File(fileOutput).exists());
	}

	private void executeGeneration(String fileInput, String fileOutput) {
		String[] args = new String[2];
		if(fileInput != null){
			args[0] = getClass().getResource(fileInput).getFile();	
		}
		args[1] = fileOutput;
		GeneratorCommandLine.main(args);
	}

}
