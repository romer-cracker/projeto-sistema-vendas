package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelCliente;
import model.ModelLogin;

public class DAOClienteRepository {
	
	private Connection connection;
	
	public DAOClienteRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelCliente gravarCliente(ModelCliente objeto, Long userLogado) throws Exception{
		
		if(objeto.isNovo()) {	
			
			String sql = "INSERT INTO tbl_cliente (cli_cpf, cli_nome, cli_cep, cli_endereco, cli_bairro, cli_cidade, cli_uf, cli_telefone, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, objeto.getCli_cpf());
			statement.setString(2, objeto.getCli_nome());
			statement.setString(3, objeto.getCli_cep());
			statement.setString(4, objeto.getCli_endereco());
			statement.setString(5, objeto.getCli_bairro());
			statement.setString(6, objeto.getCli_cidade());
			statement.setString(7, objeto.getCli_uf());
			statement.setString(8, objeto.getCli_telefone());
			statement.setLong(9, userLogado);
			
			statement.execute();
			connection.commit();
			
			}else {
				
				String sql = "UPDATE tbl_cliente SET cli_cpf=?, cli_nome=?, cli_cep=?, cli_endereco=?, cli_bairro=?, cli_cidade=?, cli_uf=?, cli_telefone=? WHERE pk_id_cliente = "+objeto.getPk_id_cliente()+";";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setString(1, objeto.getCli_cpf());
				statement.setString(2, objeto.getCli_nome());
				statement.setString(3, objeto.getCli_cep());
				statement.setString(4, objeto.getCli_endereco());
				statement.setString(5, objeto.getCli_bairro());
				statement.setString(6, objeto.getCli_cidade());
				statement.setString(7, objeto.getCli_uf());
				statement.setString(8, objeto.getCli_telefone());
				
				statement.executeUpdate();
				connection.commit();
			}
			
			return this.consultarCliente(objeto.getCli_cpf());
		}
		
			public List<ModelCliente> consultarClienteList(Long userLogado) throws Exception{
			
			List<ModelCliente> retorno = new ArrayList<ModelCliente>();	
				
			String sql = "select * from tbl_cliente where useradmin is false and usuario_id = " + userLogado + "limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelCliente modelCliente = new ModelCliente();
				
				modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
				modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
				modelCliente.setCli_nome(resultSet.getString("cli_nome"));
				modelCliente.setCli_cep(resultSet.getString("cli_cep"));
				modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
				modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
				modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
				modelCliente.setCli_uf(resultSet.getString("cli_uf"));
				modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
				
				retorno.add(modelCliente);
			}
			
			return retorno;
		}
		
			public List<ModelCliente> consultarClienteList(String cli_cpf, Long userLogado) throws Exception{
			
			List<ModelCliente> retorno = new ArrayList<ModelCliente>();	
				
			String sql = "select * from tbl_cliente where upper(cli_cpf) like upper(?) and useradmin is false and usuario_id = ? and limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, "%" +cli_cpf+ "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelCliente modelCliente = new ModelCliente();
				
				modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
				modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
				modelCliente.setCli_nome(resultSet.getString("cli_nome"));
				modelCliente.setCli_cep(resultSet.getString("cli_cep"));
				modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
				modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
				modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
				modelCliente.setCli_uf(resultSet.getString("cli_uf"));
				modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
				
				retorno.add(modelCliente);
			}
			
			return retorno;
		}
			
			public int consultarClienteListTotalPaginaPaginacao(String cli_nome, Long userLogado) throws Exception {
				
				
				String sql = "select count(1) as total from tbl_cliente  where upper(cli_nome) like upper(?) and useradmin is false and usuario_id = ? ";
			
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + cli_nome + "%");
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
			
			public List<ModelCliente> consultaClienteListPaginada(Long userLogado, Integer offset) throws Exception {
				
				List<ModelCliente> retorno = new ArrayList<ModelCliente>();
				
				String sql = "select * from tbl_cliente where useradmin is false and usuario_id = " + userLogado + " order by cli_nome offset "+offset+" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelCliente modelCliente = new ModelCliente();
					
					modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
					modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
					modelCliente.setCli_nome(resultSet.getString("cli_nome"));
					modelCliente.setCli_cep(resultSet.getString("cli_cep"));
					modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
					modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
					modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
					modelCliente.setCli_uf(resultSet.getString("cli_uf"));
					modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
					
					retorno.add(modelCliente);
				}
				
				
				return retorno;
			}
			
			public List<ModelCliente> consultarClienteListOffSet(String cli_nome, Long userLogado, int offset) throws Exception {
				
				List<ModelCliente> retorno = new ArrayList<ModelCliente>();
				
				String sql = "select * from tbl_cliente  where upper(cli_nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + cli_nome + "%");
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelCliente modelCliente = new ModelCliente();
					
					modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
					modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
					modelCliente.setCli_nome(resultSet.getString("cli_nome"));
					modelCliente.setCli_cep(resultSet.getString("cli_cep"));
					modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
					modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
					modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
					modelCliente.setCli_uf(resultSet.getString("cli_uf"));
					modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
					
					retorno.add(modelCliente);
				}
				
				
				return retorno;
			}
			
			
			public ModelCliente consultarClienteID(String pk_id_cliente, Long userLogado) throws Exception{
				
				ModelCliente modelCliente = new ModelCliente();
				
				String sql = "select * from tbl_cliente where pk_id_cliente = ? and useradmin is false and usuario_id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setLong(1, Long.parseLong(pk_id_cliente));
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					
					modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
					modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
					modelCliente.setCli_nome(resultSet.getString("cli_nome"));
					modelCliente.setCli_cep(resultSet.getString("cli_cep"));
					modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
					modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
					modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
					modelCliente.setCli_uf(resultSet.getString("cli_uf"));
					modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
				}
				
				return modelCliente;
			}
			

		
			public ModelCliente consultarCliente(String cli_cpf) throws Exception{
			
			ModelCliente modelCliente = new ModelCliente();
			
			String sql = "select * from tbl_cliente where upper(cli_cpf) = upper('"+cli_cpf+"') and useradmin is false";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				modelCliente.setPk_id_cliente(resultSet.getLong("pk_id_cliente"));
				modelCliente.setCli_cpf(resultSet.getString("cli_cpf"));
				modelCliente.setCli_nome(resultSet.getString("cli_nome"));
				modelCliente.setCli_cep(resultSet.getString("cli_cep"));
				modelCliente.setCli_endereco(resultSet.getString("cli_endereco"));
				modelCliente.setCli_bairro(resultSet.getString("cli_bairro"));
				modelCliente.setCli_cidade(resultSet.getString("cli_cidade"));
				modelCliente.setCli_uf(resultSet.getString("cli_uf"));
				modelCliente.setCli_telefone(resultSet.getString("cli_telefone"));
			}
			
			return modelCliente;
		}
		
		public boolean validarCpf(String cli_cpf) throws Exception{
			
			String sql = "select count(1) > 0 as existe from tbl_cliente where upper(cli_cpf) = upper('"+cli_cpf+"');";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			
			return resultSet.getBoolean("existe");
		}
		
		public void deletarCliente(String pk_id_clienteUser) throws Exception{
			
			String sql = "DELETE FROM tbl_cliente WHERE pk_id_cliente = ? and useradmin is false";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, Long.parseLong(pk_id_clienteUser));
			
			statement.executeUpdate();
			
			connection.commit();
		}
		
		public int totalPagina(Long userLogado)throws Exception {
			
			String sql = "select count(1) as total from tbl_cliente where usuario_id = " + userLogado;
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
