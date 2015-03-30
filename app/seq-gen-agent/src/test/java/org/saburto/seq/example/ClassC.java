package org.saburto.seq.example;

public class ClassC {

	public ClassD methodOne(String string) {
		
		ClassE e = ClassE.excute(string, 2);
		
		return new ClassD(e);
	}

}
