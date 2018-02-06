/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class RegistraDTO {

    private final SimpleLongProperty numero;
    private final FuncionarioDTO funcionarioEntrada;
    private final FuncionarioDTO matProp;
    private final FuncionarioDTO funcionarioSaida;
    private final VeiculoDTO veiculo;
    private final SimpleStringProperty dataEntrada;
    private final SimpleStringProperty horaEntrada;
    private final SimpleStringProperty dataSaida;
    private final SimpleStringProperty horaSaida;
    private final SimpleStringProperty obs;
    private final SimpleStringProperty ativo;

    public RegistraDTO(Long numero, FuncionarioDTO matProp, FuncionarioDTO funcionarioEntrada, FuncionarioDTO funcionarioSaida, VeiculoDTO veiculo, String dataEntrada, String horaEntrada, String dataSaida, String horaSaida, String obs, String ativo) {
        this.funcionarioEntrada = funcionarioEntrada;
        this.funcionarioSaida = funcionarioSaida;
        this.veiculo = veiculo;
        this.dataEntrada = new SimpleStringProperty(dataEntrada);
        this.horaEntrada = new SimpleStringProperty(horaEntrada);
        this.dataSaida = new SimpleStringProperty(dataSaida);
        this.horaSaida = new SimpleStringProperty(horaSaida);
        this.obs = new SimpleStringProperty(obs);
        this.ativo = new SimpleStringProperty(ativo);
        this.numero = new SimpleLongProperty(numero);
        this.matProp = matProp;
    }

    public FuncionarioDTO getServidorDTO() {
        return matProp;
    }

    public long getNumero() {
        return numero.get();
    }

    public FuncionarioDTO getFuncionarioEntrada() {
        return funcionarioEntrada;
    }

    public FuncionarioDTO getFuncionarioSaida() {
        return funcionarioSaida;
    }

    public VeiculoDTO getVeiculo() {
        return veiculo;
    }

    public String getDataEntrada() {
        return dataEntrada.get();
    }

    public String getHoraEntrada() {
        return horaEntrada.get();
    }

    public String getDataSaida() {
        return dataSaida.get();
    }

    public String getHoraSaida() {
        return horaSaida.get();
    }

    public String getObs() {
        return this.obs.get();
    }

}
