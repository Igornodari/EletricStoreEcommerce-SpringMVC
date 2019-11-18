/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static long serialVersionUID = 1L;

    public Cliente() {
    }

    public Cliente(int id, String nome, String sobrenome, String cpf, String email,
            LocalDate dataNasc, String senha, String telefone, String celular, String sexo, Boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.dataNasc = dataNasc;
        if (senha.length() == 60) {
            this.senha = senha;
        } else {
            setSenhaEncriptada(senha);
        }
        this.telefone = telefone;
        this.celular = celular;
        this.sexo = sexo;
        this.ativo = ativo;
    }

    public Cliente(int id, String nome, String sobrenome, String cpf, String email,
            LocalDate dataNasc, String telefone, String celular, String sexo, Boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.celular = celular;
        this.sexo = sexo;
        this.ativo = ativo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "Campo Nome é obrigatório")
    private String nome;

    @Column(name = "sobrenome")
    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @Column(name = "cpf")
    @NotBlank(message = "Cpf obrigatório")
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(name = "email")
    @NotBlank(message = "Email é obrigatório")
    @Email
    private String email;

    @Column(name = "datanasc")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNasc;

    @Column(name = "senha")
    @NotBlank(message = "Campo senha é obrigatório")
    private String senha;

    @Column(name = "telefone")
    @Size(max = 10)
    private String telefone;

    @Column(name = "celular")
    @NotBlank(message = "Campo celular é obrigatório")
    @Size(min = 11, max = 11)
    private String celular;

    @NotBlank(message = "Campo sexo é obrigatório")
    @Column(name = "sexo")
    private String sexo;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Endereco> enderecos;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<CartaoCredito> cartoes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public final void setSenhaEncriptada(String senha) {
        this.senha = BCrypt.hashpw(senha, BCrypt.gensalt(12));
    }

    public boolean validarSenha(String senha) {
        boolean senhaValida = BCrypt.checkpw(senha, this.getSenha());
        return senhaValida;
    }
}
