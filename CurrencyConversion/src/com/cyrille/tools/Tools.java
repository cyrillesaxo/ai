package com.cyrille.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tools {

	private static final char DEFAULT_SEPARATOR = ',';

	public static void main(String[] args) {
		readFile("input.csv");
	}

	public static Map<String, Double> readFile(final String filename) {

		Map<String, Double> result = new LinkedHashMap<String, Double>();
		String csvFile = filename;
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";

		try {

			br = new BufferedReader(new InputStreamReader(
					Tools.class.getResourceAsStream(filename)));
			while ((line = br.readLine()) != null) {

				String[] test = line.split(splitBy);
/*				System.out.println("csvFile [Value= " + test[0] + ", Value="
						+ test[1] + "]");*/
				try {
					
					final Double valueToAdd = Double.parseDouble(test[1]);
					//System.out.println(test[0]+"-"+ valueToAdd);
					result.put(test[0], valueToAdd);
				} catch (NumberFormatException nfe) {
					System.out.println("Failed to parse line: " + line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static void writeLine(Writer w, List<String> values,
			char separators, char customQuote) throws IOException {

		boolean first = true;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(value);
			} else {
				sb.append(customQuote).append(value).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());

	}

}
