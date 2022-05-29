package model;

import java.io.Serializable;

public class ModelVendasProdutos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long pk_id_venda_produtos;
	
	private Long fk_produto;
	
	private Long fk_vendas;
	
	private Double ven_valor;
	
	private String ven_pro_qtd;
	
	private String ven_pro_vendedor;
	
	
	public boolean isNovo() {
		if(this.pk_id_venda_produtos == null) {
			return true;
		}else if(this.pk_id_venda_produtos != null && this.pk_id_venda_produtos > 0) {
			return false;
		}
		
		return pk_id_venda_produtos == null;
	}
	
	

	public String getVen_pro_vendedor() {
		return ven_pro_vendedor;
	}



	public void setVen_pro_vendedor(String ven_pro_vendedor) {
		this.ven_pro_vendedor = ven_pro_vendedor;
	}



	public Long getPk_id_venda_produtos() {
		return pk_id_venda_produtos;
	}

	public void setPk_id_venda_produtos(Long pk_id_venda_produtos) {
		this.pk_id_venda_produtos = pk_id_venda_produtos;
	}

	public Long getFk_produto() {
		return fk_produto;
	}

	public void setFk_produto(Long fk_produto) {
		this.fk_produto = fk_produto;
	}

	public Long getFk_vendas() {
		return fk_vendas;
	}

	public void setFk_vendas(Long fk_vendas) {
		this.fk_vendas = fk_vendas;
	}

	public Double getVen_valor() {
		return ven_valor;
	}

	public void setVen_valor(Double ven_valor) {
		this.ven_valor = ven_valor;
	}

	public String getVen_pro_qtd() {
		return ven_pro_qtd;
	}

	public void setVen_pro_qtd(String ven_pro_qtd) {
		this.ven_pro_qtd = ven_pro_qtd;
	}
	
	

}
