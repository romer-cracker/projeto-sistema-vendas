package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelVendasProdutos;

public class DAOVendasProdutosRepository {
	
	private Connection connection;
	
	public DAOVendasProdutosRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
public ModelVendasProdutos gravarVendasProdutos(ModelVendasProdutos objeto, Long userlogado) throws Exception{
		
		if(objeto.isNovo()) {	
			
			String sql = "INSERT INTO tbl_vendas_produtos (fk_produto, fk_vendas, ven_valor, ven_pro_qtd, usuario_id, ven_pro_vendedor) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, objeto.getFk_produto());
			statement.setLong(2, objeto.getFk_vendas());
			statement.setDouble(3, objeto.getVen_valor());
			statement.setString(4, objeto.getVen_pro_qtd());
			statement.setLong(5, userlogado);
			statement.setString(6, objeto.getVen_pro_vendedor());
			
			statement.execute();
			connection.commit();
			
			}else {
				
				String sql = "UPDATE tbl_vendas_produtos SET fk_produto=?, fk_vendas=?, ven_valor=?, ven_pro_qtd=?, ven_pro_vendedor=? WHERE pk_id_venda_produtos = "+objeto.getPk_id_venda_produtos()+";";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setLong(1, objeto.getFk_produto());
				statement.setLong(2, objeto.getFk_vendas());
				statement.setDouble(3, objeto.getVen_valor());
				statement.setString(4, objeto.getVen_pro_qtd());
				statement.setString(5, objeto.getVen_pro_vendedor());
				
				statement.executeUpdate();
				connection.commit();
			}
			
			return this.consultarVendasProdutos(objeto.getVen_pro_vendedor(), userlogado);
		}
		
			public List<ModelVendasProdutos> consultarVendasProdutosList(Long userLogado) throws Exception{
			
			List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();	
				
			String sql = "select * from tbl_vendas_produtos where useradmin is false and usuario_id = " + userLogado +"limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
				
				modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
				modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
				modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
				modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
				modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
				modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
				
				retorno.add(modelVendasProdutos);
			}
			
			return retorno;
		}
		
			public List<ModelVendasProdutos> consultarVendasProdutosList(String pk_id_venda_produtos, Long userLogado) throws Exception{
			
			List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();	
				
			String sql = "select * from tbl_vendas_produtos where pk_id_venda_produtos like ? and useradmin is false and usuario_id = ? and limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, "%" +pk_id_venda_produtos+ "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
				
				modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
				modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
				modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
				modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
				modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
				modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
				
				retorno.add(modelVendasProdutos);
			}
			
			return retorno;
		}
			
			public int consultarVendasProdutosListTotalPaginaPaginacao(String pk_id_venda_produtos, Long userLogado) throws Exception {
				
				
				String sql = "select count(1) as total from tbl_vendas_produtos  where pk_id_venda_produtos like ? and useradmin is false and usuario_id = ?";
			
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + pk_id_venda_produtos + "%");
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
			
			public List<ModelVendasProdutos> consultaVendasProdutosListPaginada(Long userLogado, Integer offset) throws Exception {
				
				List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();
				
				String sql = "select * from tbl_vendas_produtos where useradmin is false and usuario_id = " + userLogado + " order by pk_id_venda_produtos offset "+ offset +" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
					
					modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
					modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
					modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
					modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
					modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
					modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
					
					retorno.add(modelVendasProdutos);
				}
				
				
				return retorno;
			}
			
			public List<ModelVendasProdutos> consultarVendasProdutosListOffSet(String pk_id_venda_produtos, Long userLogado, int offset) throws Exception {
				
				List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();
				
				String sql = "select * from tbl_vendas_produtos  where pk_id_venda_produtos like ? and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + pk_id_venda_produtos + "%");
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
					
					modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
					modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
					modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
					modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
					modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
					modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
					
					retorno.add(modelVendasProdutos);
				}
				
				
				return retorno;
			}
			
			
			public ModelVendasProdutos consultarVendasProdutosID(String pk_id_venda_produtos, Long userLogado) throws Exception{
				
				ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
				
				String sql = "select * from tbl_vendas_produtos where pk_id_venda_produtos = ? and useradmin is false and usuario_id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setLong(1, Long.parseLong(pk_id_venda_produtos));
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					
					modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
					modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
					modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
					modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
					modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
					modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
					
				}
				
				return modelVendasProdutos;
			}
			
			public ModelVendasProdutos consultarVendasProdutosLogado(String pk_id_venda_produtos) throws Exception {

				ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

				String sql = "select * from tbl_vendas_produtos where pk_id_venda_produtos = ('" + pk_id_venda_produtos + "')"; 

				PreparedStatement statement = connection.prepareStatement(sql);

				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					
					modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
					modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
					modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
					modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
					modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
					modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
				}

				return modelVendasProdutos;
			}
			
		
			public ModelVendasProdutos consultarVendasProdutos(String ven_pro_vendedor, Long userLogado) throws Exception{
			
			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
			
			String sql = "select * from tbl_vendas_produtos where ven_pro_vendedor = ('"+ven_pro_vendedor+"') and useradmin is false and usuario_id = " + userLogado;
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				modelVendasProdutos.setPk_id_venda_produtos(resultSet.getLong("pk_id_venda_produtos"));
				modelVendasProdutos.setFk_produto(resultSet.getLong("fk_produto"));
				modelVendasProdutos.setFk_vendas(resultSet.getLong("fk_vendas"));
				modelVendasProdutos.setVen_valor(resultSet.getDouble("ven_valor"));
				modelVendasProdutos.setVen_pro_qtd(resultSet.getString("ven_pro_qtd"));
				modelVendasProdutos.setVen_pro_vendedor(resultSet.getString("ven_pro_vendedor"));
			}
			
			return modelVendasProdutos;
		}
		
		
		public void deletarVendasProdutos(String pk_id_venda_produtosUser) throws Exception{
			
			String sql = "DELETE FROM tbl_vendas_produtos WHERE pk_id_venda_produtos = ? and useradmin is false";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, Long.parseLong(pk_id_venda_produtosUser));
			
			statement.executeUpdate();
			
			connection.commit();
		}
		
		public int totalPagina(Long userLogado)throws Exception {
			
			String sql = "select count(1) as total from tbl_vendas_produtos where usuario_id = " + userLogado;
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
