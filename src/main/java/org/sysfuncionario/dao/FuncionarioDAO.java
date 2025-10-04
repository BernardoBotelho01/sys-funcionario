package org.sysfuncionario.dao;

import org.sysfuncionario.model.Funcionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FuncionarioDAO {

    private static final String CAMINHO_CSV = "funcionario.csv";

    public void cadastrarFuncionario(Funcionario funcionario) {
        try {
            FileWriter salvarFuncionario = new FileWriter(CAMINHO_CSV, true);
            salvarFuncionario.write(
                    funcionario.getMatricula() + "," +
                            funcionario.getNome() + "," +
                            funcionario.getCpf() + "," +
                            funcionario.getDataNascimento() + "," +
                            funcionario.getCargo() + "," +
                            funcionario.getSalario() + "," +
                            funcionario.getDataContratacao() + "," +
                            funcionario.getEndereco() + "\n"
            );
            salvarFuncionario.close();
        } catch (IOException ex) {
            System.out.println("Erro ao na gravação de arquivo");
        }
    }

    public List<Funcionario> listarFuncionario() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            BufferedReader lerCsv = new BufferedReader(new FileReader(CAMINHO_CSV));
            String linha;
            while ((linha = lerCsv.readLine()) != null){
                String[] valores = linha.split(",");

                if (valores.length >= 14){

                }
            }
        }catch (IOException ex){

        }
        return funcionarios;
    }
}
