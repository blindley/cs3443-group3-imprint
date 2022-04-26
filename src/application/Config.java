package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {
	static HashMap<String, String> configData;

	/**
	 * Initializes the configData HashMap from file "config.csv"
	 */
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
	
	/**
	 * Returns all config data, so the user iterate over all existing values
	 * 
	 * @return all of the configData
	 */
	public static HashMap<String, String> getAllConfigData() {
		return configData;
	}
	
	/**
	 * Checks if configData contains a particular key
	 * 
	 * @param key	The key to check
	 * @return		true if configData contains the key
	 */
	public static boolean containsKey(String key) {
		return configData.containsKey(key);
	}
	
	/**
	 * Gets the value associated with a key in configData. Returns an empty
	 * string if the key is not present.
	 * 
	 * @param key	The key to check
	 * @return The value associated with the key, or an empty string
	 */
	public static String getValue(String key) {
		String value = configData.get(key);
		if (value == null)
			return "";
		else
			return value;
	}
}
