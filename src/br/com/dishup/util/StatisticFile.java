package br.com.dishup.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatisticFile {
	
	/***************************
	 * ATTRIBUTE
	 ***************************/
	
	private long start;
	private long end;
	private String executionDate;
	private String classExecuting;
	private String methodExecuting;
	private long numberOfRegisterFile;
	private long numberOfRegisterBase;//used to balance-line logic
	private long registerRead;
	private long registerWrite;
	private long registerDeleted;
	private String filePath;
	private String databaseTable;
	private boolean isBalanceLine;
	
	/***************************
	 * CONSTRUCTOR
	 ***************************/
	
	/*****************************
	 * Constructor of StaticFile Object
	 * @param object - Object that is using the statistic
	 * @param method - Method of the object that the statistic is running
	 * @param file - File is being used
	 *****************************/
	public StatisticFile(Object object, String method, File file, boolean isBalanceLine, String databaseTable) {
		this.start = 0;
		this.end = 0;
		this.numberOfRegisterFile = 0;
		this.numberOfRegisterBase = 0;
		this.registerRead = 0;
		this.registerWrite = 0;
		this.registerDeleted = 0;
		this.executionDate = setExecutionDate();
		this.classExecuting = object.getClass().getName();
		this.methodExecuting = method;
		this.filePath = file.getPath();
		this.isBalanceLine = isBalanceLine;
		this.databaseTable = databaseTable;
	}
	
	/***************************
	 * PUBLIC METHODS 
	 ***************************/
	
	/**************************
	 * Method responsible for initialize the statistic
	 **************************/
	public void start(){
		this.start = Calendar.getInstance().getTimeInMillis();
	}
	
	/**************************
	 * Method responsible for finalize the statistic
	 **************************/
	public void end(){
		this.end = Calendar.getInstance().getTimeInMillis();
	}
	
	/**************************
	 * Method responsible for increment by one the number of register that exists in a file
	 **************************/
	public void incrementNumberOfRegisterFile(){
		this.numberOfRegisterFile++;
	}
	
	/**************************
	 * Method responsible for set number of register that exists in a file
	 **************************/
	public void setNumberOfRegisterFile(int numberOfRegisterFile){
		this.numberOfRegisterFile = numberOfRegisterFile;
	}
	
	/**************************
	 * Method responsible for set number of register that exists in a file
	 **************************/
	public void setNumberOfRegisterBase(int numberOfRegisterBase){
		this.numberOfRegisterBase = numberOfRegisterBase;
	}
	
	/**************************
	 * Method responsible for increment by one the number of register that has been read from database or from a file
	 **************************/
	public void incrementRegisterRead(){
		this.registerRead++;
	}
	
	/**************************
	 * Method responsible for increment by one the number of register that has been writen in database or in a file
	 **************************/
	public void incrementRegisterWrite(){
		this.registerWrite++;
	}
	
	/**************************
	 * Method responsible for increment by one the number of register that has been deleted in database or in a file
	 **************************/
	public void incrementRegisterDeleted(){
		this.registerDeleted++;
	}
	
	/**************************
	 * Method responsible for return the number of register read
	 * @return numberOfRegister
	 **************************/
	public long getRegisterRead() {
		return registerRead;
	}
	
	/**************************
	 * Method responsible for return the number of register deleted
	 * @return numberOfRegister
	 **************************/
	public long getRegisterDeleted() {
		return registerDeleted;
	}
	
	/***************************
	 * PRIVATE METHODS 
	 ***************************/

	/**************************
	 * Method responsible for calculate the execution duration in seconds
	 * @return the execution duration in seconds
	 **************************/
	private int getDuration(){
		if(this.start > 0 && this.end > 0)
			return (int)(this.end - this.start)/100;
		else{
			System.out.println("START OR END NOT INITIALIZED.");
			return 0;
		}
	}
	
	/**************************
	 * Method responsible for get the system's date
	 * @return system's date
	 **************************/
	private String setExecutionDate(){
		Date today = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		String data = formatDate.format(today);
		return data;
	}
	
	/***************************
	*       METODOS OVERRIDE 
	****************************/
	
	/**************************
	 * Method for display the statistics values
	 **************************/
	@Override
	public String toString() {
		return
		"*------------------------------------------------------------------------------------------*\n"+
		"|                ESTATÍSTICA DE PROCESSAMENTO - DATA DE PROCESSAMENTO: "+executionDate+"          |\n"+
		"*------------------------------------------------------------------------------------------*\n"+
		"| FLAG SE PROCESSO É UM BALANCE-LINE.......: "+isBalanceLine+"\n"+
		"| CLASSE EXECUTADA.........................: "+this.classExecuting+"\n"+
		"| MÉTODO EXECUTADO.........................: "+this.methodExecuting+"\n"+
		"| CAMINHO ARQUIVO LIDO.....................: "+this.filePath+"\n"+
		"| TABELA DO BANCO DE DADOS.................: "+databaseTable+"\n"+
		"| QUANTIDADE TOTAL DE REGISTROS ARQUIVO....: "+numberOfRegisterFile+"\n"+
		"| QUANTIDADE TOTAL DE REGISTROS TABELA.....: "+numberOfRegisterBase+"\n"+
		"| QUANTIDADE DE REGISTROS LIDOS............: "+registerRead+"\n"+
		"| QUANTIDADE DE REGISTROS GRAVADOS.........: "+registerWrite+"\n"+
		"| QUANTIDADE DE REGISTROS DELETADOS........: "+registerDeleted+"\n"+
		"| DURAÇÃO DO PROCESSAMENTO EM SEG..........: "+getDuration()+"\n"+
		"*------------------------------------------------------------------------------------------*";
	}
	
	
}
