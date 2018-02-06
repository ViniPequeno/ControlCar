/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CadastraDAO;
import Model.CadastraDTO;
import Model.FuncionarioDTO;
import Model.VeiculoDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class CadastraCTR {

    public int adicionarCadastra(FuncionarioDTO matProp, FuncionarioDTO matFun, VeiculoDTO placa) {
        Date data = new Date();
        SimpleDateFormat calendario = new SimpleDateFormat("dd/MM/yyyy");

        CadastraDTO c = new CadastraDTO(matProp, matFun, placa, calendario.format(data), "S");
        return new CadastraDAO().adicionar(c);
    }

    public void excluirCadastra(Long cod, String placa) {
        new CadastraDAO().exluir(placa, cod);
    }

    public void readmitirCadastra(Long cod, String placa) {
        new CadastraDAO().readmitirCadastra(placa, cod);
    }

    public ArrayList<CadastraDTO> listarVeiculo() {
        return new CadastraDAO().listar();
    }

    public ArrayList<CadastraDTO> listarVeiculo(String nome) {
        return new CadastraDAO().listar(nome);
    }

    public ArrayList<CadastraDTO> procurarEntrada(String placa) {
        return new CadastraDAO().procurarEntrada(new VeiculoDTO(placa, "", "", "", ""));
    }

    public ArrayList<CadastraDTO> procurarSaida(String placa) {
        return new CadastraDAO().procurarSaida(new VeiculoDTO(placa, "", "", "", ""));
    }
}
