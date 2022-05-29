package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelProduto;

public class DAOProdutoRepository {

	private Connection connection;

	public DAOProdutoRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelProduto gravarProduto(ModelProduto objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO tbl_produto (pro_nome, pro_valor, pro_estoque, usuario_id) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getPro_nome());
			statement.setDouble(2, objeto.getPro_valor());
			statement.setInt(3, objeto.getPro_estoque());
			statement.setLong(4, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE tbl_produto SET pro_nome=?, pro_valor=?, pro_estoque=? WHERE pk_id_produto = "
					+ objeto.getPk_id_produto() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getPro_nome());
			statement.setDouble(2, objeto.getPro_valor());
			statement.setInt(3, objeto.getPro_estoque());

			statement.executeUpdate();
			connection.commit();
		}

		return this.consultarProduto(objeto.getPro_nome());
	}

	public List<ModelProduto> consultarProdutoList(Long userLogado) throws Exception{
		
		List<ModelProduto> retorno = new ArrayList<ModelProduto>();	
			
		String sql = "select * from tbl_produto where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelProduto modelProduto = new ModelProduto();
			
			modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
			modelProduto.setPro_nome(resultSet.getString("pro_nome"));
			modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
			modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
			
			retorno.add(modelProduto);
		}
		
		return retorno;
	}
	
		public List<ModelProduto> consultarProdutoList(String pro_nome, Long userLogado) throws Exception{
		
		List<ModelProduto> retorno = new ArrayList<ModelProduto>();	
			
		String sql = "select * from tbl_produto where upper(pro_nome) like upper(?) and useradmin is false and usuario_id = ? and limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, "%" +pro_nome+ "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelProduto modelProduto = new ModelProduto();
			
			modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
			modelProduto.setPro_nome(resultSet.getString("pro_nome"));
			modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
			modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
			
			retorno.add(modelProduto);
		}
		
		return retorno;
	}
		
		public int consultarProdutoListTotalPaginaPaginacao(String pro_nome, Long userLogado) throws Exception {
			
			
			String sql = "select count(1) as total from tbl_produto  where upper(pro_nome) like upper(?) and useradmin is false and usuario_id = ? ";
		
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + pro_nome + "%");
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
		
		public List<ModelProduto> consultaProdutoListPaginada(Long userLogado, Integer offset) throws Exception {
			
			List<ModelProduto> retorno = new ArrayList<ModelProduto>();
			
			String sql = "select * from tbl_produto where useradmin is false and usuario_id = " + userLogado + " order by pro_nome offset "+offset+" limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) { 
				
				ModelProduto modelProduto = new ModelProduto();
				
				modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
				modelProduto.setPro_nome(resultSet.getString("pro_nome"));
				modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
				modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
				
				retorno.add(modelProduto);
			}
			
			
			return retorno;
		}
		
		public List<ModelProduto> consultarProdutoListOffSet(String pro_nome, Long userLogado, int offset) throws Exception {
			
			List<ModelProduto> retorno = new ArrayList<ModelProduto>();
			
			String sql = "select * from tbl_produto  where upper(pro_nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + pro_nome + "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) { 
				
				ModelProduto modelProduto = new ModelProduto();
				
				modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
				modelProduto.setPro_nome(resultSet.getString("pro_nome"));
				modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
				modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
				
				retorno.add(modelProduto);
			}
			
			
			return retorno;
		}
		
		
		public ModelProduto consultarProdutoID(String pk_id_produto, Long userLogado) throws Exception{
			
			ModelProduto modelProduto = new ModelProduto();
			
			String sql = "select * from tbl_produto where pk_id_produto = ? and useradmin is false and usuario_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, Long.parseLong(pk_id_produto));
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
				modelProduto.setPro_nome(resultSet.getString("pro_nome"));
				modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
				modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
			}
			
			return modelProduto;
		}
		
		

	
		public ModelProduto consultarProduto(String pro_nome) throws Exception{
		
		ModelProduto modelProduto = new ModelProduto();
		
		String sql = "select * from tbl_produto where upper(pro_nome) = upper('"+pro_nome+"') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			modelProduto.setPk_id_produto(resultSet.getLong("pk_id_produto"));
			modelProduto.setPro_nome(resultSet.getString("pro_nome"));
			modelProduto.setPro_valor(resultSet.getDouble("pro_valor"));
			modelProduto.setPro_estoque(resultSet.getInt("pro_estoque"));
		}
		
		return modelProduto;
	}
	
	
	
	public void deletarProduto(String pk_id_produtoUser) throws Exception{
		
		String sql = "DELETE FROM tbl_produto WHERE pk_id_produto = ? and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, Long.parseLong(pk_id_produtoUser));
		
		statement.executeUpdate();
		
		connection.commit();
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "select count(1) as total from tbl_produto where usuario_id = " + userLogado;
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
