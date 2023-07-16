public class HuffmanNode implements Comparable<HuffmanNode> {
	private int frequency;
	private char character;
	private HuffmanNode left;
	private HuffmanNode right;
	private String code;
	public HuffmanNode (int frequency)
	{
		this.frequency = frequency;
		this.code = null;
	}
	public HuffmanNode (char c)
	{
		this.character = c;
		this.frequency = 0;
		this.code = null;
	}
	public void setRight (HuffmanNode node)
	{
		this.right = node;
	}
	public void setLeft (HuffmanNode node)
	{
		this.left = node;
	}
	public HuffmanNode getRight ()
	{
		return this.right;
	}
	public HuffmanNode getLeft ()
	{
		return this.left;
	}
	public char getChar ()
	{
		return this.character;
	}
	public int getFrequency ()
	{
		return this.frequency;
	}
	public void setFrequency (int frequency)
	{
		this.frequency = frequency;
	}
	public int compareTo(HuffmanNode o) 
	{
        return this.frequency - o.frequency;
    }
	public String getCode ()
	{
		return this.code;
	}
	public void setCode (String code)
	{
		this.code = code;
	}
	public String toString ()
	{
		return "Char: " + character + " Frequency: " + frequency + " Code: " + code;
	}
	
}
