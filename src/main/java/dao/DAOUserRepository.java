package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUserRepository {

	private Connection connection;

	public DAOUserRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin modelLogin, Long userLogado) throws Exception {

		if ( modelLogin.isNovo() ) { /* Gravar um novo */

			String sql = "insert into model_login(login, senha, nome, email, usuario_id, perfil, "
					+ "sexo, cep, logradouro, bairro, localidade, uf, numero, complemento, "
					+ "datanascimento, rendamensal) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, modelLogin.getLogin());
			preparedSql.setString(2, modelLogin.getSenha());
			preparedSql.setString(3, modelLogin.getNome());
			preparedSql.setString(4, modelLogin.getEmail());
			preparedSql.setLong(5, userLogado);
			preparedSql.setString(6, modelLogin.getPerfil());
			preparedSql.setString(7, modelLogin.getSexo());
			preparedSql.setString(8, modelLogin.getCep());
			preparedSql.setString(9, modelLogin.getLogradouro());
			preparedSql.setString(10, modelLogin.getBairro());
			preparedSql.setString(11, modelLogin.getLocalidade());
			preparedSql.setString(12, modelLogin.getUf());
			preparedSql.setString(13, modelLogin.getNumero());
			preparedSql.setString(14, modelLogin.getComplemento());
			preparedSql.setDate(15, modelLogin.getDataNascimento());
			preparedSql.setDouble(16, modelLogin.getRendaMensal());
			
			preparedSql.execute();
			connection.commit();

			if ( modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty()) {

				sql = "update model_login set fotouser=?, extensaofotouser=? where login=?;";
				
				preparedSql = connection.prepareStatement(sql);
				preparedSql.setString(1, modelLogin.getFotoUser());
				preparedSql.setString(2, modelLogin.getExtensaoFotoUser());
				preparedSql.setString(3, modelLogin.getLogin());

				preparedSql.execute();
				connection.commit();
				
			}
		}else { /*Regrava no banco*/

			String sql = "update model_login set login=?, senha=?, nome=?, email=?, perfil=?, "
					+ "sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, " + 
		             " complemento= ?, datanascimento=?, rendamensal=? where id = " + modelLogin.getId() + ";";
			
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, modelLogin.getLogin());
			preparedSql.setString(2, modelLogin.getSenha());
			preparedSql.setString(3, modelLogin.getNome());
			preparedSql.setString(4, modelLogin.getEmail());
			preparedSql.setString(5, modelLogin.getPerfil());
			preparedSql.setString(6, modelLogin.getSexo());
			preparedSql.setString(7, modelLogin.getCep());
			preparedSql.setString(8, modelLogin.getLogradouro());
			preparedSql.setString(9, modelLogin.getBairro());
			preparedSql.setString(10, modelLogin.getLocalidade());
			preparedSql.setString(11, modelLogin.getUf());
			preparedSql.setString(12, modelLogin.getNumero());
			preparedSql.setString(13, modelLogin.getComplemento());
			preparedSql.setDate(14, modelLogin.getDataNascimento());
			preparedSql.setDouble(15, modelLogin.getRendaMensal());

			preparedSql.executeUpdate();
			connection.commit();

			if ( modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty()) {

				sql = "update model_login set fotouser=?, extensaofotouser=? where id=?;";
				
				preparedSql = connection.prepareStatement(sql);
				preparedSql.setString(1, modelLogin.getFotoUser());
				preparedSql.setString(2, modelLogin.getExtensaoFotoUser());
				preparedSql.setLong(3, modelLogin.getId());

				preparedSql.executeUpdate();
				connection.commit();
				
			}

		}

		return this.consultaUsuario(modelLogin.getLogin(), userLogado);

	}

	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where useradmin is false and usuario_id = " + userLogado + ";";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		resultSet.next();/*Pra entrar nos resultados do sql*/
		
		Double totalCadastrados = resultSet.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = totalCadastrados / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
		
	}
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and "
				+ "usuario_id = " + userLogado + " order by nome offset " + offset + " limit 5;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	

			retorno.add(modelLogin);
			
		}

		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and "
				+ "usuario_id = " + userLogado + " order by nome limit 5;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	

			retorno.add(modelLogin);
			
		}

		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and "
				+ "usuario_id = " + userLogado + ";";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);
			
		}

		return retorno;
		
	}

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and "
				+ "usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ?;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedSql.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));

			retorno.add(modelLogin);
			
		}

		return retorno;
		
	}
	
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and "
				+ "useradmin is false and usuario_id = ?;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, "%" + nome + "%");
		preparedSql.setLong(2, userLogado);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		resultSet.next();/*Pra entrar nos resultados do sql*/
		
		Double totalCadastrados = resultSet.getDouble("total");
		Double porPagina = 5.0;
		Double pagina = totalCadastrados / porPagina;
		Double resto = pagina % 2;

		if (resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
		
	}

	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offSet) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and "
				+ "useradmin is false and usuario_id = ? order by nome offset " + offSet  
				+ " limit 5;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, "%" + nome + "%");
		preparedSql.setLong(2, userLogado);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email").trim());
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login").trim());
			modelLogin.setNome(resultSet.getString("nome").trim());
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	

			retorno.add(modelLogin);
		
		}
		
		return retorno;
		
	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
	
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and "
				+ "useradmin is false and usuario_id = ? order by nome limit 5;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, "%" + nome + "%");
		preparedSql.setLong(2, userLogado);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			
			//Para cada linha, temos que iniciar o objeto para depois retornar
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultSet.getString("email").trim());
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login").trim());
			modelLogin.setNome(resultSet.getString("nome").trim());
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	

			retorno.add(modelLogin);
		
		}
		
		return retorno;
		
	}
	
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		
		//inicia o objeto para depois retornar
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper(?) and useradmin is false and usuario_id = " + userLogado + ";";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, login);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* Se tem resultado */
			modelLogin.setId(resultSet.getLong("id"));	
			modelLogin.setNome(resultSet.getString("nome"));	
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));	
			modelLogin.setSenha(resultSet.getString("senha"));	
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));	
			modelLogin.setFotoUser(resultSet.getString("fotouser"));
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			modelLogin.setComplemento(resultSet.getString("complemento"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;
		
	}

	public ModelLogin consultaUsuario(String login) throws Exception {
		
		//inicia o objeto para depois retornar
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper(?) and useradmin is false;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, login);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* Se tem resultado */
			modelLogin.setId(resultSet.getLong("id"));	
			modelLogin.setNome(resultSet.getString("nome"));	
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));	
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	
			modelLogin.setFotoUser(resultSet.getString("fotouser"));
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			modelLogin.setComplemento(resultSet.getString("complemento"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;
		
	}

	public ModelLogin consultaUsuarioID(Long id) throws Exception {
		
		//inicia o objeto para depois retornar
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, id);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* Se tem resultado */
			modelLogin.setId(resultSet.getLong("id"));	
			modelLogin.setNome(resultSet.getString("nome"));	
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));	
			modelLogin.setSenha(resultSet.getString("senha"));	
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	
			modelLogin.setFotoUser(resultSet.getString("fotouser"));
			modelLogin.setExtensaoFotoUser(resultSet.getString("extensaofotouser"));
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			modelLogin.setComplemento(resultSet.getString("complemento"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {
		
		//inicia o objeto para depois retornar
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?;";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, Long.parseLong(id));
		preparedSql.setLong(2, userLogado);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* Se tem resultado */
			modelLogin.setId(resultSet.getLong("id"));	
			modelLogin.setNome(resultSet.getString("nome"));	
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));	
			modelLogin.setSenha(resultSet.getString("senha"));	
			modelLogin.setPerfil(resultSet.getString("perfil"));	
			modelLogin.setSexo(resultSet.getString("sexo"));	
			modelLogin.setFotoUser(resultSet.getString("fotouser"));
			modelLogin.setExtensaoFotoUser(resultSet.getString("extensaofotouser"));
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			modelLogin.setComplemento(resultSet.getString("complemento"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;
		
	}

	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
		//inicia o objeto para depois retornar
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper(?);";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, login);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();
		
		while (resultSet.next()) { /* Se tem resultado */
			modelLogin.setId(resultSet.getLong("id"));	
			modelLogin.setNome(resultSet.getString("nome"));	
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));	
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setUseradmin(resultSet.getBoolean("useradmin"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));	
			modelLogin.setFotoUser(resultSet.getString("fotouser"));
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setLocalidade(resultSet.getString("localidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			modelLogin.setComplemento(resultSet.getString("complemento"));
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;
		
	}
	
	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();

		resultSet.next();/*Pra entrar nos resultados do sql*/

		return resultSet.getBoolean("existe");
		
	}

	public void deletarUser(String idUser) throws Exception {

		String sql = "delete from model_login where id = ? and useradmin is false;";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, Long.parseLong(idUser));
		
		preparedSql.executeUpdate();
		connection.commit();

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
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
			
		}

		return retorno;
		
	}

	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception {
		
		String sql = "select avg(rendamensal) as media_salarial, perfil from model_login"
				+ " where usuario_id = ? group by perfil;";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, userLogado);
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();

		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;

	}

	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		String sql = "select avg(rendamensal) as media_salarial, perfil from model_login"
				+ " where usuario_id = ? and datanascimento >= ? and datanascimento <= ?"
				+ " group by perfil;";

		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setLong(1, userLogado);
		preparedSql.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedSql.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		//Resultado do sql
		ResultSet resultSet = preparedSql.executeQuery();

		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) { /* percorre as linhas de resultado do sql*/
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
}
