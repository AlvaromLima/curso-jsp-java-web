/*
 Classe que faz busca no banco de dados o login e a senha e faz a validação 
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		
		//Prepara parametros para inserir login e senha no sql 
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		//Resultado do sql
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true;/*autenticado*/
		}
		
		return false;/*Não autenticado*/

	}

}
