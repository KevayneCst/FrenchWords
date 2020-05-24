package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Core {

	private String pathInputFile;
	private String pathOutputFile;
	private List<String> inputLines = new ArrayList<>();
	private List<String> outputLines = new ArrayList<>();
	private Map<String,String> correctedChar = new HashMap<>();
	
	public static void main(String[] args) {
		Core c = new Core("D:\\new 5.txt","D:\\Dictionnary.txt");
	}
	
	public Core(String pathSrc, String pathDest) {
		this.pathInputFile = pathSrc;
		this.pathOutputFile = pathDest;
		correctedChar.put("Ã©", "é");
		correctedChar.put("Ã¨", "è");
		correctedChar.put("Ã¢", "â");
		correctedChar.put("Ã§", "ç");
		correctedChar.put("Ã®", "î");
		correctedChar.put("Ãª", "ê");
		correctedChar.put("Ã´", "ô");
		correctedChar.put("Ã¹", "ù");
		correctedChar.put("Ã»", "û");
		correctedChar.put("Ã¯", "ï");
		correctedChar.put("Ã«", "ë");
		correctedChar.put("Ã¼", "ü");
		startCorrection();
	}
	
	public void startCorrection() {
		fillInputLines();
		correctAllWords();
		fillOutputFile();
	}
	
	public void fillInputLines() {
		List<String> list = new ArrayList<>();
		File fichier = new File(pathInputFile);
		BufferedReader br = null;
		int i = 1;
		try {
			br = new BufferedReader(new FileReader(fichier));
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
				System.out.println(i+ ";" + line);
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inputLines = list;
	}
	
	public void fillOutputFile() {
		try {
			FileWriter myWriter = new FileWriter(pathOutputFile);
			for (String s : outputLines) {
				myWriter.write(s + "\n");
			}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void correctAllWords() {
		for (String s : inputLines) {
			outputLines.add(correctAllUnknowOccurence(s));
		}
	}
	
	public String correctAllUnknowOccurence(String word) {
		while (needCorrection(word)) {
			word = correctFirstUnknowOccurence(word);
		}
		return word;
	}
	
	public String correctFirstUnknowOccurence(String word) {
		StringBuilder sb = new StringBuilder(word);
		for (Map.Entry<String, String> map : correctedChar.entrySet()) {
			if (sb.toString().contains(map.getKey())) {
				int lengthCharAt = map.getKey().length();
				int index = sb.indexOf(map.getKey());
				sb.replace(index, index+lengthCharAt, map.getValue());
				return sb.toString();
			}
		}
		if (sb.toString().contains("Ã")) {
			int index = sb.indexOf("Ã");
			sb.replace(index, index+1, "à");
			return sb.toString();
		}
		return sb.toString();
	}
	
	public boolean needCorrection(String word) {
		for (Map.Entry<String, String> map : correctedChar.entrySet()) {
			if (word.contains(map.getKey())) {
				return true;
			}
		}
		return word.contains("Ã");
	}
	
}
