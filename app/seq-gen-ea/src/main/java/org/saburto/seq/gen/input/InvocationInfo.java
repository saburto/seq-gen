package org.saburto.seq.gen.input;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType( propOrder = { "name", "type", "args", "returnType", "invocationInfo" })
@XmlAccessorType(XmlAccessType.FIELD)
public class InvocationInfo {
	
	private String name;
	private String type;
	private Map<String, String> args = new LinkedHashMap<String, String>();
	private String returnType = "void";
	private List<InvocationInfo> invocationInfo;
	
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
	public List<InvocationInfo> getInvocationInfo() {
		return invocationInfo;
	}
	public void setInvocationInfo(List<InvocationInfo> invocationInfo) {
		this.invocationInfo = invocationInfo;
	}
	
}
