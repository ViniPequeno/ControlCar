package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class VeiculoDTO {

    SimpleStringProperty placa;
    SimpleStringProperty marca;
    SimpleStringProperty cor;
    SimpleStringProperty modelo;
    SimpleStringProperty anoFabri;

    public VeiculoDTO(String placa, String marca, String cor, String modelo, String anoFabri) {
        this.placa = new SimpleStringProperty(placa);
        this.marca = new SimpleStringProperty(marca);
        this.cor = new SimpleStringProperty(cor);
        this.modelo = new SimpleStringProperty(modelo);
        this.anoFabri = new SimpleStringProperty(anoFabri);
    }

    public String getPlaca() {
        return placa.get();
    }

    public String getMarca() {
        return marca.get();
    }

    public String getCor() {
        return cor.get();
    }

    public String getModelo() {
        return modelo.get();
    }

    public String getAnoFabri() {
        return anoFabri.get();
    }

}
