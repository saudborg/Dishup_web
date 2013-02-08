package br.com.dishup.environment;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the geographic components at system's environment.
 **********************************/
public class CarregaAreaGeografica {
	
	public static void main(String[] args) {
		
		String filePathPais = "geographic//pais.txt";
		String filePathEstado = "geographic//estado.txt";
		String filePathCidade = "geographic//cidade.txt";
		
		LoadPaisEnvironment loadPaisEnvironment = new LoadPaisEnvironment();
		loadPaisEnvironment.carregaPais(filePathPais);
		
		LoadEstadoEnvironment loadEstadoEnvironment = new LoadEstadoEnvironment();
		loadEstadoEnvironment.carregaEstado(filePathEstado);
		
		LoadCidadeEnvironment loadCidadeEnvironment = new LoadCidadeEnvironment();
		loadCidadeEnvironment.carregaCidade(filePathCidade);
	}
	
}
