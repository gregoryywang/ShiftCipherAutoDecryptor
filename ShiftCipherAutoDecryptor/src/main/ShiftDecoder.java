/**
 * ShiftDecoder.java
 * Automatic decryption of shift cipher using frequency analysis.
 * 
 * @author Yong Yu Wang
 */
package main;

import java.util.Arrays;
import java.util.Scanner;

public class ShiftDecoder {

	/**
	 * The expected IOC for the natural English language.
	 */
	public static final double idealIndexOfCoincidence = 0.065;
	
	/**
	 * The inputed ciphertext.
	 */
	private static String ciphertext;

	/**
	 * Map of all letters in English to find during frequency analysis
	 */
	private static String indexes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * Percentage of occurrences of the English alphabet in natural language
	 */
	private static double[] normalLetterPercentages = {0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 
		0.061, 0.070, 0.002, 0.008, 0.040, 0.024, 0.067, 0.075, 0.019, 0.001, 0.060, 0.063, 0.097, 
		0.028, 0.010, 0.023, 0.001, 0.020, 0.001};
	
	/**
	 * Array of all 26 shifts stored as ShiftedText objects.
	 */
	private static ShiftedText[] arrayOfShifts = new ShiftedText[indexes.length()];

	/**
	 * Point of execution for this program
	 * @param args
	 */
	public static void main(String[] args) {
		input();
		shiftedTextBuilder();
		processShifts();
		ShiftedText result = selectBestResult();
		printResult(result);
	}
	
	/**
	 * Prints the final result found by the program.
	 * @param result	The ShiftedText object containing the correct shift.
	 */
	public static void printResult(ShiftedText result) {
		System.out.println("\nThe shift with the best index of coincidence value was found to be: \n"
				+ "Text: [" + result.getText() + "] \nShift Number: [" + result.getShiftNumber() 
				+ "] \nIndex of coincidence value: [" + result.getIndexOfCoincidence() +"]");
	}
	
	/**
	 * Prompts the user for input.
	 */
	public static void input() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Automatic Caesar (shift) Cipher Decoder V1\nBy Yong Yu Wang, TCSS 490 S14\n"
				+ "Input is case insensative but does not count special characters or numbers.\n"
				+ "Please ensure input is one contiguous string with no spaces. Check output for warnings.\n"
				+ "Please enter the ciphertext:");
		ciphertext = scan.nextLine().toUpperCase();
		scan.close();
	}
	
	/**
	 * Creates 26 ShiftedText objects containing the 26 shifts of the ciphertext and stores them in an array.
	 */
	public static void shiftedTextBuilder() {
		arrayOfShifts[0] = new ShiftedText(ciphertext, 0);
		for (int i = 1; i < arrayOfShifts.length; i++) {
			arrayOfShifts[i] = new ShiftedText(shiftByOne(arrayOfShifts[i - 1].getText()), i);
		}
	}
	
	/**
	 * Performs calculations on each of the 26 shifts and calculates their IOC values.
	 */
	public static void processShifts() {
		for (int i = 0; i < arrayOfShifts.length; i++) {
			ShiftedText current = arrayOfShifts[i];
			mapFrequencies(current);
			mapLetterPercentage(current);
			calculateIndexOfCoincidence(current);
			
			System.out.println("\nShift Number: [" + current.getShiftNumber() + "]\nText: [" + current.getText() 
					+ "]\nIndex of coincidence: [" + current.getIndexOfCoincidence() + "]");
			System.out.println("Letter count: " + Arrays.toString(current.getLetterCount()));
			System.out.println("Letter frequencies: " + Arrays.toString(current.getLetterPercentage()));
		}
	}
	
	/**
	 * Selects the shift with the closest matching IOC value to the desired IOC value.
	 * @return	The ShiftedText object with the closest results.
	 */
	public static ShiftedText selectBestResult() {
		
		ShiftedText bestShift = arrayOfShifts[0];
		double bestIOCValue = Math.abs(bestShift.getIndexOfCoincidence() - idealIndexOfCoincidence);
		for (int i = 1; i < arrayOfShifts.length; i++) {
			ShiftedText current = arrayOfShifts[i];
			double currentIOCValue = Math.abs(current.getIndexOfCoincidence() - idealIndexOfCoincidence);
			if (currentIOCValue < bestIOCValue) {
				bestShift = arrayOfShifts[i];
				bestIOCValue = currentIOCValue;
			}
		}
		return bestShift;
	}

	/**
	 * Finds the number of occurrences of each letter of the alphabet for a given shift.
	 * @param input	The ShiftedText object containing the shift to perform analysis on.
	 */
	public static void mapFrequencies(ShiftedText input) {
		
		int[] letterCount = new int[indexes.length()];
		
		//Loop through the sentence looking for matches.
		for (int i = 0; i < input.getText().length(); i++) {
			//This will get the index in the array, if it's a character we are tracking
			int index = indexes.indexOf(input.getText().charAt(i));

			//If it's not a character we are tracking, indexOf returns -1, so skip those.
			if (index < 0) {
				System.out.println("Alert: Input contains character that is not a letter."
						+ " Invalid character is: [" + ciphertext.charAt(i) + "]");
				continue;
			}
			letterCount[index]++;
		}
		input.setLetterCount(letterCount);
	}
	
	/**
	 * Finds the percentage of occurrences of each letter of the alphabet for a given shift.
	 * @param input	The ShiftedText object containing the shift to perform analysis on.
	 */
	public static void mapLetterPercentage(ShiftedText input) {
		int[] letterCount = input.getLetterCount();
		double[] letterPercentage = new double[letterCount.length];
		for (int i = 0; i < letterPercentage.length; i++) {
			letterPercentage[i] = (double) letterCount[i] / ciphertext.length();
		}
		input.setLetterPercentage(letterPercentage);
	}
	
	/**
	 * Displays the frequencies found for a shift.
	 * DEPRECIATED
	 * @param input	The ShiftedText object containing the shift to display frequencies for.
	 */
	public static void displayFrequencies(ShiftedText input) {
		
		int[] letterCount = input.getLetterCount();

		System.out.println("Frequency analysis complete."
				+ "\nBelow are the found letters and their occurrences:");
		for (int i = 0; i < letterCount.length; i++) {
		    if (letterCount[i] < 1)
		        continue;
		    System.out.println(String.format("%s (%d)", indexes.charAt(i), letterCount[i]));        
		}
	}
	
	/**
	 * Calculates the IOC for each shift by multiplying the letter percentages for a given shift 
	 * by the actual percentages found in natural language and summing these values.
	 * @param input	The ShiftedText object containing the shift to calculate the IOC value for.
	 */
	public static void calculateIndexOfCoincidence(ShiftedText input) {
		double[] letterPercentage = input.getLetterPercentage();
		double[] temp = new double[letterPercentage.length];
		double indexOfCoincidence = 0;

		for (int i = 0; i < letterPercentage.length; i++) {
			temp[i] = letterPercentage[i] * normalLetterPercentages[i];
		}
		
		for (int i = 0; i < temp.length; i++) {
			indexOfCoincidence += temp[i];
		}
		input.setIndexOfCoincidence(indexOfCoincidence);
	}
	
	/**
	 * Given a String, shift each character in the String one over.
	 * Works only on Strings with capitalized letters from A to Z.
	 * @param input	The String to shift letters over.
	 * @return	The resulting String with letters shifted by one.
	 */
	public static String shiftByOne(String input) {
		StringBuilder shift = new StringBuilder(input);
		for (int i = 0; i < input.length(); i++) {
			shift.setCharAt(i, (char) (((input.charAt(i) - 'A' + 1) % 26) + 'A'));
		}
		return shift.toString();
	}
}
