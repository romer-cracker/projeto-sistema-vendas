package model;

import java.io.Serializable;
import java.sql.Date;

public class ModelVenda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long pk_id_vendas;
	
	private Long fk_cliente;
	
	private String ven_vendedor;
	
	private String ven_data_venda;
	
	private Double ven_valor_liquido;
	
	private Double ven_valor_bruto;
	
	private Double ven_desconto;
	
	public boolean isNovo() {
		if(this.pk_id_vendas == null) {
			return true;
		}else if(this.pk_id_vendas != null && this.pk_id_vendas > 0) {
			return false;
		}
		
		return pk_id_vendas == null;
	}
	
	

	public String getVen_vendedor() {
		return ven_vendedor;
	}



	public void setVen_vendedor(String ven_vendedor) {
		this.ven_vendedor = ven_vendedor;
	}



	public Long getPk_id_vendas() {
		return pk_id_vendas;
	}

	public void setPk_id_vendas(Long pk_id_vendas) {
		this.pk_id_vendas = pk_id_vendas;
	}

	public Long getFk_cliente() {
		return fk_cliente;
	}

	public void setFk_cliente(Long fk_cliente) {
		this.fk_cliente = fk_cliente;
	}

	public String getVen_data_venda() {
		return ven_data_venda;
	}

	public void setVen_data_venda(String ven_data_venda) {
		this.ven_data_venda = ven_data_venda;
	}

	public Double getVen_valor_liquido() {
		return ven_valor_liquido;
	}

	public void setVen_valor_liquido(Double ven_valor_liquido) {
		this.ven_valor_liquido = ven_valor_liquido;
	}

	public Double getVen_valor_bruto() {
		return ven_valor_bruto;
	}

	public void setVen_valor_bruto(Double ven_valor_bruto) {
		this.ven_valor_bruto = ven_valor_bruto;
	}

	public Double getVen_desconto() {
		return ven_desconto;
	}

	public void setVen_desconto(Double ven_desconto) {
		this.ven_desconto = ven_desconto;
	}
	
	

}
