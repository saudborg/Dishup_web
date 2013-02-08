package br.com.dishup.object;

/**************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object TipoUsuario.
 * This object represents a code data that indicates a type of system's user, as Consumidor or a Restaurante.
 *************************/
public class TipoUsuario {
	
	/***********************
	 * Attributes
	 ***********************/
	private int idTipoUsuario;
	private String nomeTipoUsuario;
	private String descTipoUsuario;
	
	/***********************
	 * Constructor
	 ***********************/
	
	public TipoUsuario(){
		
	}
	
	public TipoUsuario(int idTipoUsuario, String nomeTipoUsuario, String descTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
		this.nomeTipoUsuario = nomeTipoUsuario;
		this.descTipoUsuario = descTipoUsuario;
	}

	/***********************
	 * Getter Methods
	 ***********************/
	
	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public String getNomeTipoUsuario() {
		return nomeTipoUsuario;
	}

	public String getDescTipoUsuario() {
		return descTipoUsuario;
	}
}
