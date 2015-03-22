package org.saburto.seq.gen.output;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Test;
import org.saburto.seq.gen.input.InvocationInfo;
import org.saburto.seq.gen.input.SequenceInfo;
import org.saburto.seq.gen.output.RecordingHistory;
import org.saburto.seq.gen.output.RecordingHistoryBuilder;

public class TestSequenceEntryBuilder {
	
	private SequenceInfo sequenceInfo;
	
	@Before
	public void setup(){
		sequenceInfo = new SequenceInfo();
		ArrayList<InvocationInfo> invocations = new ArrayList<InvocationInfo>();
		
		InvocationInfo invocation = new InvocationInfo();
		invocation.setType("Hola");
		invocation.setName("ok");
		invocation.setReturnType("int");
		invocations.add(invocation);
		
		ArrayList<InvocationInfo> subInvocations = new ArrayList<InvocationInfo>();
		for (int i = 0; i < 4; i++) {
			InvocationInfo subInvocation = new InvocationInfo();
			subInvocation.setType("Hola");
			subInvocation.setName("ok" + i);
			subInvocation.setReturnType("int");
			subInvocations.add(subInvocation);	
		}
		
		invocation.setInvocationInfo(subInvocations);
		
		sequenceInfo.setInvocationInfo(invocations);
	}
	
	
	@Test
	public void test_Generate_SequenceEntry() throws Exception {
		RecordingHistory recordingHistory = new RecordingHistoryBuilder().addSequenceInfo(sequenceInfo).build();
		
		JAXBContext context = JAXBContext.newInstance(RecordingHistory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(recordingHistory, writer);
		System.out.println(writer.toString());
					
	}

}
