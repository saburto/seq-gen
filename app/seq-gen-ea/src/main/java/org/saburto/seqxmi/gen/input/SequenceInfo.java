package org.saburto.seqxmi.gen.input;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SequenceInfo {
	private List<InvocationInfo> invocationInfo;

	public List<InvocationInfo> getInvocationInfo() {
		return invocationInfo;
	}

	public void setInvocationInfo(List<InvocationInfo> invocationInfo) {
		this.invocationInfo = invocationInfo;
	}

	
}
