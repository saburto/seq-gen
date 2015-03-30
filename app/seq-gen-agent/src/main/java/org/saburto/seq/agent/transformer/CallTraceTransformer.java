package org.saburto.seq.agent.transformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.junit.Test;
import org.saburto.seq.agent.ConfigurationManager;
import org.saburto.seq.agent.transformer.InvocationInfo.InvocationInfoBuilder;

public class CallTraceTransformer implements ClassFileTransformer {
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		ConfigurationManager configurationManager = ConfigurationManager.INSTANCE;

		byte[] byteCode = classfileBuffer;

		try {

			String classForJava = className.replace("/", ".");
			if (!classForJava.equals("java.lang.Shutdown")
					&& configurationManager.isPackageInclude(classForJava)) {
				ClassPool cp = ClassPool.getDefault();
				CtClass cc = cp.get(classForJava);
				CtBehavior[] declaredMethods = cc.getDeclaredBehaviors();
				
				for (CtBehavior m : declaredMethods) {
					if (m instanceof CtMethod && m.isEmpty())
						continue;

					String beforeStatement = "";
					String afterStatement = "";
					if ((configurationManager.testAsStartMethod() 
							&& m.hasAnnotation(Test.class))
							|| configurationManager.isStartMethod(classForJava + "." + m.getName())) {
						beforeStatement += "org.saburto.seq.agent.transformer.CallTraceTransformer.startFileTest(\""
								+ m.getName() + "\");";
						afterStatement += "org.saburto.seq.agent.transformer.CallTraceTransformer.endFileTest(\""
								+ m.getName() + "\");";
					}

					InvocationInfo invocationInfo = getInvocationInfo(m, cc);
					beforeStatement += "org.saburto.seq.agent.transformer.CallTraceTransformer.addInvocation(\""
							+ invocationInfo.toXMLStart() + "\");";
					m.insertBefore(beforeStatement);

					m.insertAfter("org.saburto.seq.agent.transformer.CallTraceTransformer.addInvocation(\""
							+ invocationInfo.toXMLEnd()
							+ "\");"
							+ afterStatement);
				}

				byteCode = cc.toBytecode();
				cc.detach();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return byteCode;
	}

	private static String filename = null;

	public static void startFileTest(String testName) {
		filename = testName + ".xml";
		System.out.println("FileName:" + filename);
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
		writeXML(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><sequenceInfo>",
				filename);
	}

	public static void endFileTest(String testName) {
		writeXML("</sequenceInfo>", filename);
		filename = null;
	}

	public static void addInvocation(String xml) {
		writeXML(xml, filename);
	}

	private static void writeXML(String xml, String pathname) {
		if (pathname == null) {
			return;
		}
		try {
			File file = new File(pathname);
			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
			output.write(xml);
			output.close();
		} catch (Exception e) {
		}
	}

	public InvocationInfo getInvocationInfo(CtBehavior method, CtClass cc) {
		InvocationInfoBuilder builder = InvocationInfo.builder()
				.setName(method.getName()).setType(cc.getName());
		setReturnType(method, builder);
		addParameters(method, builder);
		return builder.build();
	}

	private void addParameters(CtBehavior method, InvocationInfoBuilder builder) {
		try {
			CtClass[] parameterTypes = method.getParameterTypes();
			MethodInfo methodInfo = method.getMethodInfo();

			LocalVariableAttribute table = (LocalVariableAttribute) methodInfo
					.getCodeAttribute()
					.getAttribute(LocalVariableAttribute.tag);

			int index = 0;
			if (!Modifier.isStatic(method.getModifiers())) {
				index++;
			}

			for (CtClass ctClass : parameterTypes) {

				int frameWithNameAtConstantPool = table.nameIndex(index++);
				String variableName = methodInfo.getConstPool().getUtf8Info(
						frameWithNameAtConstantPool);

				builder.addArg(ctClass.getName(), variableName);
			}
		} catch (NotFoundException e) {
		}
	}

	private void setReturnType(CtBehavior method, InvocationInfoBuilder builder) {
		try {
			if(method instanceof CtMethod){
				builder.setReturnType(((CtMethod) method).getReturnType().getName());	
			}else{
				builder.setReturnType(method.getDeclaringClass().getName());
			}
			
		} catch (NotFoundException e1) {
		}
	}

}
