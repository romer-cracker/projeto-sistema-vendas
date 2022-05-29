package model;

import java.io.Serializable;

public class ModelCliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long pk_id_cliente;
	
	private String cli_cpf;
	
	private String cli_nome;
	
	private String cli_cep;
	
	private String cli_endereco;
	
	private String cli_bairro;
	
	private String cli_cidade;
	
	private String cli_uf;
	
	private String cli_telefone;
	
	public boolean isNovo() {
		if (this.pk_id_cliente == null) {
			return true;
		} else if (this.pk_id_cliente != null && this.pk_id_cliente > 0) {
			return false;
		}

		return pk_id_cliente == null;
	}
	

	public String getCli_cpf() {
		return cli_cpf;
	}

	public void setCli_cpf(String cli_cpf) {
		this.cli_cpf = cli_cpf;
	}

	public Long getPk_id_cliente() {
		return pk_id_cliente;
	}

	public void setPk_id_cliente(Long pk_id_cliente) {
		this.pk_id_cliente = pk_id_cliente;
	}

	public String getCli_nome() {
		return cli_nome;
	}

	public void setCli_nome(String cli_nome) {
		this.cli_nome = cli_nome;
	}

	public String getCli_cep() {
		return cli_cep;
	}

	public void setCli_cep(String cli_cep) {
		this.cli_cep = cli_cep;
	}

	public String getCli_endereco() {
		return cli_endereco;
	}

	public void setCli_endereco(String cli_endereco) {
		this.cli_endereco = cli_endereco;
	}

	public String getCli_bairro() {
		return cli_bairro;
	}

	public void setCli_bairro(String cli_bairro) {
		this.cli_bairro = cli_bairro;
	}

	public String getCli_cidade() {
		return cli_cidade;
	}

	public void setCli_cidade(String cli_cidade) {
		this.cli_cidade = cli_cidade;
	}

	public String getCli_uf() {
		return cli_uf;
	}

	public void setCli_uf(String cli_uf) {
		this.cli_uf = cli_uf;
	}

	public String getCli_telefone() {
		return cli_telefone;
	}

	public void setCli_telefone(String cli_telefone) {
		this.cli_telefone = cli_telefone;
	}
	
	
	
}
