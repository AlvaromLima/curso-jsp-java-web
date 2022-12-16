package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {

	private Connection connection;

	private DAOUserRepository daoUserRepository = new DAOUserRepository();
	
	public DAOTelefoneRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public List<ModelTelefone> listFone(Long idUserPai) throws Exception {

		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, idUserPai);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelTelefone modelTelefone = new ModelTelefone();

			modelTelefone.setId(resultSet.getLong("id"));;
			modelTelefone.setNumero(resultSet.getString("numero"));
			modelTelefone.setUsuario_cad_id(daoUserRepository.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUserRepository.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
			
		}

		return retorno;
		
	}
	
	public void gravaTelefone(ModelTelefone modelTelefone) throws Exception {
		
		String sql = "insert into telefone(numero, usuario_pai_id, usuario_cad_id) values (?,?,?);";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, modelTelefone.getNumero());
		preparedSql.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedSql.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		preparedSql.execute();
		connection.commit();
	}

	public void deleteFone(Long id) throws Exception {
		
		String sql = "delete from telefone where id=?;";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setLong(1, id);
		
		preparedSql.executeUpdate();
		connection.commit();
	}

	public boolean existeFone(String fone, Long idUser) throws Exception {
		
		String sql = "select count(1) > 0 as existe from telefone where usuario_pai_id = ? and numero = ?";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setLong(1, idUser);
		preparedSql.setString(2, fone);

		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		resultSet.next();
		
		return resultSet.getBoolean("existe");
		
	}
}
