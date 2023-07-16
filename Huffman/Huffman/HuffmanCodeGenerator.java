import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;
public class HuffmanCodeGenerator {
private HashMap<Character, Integer> map;
private HuffmanTree tree;
private File codeFile;
private ArrayList <HuffmanNode> list;
	public HuffmanCodeGenerator (String inputFile) throws IOException {
		File fileName = new File (inputFile);
		BufferedReader br = new BufferedReader (new FileReader (fileName));
		HashMap<Character, Integer> map = new HashMap <Character, Integer> ();
		this.map = map;
		int num = br.read ();
		char first = (char)num;
		map.put(first, 1);
		while (br.ready ()) {
			int nextNum = br.read();
			char nextChar = (char)nextNum;
			if (!map.containsKey (nextChar))
				map.put(nextChar, 1);
			else {
				int frequency = map.get(nextChar);
				frequency++;
				map.put(nextChar, frequency);
			}
		}
		br.close ();
		createTree ();
		this.list = tree.getListOfNodes ();
	}
	public int getFrequency (char c) {
		if (map.containsKey(c))
			return map.get(c);
		return 0;
	}
	private Character [] getCharList () {
		Set<Character> set = this.map.keySet();
		Character [] arr = new Character [set.size()];
		int index = 0;
		for (Character c:set) {
			arr[index] = c;
			index++;
		}
		return arr;
	}
	
	public String getCode (char c) {
		for (int i = 0; i < list.size(); i++) {
			if ((Character)list.get(i).getChar() != null && list.get(i).getChar() == c)
				return list.get(i).getCode ();
		}
		return null;
	}
	public void makeCodeFile (String codeFile) throws FileNotFoundException {
		//ArrayList <HuffmanNode> tempList = list;
		PrintWriter pw = new PrintWriter(codeFile);
		int lineNumber = 0;
		ArrayList<Integer> numbers = arrayOfASCII ();
		int tempChar = numbers.remove(0);
		while (lineNumber < 128) {
				if (lineNumber == tempChar) {
					pw.print(getCode((char)tempChar));
					if (numbers.size() > 0) {
						tempChar = numbers.remove(0);
					}
				}
				pw.print("\n");
				lineNumber++;
		}
		pw.close();
	}
	public void createTree () {
		HuffmanTree tree = new HuffmanTree (getCharList (), this.map);
		this.tree = tree;
	}
	private ArrayList<Integer> arrayOfASCII () {
		Character [] charList = getCharList ();
		int index = 0;
		ArrayList<Integer> numbers = new ArrayList<Integer> ();
		for (int i = 0; i < charList.length; i++) {
			numbers.add((int)charList[index]);
			index++;
		}
		Collections.sort(numbers);
		return numbers;
	}
}
