package entidades;

import javax.validation.constraints.NotNull;

public class Login {
	
	@NotNull(message = "O campo e-mail é obrigatório")
	private String email;
	
	@NotNull(message = "O campo senha é obrigatório")
	private String senha;
	
	public Login() { }
	
	public Login(String email, String senha){
		this.email = email;
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
