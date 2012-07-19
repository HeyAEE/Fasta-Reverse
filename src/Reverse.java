import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;


/* Reverse.java - Used to reverse sequences in a fasta file
 * 
 * Code written by Adam Even Engel, released under licensing TBD.
 * For now, assume GPLv3.
 */

public class Reverse {

	/* A FASTA (not pasta!) file is used to record an amino acid sequence. Each amino acid
	 * sequence is stored as follows:
	 * 
	 * 		> Info about the protein
	 * 		LIW...	 
	 * 		> Info about the protein
	 * 		LIW...
	 * 
	 * Reverse.java should do a couple things. 
	 * First, it will standardize the titles. All amino acid sequences will be named 
	 * "REV_" followed by the number it is located in the file, i.e. the first protein 
	 * will be REV_00001.
	 * The second job of Reverse.java is to reverse the sequence. Sounds simple, eh?
	 * Finally, it will spit the sequences back out into a file. Fairly simple, right?
	 * And away we go!
	 * 
	 * Instructions: Reverse.java requires one argument: the Fasta file.
	 * 
	 */

	private static int numProtein = 0; //number of the protein in the file

	private static String filename, file; //the name of the original file
	private static PrintStream ps; //will be used to print to a specified file (Declared later)
	private static FileOutputStream fout; //like ps, will be used to output to a file
	private static FileInputStream fin; //Used to read in the file
	private static Scanner scan; //to scan through fin

	public static void main(String[] args) throws FileNotFoundException
	{
		if (args.length < 1)
		{
			System.out.println("Must input a filepath. Try again!");
		}
		else
		{
			// Quasi-constructor method to initialize everything and import the fasta file!
			// I keep wanting to write pasta. I really do.
			file = args[0]; //set for compatibility purposes
//			System.out.println(file.substring(file.length()-6, file.length()));
			if (!(file.substring(file.length()-5, file.length()).equalsIgnoreCase("fasta")))
			{
				System.out.println("Input file must be in the fasta format. Try again!");
			}
			else{
				filename = file.substring(0, file.length()-6);
				fin = new FileInputStream(file);
				fout = new FileOutputStream((filename + "_reverse_abridged.fasta"));
				ps = new PrintStream(fout);
				scan = new Scanner(fin);
				reverseFile();
			}
		}
	}

	public static int reverseFile()
	{
		//This is pretty much the "main" function. I only wrote it as I did because main was initially a constructor and blueprint classes make me happy.
		if (!(scan.hasNextLine()))
		{
			System.out.println("Empty file. Try again later.");
			return -2;
		}
		/*	
		 String aminoString = "";
		 if (!(currentLine.charAt(0) == '>'))
		 */
		String currentLine = scan.nextLine(); //eats up the first line.
		if (!(currentLine.charAt(0)=='>'))
		{
			//				System.out.println(scan.next());
			System.out.println("Improperly formatted file. Must start with \">\". Ask again later.");
			return -1;
		}
		//end of error checking for beginning of file. Now on to the file itself...

		else
		{
			while (scan.hasNextLine()) //this way, repeats until the end of the file
				//this while loop repeats for each section, i.e. each amino acid sequence
			{
				numProtein++;
				int lineInd = 0;
				String aminoString = "";
				//print the title line of this section
				ps.println(">REV_" + Integer.toString(numProtein) + " reversed"); //prints the title of the protein.
				/*			while (scan.hasNextLine() && !((currentLine = scan.nextLine()).charAt(0) == '>')) //checks for (and possibly retrieves) next line
				 */			
				while (scan.hasNextLine() && !((currentLine = scan.nextLine()).charAt(0) == '>'))
				{
					//currentLine = scan.nextLine();
					//creating the entire string of amino acids
					aminoString += currentLine;
				}
				//print the string in reverse order
				//System.out.println("Test!");
				for (int i = aminoString.length()-1; i>=0; i--)
				{
					//						i--;
					if (lineInd % 80 == 0 && lineInd > 0)
					{
						//breaks up the string for readability purposes (should be 80/line)
						ps.println();
						//							System.out.println();
					}
					ps.print(aminoString.charAt(i)); //prints the amino acid
					//						System.out.print(aminoString.charAt(i));
					lineInd++; //which column the character is in. Helps with readability.
				}
				ps.println();
				//					System.out.println();
				if (scan.hasNext())
				{
					currentLine = scan.nextLine();
				}
			}
			return 1;
		}
	}
}