package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception{
		
	if(objeto.isNovo()) {	
		
		String sql = "INSERT INTO tbl_usuario (usu_nome, usu_login, cli_senha, usu_perfil, usuario_id) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, objeto.getUsu_nome());
		statement.setString(2, objeto.getUsu_login());
		statement.setString(3, objeto.getCli_senha());
		statement.setString(4, objeto.getUsu_perfil());
		statement.setLong(5, userLogado);
		
		statement.execute();
		connection.commit();
		
		}else {
			
			String sql = "UPDATE tbl_usuario SET usu_nome=?, usu_login=?, cli_senha=?, usu_perfil=? WHERE pk_usuario = "+objeto.getPk_usuario()+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, objeto.getUsu_nome());
			statement.setString(2, objeto.getUsu_login());
			statement.setString(3, objeto.getCli_senha());
			statement.setString(4, objeto.getUsu_perfil());
			
			statement.executeUpdate();
			connection.commit();
		}
		
		return this.consultarUsuario(objeto.getUsu_login());
	}
	
		public List<ModelLogin> consultarUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();	
			
		String sql = "select * from tbl_usuario where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
			modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
			modelLogin.setUsu_login(resultSet.getString("usu_login"));
			modelLogin.setCli_senha(resultSet.getString("cli_senha"));
			modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
		public List<ModelLogin> consultarUsuarioList(String usu_nome, Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();	
			
		String sql = "select * from tbl_usuario where upper(usu_nome) like upper(?) and useradmin is false and usuario_id = ? and limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, "%" +usu_nome+ "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
			modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
			modelLogin.setUsu_login(resultSet.getString("usu_login"));
			modelLogin.setCli_senha(resultSet.getString("cli_senha"));
			modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
		
		public int consultarUsuarioListTotalPaginaPaginacao(String usu_nome, Long userLogado) throws Exception {
			
			
			String sql = "select count(1) as total from tbl_usuario  where upper(usu_nome) like upper(?) and useradmin is false and usuario_id = ? ";
		
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + usu_nome + "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultado = statement.executeQuery();
			
			resultado.next();
			
			Double cadastros = resultado.getDouble("total");
			
			Double porpagina = 5.0;
			
			Double pagina = cadastros / porpagina;
			
			Double resto = pagina % 2;
			
			if (resto > 0) {
				pagina ++;
			}
			
			return pagina.intValue();
			
		}
		
		public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from tbl_usuario where useradmin is false and usuario_id = " + userLogado + " order by usu_nome offset "+offset+" limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) { 
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
				modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
				modelLogin.setUsu_login(resultSet.getString("usu_login"));
				modelLogin.setCli_senha(resultSet.getString("cli_senha"));
				modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
				
				retorno.add(modelLogin);
			}
			
			
			return retorno;
		}
		
		public List<ModelLogin> consultarUsuarioListOffSet(String usu_nome, Long userLogado, int offset) throws Exception {
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from tbl_usuario  where upper(usu_nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + usu_nome + "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) { 
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
				modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
				modelLogin.setUsu_login(resultSet.getString("usu_login"));
				modelLogin.setCli_senha(resultSet.getString("cli_senha"));
				modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
				
				retorno.add(modelLogin);
			}
			
			
			return retorno;
		}
		
		
		public ModelLogin consultarUsuarioID(String pk_usuario, Long userLogado) throws Exception{
			
			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "select * from tbl_usuario where pk_usuario = ? and useradmin is false and usuario_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, Long.parseLong(pk_usuario));
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
				modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
				modelLogin.setUsu_login(resultSet.getString("usu_login"));
				modelLogin.setCli_senha(resultSet.getString("cli_senha"));
				modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
			}
			
			return modelLogin;
		}
		
		public ModelLogin consultarUsuarioLogado(String usu_login) throws Exception {

			ModelLogin modelLogin = new ModelLogin();

			String sql = "select * from tbl_usuario where upper(usu_login) = upper('" + usu_login + "')"; /*SQL CRIADO NO BANCO*/

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				
				modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
				modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
				modelLogin.setUsu_login(resultSet.getString("usu_login"));
				modelLogin.setCli_senha(resultSet.getString("cli_senha"));
				modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
			}

			return modelLogin;
		}

	
		public ModelLogin consultarUsuario(String usu_login) throws Exception{
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from tbl_usuario where upper(usu_login) = upper('"+usu_login+"') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			modelLogin.setPk_usuario(resultSet.getLong("pk_usuario"));
			modelLogin.setUsu_nome(resultSet.getString("usu_nome"));
			modelLogin.setUsu_login(resultSet.getString("usu_login"));
			modelLogin.setCli_senha(resultSet.getString("cli_senha"));
			modelLogin.setUsu_perfil(resultSet.getString("usu_perfil"));
		}
		
		return modelLogin;
	}
	
	public boolean validarLogin(String usu_login) throws Exception{
		
		String sql = "select count(1) > 0 as existe from tbl_usuario where upper(usu_login) = upper('"+usu_login+"');";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		resultSet.next();
		
		return resultSet.getBoolean("existe");
	}
	
	public void deletarUser(String pk_usuarioUser) throws Exception{
		
		String sql = "DELETE FROM tbl_usuario WHERE pk_usuario = ? and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, Long.parseLong(pk_usuarioUser));
		
		statement.executeUpdate();
		
		connection.commit();
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "select count(1) as total from tbl_usuario where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		resultSet.next();
		
		Double cadastros = resultSet.getDouble("total");
		
		Double porPagina = 5.0;
		
		Double pagina = cadastros / porPagina;
		
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
	}

}
