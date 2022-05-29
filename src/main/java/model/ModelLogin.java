package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pk_usuario;
	private String usu_nome;
	private String usu_login;
	private String cli_senha;
	private String usu_perfil;

	public boolean isNovo() {
		if (this.pk_usuario == null) {
			return true;
		} else if (this.pk_usuario != null && this.pk_usuario > 0) {
			return false;
		}

		return pk_usuario == null;
	}

	public String getUsu_perfil() {
		return usu_perfil;
	}

	public void setUsu_perfil(String usu_perfil) {
		this.usu_perfil = usu_perfil;
	}

	public Long getPk_usuario() {
		return pk_usuario;
	}

	public void setPk_usuario(Long pk_usuario) {
		this.pk_usuario = pk_usuario;
	}

	public String getUsu_nome() {
		return usu_nome;
	}

	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}

	public String getUsu_login() {
		return usu_login;
	}

	public void setUsu_login(String usu_login) {
		this.usu_login = usu_login;
	}

	public String getCli_senha() {
		return cli_senha;
	}

	public void setCli_senha(String cli_senha) {
		this.cli_senha = cli_senha;
	}

}
