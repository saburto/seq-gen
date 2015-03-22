package org.saburto.seq.gen.output;

import java.util.List;

import org.saburto.seq.gen.input.InvocationInfo;
import org.saburto.seq.gen.input.SequenceInfo;

public class RecordingHistoryBuilder {
	
	private int seq = 1;
	private RecordingHistory recordingHistory;

	public RecordingHistoryBuilder addSequenceInfo(SequenceInfo sequenceInfo) {
		
		recordingHistory = new RecordingHistory();

		List<InvocationInfo> invocationsInfo = sequenceInfo.getInvocationInfo();
		for (InvocationInfo invocationInfo : invocationsInfo) {
			addSequenceEntry(invocationInfo, null, 1);	
		}
		
		return this;
	}

	private void addSequenceEntry(InvocationInfo first, InvocationInfo parent,
			int depth) {
		SequenceEntry entry = new SequenceEntry();
		//<SequenceEntry Checked="true" Depth="1" Direction="Call" 
		//FromIID="" FromLine="0" FromMethod="" FromSource="" Scope="1" Sequence="00000001 " 
		//State="" Thread="0x00000abc" ToIID="" ToLine="34" ToMethod="Main.main" 
		//ToSource="c:\documents and settings\tcs\my documents\seqjava\main.java" ToValues=""/>

		entry.setDepth(depth);
		entry.setScope("" + depth);
		entry.setDirection("Call");
		entry.setSequence(String.format("%08d", seq++));
		entry.setThread("0x00000abc");
		entry.setToMethod(first.getType() + "."  +first.getName());
		
		entry.setToSource("c:/documents and settings/tcs/my documents/seqjava/main.java");
		
		recordingHistory.addEntry(entry);
		
		List<InvocationInfo> invocationsInfo = first.getInvocationInfo();
		if(invocationsInfo != null && !invocationsInfo.isEmpty()){
			for (InvocationInfo invocationInfo : invocationsInfo) {
				addSequenceEntry(invocationInfo, first, depth + 1);
			}	
		}
		
		
		
		
		if(parent != null){
			entry.setFromMethod(parent.getType() + "." +  parent.getName());
			
			SequenceEntry entryReturn = new SequenceEntry();
			//	<SequenceEntry Checked="true" 
			//Depth="1" Direction="Return" FromIID="" FromLine="0" 
			//FromMethod="" FromSource="" Scope="1" Sequence="00000003 " State="" Thread="0x00000abc" 
			//ToIID="" ToLine="36" ToMethod="Main.main" ToSource="c:\documents and settings\tcs\my documents\seqjava\main.java" ToValues=""/>


			entryReturn.setDepth(depth - 1);
			entryReturn.setScope("" + (depth - 1));
			entryReturn.setDirection("Return");
			entryReturn.setSequence(String.format("%08d", seq++));
			entryReturn.setThread("0x00000abc");
			entryReturn.setToMethod(parent.getType() + "." +  parent.getName());
			entryReturn.setToSource("c:/documents and settings/tcs/my documents/seqjava/main.java");
			
			recordingHistory.addEntry(entryReturn);	
		}
	}

	public RecordingHistory build() {

		return recordingHistory;
	}
	
	

}
