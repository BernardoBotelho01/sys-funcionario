package org.sysfuncionario.dao;

import org.sysfuncionario.dto.FuncionarioDTO;
import org.sysfuncionario.model.Endereco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private static final String CAMINHO_CSV = "funcionario.csv";
    private static final String CABECALHO = "matricula;nome;cpf;dataNascimento;cargo;salario;dataContratacao;" +
            "logradouro;numero;complemento;bairro;cidade;estado;cep";

    // Formatter BR para dd/MM/yyyy
    private static final DateTimeFormatter BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private void inicializarCsv() throws IOException {
        File f = new File(CAMINHO_CSV);
        if (!f.exists() || f.length() == 0) {
            try (FileWriter fw = new FileWriter(f, false)) {
                fw.write(CABECALHO + System.lineSeparator());
            }
        }
    }

    public void cadastrarFuncionario(FuncionarioDTO funcionario) {
        try {
            inicializarCsv(); // garante arquivo com cabeçalho
            try (FileWriter salvarFuncionario = new FileWriter(CAMINHO_CSV, true)) {
                salvarFuncionario.write(
                        valor(funcionario.matricula()) + ";" +
                                valor(funcionario.nome()) + ";" +
                                valor(funcionario.cpf()) + ";" +
                                valorData(funcionario.dataNascimento()) + ";" +      // <<< formata dd/MM/yyyy
                                valor(funcionario.cargo()) + ";" +
                                valor(funcionario.salario()) + ";" +
                                valorData(funcionario.dataContratacao()) + ";" +     // <<< formata dd/MM/yyyy
                                valor(funcionario.endereco() != null ? funcionario.endereco().getLogradouro() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getNumero() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getComplemento() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getBairro() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getCidade() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getEstado() : null) + ";" +
                                valor(funcionario.endereco() != null ? funcionario.endereco().getCep() : null) +
                                System.lineSeparator()
                );
            }
        } catch (IOException ex) {
            System.out.println("Erro na gravação de arquivo");
            ex.printStackTrace();
        }
    }

    public List<FuncionarioDTO> listarFuncionarios() {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        try {
            inicializarCsv(); // garante arquivo
            try (BufferedReader lerCsv = new BufferedReader(new FileReader(CAMINHO_CSV))) {
                String linha;
                boolean primeira = true;

                while ((linha = lerCsv.readLine()) != null) {
                    if (linha.isEmpty()) continue;

                    // pula o cabeçalho
                    if (primeira) {
                        primeira = false;
                        if (linha.toLowerCase().startsWith("matricula;")) continue;
                    }

                    String[] valores = linha.split(";", -1); // preserva campos vazios
                    if (valores.length < 14) continue;

                    String matricula = valores[0];
                    String nome = valores[1];
                    String cpf = valores[2];

                    LocalDate dataNascimento = parseData(safe(valores[3]));   // <<< lê dd/MM/yyyy
                    String cargo = valores[4];

                    BigDecimal salario = null;
                    String rawSalario = safe(valores[5]);
                    if (!rawSalario.isEmpty()) {
                        salario = new BigDecimal(rawSalario.replace(".", "").replace(",", "."));
                    }

                    LocalDate dataContratacao = parseData(safe(valores[6])); // <<< lê dd/MM/yyyy

                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(valores[7]);
                    endereco.setNumero(valores[8]);
                    endereco.setComplemento(valores[9]);
                    endereco.setBairro(valores[10]);
                    endereco.setCidade(valores[11]);
                    endereco.setEstado(valores[12]);
                    endereco.setCep(valores[13]);

                    FuncionarioDTO funcionario = new FuncionarioDTO(
                            matricula, nome, cpf, dataNascimento, cargo, salario, dataContratacao, endereco
                    );

                    funcionarios.add(funcionario);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro na leitura de arquivo");
            e.printStackTrace();
        }
        return funcionarios;
    }

    public void excluirFuncionario(String matricula){
        List<FuncionarioDTO> funcionarios = listarFuncionarios();

        try {
            inicializarCsv(); // garante arquivo
            try (FileWriter excluir = new FileWriter(CAMINHO_CSV, false)) {
                excluir.write(CABECALHO + System.lineSeparator());

                for (FuncionarioDTO f : funcionarios) {
                    if (!matricula.equals(f.matricula())) {
                        excluir.write(
                                valor(f.matricula()) + ";" +
                                        valor(f.nome()) + ";" +
                                        valor(f.cpf()) + ";" +
                                        valorData(f.dataNascimento()) + ";" +      // <<< formata dd/MM/yyyy
                                        valor(f.cargo()) + ";" +
                                        valor(f.salario()) + ";" +
                                        valorData(f.dataContratacao()) + ";" +     // <<< formata dd/MM/yyyy
                                        valor(f.endereco() != null ? f.endereco().getLogradouro() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getNumero() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getComplemento() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getBairro() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getCidade() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getEstado() : null) + ";" +
                                        valor(f.endereco() != null ? f.endereco().getCep() : null) +
                                        System.lineSeparator()
                        );
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Falha ao excluir o funcionario");
            ex.printStackTrace();
        }
    }

    public FuncionarioDTO buscarPorMatricula(String matricula) {
        return listarFuncionarios().stream()
                .filter(f -> f.matricula() != null && f.matricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

    // ===== Helpers =====
    private String valor(Object o) {
        return o == null ? "" : o.toString();
    }

    private String valorData(LocalDate d) {           // <<< novo: formata dd/MM/yyyy
        return d == null ? "" : BR.format(d);
    }

    private LocalDate parseData(String s) {           // <<< novo: lê dd/MM/yyyy
        if (s == null || s.isEmpty()) return null;
        return LocalDate.parse(s, BR);
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
