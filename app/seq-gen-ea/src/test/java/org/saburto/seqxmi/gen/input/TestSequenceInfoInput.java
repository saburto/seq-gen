package org.saburto.seqxmi.gen.input;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class TestSequenceInfoInput {
	
	@Test
	public void testReadXML() throws Exception {
		SequenceInfo sequenceInfo = new SequenceInfo();
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
			subInvocation.setName("ok");
			subInvocation.setReturnType("int");
			subInvocations.add(subInvocation);	
		}
		
		invocation.setInvocationInfo(subInvocations);
		
		
		
		
		sequenceInfo.setInvocationInfo(invocations);
		
		JAXBContext context = JAXBContext.newInstance(SequenceInfo.class);
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.marshal(sequenceInfo, writer);
		System.out.println(writer.toString());
	}

}
