package org.serratec.backend.DTO;

import jakarta.validation.constraints.Min;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.entity.Produto;

import java.math.BigDecimal;

public class FuncionarioRequestDTO {


    private String nome;
    private String cargo;
    private String telefone;
    private String email;
    private String cpf;
    private String senha;

    @Min(0)
    private BigDecimal salario;

    public FuncionarioRequestDTO() {
    }

    public FuncionarioRequestDTO(String nome, String cargo, BigDecimal salario, String telefone, String email, String cpf, String senha, BigDecimal salario1) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.salario = salario1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
