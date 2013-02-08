package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.exception.PaisAlreadyExistException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.object.Pais;
import br.com.dishup.persistence.PaisDAO;
import br.com.dishup.util.PaisComparator;
import br.com.dishup.util.StatisticFile;


/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the Pais in system's environment.
 * This class has a main method that should be run only when needs to load Pais
 * To run these method must exist one file with an specific layout into geographic path.
 **********************************/
public class LoadPaisEnvironment {

	/***********************************
	 * Method responsible for load the Pais on system's environment by a balance-line logic
	 * @param filePath - source of file
	 ***********************************/
	public void carregaPais(String filePath){
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadPaisEnvironment(), "carregaPais(String filePath)", file, true, "dishup.pais");
		statistic.start();
		ArrayList<Pais> listFile = new ArrayList<Pais>();
		PaisDAO paisDAO = new PaisDAO();
		try{
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new PaisComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<Pais> listBase = new ArrayList<Pais>();;
			try {
				listBase = paisDAO.selectAllOrderById();
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							paisDAO.deleteById(listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							paisDAO.insert(listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						paisDAO.deleteById(listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						paisDAO.insert(listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (EmptyTableException e){
				statistic = new StatisticFile(new LoadPaisEnvironment(), "carregaPais(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						paisDAO.insert(listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch (TableFieldTruncationException e1){
						System.out.println(e1.getMessage());
					}catch (PaisAlreadyExistException e1){
						System.out.println(e1.getMessage());
					}catch (TableFieldNullValueException e1){
						System.out.println(e1.getMessage());
					}catch (TableFieldCheckException e1){
						System.out.println(e1.getMessage());
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (TableFieldTruncationException e){
				System.out.println(e.getMessage());
			}catch (PaisAlreadyExistException e){
				System.out.println(e.getMessage());
			}catch (TableFieldNullValueException e){
				System.out.println(e.getMessage());
			}catch (TableFieldCheckException e){
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
	 * @return {@link ArrayList} of {@link Pais}
	 * @throws FileNotFoundException
	 * @throws FileEmptyException 
	 ********************************/
	private ArrayList<Pais> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException{
		ArrayList<Pais> listaPais = new ArrayList<Pais>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				listaPais.add(new Pais(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				System.out.println("****** EXCE‚ÌO NÌO ESPERADA *******");
				System.out.println(e.getMessage());
				System.out.println("Registro do arquivo: "+var);
				e.printStackTrace();
			}
		}
		scanner.close();
		return listaPais;
	}
}
