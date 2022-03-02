package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVLoader {
	public static ArrayList<String[]> loadCSV(String filename) throws IOException {
		try (
	        	BufferedReader reader = new BufferedReader(new FileReader("data/" + filename))
	        )
        {
			ArrayList<String[]> rows = new ArrayList<String[]>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (!line.isEmpty()) {
                	// TODO: This should not split on commas embedded in quoted strings
                	String[] rowEntries = line.split(",");
                	rows.add(rowEntries);
                }
            }
            
            return rows;
        }
	}
}
