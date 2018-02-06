/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.VeiculoDAO;
import Model.VeiculoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class VeiculoCTR {

    private VeiculoDTO veiculo;

    public int cadastraVeiculo(String placa, String cor, String marca, String modelo, String anoFabri) {
        VeiculoDTO v = new VeiculoDTO(placa, marca, cor, modelo, anoFabri);
        return new VeiculoDAO().adicionar(v);

    }

    public List<VeiculoDTO> listarVeiculo() {

        return new VeiculoDAO().listar();
    }

    public VeiculoDTO pesquisarVeiculo(String placa) {
        return new VeiculoDAO().pesquisa(placa);
    }

    public ArrayList<VeiculoDTO> listarVeiculo(String palavra) {
        return new VeiculoDAO().listarFiltrado(palavra);
    }

    public VeiculoDTO pesquisar(String placa) {
        if (new VeiculoDAO().pesquisaVeiculo(placa) == null) {
            System.out.println("Gab");
            return null;
        } else {
            System.out.println("Ped");
            return new VeiculoDAO().pesquisaVeiculo(placa);
        }
    }
}
