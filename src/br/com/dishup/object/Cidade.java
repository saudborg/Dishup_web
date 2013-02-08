package br.com.dishup.object;

/**
 * @version 1.0 Class responsible for encapsulate the Cidade object
 * @author Lucas Biccio Ribeiro
 * @since 21/01/2013
 */
public class Cidade {
	
	/***************************
	*       ATRIBUTOS 
	****************************/
	private int id;
	private String nome;
	private Estado estado;
	private Pais pais;
	
	/***************************
	*       CONSTRUTORES 
	****************************/
	public Cidade() {
	}
	
	public Cidade(int id, String nome, Estado estado, Pais pais) {
		this.id = id;
		this.nome = nome;
		this.estado = estado;
		this.pais = pais;
	}

	/***************************
	*       METODOS GET 
	****************************/
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public Pais getPais() {
		return pais;
	}
	
	/***************************
	*       METODOS OVERRIDE 
	****************************/
	@Override
	public String toString() {
		return "CIDADE: ID("+getId()+") NOME("+getNome()+") ESTADO("+getEstado().toString()+") PAIS("+getPais().toString()+")";
	}
}
