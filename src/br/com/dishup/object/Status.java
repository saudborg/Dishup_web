package br.com.dishup.object;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object Status.
 * This object represents a code data that indicates a type of system's status for an user, as active, blocked and deleted.
 **********************************/
public class Status {
	
	/***********************
	 * Attributes
	 ***********************/
	private int idStatus;
	private String nomeStatus;
	private String descStatus;
	
	/***********************
	 * Constructor
	 ***********************/
	
	public Status(){
		
	}

	public Status(int idStatus, String nomeStatus, String descStatus) {
		super();
		this.idStatus = idStatus;
		this.nomeStatus = nomeStatus;
		this.descStatus = descStatus;
	}
	
	/***********************
	 * Getter Methods
	 ***********************/

	public int getIdStatus() {
		return idStatus;
	}

	public String getNomeStatus() {
		return nomeStatus;
	}

	public String getDescStatus() {
		return descStatus;
	}
}
