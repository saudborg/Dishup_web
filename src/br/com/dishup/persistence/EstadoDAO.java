package br.com.dishup.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoAlreadyExistException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.Estado;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Estado data access object 
 ****************************************/
public class EstadoDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	/**********************************************
	 * Method responsible for insert the estado values in the database
	 * @since 02/02/2013
	 * @param estado {@link Estado}
	 * @throws TableFieldTruncationException
	 * @throws EstadoAlreadyExistException
	 * @throws TableFieldNullValueException
	 * @throws TableFieldCheckException
	 * @throws TableForeignKeyViolationException
	 **********************************************/
	public void insert(Estado estado) throws TableFieldTruncationException, EstadoAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, TableForeignKeyViolationException{
		String sql = "INSERT INTO estado (idestado, siglaestado, nmestado, idpais) VALUES (?, ?, ?, ?);";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, estado.getId());
			stmt.setString(2, estado.getSigla());
			stmt.setString(3, estado.getNome());
			stmt.setInt(4, estado.getPais().getId());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new EstadoAlreadyExistException("Registro já existe no sistema: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela não aceita a inserção de valores nulos: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Chave estrangeira não existe na tabela pai: "+estado.toString());
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
	
	/**********************************
	 * Method responsible for select an Estado for database by an sigla value
	 * @since 02/02/2013
	 * @param sigla - the Estado's sigla
	 * @return {@link Estado}
	 * @throws EstadoNotFoundException
	 ***********************************/
	public Estado selectBySigla(String sigla) throws EstadoNotFoundException{
		String sql = "SELECT idestado, siglaestado, nmestado, idpais FROM estado WHERE siglaestado = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		ResultSet rs;
		Estado estado = new Estado();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setString(1, sigla);
			rs = stmt.executeQuery();
			if(rs.next())
				estado = new Estado(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(rs.getInt(4)));
			else
				throw new EstadoNotFoundException("Estado com a sigla: "+sigla+" não encontrado.");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (PaisNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Estado: "+estado.toString());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return estado;
	}
	
	/**********************************
	 * Method responsible for select an Estado from database by an id value
	 * @since 02/02/2013
	 * @param id
	 * @return {@link Estado}
	 * @throws EstadoNotFoundException
	 ***********************************/
	public Estado selectById(int id) throws EstadoNotFoundException{
		String sql = "SELECT idestado, siglaestado, nmestado, idpais FROM estado WHERE idestado = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		ResultSet rs;
		Estado estado = new Estado();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				estado = new Estado(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(rs.getInt(4)));
			else
				throw new EstadoNotFoundException("Estado com o id: "+id+" não encontrado.");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (PaisNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Estado: "+estado.toString());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return estado;
	}

	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link Estado}
	 * @throws EmptyTableException
	 *******************************************/
	public ArrayList<Estado> selectAllOrderById() throws EmptyTableException {
		String sql = "SELECT idestado, siglaestado, nmestado, idpais FROM estado ORDER BY idestado";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		ArrayList<Estado> lista = new ArrayList<Estado>();
		PreparedStatement stmt;
		ResultSet rs;
		Estado estado = new Estado();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				estado = new Estado(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(rs.getInt(4)));
				lista.add(estado);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Estado está vazia");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (PaisNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Estado from database by an Id
	 * @param id - Estados's id
	 **********************/
	public void deleteById(int id){
		String sql = "DELETE FROM estado WHERE idestado = ?;";
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
