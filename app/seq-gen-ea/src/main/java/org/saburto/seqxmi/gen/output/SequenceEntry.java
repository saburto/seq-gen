package org.saburto.seqxmi.gen.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SequenceEntry {

	@XmlAttribute(name = "Checked")
	private boolean checked = true;
	@XmlAttribute(name = "Depth")
	private int depth = 1;
	@XmlAttribute(name = "Direction")
	private String direction;
	@XmlAttribute(name = "FromIID")
	private String fromIID;
	@XmlAttribute(name = "Scope")
	private String scope = "1";
	@XmlAttribute(name = "FromLine")
	private String fromLine;
	@XmlAttribute(name = "FromMethod")
	private String fromMethod;
	@XmlAttribute(name = "FromSource")
	private String fromSource;
	@XmlAttribute(name = "Sequence")
	private String sequence;
	@XmlAttribute(name = "State")
	private String state;
	@XmlAttribute(name = "Thread")
	private String thread;
	@XmlAttribute(name = "ToIID")
	private String toIID;
	@XmlAttribute(name = "ToLine")
	private String toLine;
	@XmlAttribute(name = "ToMethod")
	private String toMethod;
	@XmlAttribute(name = "ToSource")
	private String toSource;
	@XmlAttribute(name = "ToValues")
	private String toValues;
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getFromIID() {
		return fromIID;
	}
	public void setFromIID(String fromIID) {
		this.fromIID = fromIID;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getFromLine() {
		return fromLine;
	}
	public void setFromLine(String fromLine) {
		this.fromLine = fromLine;
	}
	public String getFromMethod() {
		return fromMethod;
	}
	public void setFromMethod(String fromMethod) {
		this.fromMethod = fromMethod;
	}
	public String getFromSource() {
		return fromSource;
	}
	public void setFromSource(String fromSource) {
		this.fromSource = fromSource;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getToIID() {
		return toIID;
	}
	public void setToIID(String toIID) {
		this.toIID = toIID;
	}
	public String getToLine() {
		return toLine;
	}
	public void setToLine(String toLine) {
		this.toLine = toLine;
	}
	public String getToMethod() {
		return toMethod;
	}
	public void setToMethod(String toMethod) {
		this.toMethod = toMethod;
	}
	public String getToSource() {
		return toSource;
	}
	public void setToSource(String toSource) {
		this.toSource = toSource;
	}
	public String getToValues() {
		return toValues;
	}
	public void setToValues(String toValues) {
		this.toValues = toValues;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

}
