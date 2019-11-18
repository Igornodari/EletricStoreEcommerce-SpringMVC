package entidades;

import javax.validation.constraints.NotNull;

public class TrocaSenha {
	@NotNull(message = "A senha atual é obrigatória")
	private String senhaAtual;
	
	@NotNull(message = "A nova senha é obrigatória")
	private String novaSenha;
	
	@NotNull(message = "A nova confirmação de senha é obrigatória")
	private String confirmacaoSenha;
	
	public String getSenhaAtual() {
		return senhaAtual;
	}
	
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	
	public String getNovaSenha() {
		return novaSenha;
	}
	
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	
}
