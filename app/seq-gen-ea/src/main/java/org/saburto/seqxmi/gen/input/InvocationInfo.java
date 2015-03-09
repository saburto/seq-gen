package org.saburto.seqxmi.gen.input;

import java.util.LinkedHashMap;
import java.util.Map;

public class InvocationInfo {
	
	private String name;
	private String type;
	private Map<String, String> args = new LinkedHashMap<String, String>();
	private String returnType = "void";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getArgs() {
		return args;
	}
	public void setArgs(Map<String, String> args) {
		this.args = args;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
