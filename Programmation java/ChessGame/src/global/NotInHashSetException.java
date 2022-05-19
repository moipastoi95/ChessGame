package global;

/**
 * Exception that alerts on the non-presence of an element in a HashSet
 *
 */
@SuppressWarnings("serial")
public class NotInHashSetException extends Exception {

	/**
	 * Default constructor
	 * 
	 * @param msg The message to display in case of error
	 */
	public NotInHashSetException(String msg) {
		super(msg);
	}

}
