

/**
 * Validate a string against a set of rules for example : an integer between 0
 * and 100... workaround to extend the string class
 * 
 * To use "classical" methods of String class use: mysuperStringObject.str (
 * mysuperStringObject.str.contains("something"); )
 * 
 * @author daniel.conil
 *
 */
public class DclString {

	// use a global object
	private GblVars TheGblVars = GblVars.getInstance();

	private Boolean correct = null;
	public String str;
	private Integer str2int = null;
	private String strInfo = "no action has been recorded";

	/*
	 * Constructors
	 */

	/**
	 * @param input
	 */
	public DclString(String str) {
		this.str = str;
	}

	/*
	 * Getters & Setters
	 */

	/**
	 * @return the result of the last operation
	 */
	public Boolean isCorrect() {
		return correct;
	}

	/**
	 * @return the result of the last conversion to integer
	 */
	public Integer str2int() {
		return str2int;
	}

	/**
	 * @return the meta information of the last operation (error, comments,
	 *         warning...)
	 */
	public String getStrInfo() {
		return strInfo;
	}

	/*
	 * Methods
	 */

	/**
	 * 
	 * @return true if it is an integer
	 */
	public boolean isInteger() {

		boolean theResult = false;
		int tmp; // working variable

		try {
			// the String to int conversion happens here
			tmp = Integer.parseInt(this.str.trim());
			theResult = true;

		} catch (NumberFormatException nfe) {
			theResult = false;
			this.strInfo = "the string " + this.str + " is not an integer";
			TheGblVars.echoDebug(2, "NumberFormatException: " + nfe.getMessage());
		}

		return theResult;
	}

	/**
	 * 
	 * @param min : if min > max then min <-> max
	 * @param max
	 * @return true if it is an integer and in the range min, max
	 */
	public boolean isInteger(int min, int max) {

		boolean theResult = false;
		int tmp; // working variable

		if (min > max) {
			// swap min and max
			tmp = min;
			min = max;
			max = tmp;
		}

		theResult = isInteger();
		if (theResult == true) {
			if ((this.str2int < min) || (this.str2int > max)) {
				// out of range
				theResult = false;
				this.strInfo = "the integer " + this.str + " is not in the range " + min + " ; " + max;
			}
		}
		return theResult;
	}

	/**
	 * 
	 * @return the value of the string, null if the string is not a valid number
	 *         such as "45ty67"
	 */
	public Integer toInteger() {

		Integer theResult = null;

		try {
			// the String to int conversion happens here
			theResult = Integer.parseInt(this.str.trim());
	
		} catch (NumberFormatException nfe) {
			theResult = null;
			this.strInfo = "the string " + this.str + " is not an integer";
			TheGblVars.echoDebug(2, "NumberFormatException: " + nfe.getMessage());
		}

		return theResult;
	}

}
