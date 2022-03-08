package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {
	static HashMap<String, String> configData;
	
	public static void initialize() {
		configData = new HashMap<String, String>();
		
		String configFilePath = "config.csv";
		File configFile = new File(configFilePath);
		if (configFile.exists() && configFile.isFile()) {
			try {
				ArrayList<String[]> rows = CSVLoader.loadCSV(configFilePath);
				for (String[] row : rows) {
					if (row.length == 1) {
						configData.put(row[0], "true");
					} else if (row.length == 2) {
						configData.put(row[0], row[1]);
					} else if (row.length > 2) {
						String key = row[0];
						String value = String.join(",", row);
						configData.put(key, value);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public static HashMap<String, String> getAllConfigData() {
		return configData;
	}
	
	public static boolean containsKey(String key) {
		return configData.containsKey(key);
	}
	
	public static String getValue(String key) {
		return configData.get(key);
	}
}
