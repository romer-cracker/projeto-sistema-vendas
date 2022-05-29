package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pk_id_produto;

	private String pro_nome;

	private Double pro_valor;

	private int pro_estoque;
	
	public boolean isNovo() {
		if (this.pk_id_produto == null) {
			return true;
		} else if (this.pk_id_produto != null && this.pk_id_produto > 0) {
			return false;
		}

		return pk_id_produto == null;
	}
	

	public Long getPk_id_produto() {
		return pk_id_produto;
	}

	public void setPk_id_produto(Long pk_id_produto) {
		this.pk_id_produto = pk_id_produto;
	}

	public String getPro_nome() {
		return pro_nome;
	}

	public void setPro_nome(String pro_nome) {
		this.pro_nome = pro_nome;
	}

	public Double getPro_valor() {
		return pro_valor;
	}

	public void setPro_valor(Double pro_valor) {
		this.pro_valor = pro_valor;
	}

	public int getPro_estoque() {
		return pro_estoque;
	}

	public void setPro_estoque(int pro_estoque) {
		this.pro_estoque = pro_estoque;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pk_id_produto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelProduto other = (ModelProduto) obj;
		return Objects.equals(pk_id_produto, other.pk_id_produto);
	}

}
