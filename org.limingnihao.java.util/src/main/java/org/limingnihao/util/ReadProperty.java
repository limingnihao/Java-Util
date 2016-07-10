package org.limingnihao.util;

import java.util.Properties;
import java.io.*;

public class ReadProperty {
	private static Object initLock = new Object();

	private static ReadProperty dbconfig = null;

	private Properties props = null;

	private String propertyFile = "/application.properties";

	public static ReadProperty getInstance(String propertyFile) {
		if (dbconfig == null) {
			synchronized (initLock) {
				if (dbconfig == null) {
					dbconfig = new ReadProperty();
				}
			}
		}
		dbconfig.propertyFile = propertyFile;
		return dbconfig;
	}

	private synchronized void loadProperties() {
		props = new Properties();
		try {
			System.out.println("Load pro file");
			InputStream in = getClass().getResourceAsStream(this.propertyFile);
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String propName) {
		if (props == null) {
			loadProperties();
		}
		String value = props.getProperty(propName);
		if(value == null){
			return "";
		}
		return value;
	}
	
}