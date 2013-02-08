package br.com.dishup.exception;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the exception when trying to insert a value and this value does not exist in a daughter table (Foreign Key Violation).
 *********************************/
public class TableForeignKeyViolationException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	/*****************
	 * Constructor
	 * @param message
	 ****************/
	public TableForeignKeyViolationException(String message){
		super(message);
	}
}
