public class LetterFrequencies {
	private char letter;
	private double frequency;
	
	public LetterFrequencies (char letter, double frequency)
	{
		this.letter = letter;
		this.frequency = frequency;
	}
	public void incrementFrequency ()
	{
		this.frequency += 1.0;
	}
	public double getFrequency ()
	{
		return this.frequency;
	}
	public char getLetter ()
	{
		return this.letter;
	}
	public String toString ()
	{
		return "Letter: " + getLetter () + " Frequency: " + getFrequency ();
	}
}
