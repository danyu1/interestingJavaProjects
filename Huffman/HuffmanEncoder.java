import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class HuffmanEncoder {
	private String [] arrayOfCodes;
	public HuffmanEncoder (String codeFile) throws IOException {
		String currentCode = "";
		File fileName = new File (codeFile);
		BufferedReader br = new BufferedReader (new FileReader (fileName));
		String [] arr = new String [128];
		this.arrayOfCodes = arr;
		for (int i = 0; i < 128; i++) {
			String code = "";
			code = br.readLine();
			arr[i] = code;
		}
		br.close ();
	}
	public String encodeChar (char input) {
		return arrayOfCodes [(int)(input)];
	}
	public void encodeLong (String fileToCompress, String encodedFile) throws IOException {
		File file1 = new File (fileToCompress);
		BufferedReader br = new BufferedReader (new FileReader (file1));
		PrintWriter pw = new PrintWriter (encodedFile);
		while (br.ready()) {
			pw.print (encodeChar((char)br.read()));
		}
		pw.close ();
		br.close();
	}
	//takes in randomchars, converts it to 0's and 1's and then turn those eight bits into a single char
	public void encodeFile (String fileToCompress) throws IOException {
		File file = new File (fileToCompress);
		BufferedReader br = new BufferedReader (new FileReader (file));
		StringBuilder codes = new StringBuilder ();
		PrintWriter pw = new PrintWriter (fileToCompress + ".huf");
		while (br.ready()) 
			codes.append(encodeChar((char)br.read()));
		int bits = 0;
		String eightBits = "";
		for (int i = 0; i < codes.length(); i++) {
			if (bits == 8) {
				pw.print((char)Integer.parseInt(eightBits, 2));
				bits = 1;
				eightBits = codes.charAt(i) + "";
			}
			else {
				bits++;
				eightBits += codes.charAt(i);
			}
			
		}
		if (bits != 8) {
			for (int i = 0; i < 8-bits; i++)
				eightBits += "0";
			pw.print((char)Integer.parseInt(eightBits, 2));
			pw.print(8-bits);
		}
		else {
			pw.print((char)Integer.parseInt(eightBits, 2));
		}
		pw.close();
	}
}