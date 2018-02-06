/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FuncionarioDTO;
import Model.FuncionarioDAO;
import java.io.File;
import java.util.List;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FuncionarioCTR {

    public int cadastrarFuncionario(long matricula, String nome, String cargo, String email, String senha,
            String telFixo, String telCelular, String ativo, File f) {

        FuncionarioDTO funcionario = null;

        funcionario = new FuncionarioDTO(matricula, nome, cargo, senha, email, telFixo, telCelular, ativo);
        return new FuncionarioDAO().adicionarFuncionario(funcionario, f);
    }

    public int excluirFuncionario(long matricula) {

        FuncionarioDTO funcionario = new FuncionarioDTO(matricula, null, null, null, null, null, null, null);

        return new FuncionarioDAO().excluirFuncionario(funcionario);
    }

    public int alterarFuncionario(long matricula, String nome, String cargo, String email, String senha,
            String telFixo, String telCelular, String ativo, File file) {
        FuncionarioDTO funcionario = null;
        FuncionarioDTO f = this.procurarFunc(matricula);
        funcionario = new FuncionarioDTO(matricula, nome, cargo, senha, email, telFixo, telCelular, ativo, f.getImagem());

        return new FuncionarioDAO().alterarFuncionario(funcionario, file);
    }

    public List<FuncionarioDTO> listarFuncionario() {
        return new FuncionarioDAO().listarFuncionarios();
    }

    public boolean permitirAcesso(long mat, String senha) {

        FuncionarioDTO funcionario = new FuncionarioDAO().procurarFuncionario(new FuncionarioDTO(mat, "", "", "", "", "", "", ""));
        if (funcionario == null) {
            return false;
        }
        return (mat == funcionario.getMatricula() && senha.equals(funcionario.getSenha()));
    }

    public FuncionarioDTO procurarFunc(long mat) {
        return new FuncionarioDAO().procurarFuncionario(new FuncionarioDTO(mat, "", "", "", "", "", "", ""));
    }

    public FuncionarioDTO procurarFuncgERAL(long mat) {
        return new FuncionarioDAO().procurarFuncionarioGeral(new FuncionarioDTO(mat, "", "", "", "", "", "", ""));
    }

    public List<FuncionarioDTO> listarFuncionario(String nome) {
        return new FuncionarioDAO().listarFuncionarios(nome);
    }

    public void readmitirFunc(long mat) {
        FuncionarioDTO funcionario = new FuncionarioDTO(mat, "", "", "", "", "", "", "");
        new FuncionarioDAO().readmitirFunc(funcionario);
    }

    public void inserirSenha(long mat, String senha) {
        FuncionarioDTO funcionario = new FuncionarioDTO(mat, "", "", senha, "", "", "", "");
        new FuncionarioDAO().inserirSenha(funcionario);
    }
}
