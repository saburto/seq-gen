package org.saburto.seq.gen;

public class GeneratorCommandLine {

	private static final int INPUT_FILE = 0;
	private static final int OUTPUT_FILE = 1;
	private static final String USAGE = "usage: java -jar generator.jar [inputFile] [outputFile]";
	private static final String SUCCESS_MESSAGE = "GENERATION SUCCESSFUL";

	public static void main(String[] args) {
		
		if(!argsAreValid(args)){
			System.err.println(USAGE);
			return;
		}
		
		try {
			GeneratorEA gen = new GeneratorEA(args[INPUT_FILE], args[OUTPUT_FILE]);
			gen.execute();
			System.out.println(SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static boolean argsAreValid(String[] args) {
		return args.length >= 2 && argsHaveContents(args);
	}

	private static boolean argsHaveContents(String[] args) {
		return argHasValue(args, 0) && argHasValue(args, 1);
	}

	private static boolean argHasValue(String[] args, int i) {
		return args[i] != null && !args[i].isEmpty();
	}

}
