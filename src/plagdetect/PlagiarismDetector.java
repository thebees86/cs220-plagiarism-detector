package plagdetect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import logger.Log;

public class PlagiarismDetector implements IPlagiarismDetector {
	public int n; //n
	Log log; //log
	
	//Stores all the things
	public Map<String, HashSet<String>> nGrams = new HashMap<String, HashSet<String>>();
	public Map<String,Map<String,Integer>> results = new HashMap<String,Map<String,Integer>>();
	
	public PlagiarismDetector(int n) throws IOException {
		this.n = n;
		log = new Log(); //Starts log in default location (logs/log.txt)
	}
	
	@Override
	public int getN() {
		return n;
	}

	@Override
	public Collection<String> getFilenames() {
		return nGrams.keySet();
	}

	@Override
	public Collection<String> getNgramsInFile(String filename) {
		return nGrams.get(filename);
	}

	@Override
	public int getNumNgramsInFile(String filename) {
		return nGrams.get(filename).size();
	}

	@Override
	public Map<String, Map<String, Integer>> getResults() {
		return results;
	}

	@Override
	public void readFile(File file) throws IOException { //where the magic happens
		// TODO Auto-generated method stub
		// most of your work can happen in this method
		
		//Part One: Read file & get nGrams
		log.add(String.format("Began reading file %s", file.getName()));
		LinkedHashSet<String> NGs = new LinkedHashSet<String>(); //using LinkedHashSet b/c it keeps nGrams in order and doesn't alphabetize them
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] words = line.split(" ");
			if(words.length >= n) {
				for(int count = 0; count < words.length-n+1; count++) {
					NGs.add(String.format("%s %s %s", words[count], words[count+1], words[count+2]));
				}
			}
		}
		nGrams.put(file.getName(),NGs);
		log.add(String.format("Saved nGrams for file %s", file.getName()));
		
		//Part Two: Calc nGram commonality
		
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readFilesInDirectory(File dir) throws IOException { //complete!
		// delegation!
		// just go through each file in the directory, and delegate
		// to the method for reading a file
		for (File f : dir.listFiles()) {
			readFile(f);
		}
	}
}
