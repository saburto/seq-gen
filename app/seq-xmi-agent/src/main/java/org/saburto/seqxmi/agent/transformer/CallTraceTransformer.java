package org.saburto.seqxmi.agent.transformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.saburto.seqxmi.agent.transformer.InvocationInfo.InvocationInfoBuilder;

public class CallTraceTransformer implements ClassFileTransformer  {
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		byte[] byteCode = classfileBuffer;

		try {
			
			String classForJava = className.replace("/", ".");
			if (!classForJava.equals("java.lang.Shutdown") && classForJava.contains("org.saburto")) {
				ClassPool cp = ClassPool.getDefault();
				CtClass cc = cp.get(classForJava);
				CtMethod[] declaredMethods = cc.getDeclaredMethods();
				for (CtMethod m : declaredMethods) {
					if(m.isEmpty()) continue;

					InvocationInfo invocationInfo = getInvocationInfo(m, cc);
					String string = "org.saburto.seqxmi.agent.transformer.CallTraceTransformer.addInvocation(\"" + invocationInfo.toXML() + "\");";
					m.insertBefore(string);	
					
					//m.insertAfter("System.out.println(\"" + info + "\");");
				}

				byteCode = cc.toBytecode();
				cc.detach();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteCode;
	}
	
	public static void addInvocation(String xml){
		
		
		try {
			
			File file = new File("example.txt");
	        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
	        output.write(xml);
	        output.close();
			
			
		} catch (Exception e) {
		}
		
		
	}
	

	

	public InvocationInfo getInvocationInfo(CtMethod method, CtClass cc) {
		InvocationInfoBuilder builder = InvocationInfo.builder().setName(method.getName()).setType(cc.getName());
		setReturnType(method, builder);
		addParameters(method, builder);
		return builder.build();
	}



	private void addParameters(CtMethod method, InvocationInfoBuilder builder) {
		try {
			CtClass[] parameterTypes = method.getParameterTypes();
			MethodInfo methodInfo = method.getMethodInfo();
			
			LocalVariableAttribute table = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);
			
			int index = 0;
			if(!Modifier.isStatic(method.getModifiers())){
				index++;
			}
			
			for (CtClass ctClass : parameterTypes) {
				
				int frameWithNameAtConstantPool = table.nameIndex(index++); 
				String variableName = methodInfo.getConstPool().getUtf8Info(frameWithNameAtConstantPool);
				
				builder.addArg(ctClass.getName(), variableName);
			}
		} catch (NotFoundException e) {
		}
	}



	private void setReturnType(CtMethod method, InvocationInfoBuilder builder) {
		try {
			builder.setReturnType(method.getReturnType().getName());
		} catch (NotFoundException e1) {
		}
	}

}
