package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	//conectar servidor local
	private static String banco = "jdbc:postgresql://localhost:5433/curso-jsp?autoReconnect=true";
	private static String user = "postgres";	
	private static String senha = "admin";
	
	/*
	//conectar servidor heroku
	private static String banco = 
		"jdbc:postgresql://ec2-52-207-90-231.compute-1.amazonaws.com:5432/d2bpd41i4sfm6b?sslmode=require&autoReconnect=true";
	private static String user = "hqqanefkjxravz";
	private static String senha = "964a41e912434c4f9229d61b4603a9d8bb2217ab8e143c752d2f0ddb4f83c99b";
	*/
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	//Para manter a garantia de uma conexão
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {/*qdo tiver uma instância vai conectar*/
		conectar();
	}
	
	private static void conectar() {
		
		System.out.println("Abrindo conexão com o banco de dados...");

		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); /*Carrega o driver de coneão com o banco*/
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);/*para não efetuar alterações no banco sem nosso comando*/

				System.out.println("Conexão com o banco de dados Ok...");
				
			}
		}catch (Exception e){
			e.printStackTrace(); /*Mostra qq erro no momento de conectar*/
		}
		
	}

}
