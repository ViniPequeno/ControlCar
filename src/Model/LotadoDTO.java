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
public class LotadoDTO {

    private final ServidorDTO matFunc;
    private final SetorDTO codSetor;
    private final SimpleStringProperty dataInicio;
    private final SimpleStringProperty dataFim;

    public LotadoDTO(ServidorDTO matFunc, SetorDTO codSetor, String dataInicio) {
        this.matFunc = matFunc;
        this.codSetor = codSetor;
        this.dataInicio = new SimpleStringProperty(dataInicio);
        this.dataFim = new SimpleStringProperty("");
    }

    public ServidorDTO getMatFunc() {
        return matFunc;
    }

    public SetorDTO getCodSetor() {
        return codSetor;
    }

    public SimpleStringProperty getDataInicio() {
        return dataInicio;
    }

    public SimpleStringProperty getDataFim() {
        return dataFim;
    }

}
