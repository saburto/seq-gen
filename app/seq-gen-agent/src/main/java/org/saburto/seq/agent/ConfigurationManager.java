package org.saburto.seq.agent;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public enum ConfigurationManager {
	
	INSTANCE;
	
	
	private Properties properties;
	private Set<String> packages;
	private Set<String> startMethods;
	
	private ConfigurationManager(){
		
		String filePath = System.getProperty("seq.conf.file");
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePath)));
			loadPackages();
			loadStartMethods();
		} catch (Exception e) {
			System.err.println("Please define the property seq.conf.file. Example: -Dseq.conf.file=some.properties");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void loadStartMethods() {
		startMethods = new HashSet<String>();
		loadManyProperties(startMethods, "startMethods");
		System.out.println("Start Methods :\n" + startMethods);
	}

	private void loadPackages() {
		packages = new HashSet<String>();
		loadManyProperties(packages, "packages");
		System.out.println("Packages to include:\n" + packages);
	}
	
	private void loadManyProperties(Collection<String> collection, String propertyName) {
		String props = properties.getProperty(propertyName, "");
		String[] prop = props.split(",");
		for (String pk : prop) {
			collection.add(pk.trim());
		}
	}

	public boolean isPackageInclude(String classJava) {
		String packageString = getPackageFromFullClass(classJava);
		
		for (String pack : packages) {
			if(packageString.startsWith(pack)){
				return true;
			}
		}
		
		return false;
	}

	private String getPackageFromFullClass(String classJava) {
		if(classJava.contains(".")){
			int index = classJava.lastIndexOf(".");
			return classJava.substring(0, index);
		}
		return "";
	}

	public boolean testAsStartMethod() {
		return Boolean.valueOf(properties.getProperty("testAsStartMethod", "false"));
	}

	public boolean isStartMethod(String classForJava) {
		
		return startMethods.contains(classForJava);
	}
	
	
	

}
