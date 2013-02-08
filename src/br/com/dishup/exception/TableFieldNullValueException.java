package br.com.dishup.exception;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the exception when trying to insert a null value in database when null is not permitted.
 *********************************/
public class TableFieldNullValueException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	/*****************
	 * Constructor
	 * @param message
	 ****************/
	public TableFieldNullValueException(String message){
		super(message);
	}
}
