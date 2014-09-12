/**
 * ShiftedText.java
 * Data class storing information of a shift.
 * 
 * @author Yong Yu Wang
 */
package main;

public class ShiftedText {

	/**
	 * The text of the shift.
	 */
	private String shiftedText;

	/**
	 * The number of shifts from the original ciphertext.
	 */
	private int shiftNumber;

	/**
	 * The calculated IOC value of this shift.
	 */
	private double indexOfCoincidence;

	/**
	 * Map of all letters in English to find during frequency analysis
	 */	private static String indexes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	 /**
	  * The number of occurrences of each letter in this shift.
	  */
	 private static int[] letterCount = new int[indexes.length()];

	 /**
	  * The percentage of occurrences of each letter in this shift.
	  */
	 private static double[] letterPercentage = new double[indexes.length()];

	 /**
	  * Constructor for this class.
	  * @param shiftedText	The text of the shift.
	  * @param shiftNumber	The number of shifts this shift is from the original ciphertext.
	  */
	 public ShiftedText(String shiftedText, int shiftNumber) {
		 this.shiftedText = shiftedText;
		 this.shiftNumber = shiftNumber;
	 }

	 /**
	  * Gets the shifted text.
	  * @return	the shifted text.
	  */
	 public String getText() {
		 return shiftedText;
	 }

	 /**
	  * Gets the IOC value calculated for this shift.
	  * @return	the calculated IOC value for this shift.
	  */
	 public double getIndexOfCoincidence() {
		 return indexOfCoincidence;
	 }

	 /**
	  * Gets the calculated letter occurrences count for all letters in this shift.
	  * @return	the array containing the letter counts.
	  */
	 public int[] getLetterCount() {
		 return letterCount;
	 }

	 /**
	  * Gets the shift number for this shift.
	  * @return	the shift number for this shift.
	  */
	 public int getShiftNumber() {
		 return shiftNumber;
	 }

	 /**
	  * Gets the percentages of letter occurrences for all letters in this shift.
	  * @return
	  */
	 public double[] getLetterPercentage() {
		 return letterPercentage;
	 }

	 /**
	  * Sets the array containing the calculated letter occurrences count for all letters in this shift.
	  * @param input	The new array of letter occurrences.
	  */
	 public void setLetterPercentage(double[] input) {
		 letterPercentage = input;
	 }

	 /**
	  * Sets the IOC value for this shift.
	  * @param value	The new IOC value.
	  */
	 public void setIndexOfCoincidence(double value) {
		 indexOfCoincidence = value;
	 }

	 /**
	  * Sets the array containing the percentages of letter occurrences for all letters in this shift.
	  * @param input	The new array of letter percentages.
	  */
	 public void setLetterCount(int[] input) {
		 letterCount = input;
	 }
}
