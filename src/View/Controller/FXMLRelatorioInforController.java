/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Model.RegistraDTO;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLRelatorioInforController implements Initializable {

    @FXML
    private Label placa, prop, dataHora1, dataHora2, porteiroEn, porteiroSa;
    @FXML
    private JFXTextArea obs;

    /**
     * Initializes the controller class.
     */
    public static RegistraDTO r;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        placa.setText(r.getVeiculo().getPlaca());
        prop.setText(r.getServidorDTO().getNome());
        porteiroEn.setText(r.getFuncionarioEntrada().getNome());
        if (r.getFuncionarioSaida() == null) {
            porteiroSa.setText("---");
        } else {
            porteiroSa.setText(r.getFuncionarioSaida().getNome());
        }
        dataHora1.setText("Entrada - Data: " + r.getDataEntrada() + " Hora: " + r.getHoraEntrada());
        if (r.getDataSaida() == null) {
            dataHora2.setText("---");
        } else {
            dataHora2.setText("Saida - Data: " + r.getDataSaida() + " Hora: " + r.getHoraSaida());
        }
        obs.setText(r.getObs());
    }

}
