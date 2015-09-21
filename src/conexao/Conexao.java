package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static Connection sqlConn = null, sqlConnAux = null,
			sqlConnConsulta = null;
	private static Connection postgresConn = null, postgresConnAux = null;

	public static String SQL_SERVIDOR = "";
	public static String SQL_BANCO = "";
	public static String SQL_USUARIO = "";
	public static String SQL_SENHA = "";
	public static String SQL_INSTANCIA = "";
	public static String PostGres_SERVIDOR = "";
	public static String PostGres_BANCO = "";
	public static String PostGres_PORTA = "";
	public static String PostGres_USUARIO = "";
	public static String PostGres_SENHA = "";
	public static String instancia = "";

	// Conex�o SqlServer
	public static Connection getSqlConnection() {
		try {
			if (sqlConn == null || sqlConn.isClosed()) {
				if (!SQL_INSTANCIA.equals("")) {
					instancia = ";instance=" + SQL_INSTANCIA;
				}
				String url = "jdbc:jtds:sqlserver://" + SQL_SERVIDOR+""+instancia;
				String usuario = SQL_USUARIO;
				String senha = SQL_SENHA;
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				sqlConn = DriverManager.getConnection(url, usuario, senha);
				System.out.println("conectou " + SQL_BANCO);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return sqlConn;
	}

	// Conex�o Auxiliar SqlServer
	public static Connection getSqlConnectionAux() {
		try {
			if (sqlConnAux == null || sqlConnAux.isClosed()) {
				String url = "jdbc:jtds:sqlserver://" + SQL_SERVIDOR + "/"
						+ SQL_BANCO + ";instance=SQLEXPRESS";
				String usuario = SQL_USUARIO;
				String senha = SQL_SENHA;
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				sqlConnAux = DriverManager.getConnection(url, usuario, senha);
				System.out.println("conectou " + SQL_BANCO);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return sqlConnAux;
	}

	// Conex�o Postgres
	public static Connection getPostgresConnection() {
		try {
			if (postgresConn == null || postgresConn.isClosed()) {
				String url = "jdbc:postgresql://localhost:" + PostGres_PORTA
						+ "/" + PostGres_BANCO;
				String usuario = PostGres_USUARIO;
				String senha = PostGres_SENHA;
				Class.forName("org.postgresql.Driver");
				postgresConn = DriverManager.getConnection(url, usuario, senha);
				postgresConn.setAutoCommit(true);
				System.out.println("conectou Postgres");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
		return postgresConn;
	}

	// Conexao Auxiliar Postgres
	public static Connection getPostgresConnectionAux() {
		try {
			if (postgresConnAux == null || postgresConnAux.isClosed()) {
				String url = "jdbc:postgresql://localhost:" + PostGres_PORTA
						+ "/" + PostGres_BANCO;
				String usuario = PostGres_USUARIO;
				String senha = PostGres_SENHA;
				Class.forName("org.postgresql.Driver");
				postgresConnAux = DriverManager.getConnection(url, usuario,
						senha);
				postgresConnAux.setAutoCommit(true);
				System.out.println("conectou Postgres2");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
		return postgresConnAux;
	}
}