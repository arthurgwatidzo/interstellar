package za.co.interstellar.exception;
/**
 * @Author Arthur Gwatidzo  
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public class InterstellarApplicationException extends Exception{

	public InterstellarApplicationException() {
		super();
	}
	
	
	public InterstellarApplicationException(Throwable cause) {
		super(cause);
	}
	
	
	public InterstellarApplicationException(String message) {
		super(message);
	}
	
	public InterstellarApplicationException(String message, Throwable cause) {
		super(message,cause);
	}
	
}
