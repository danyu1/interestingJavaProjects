import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
public class HuffmanDecoder {
	private String [] arrayOfCodes;
	public HuffmanDecoder (String codeFile) throws IOException {
		String currentCode = "";
		File fileName = new File (codeFile);
		BufferedReader br = new BufferedReader (new FileReader (fileName));
		String [] arr = new String [128];
		this.arrayOfCodes = arr;
		for (int i = 0; i < 128; i++) {
			String code = "";
			code = br.readLine();
			arrayOfCodes [i] = code;
		}
		br.close ();
	}
	public boolean isCode (String binary)
	{
		for (int i = 0; i < 128; i++) {
			if (arrayOfCodes [i].equals (binary))
				return true;
		}
		return false;
	}
	public char decodeChar (String binary) {
		for (int i = 0; i < 128; i++) {
			if (arrayOfCodes [i].equals(binary))
				return (char)(i);
		}
		return 0;
	}
	public void decodeLong (String encodedFile, String decodedFile) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader (encodedFile));
		String binary = "";
		binary += (char)br.read();
		PrintWriter pw = new PrintWriter (decodedFile);
		while (br.ready()) {
			while (!isCode(binary))
			{
				binary += (char)br.read();
			}
			pw.print(decodeChar(binary));
			binary = "";
			binary += (char)br.read();
		}
		if (binary.length()>0)
			pw.print(decodeChar(binary));
		pw.close();
		br.close();
	}

	public void decodeFile (String encodedFile) throws IOException {
		if (!encodedFile.endsWith(".huf"))
			throw new IllegalArgumentException ();
		BufferedReader br = new BufferedReader (new FileReader (encodedFile));
		StringBuilder sb = new StringBuilder ();
		PrintWriter pw = new PrintWriter (encodedFile.substring(0, encodedFile.length()-4));
		//take in the compressed file turn it into original code and put it into the string builder
		int current = br.read();
		int next = br.read();
		while (br.ready()) {
			if (next!=-1) {
				if (current != 0) {
					String toPrint = Integer.toBinaryString(current);
					for (int i = 0; i < 8 - toPrint.length (); i++)
						sb.append("0");
					sb.append(Integer.toBinaryString(current));
				}
				else {
					sb.append("00000000");
				}
				current = next;
				next = br.read();
			}
		}
		if (next != -1 && next != 0 || current != -1) {
			String toPrint = Integer.toBinaryString(current);
			String lastPrintMinusZeros = "";
			for (int i = 0; i < 8 - toPrint.length (); i++)
				lastPrintMinusZeros += "0";
			lastPrintMinusZeros += toPrint;
			String padded = "0";
			if (next != -1)
				padded = (char)next + "";
			lastPrintMinusZeros = lastPrintMinusZeros.substring(0, 8-Integer.parseInt(padded));
			sb.append(lastPrintMinusZeros);
		}
		else
			sb.append("00000000");
		br.close();
		//turn the code back into the original file
		String code = "";
		code += sb.charAt(0);
		for (int i = 1; i < sb.length(); i++) {
			if (!isCode(code))
				code += sb.charAt(i);
			else {
				pw.print(decodeChar(code));
				code = sb.charAt(i) + "";
			}
		}
		if (isCode(code))
			pw.print(decodeChar(code));
		pw.close();
	}
}
