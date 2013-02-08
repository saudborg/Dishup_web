package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoAlreadyExistException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.Estado;
import br.com.dishup.persistence.EstadoDAO;
import br.com.dishup.persistence.PaisDAO;
import br.com.dishup.util.EstadoComparator;
import br.com.dishup.util.StatisticFile;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the Estado in system's environment.
 * This class has a main method that should be run only when needs to load Estado
 * To run these method must exist one file with an specific layout into geographic path.
 **********************************/
public class LoadEstadoEnvironment {
	
	/*******************************
	 * Method responsible for load the Estado on system's environment by a balance-line logic
	 * @param filePath
	 *******************************/
	public void carregaEstado(String filePath){
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadEstadoEnvironment(), "carregaEstado(String filePath)", file, true, "dishup.estado");
		statistic.start();
		ArrayList<Estado> listFile = new ArrayList<Estado>();
		EstadoDAO estadoDAO = new EstadoDAO();
		try{
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new EstadoComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<Estado> listBase = new ArrayList<Estado>();;
			try {
				listBase = estadoDAO.selectAllOrderById();
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							estadoDAO.deleteById(listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							estadoDAO.insert(listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						estadoDAO.deleteById(listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						estadoDAO.insert(listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (EmptyTableException e){
				statistic = new StatisticFile(new LoadEstadoEnvironment(), "carregaEstado(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						estadoDAO.insert(listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch (TableFieldTruncationException e1){
						System.out.println(e1.getMessage());
					}catch (EstadoAlreadyExistException e1){
						System.out.println(e1.getMessage());
					}catch (TableFieldNullValueException e1){
						System.out.println(e1.getMessage());
					}catch (TableFieldCheckException e1){
						System.out.println(e1.getMessage());
					}catch (TableForeignKeyViolationException e1) {
						System.out.println(e1.getMessage());
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (TableFieldTruncationException e){
				System.out.println(e.getMessage());
			}catch (TableFieldNullValueException e){
				System.out.println(e.getMessage());
			}catch (TableFieldCheckException e){
				System.out.println(e.getMessage());
			}catch (EstadoAlreadyExistException e) {
				System.out.println(e.getMessage());
			}catch (TableForeignKeyViolationException e) {
				System.out.println(e.getMessage());
			}
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (FileEmptyException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/********************************
	 * Method responsible for create an arrayList with the elements in file
	 * @param filePath
	 * @return {@link ArrayList} of {@link Estado}
	 * @throws FileNotFoundException
	 * @throws FileEmptyException 
	 ********************************/
	private ArrayList<Estado> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException{
		ArrayList<Estado> listaEstado = new ArrayList<Estado>();
		PaisDAO paisDAO = new PaisDAO();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				listaEstado.add(new Estado(Integer.valueOf(parms[0]), parms[1], parms[2], paisDAO.selectBySigla(parms[3])));
			}catch (PaisNotFoundException e) {
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println("****** EXCE‚ÌO NÌO ESPERADA *******");
				System.out.println(e.getMessage());
				System.out.println("Registro do arquivo: "+var);
				e.printStackTrace();
			}
		}
		scanner.close();
		return listaEstado;
	}
}
