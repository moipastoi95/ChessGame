package application;

@SuppressWarnings("serial")
public class NotInHashSetException extends Exception{
	public NotInHashSetException(String msg) {
		super(msg);
	}

}
