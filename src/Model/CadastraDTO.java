/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class CadastraDTO {

    private final FuncionarioDTO matProp;
    private final FuncionarioDTO matFun;
    private final VeiculoDTO placa;
    private final SimpleStringProperty calendario;
    private final SimpleStringProperty ativo;

    public CadastraDTO(FuncionarioDTO matProp, FuncionarioDTO matFun, VeiculoDTO placa, String calendario, String ativo) {
        this.matProp = matProp;
        this.matFun = matFun;
        this.placa = placa;
        this.calendario = new SimpleStringProperty(calendario);
        this.ativo = new SimpleStringProperty(ativo);
    }

    public String getAtivo() {
        return ativo.get();
    }

    public FuncionarioDTO getMatProp() {
        return matProp;
    }

    public FuncionarioDTO getMatFun() {
        return matFun;
    }

    public VeiculoDTO getPlaca() {
        return placa;
    }

    public String getCalendario() {
        return calendario.get();
    }

}
