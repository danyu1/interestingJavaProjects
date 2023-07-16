import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

//BufferedReader br = new BufferedReader (new FileReader(fileName))
//throw exception
//Style Requirements:
//1. final submission can only read the file once
//2. can't put the file into a string

public class MiniGPT {
private HashMap<String, ArrayList<LetterFrequencies>> map;
private String seed;
	public MiniGPT(String fileName, int chainOrder) throws IOException {
		double max = 0;
		HashMap<String, ArrayList<LetterFrequencies>> map = new HashMap <String, ArrayList<LetterFrequencies>> ();
		this.map = map;
		BufferedReader br = new BufferedReader (new FileReader(fileName));
		String combo = "";
		char next = ' ';
		int value;
		//create a key of chainOrder length
		for (int i = 0; i < chainOrder; i++)
		{
			value = br.read();
			combo += (char)value;
		}
		map.put(combo, new ArrayList<LetterFrequencies>());
		while (br.ready())
		{
			//if the combo is already a key that exists in the map
			if (map.containsKey(combo))
			{
				//returns the list at the key
				ArrayList<LetterFrequencies> list = map.get (combo);
				double frequency = checkFrequency (list);
				if (frequency >= max)
				{
					max = frequency;
					seed = combo;
				}
				//the next char after the combo
				int val = br.read();
				next = (char)val;
				//change combo into the next possible key
				combo = combo.substring(1, combo.length());
				combo += next;
				//index of char object in the list of the given key of hashmap, -1 if the object with that char doesn't exist
				int indexOfChar = hasCharInList (list, next);
				if (indexOfChar >= 0)	
				{
					//if char already has an object assigned to it, then increment the frequency by 1
					list.get(indexOfChar).incrementFrequency();
					//System.out.println (list.get(indexOfChar));
				}
				else
				{
					//if char is not in the list of that key then create new object with that char
					list.add(new LetterFrequencies (next, 1.0));
				}
			}
			else
			{
				//create the key with the new combo of chars with a new array list of LetterFrequency objects
				map.put(combo, new ArrayList<LetterFrequencies> ());
				//the next char after the combo
				int val = br.read();
				next = (char)val;
				ArrayList<LetterFrequencies> list = map.get(combo);
				//create new LetterFrequencies object with the next char that has a frequency of 1
				list.add(new LetterFrequencies (next, 1.0));
				//change combo into the next possible key
				combo = combo.substring(1, combo.length());
				combo += next;
			}
			
		}	
		br.close ();
	}

	
	public void generateText(String outputFileName, int numChars) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(outputFileName);
		pw.print(seed);
		//System.out.println (seed);
		String combo = seed;
		int count = 1;
		for (int i = 0; i < numChars; i++)
		{
			char letter = chooseLetterFromList((ArrayList<LetterFrequencies>) map.get(combo));
			pw.print(letter);
			combo = combo.substring(1, combo.length());
			combo += letter;
		}
		//pw.print("");
		pw.close ();
	}
	//checks the list mapped to a key in hash map and checks if a given char after a combo of letters is in any one of the objects in the list
	public int hasCharInList (ArrayList<LetterFrequencies> list, char next)
	{
		//if not there return -1;
		for (int i = 0; i < list.size (); i++)
		{
			if (list.get(i).getLetter() == next)
			{
				return i;
			}
		}
		return -1;
	}
	//checks all the # of occurrences in each of the objects within a list
	public double checkFrequency (ArrayList<LetterFrequencies> list)
	{
		double max = 0;
		for  (LetterFrequencies obj:list)
		{
			max += obj.getFrequency ();
		}
		return max;
	}
	//this is the method that randomly chooses a letter from the list of objects
	//creates an array of all the chars from all the objects
	public char chooseLetterFromList (ArrayList<LetterFrequencies> list)
	{
		if (list == null)
		{
			return ' ';
		}
		int count = 0;
		int size = 0;
		for (LetterFrequencies obj:list)
		{
			size += obj.getFrequency();
		}
		char [] arrayOfLetters = new char [size];
		for (LetterFrequencies obj:list)
		{
			for (int i = 0; i < obj.getFrequency (); i++)
			{
				arrayOfLetters [count] = obj.getLetter();
				count++;
			}
		}
		int index = (int) (Math.random() * size);
		return arrayOfLetters [index];
	}
}