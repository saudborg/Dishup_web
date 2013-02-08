package br.com.dishup.exception;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the exception when a check rule is violated when inserting a data in database
 *********************************/
public class TableFieldCheckException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	/*****************
	 * Constructor
	 * @param message
	 ****************/
	public TableFieldCheckException(String message){
		super(message);
	}
}
