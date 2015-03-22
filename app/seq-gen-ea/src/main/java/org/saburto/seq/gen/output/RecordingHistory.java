package org.saburto.seq.gen.output;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RecordingHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordingHistory {
	
	@XmlAttribute(name = "PackageGUID")
	private String packageGUID;
	@XmlAttribute
	private String version = "8.0";
	
	public RecordingHistory() {
		packageGUID = UUID.randomUUID().toString();
	}
	
	@XmlElement(name = "SequenceEntry")
	private List<SequenceEntry> sequenceEntry = new ArrayList<SequenceEntry>();
	
	public List<SequenceEntry> getSequenceEntry() {
		return sequenceEntry;
	}

	public void setSequenceEntry(List<SequenceEntry> sequenceEntry) {
		this.sequenceEntry = sequenceEntry;
	}

	public String getPackageGUID() {
		return packageGUID;
	}

	public void setPackageGUID(String packageGUID) {
		this.packageGUID = packageGUID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void addEntry(SequenceEntry entry) {
		sequenceEntry.add(entry);
		
	}

}
