package org.sysfuncionario.dto;

import jakarta.validation.constraints.NotBlank;
import org.sysfuncionario.model.Endereco;
import org.sysfuncionario.model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioDTO(

        String matricula,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String cargo,
        BigDecimal salario,
        LocalDate dataContratacao,
        Endereco endereco) {

    public FuncionarioDTO(Funcionario funcionario) {
        this(
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getDataNascimento(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataContratacao(),
                funcionario.getEndereco()
        );
    }
}
