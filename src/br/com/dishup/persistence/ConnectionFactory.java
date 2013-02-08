package br.com.dishup.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Lucas Biccio Ribeiro
 * @since 05/04/2012
 * @version 1.0
 * Class responsible to construct the connections to the database. 
 * It follows the factory design pattern, and actually connect only to postgresSQL database 
 */
public class ConnectionFactory {

	/**
	 * Static object connection - singleton pattern
	 */
	private static Connection connectionPostgres;
	
	/**
	 * Postgres connection parameters
	 */
	private final String driver = "org.postgresql.Driver";
	private final String url = "jdbc:postgresql://localhost:5432/dishup";
	private final String user = "postgres";
	private final String password = "bi2404";
	
	/**
	 * Method who controls the connection though the connection pool
	 * @return {@link Connection}
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException
	{
		return connectionPool(driver, url, user, password);
	}
	
	/**
	 * method who controls the connection pools and return a valid connection
	 * @param driver - database driver
	 * @param url - database URL
	 * @param user - database user
	 * @param password - database password
	 * @return {@link Connection}
	 * @throws SQLException
	 */
	private Connection connectionPool(String driver, String url, String user, String password) throws SQLException
	{
		try{
			if(connectionPostgres == null)
				connectionPostgres = DriverManager.getConnection(url,user,password);
			return connectionPostgres;
		}
		catch (Exception e) {
			System.out.println("###### FALHOU AO SE CONECTAR #######");
			System.out.println("****** PARAMETROS DA CONEXAO  *******");
			System.out.println("*DRIVER: "+driver);
			System.out.println("*URL: "+url);
			System.out.println("*USER: "+user);
			System.out.println("*PASSWORD: "+password);
			System.out.println("####################################");
			throw new SQLException(e.getMessage());
		}
	}
}
