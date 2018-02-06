/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AdminstratorDAO;
import Model.AdminstratorDTO;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class AdminstratorCTR {

    public void cadastra(String nome, String matricula, String senha) {

        AdminstratorDTO adm = new AdminstratorDTO();
        adm.setNome(nome);
        adm.setSenha(senha);
        adm.setMatricula(matricula);
        new AdminstratorDAO().salvar(adm);
    }

    public boolean permitirAcesso(String matricula, String senha) {

        AdminstratorDTO adm = new AdminstratorDTO();
        adm.setSenha(senha);
        adm.setMatricula(matricula);
        AdminstratorDAO admDAO = new AdminstratorDAO();
        if (admDAO.permitirAcesso(adm) == true) {
            return true;
        }

        return false;

    }

    public AdminstratorDTO lerArquivo() {
        AdminstratorDTO adm = null;
        AdminstratorDAO admDAO = new AdminstratorDAO();
        adm = admDAO.lerArquivo();
        System.out.println(adm.getNome() + "  " + adm.getSenha());
        return adm;
    }

    public void AlterarArquivo(AdminstratorDTO adm) {

        new AdminstratorDAO().salvar(adm);
    }
}
