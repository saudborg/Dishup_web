package br.com.dishup.object;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 21/01/2013
 * @version 1.0 Class responsible for encapsulate the methods of Estado object (geographic organization)
 *********************************/
public class Estado {

	/***************************
	*         ATRIBUTOS 
	****************************/
	private int id;
	private String sigla;
	private String nome;
	private Pais pais;
	
	/***************************
	*       CONSTRUTORES 
	****************************/
	public Estado() {
	}
	
	public Estado(int id, String sigla, String nome, Pais pais) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.pais = pais;
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

	public Pais getPais() {
		return pais;
	}
	
	/***************************
	*       METODOS OVERRIDE 
	****************************/
	@Override
	public String toString() {
		return "ESTADO: ID("+getId()+") SIGLA("+getSigla()+") NOME("+getNome()+") PAIS("+getPais().toString()+")";
	}
}
