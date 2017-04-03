package com.sub.syn.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static Properties property = new Properties();

	static {
		try {
			InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");
			property.load(inputStream);
			System.out.println("222222222");
		} catch (IOException e) {
			System.out.println("load config.properties exception: " + e.getMessage());
		}
	}

	public static String getProperty(String str) {
		String temp = property.getProperty(str);
		return temp;
	}

	public static void main(String[] args) {
		getProperty("filenumber");
		getProperty("filenumber");
		getProperty("filenumber");
	}
}
