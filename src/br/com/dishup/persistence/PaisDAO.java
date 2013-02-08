package br.com.dishup.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.PaisAlreadyExistException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.object.Pais;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Pais data access object 
 ****************************************/
public class PaisDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	
	/**********************************************
	 * Method responsible for insert on Pais table
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param pais - {@link Pais}
	 * @throws TableFieldTruncationException 
	 * @throws PaisAlreadyExistException 
	 * @throws TableFieldNullValueException 
	 * @throws TableFieldCheckException 
	 **********************************************/
	public void insert(Pais pais) throws TableFieldTruncationException, PaisAlreadyExistException, TableFieldNullValueException, TableFieldCheckException{
		String sql = "INSERT INTO pais(idpais, siglapais, nmpais) VALUES (?, ?, ?);";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, pais.getId());
			stmt.setString(2, String.valueOf(pais.getSigla()));
			stmt.setString(3, pais.getNome());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new PaisAlreadyExistException("Registro já existe no sistema: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela não aceita a inserção de valores nulos: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+pais.toString());
			else{
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
				System.out.println(e.getSQLState());
			}
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**********************************************
	 * Method responsible for select a Pais from table pais by sigla
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param sigla 
	 * @throws PaisNotFoundException 
	 **********************************************/
	public Pais selectBySigla(String sigla) throws PaisNotFoundException{
		String sql = "SELECT idpais, siglapais, nmpais FROM pais WHERE siglapais = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		ResultSet rs;
		Pais pais = new Pais();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setString(1, sigla);
			rs = stmt.executeQuery();
			if(rs.next())
				pais = new Pais(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new PaisNotFoundException("Pais com a sigla: "+ sigla +"não encontrado no sistema");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return pais;
	}
	
	/**********************************************
	 * Method responsible for select a Pais from table pais by id
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param id
	 * @throws PaisNotFoundException 
	 **********************************************/
	public Pais selectById(int id) throws PaisNotFoundException{
		String sql = "SELECT idpais, siglapais, nmpais FROM pais WHERE idpais = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		ResultSet rs;
		Pais pais = new Pais();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				pais = new Pais(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new PaisNotFoundException("Pais com o id: "+ id +" não encontrado no sistema");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return pais;
	}
	
	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link Pais}
	 * @throws EmptyTableException
	 *******************************************/
	public ArrayList<Pais> selectAllOrderById() throws EmptyTableException{
		String sql = "SELECT idpais, siglapais, nmpais FROM pais ORDER BY idpais;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		ArrayList<Pais> lista = new ArrayList<Pais>();
		PreparedStatement stmt;
		ResultSet rs;
		Pais pais = new Pais();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				pais = new Pais(rs.getInt(1), rs.getString(2), rs.getString(3));
				lista.add(pais);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Pais está vazia");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Pais from database by an Id
	 * @param id - Pais'id
	 **********************/
	public void deleteById(int id){
		String sql = "DELETE FROM pais WHERE idpais = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
