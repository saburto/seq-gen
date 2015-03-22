package org.saburto.seqxmi.transformer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import javassist.ClassPool;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.saburto.seq.agent.transformer.CallTraceTransformer;
import org.saburto.seq.agent.transformer.InvocationInfo;
import org.saburto.seqxmi.example.ClassA;

public class TestCallTraceTransformer {
	
	private CallTraceTransformer transform;
	
	@Before
	public void setup(){
		transform = new CallTraceTransformer();
	}

	@Test
	public void test_Getting_Invocation_Info_Method_No_Args() throws Exception {
		
		String methodName = "metodo1";
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName(methodName).build();
		
		assertMethod(methodName, expected);
	}
	
	@Test
	public void test_Getting_Invocation_Info_Static_Method_No_Args() throws Exception {
		
		String methodName = "metodoStatic1";
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName(methodName).build();
		
		assertMethod(methodName, expected);
	}
	
	@Test
	public void test_Getting_Signature_Info_Method_Two_Args() throws Exception {
		
		String methodName = "metodo2";
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName(methodName).addArg(String.class.getName(), "a").addArg(String.class.getName(), "b").build();
		assertMethod(methodName, expected);
	}
	
	@Test
	public void test_Getting_Signature_Info_Static_Method_Two_Args() throws Exception {
		
		String methodName = "metodoStatic2";
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName(methodName).addArg(String.class.getName(), "a").addArg(String.class.getName(), "b").build();
		assertMethod(methodName, expected);
	}
	
	@Test
	public void test_Getting_Signature_Info_Method_Two_Args_Two_Types() throws Exception {
		
		String methodName = "metodo3";
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName(methodName).addArg(String.class.getName(), "a").addArg("int", "b").setReturnType("int").build();
		assertMethod(methodName, expected);
		
	}
	
	@Test
	public void test_Getting_Info_Method_In_XML() throws Exception {
		
		InvocationInfo expected = InvocationInfo.builder().setType(ClassA.class.getName()).setName("Hola").addArg(String.class.getName(), "a").addArg("int", "b").setReturnType("int").build();
		System.out.println(expected.toXMLStart());
		
		
	}

	private void assertMethod(String methodName, InvocationInfo expected)
			throws NotFoundException {
		ClassPool cp = ClassPool.getDefault();
		
		CtMethod method = cp.getMethod(ClassA.class.getName(), methodName);
		InvocationInfo actual = transform.getInvocationInfo(method, cp.getCtClass(ClassA.class.getName()));
		assertThat(actual, equalTo(expected));
	}
	
	
}
