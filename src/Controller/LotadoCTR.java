/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LotadoDAO;
import Model.LotadoDTO;
import Model.ServidorDTO;
import Model.SetorDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class LotadoCTR {

    public int cadastrarLotado(ServidorDTO matServ, SetorDTO codSetor) {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return new LotadoDAO().adicionarLotado(matServ.getMatricula(), codSetor.getCodigo(), f.format(d));
    }

    public int excluirLotado(Long matricula) {

        return new LotadoDAO().excluirLotado(matricula);
    }

    public LotadoDTO procurarServidor(Long matricula) {
        LotadoDTO l = new LotadoDAO().procurarLotado(matricula);
        if (l == null) {
            return null;
        }
        return l;

    }

    public void trocarLotato(ServidorDTO matServ, SetorDTO codSetor) {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        new LotadoDAO().trocarLotado(matServ.getMatricula(), codSetor.getCodigo(), f.format(d));
    }

    public ArrayList<LotadoDTO> listar() {
        return new LotadoDAO().listar();
    }

    public ArrayList<LotadoDTO> listar(String nome) {
        return new LotadoDAO().listar(nome);
    }
}
