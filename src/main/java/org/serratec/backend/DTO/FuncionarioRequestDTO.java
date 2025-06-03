package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;
import org.serratec.backend.entity.Funcionario;
import org.serratec.backend.enums.CargoEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FuncionarioRequestDTO {

	@NotBlank	
    private String nome;
    private CargoEnum cargo;
    @NotBlank
    private String telefone;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    @Size(min = 6)
    private String senha;

    @Min(1518)
    private BigDecimal salario;

    public FuncionarioRequestDTO() {
    }

    public FuncionarioRequestDTO(Funcionario funcionario) {
        this.nome = funcionario.getNome();
        this.cargo = funcionario.getCargo();
        this.salario = funcionario.getSalario();
        this.telefone = funcionario.getTelefone();
        this.email = funcionario.getEmail();
        this.cpf = funcionario.getCpf();
        this.senha = funcionario.getSenha();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CargoEnum getCargo() {
        return cargo;
    }

    public void setCargo(CargoEnum cargo) {
        this.cargo = cargo;
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

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Nome:" + nome + "\n" +
                "Cargo: " + cargo + "\n" +
                "Email: " + email + "\n" +
                "Salário: " + salario;
    }
}
