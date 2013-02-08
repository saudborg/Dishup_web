package br.com.dishup.object;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object Tipo de Culinaria.
 * This object represents a code data that indicates a type of food, as japanese food.
 **********************************/
public class TipoCulinaria {
	
	/***********************
	 * Attributes
	 ***********************/
	private int idTipoCulinaria;
	private String nomeTipoCulinaria;
	private String descTipoCulinaria;
	
	/***********************
	 * Constructor
	 ***********************/
	
	public TipoCulinaria(){
		
	}

	public TipoCulinaria(int idTipoCulinaria, String nomeTipoCulinaria,
			String descTipoCulinaria) {
		super();
		this.idTipoCulinaria = idTipoCulinaria;
		this.nomeTipoCulinaria = nomeTipoCulinaria;
		this.descTipoCulinaria = descTipoCulinaria;
	}
	
	/***********************
	 * Getter Methods
	 ***********************/

	public int getIdTipoCulinaria() {
		return idTipoCulinaria;
	}

	public String getNomeTipoCulinaria() {
		return nomeTipoCulinaria;
	}

	public String getDescTipoCulinaria() {
		return descTipoCulinaria;
	}
}
