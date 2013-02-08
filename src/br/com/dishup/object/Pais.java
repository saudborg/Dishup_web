package br.com.dishup.object;

/**
 * @version 1.0 Class responsible for encapsulate the Pais object
 * @since 21/01/2013
 * @author Lucas Biccio Ribeiro
 */
public class Pais {
	
	/***************************
	*         ATRIBUTOS 
	****************************/
	private int id;
	private String sigla;
	private String nome;
	
	/***************************
	*       CONSTRUTORES 
	****************************/
	public Pais() {
	}
	
	public Pais(int id, String sigla, String nome) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
	}
	
	/***************************
	*       METODOS GET 
	****************************/
	public int getId() {
		return id;
	}
	
	public String getSigla(){
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}
	
	/***************************
	*       METODOS OVERRIDE
	****************************/
	@Override
	public String toString() {
		return "PAIS: ID("+getId()+") SIGLA("+getSigla()+") NOME("+getNome()+")";
	}
	
	
}
