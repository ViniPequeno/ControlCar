/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.CadastraCTR;
import Controller.FuncionarioCTR;
import Controller.RegistraCTR;
import Model.CadastraDTO;
import Model.FuncionarioDTO;
import Model.RegistraDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLConfirmaEntradaSaidaController implements Initializable {

    @FXML
    Label titulo, numero, placa, data, hora, funcionario;
    @FXML
    JFXTextArea obs;
    @FXML
    JFXRadioButton obsRadio;
    @FXML
    JFXButton confirma;
    @FXML
    JFXComboBox<String> nome;
    JFXComboBox<Long> codigo;
    public static FuncionarioDTO porteiro;
    public static ArrayList<CadastraDTO> cadastra;
    public static RegistraDTO registra;
    public static int estado;

    Stage saida, entrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saida = null;
        entrada = null;
        codigo = new JFXComboBox();

        if (estado == 1) {
            iniciar(new CadastraCTR().procurarEntrada(cadastra.get(0).getPlaca().getPlaca()));
            titulo.setText("Entrada");
            numero.setVisible(false);
        } else {
            iniciar(new CadastraCTR().procurarSaida(cadastra.get(0).getPlaca().getPlaca()));
            nome.getSelectionModel().select(registra.getServidorDTO().getNome());
            nome.setDisable(true);
            titulo.setText("Saida");
            numero.setVisible(true);
            numero.setText("numero: " + registra.getNumero());
        }

        placa.setText(placa.getText() + " " + cadastra.get(0).getPlaca().getPlaca());
        funcionario.setText(funcionario.getText() + " " + porteiro.getNome());
        hora.setText(getHoraAtual());
        data.setText(getDataAtual());
        obs.setDisable(true);
        obsRadio.setOnAction((event) -> {
            if (obsRadio.isSelected()) {
                obs.setDisable(false);
            } else {
                obs.setDisable(true);
            }
        });
        confirma.setOnAction((ActionEvent event) -> {
            if (obsRadio.isSelected() && obs.getText().equals("")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("insirar uma observação");
                a.showAndWait();
            } else if (estado == 1) {
                if (nome.getSelectionModel().getSelectedItem().equals("")) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Escolha o proprietário do veiculo");
                    a.showAndWait();
                } else {
                    Long x = codigo.getItems().get(nome.getSelectionModel().getSelectedIndex());
                    FuncionarioDTO f = new FuncionarioCTR().procurarFunc(x);
                    new RegistraCTR().inserirRegistroEntrada(f, porteiro, cadastra.get(0).getPlaca(), data.getText(), hora.getText());
                    if (obsRadio.isSelected() && !obs.getText().equals("")) {
                        new RegistraCTR().enviarObservacao(getNum(cadastra.get(0).getPlaca().getPlaca(),
                                data.getText(), hora.getText(), 1), "Entrada - " + obs.getText() + "\n\n");
                    }
                    ((Stage) confirma.getScene().getWindow()).fireEvent(new WindowEvent(((Stage) confirma.getScene().getWindow()), WindowEvent.WINDOW_CLOSE_REQUEST));

                }
            } else if (estado == 0) {
                if (obsRadio.isSelected() && !obs.getText().equals("")) {
                    new RegistraCTR().enviarObservacao(getNum(cadastra.get(0).getPlaca().getPlaca(),
                            data.getText(), hora.getText(), 2),
                            getObs(cadastra.get(0).getPlaca().getPlaca(), data.getText(), hora.getText()) + "Saída - " + obs.getText());
                }
                new RegistraCTR().inserirRegistroSaida(porteiro, cadastra.get(0).getPlaca(), data.getText(), hora.getText());

                ((Stage) confirma.getScene().getWindow()).fireEvent(new WindowEvent(((Stage) confirma.getScene().getWindow()), WindowEvent.WINDOW_CLOSE_REQUEST));

            }
        });
    }

    private String getDataAtual() {

        Date d = new Date();
        return java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
    }

    private String getHoraAtual() {

        Calendar tempo = Calendar.getInstance();
        System.out.println(tempo.get(Calendar.HOUR_OF_DAY) + ":" + tempo.get(Calendar.MINUTE));
        return tempo.get(Calendar.HOUR_OF_DAY) + ":" + tempo.get(Calendar.MINUTE);
    }

    private long getNum(String placa, String data, String hora, int modo) {
        if (modo == 1) {
            System.out.println(new RegistraCTR().getNumEntrada(placa, data, hora).getNumero());
            return new RegistraCTR().getNumEntrada(placa, data, hora).getNumero();
        } else {

            return new RegistraCTR().getNumSaida(placa).getNumero();
        }
    }

    private String getObs(String placa, String data, String hora) {

        RegistraDTO r = new RegistraCTR().getNumSaida(placa);
        return r.getObs() == null ? " " : r.getObs();

    }

    private void iniciar(ArrayList<CadastraDTO> u) {
        ArrayList<String> a;
        a = new ArrayList();
        ArrayList<Long> b = new ArrayList<>();
        for (CadastraDTO c : u) {
            System.out.println(c.getMatProp().getNome() + "  " + c.getMatProp().getMatricula());
            a.add(c.getMatProp().getNome());
            b.add(c.getMatProp().getMatricula());
        }
        nome.getItems().addAll(a);
        codigo.getItems().addAll(b);

    }
}
