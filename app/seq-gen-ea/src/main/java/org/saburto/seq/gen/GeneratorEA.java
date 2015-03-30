package org.saburto.seq.gen;

import java.io.File;
import java.io.FileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.saburto.seq.gen.input.SequenceInfo;
import org.saburto.seq.gen.output.RecordingHistory;
import org.saburto.seq.gen.output.RecordingHistoryBuilder;

public class GeneratorEA {
	
	private final String inputFile;
	private final String outputFile;

	public GeneratorEA(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void execute() throws Exception {
		SequenceInfo info = parseInputFile();
		RecordingHistory build = new RecordingHistoryBuilder().addSequenceInfo(info).build();
		JAXBContext context = JAXBContext.newInstance(RecordingHistory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(build, new FileWriter(outputFile));
	}

	SequenceInfo parseInputFile() throws Exception {
		JAXBContext context = JAXBContext.newInstance(SequenceInfo.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Object object = unmarshaller.unmarshal(new File(inputFile));
		return (SequenceInfo) object;
	}

}
