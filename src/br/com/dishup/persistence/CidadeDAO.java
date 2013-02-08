package br.com.dishup.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.CidadeAlreadyExistException;
import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.Cidade;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Cidade data access object 
 ****************************************/
public class CidadeDAO {

	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	/***********************************************
	 * Method responsible for insert the cidade's value in database
	 * @param cidade {@link Cidade}
	 * @throws TableFieldTruncationException
	 * @throws CidadeAlreadyExistException
	 * @throws TableFieldNullValueException
	 * @throws TableFieldCheckException
	 * @throws TableForeignKeyViolationException
	 **********************************************/
	public void insert(Cidade cidade) throws TableFieldTruncationException, CidadeAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, TableForeignKeyViolationException{
		String sql = "INSERT INTO cidade(idcidade, nmcidade, idpais, idestado) VALUES (?, ?, ?, ?);";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, cidade.getId());
			stmt.setString(2, cidade.getNome());
			stmt.setInt(3, cidade.getPais().getId());
			stmt.setInt(4, cidade.getEstado().getId());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new CidadeAlreadyExistException("Registro já existe no sistema: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela não aceita a inserção de valores nulos: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Chave estrangeira não existe na tabela pai: "+cidade.toString());
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
	
	/****************************************
	 * Method responsible for select a Cidade from database by an id value
	 * @since 02/02/2013
	 * @param id
	 * @return {@link Cidade}
	 * @throws CidadeNotFoundException
	 ***************************************/
	public Cidade selectById(int id) throws CidadeNotFoundException{
		String sql = "SELECT idcidade, nmcidade, idpais, idestado FROM cidade WHERE idcidade = ?;";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		PreparedStatement stmt;
		ResultSet rs;
		Cidade cidade = new Cidade();
		PaisDAO paisDAO = new PaisDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				cidade = new Cidade(rs.getInt(1), rs.getString(2), estadoDAO.selectById(rs.getInt(3)), paisDAO.selectById(rs.getInt(3)));
			else
				throw new CidadeNotFoundException("Cidade com o id: "+id+" não encontrada.");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (PaisNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Cidade: "+cidade.toString());
		}catch (EstadoNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Cidade: "+cidade.toString());
		}catch (Exception e) {
			System.out.println("****** EXCEÇÃO NÃO ESPERADA *******");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return cidade;
	}
	
	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link Cidade}
	 * @throws EmptyTableException
	 *******************************************/
	public ArrayList<Cidade> selectAllOrderById() throws EmptyTableException {
		String sql = "SELECT idcidade, nmcidade, idpais, idestado FROM cidade ORDER BY idcidade";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		ArrayList<Cidade> lista = new ArrayList<Cidade>();
		PreparedStatement stmt;
		ResultSet rs;
		Cidade cidade = new Cidade();
		PaisDAO paisDAO = new PaisDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		try{
			stmt = connectionFactory.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				cidade = new Cidade(rs.getInt(1), rs.getString(2), estadoDAO.selectById(rs.getInt(4)), paisDAO.selectById(rs.getInt(3)));
				lista.add(cidade);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Cidade está vazia");
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}catch (EstadoNotFoundException e) {
			System.out.println(e.getMessage());
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
	 * Method responsible for delete a Cidade from database by an Id
	 * @param id - Cidade's id
	 **********************/
	public void deleteById(int id){
		String sql = "DELETE FROM cidade WHERE idcidade = ?;";
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
