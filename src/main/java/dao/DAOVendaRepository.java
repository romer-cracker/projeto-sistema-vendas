package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelVenda;

public class DAOVendaRepository {
	
	private Connection connection;
	
	public DAOVendaRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelVenda gravarVenda(ModelVenda objeto, Long userlogado) throws Exception{
		
		if(objeto.isNovo()) {	
			
			String sql = "INSERT INTO tbl_vendas (ven_vendedor, fk_cliente, ven_data_venda, ven_valor_liquido, ven_valor_bruto, ven_desconto, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, objeto.getVen_vendedor());
			statement.setLong(2, objeto.getFk_cliente());
			statement.setString(3, objeto.getVen_data_venda());
			statement.setDouble(4, objeto.getVen_valor_liquido());
			statement.setDouble(5, objeto.getVen_valor_bruto());
			statement.setDouble(6, objeto.getVen_desconto());
			statement.setLong(7, userlogado);
			
			statement.execute();
			connection.commit();
			
			}else {
				
				String sql = "UPDATE tbl_vendas SET ven_vendedor=?, ven_data_venda=?, ven_valor_liquido=?, ven_valor_bruto=?, ven_desconto=? WHERE pk_id_vendas = "+objeto.getPk_id_vendas()+";";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setString(1, objeto.getVen_vendedor());
				statement.setString(2, objeto.getVen_data_venda());
				statement.setDouble(3, objeto.getVen_valor_liquido());
				statement.setDouble(4, objeto.getVen_valor_bruto());
				statement.setDouble(5, objeto.getVen_desconto());
				
				statement.executeUpdate();
				connection.commit();
			}
			
			return this.consultarVenda(objeto.getVen_vendedor(), userlogado);
		}
		
			public List<ModelVenda> consultarVendaList(Long userLogado) throws Exception{
			
			List<ModelVenda> retorno = new ArrayList<ModelVenda>();	
				
			String sql = "select * from tbl_vendas where useradmin is false and usuario_id = " + userLogado +"limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelVenda modelVenda = new ModelVenda();
				
				modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
				modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
				modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
				modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
				modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
				modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
				modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
				
				retorno.add(modelVenda);
			}
			
			return retorno;
		}
		
			public List<ModelVenda> consultarVendaList(String pk_id_vendas, Long userLogado) throws Exception{
			
			List<ModelVenda> retorno = new ArrayList<ModelVenda>();	
				
			String sql = "select * from tbl_vendas where pk_id_vendas like ? and useradmin is false and usuario_id = ? and limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, "%" +pk_id_vendas+ "%");
			statement.setLong(2, userLogado);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				ModelVenda modelVenda = new ModelVenda();
				
				modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
				modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
				modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
				modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
				modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
				modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
				modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
				
				retorno.add(modelVenda);
			}
			
			return retorno;
		}
			
			public int consultarVendaListTotalPaginaPaginacao(String pk_id_vendas, Long userLogado) throws Exception {
				
				
				String sql = "select count(1) as total from tbl_vendas  where pk_id_vendas like ? and useradmin is false and usuario_id = ?";
			
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + pk_id_vendas + "%");
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
			
			public List<ModelVenda> consultaVendaListPaginada(Long userLogado, Integer offset) throws Exception {
				
				List<ModelVenda> retorno = new ArrayList<ModelVenda>();
				
				String sql = "select * from tbl_vendas where useradmin is false and usuario_id = " + userLogado + " order by pk_id_vendas offset "+ offset +" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelVenda modelVenda = new ModelVenda();
					
					modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
					modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
					modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
					modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
					modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
					modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
					modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
					
					retorno.add(modelVenda);
				}
				
				
				return retorno;
			}
			
			public List<ModelVenda> consultarVendaListOffSet(String pk_id_vendas, Long userLogado, int offset) throws Exception {
				
				List<ModelVenda> retorno = new ArrayList<ModelVenda>();
				
				String sql = "select * from tbl_vendas  where pk_id_vendas like ? and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, "%" + pk_id_vendas + "%");
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while (resultSet.next()) { 
					
					ModelVenda modelVenda = new ModelVenda();
					
					modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
					modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
					modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
					modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
					modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
					modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
					modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
					
					retorno.add(modelVenda);
				}
				
				
				return retorno;
			}
			
			
			public ModelVenda consultarVendaID(String pk_id_vendas, Long userLogado) throws Exception{
				
				ModelVenda modelVenda = new ModelVenda();
				
				String sql = "select * from tbl_vendas where pk_id_vendas = ? and useradmin is false and usuario_id = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setLong(1, Long.parseLong(pk_id_vendas));
				statement.setLong(2, userLogado);
				
				ResultSet resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					
					modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
					modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
					modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
					modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
					modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
					modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
					modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
				}
				
				return modelVenda;
			}
			
			public ModelVenda consultarVendasLogado(String pk_id_vendas) throws Exception {

				ModelVenda modelVenda = new ModelVenda();

				String sql = "select * from tbl_vendas where pk_id_vendas = ('" + pk_id_vendas + "')"; 

				PreparedStatement statement = connection.prepareStatement(sql);

				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					
					modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
					modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
					modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
					modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
					modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
					modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
					modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
				}

				return modelVenda;
			}
			
		
			public ModelVenda consultarVenda(String ven_vendedor, Long userLogado) throws Exception{
			
			ModelVenda modelVenda = new ModelVenda();
			
			String sql = "select * from tbl_vendas where ven_vendedor = ('"+ven_vendedor+"') and useradmin is false and usuario_id = " + userLogado;
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				modelVenda.setPk_id_vendas(resultSet.getLong("pk_id_vendas"));
				modelVenda.setVen_vendedor(resultSet.getString("ven_vendedor"));
				modelVenda.setFk_cliente(resultSet.getLong("fk_cliente"));
				modelVenda.setVen_data_venda(resultSet.getString("ven_data_venda"));
				modelVenda.setVen_valor_liquido(resultSet.getDouble("ven_valor_liquido"));
				modelVenda.setVen_valor_bruto(resultSet.getDouble("ven_valor_bruto"));
				modelVenda.setVen_desconto(resultSet.getDouble("ven_desconto"));
			}
			
			return modelVenda;
		}
		
		
		public void deletarVenda(String pk_id_vendasUser) throws Exception{
			
			String sql = "DELETE FROM tbl_vendas WHERE pk_id_vendas = ? and useradmin is false";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(1, Long.parseLong(pk_id_vendasUser));
			
			statement.executeUpdate();
			
			connection.commit();
		}
		
		public int totalPagina(Long userLogado)throws Exception {
			
			String sql = "select count(1) as total from tbl_vendas where usuario_id = " + userLogado;
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
