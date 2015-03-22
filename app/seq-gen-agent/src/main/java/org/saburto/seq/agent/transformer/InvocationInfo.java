package org.saburto.seq.agent.transformer;

import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InvocationInfo {
	
	private static final String INVOCATION_INFO = "</invocationInfo>";
	private String name;
	private String type;
	private Map<String, String> args = new LinkedHashMap<String, String>();
	private String returnType = "void";
	
	

	public static InvocationInfoBuilder builder() {
		return new InvocationInfoBuilder();
	}
	
	
	
	public String getName() {
		return name;
	}



	public Map<String, String> getArgs() {
		return Collections.unmodifiableMap(args);
	}



	public String getReturnType() {
		return returnType;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvocationInfo other = (InvocationInfo) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!equalsArg(other.args))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String toXMLStart(){
		StringWriter writer = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(InvocationInfo.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(this, writer);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		
		String start = writer.toString();
		return start.replaceFirst("</invocationInfo>", "");
	}
	
	public String toXMLEnd(){
		return INVOCATION_INFO;
	}

	


	@Override
	public String toString() {
		return "InvocationInfo [name=" + name + ", args=" + args
				+ ", returnType=" + returnType + "]";
	}



	private boolean equalsArg(Map<String, String> argsOther) {
		if(argsOther.size() != args.size()){
			return false;
		}
		
		Set<Entry<String, String>> entrySet = args.entrySet();
		for (Entry<String, String> entry : entrySet) {
			if(argsOther.containsKey(entry.getKey())){
				String value = argsOther.get(entry.getKey());
				if(!value.equals(entry.getValue())){
					return false;
				}
			}else{
				return false;
			}
		}
		
		return true;
	}



	public static class InvocationInfoBuilder{
		private InvocationInfo info;
		
		private InvocationInfoBuilder() {
			info = new InvocationInfo();
		}
		
		public InvocationInfo build() {
			if(info.name == null){
				throw new RuntimeException("Name must be setted");
			}
			
			if(info.type == null){
				throw new RuntimeException("Type must be setted");
			}
			return info;
		}

		public InvocationInfoBuilder setName(String name) {
			info.name = name;
			return this;
		}

		public InvocationInfoBuilder addArg(String type, String name) {
			info.args.put(name, type);
			return this;
		}
		
		public InvocationInfoBuilder setReturnType(String returnType) {
			info.returnType = returnType;
			return this;
		}

		public InvocationInfoBuilder setType(String type) {
			info.type = type;
			return this;
		}
		
	}



	
	
}
