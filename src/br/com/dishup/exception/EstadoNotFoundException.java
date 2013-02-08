package br.com.dishup.exception;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the exception when an Estado does not exists in database
 *********************************/
public class EstadoNotFoundException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	/*****************
	 * Constructor
	 * @param message
	 ****************/
	public EstadoNotFoundException(String message){
		super(message);
	}
}
