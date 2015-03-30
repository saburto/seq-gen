package org.saburto.seq.example;

public class ClassB {
	
	ClassC c = new ClassC();

	public void execute() {
		ClassF f = c.methodOne("Hola").methodTwo(2);
		f.excute(c);
	}

}
