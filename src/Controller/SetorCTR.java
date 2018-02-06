/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.SetorDAO;
import Model.SetorDTO;
import java.util.List;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class SetorCTR {

    public int cadastrarSetor(long codigo, String nome, String ramal) {

        SetorDTO setor = new SetorDTO(codigo, nome, ramal, "S");

        return new SetorDAO().adicionarSetor(setor);
    }

    public int excluirSetor(long codigo) {

        SetorDTO setor = new SetorDTO(codigo, "", "", "");

        return new SetorDAO().excluirSetor(setor);
    }

    public int alterarSetor(long codigo, String nome, String ramal) {
        System.out.println("dfrsvbc");
        SetorDTO setor = new SetorDTO(codigo, nome, ramal, "S");

        return new SetorDAO().alterarSetor(setor);
    }

    public List<SetorDTO> listarSetor() {

        return new SetorDAO().listarSetores();
    }

    public List<SetorDTO> listarSetorNome(String pesquisa) {

        return new SetorDAO().listarSetores(pesquisa);
    }

    public SetorDTO procurarSetor(long cod) {
        SetorDTO d = new SetorDAO().procurarSetor(cod);
        if (d == null) {
            return null;
        } else {
            return d;
        }
    }

    public void readmitirSetor(long codigo) {

        new SetorDAO().readmitirSetor(codigo);
    }
}
