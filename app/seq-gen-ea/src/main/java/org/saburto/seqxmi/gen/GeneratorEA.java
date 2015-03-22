package org.saburto.seqxmi.gen;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.saburto.seqxmi.gen.input.SequenceInfo;

public class GeneratorEA {
	
	private final String inputFile;
	private final String outputFile;

	public GeneratorEA(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void execute() {
		
	}

	SequenceInfo parseInputFile() throws Exception {
		JAXBContext context = JAXBContext.newInstance(SequenceInfo.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Object object = unmarshaller.unmarshal(new File(inputFile));
		return (SequenceInfo) object;
	}

}
