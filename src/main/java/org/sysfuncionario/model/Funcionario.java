package org.sysfuncionario.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Funcionario {
    private String matricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataContratacao;
    private Endereco endereco;

    @Override
    public String toString() {
        return "{" +
                "matricula= " + matricula +
                ", nome= " + nome +
                ", cpf= " + cpf +
                ", dataNascimento= " + dataNascimento +
                ", cargo= " + cargo +
                ", salario= " + salario +
                ", dataContratacao= " + dataContratacao +
                ", endereco= " + endereco +
                '}';
    }
}
