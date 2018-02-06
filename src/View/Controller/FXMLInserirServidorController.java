/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Controller.*;
import Model.LotadoDTO;
import Model.ServidorDTO;
import Model.SetorDTO;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLInserirServidorController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private JFXButton inserir;
    @FXML
    private JFXTextField mat, nome, email, telFixo, telCelular, setor;
    @FXML
    private JFXComboBox<String> cargo;
    JFXComboBox<Long> codigo;

    private static ServidorDTO s;
    private static LotadoDTO l;
    public static int estado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setor.setVisible(false);
        codigo = new JFXComboBox<>();
        if (estado == 1) {
            System.out.println("ENTROU NO MODO ALTERAR");
            s = FXMLServidorController.servidor;
            l = FXMLServidorController.lotado;
            mat.setText(s.getMatricula() + "");
            mat.setDisable(false);
            nome.setText(s.getNome());
            email.setText(s.getEmail());
            telFixo.setText(s.getTelFixo());
            telCelular.setText(s.getTelCelular());
            inserir.setText("Alterar");
            cargo.getSelectionModel().select(l.getCodSetor().getNome());

        } else {
            limparCampos();
            mat.setText("");
        }

        ArrayList<String> a = new ArrayList<>();
        ArrayList<Long> al = new ArrayList<>();
        a.add("");
        al.add(Long.parseLong("0"));
        for (SetorDTO e : new SetorCTR().listarSetor()) {
            a.add(e.getNome());
            al.add(e.getCodigo());
        }
        cargo.getItems().addAll(a);
        codigo.getItems().addAll(al);
        cargo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                codigo.getSelectionModel().select(cargo.getSelectionModel().getSelectedIndex());
            }
        });

        mat.setOnKeyPressed((KeyEvent event) -> {
            LotadoDTO d;

            if (event.getCode() != KeyCode.BACK_SPACE) {
                d = new LotadoCTR().procurarServidor(Long.parseLong(mat.getText() + event.getText()));
            } else if (mat.getText().length() <= 1) {
                d = null;
            } else {
                d = new LotadoCTR().procurarServidor(Long.parseLong(mat.getText().substring(0, mat.getText().length() - 1)));
            }

            if (d != null) {
             
                nome.setText(d.getMatFunc().getNome());
                email.setText(d.getMatFunc().getEmail());
                telCelular.setText(d.getMatFunc().getTelCelular());
                telFixo.setText(d.getMatFunc().getTelFixo());
                cargo.setVisible(false);
                setor.setText(d.getCodSetor().getNome());
                setor.setVisible(true);
                System.out.println(d.getMatFunc().getAtivo());
                if (d.getMatFunc().getAtivo().equals("N")) {
                    inserir.setDisable(false);
                } else {
                    inserir.setDisable(true);
                }
                desabilitar();
            } else {
                limparCampos();
                setor.setVisible(false);
                cargo.setVisible(true);
                inserir.setDisable(false);
                habilitar();
            }
        });

        inserir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!nome.isEditable()) {
                    if (mat.getText().equals("")) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Matricula não pode estar em branco");
                        a.showAndWait();
                    } else {
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        ButtonType btnSim = new ButtonType("Sim");
                        ButtonType btnNao = new ButtonType("Não");
                        a.setContentText("Ativa esse servidor?");
                        a.getButtonTypes().setAll(btnSim, btnNao);
                        a.showAndWait().ifPresent(b -> {
                            if (b == btnSim) {
                                new FuncionarioCTR().readmitirFunc(Long.parseLong(mat.getText()));
                                ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                            }
                        });

                    }
                } else if (mat.getText().equals("") || nome.getText().equals("")) {
                    Alert t = new Alert(Alert.AlertType.ERROR);
                    t.setContentText("Matricula e nome não podem está branco");
                    t.showAndWait();
                } else if (cargo.getSelectionModel().getSelectedItem() == null || cargo.getSelectionModel().getSelectedItem().equals("")) {
                    Alert t = new Alert(Alert.AlertType.ERROR);
                    t.setContentText("Por favor, selecione um setor para o servidor.");
                    t.showAndWait();
                } else {
                    if (estado != 1) {
                        ServidorDTO serv = new ServidorDTO(Long.parseLong(mat.getText()),
                                nome.getText(), email.getText(), telFixo.getText(), telCelular.getText(), "");
                        SetorDTO setor = new SetorCTR().procurarSetor(codigo.getSelectionModel().getSelectedItem());
                        if (new ServidorCTR().cadastrarServidor(serv.getMatricula(), serv.getNome(), serv.getEmail(),
                                serv.getTelFixo(), serv.getTelCelular()) == 1) {
                            new LotadoCTR().cadastrarLotado(serv, setor);
                            Alert t = new Alert(Alert.AlertType.CONFIRMATION);
                            t.setContentText("Servidor cadastrado!");
                            limparCampos();
                            mat.setText("");
                            t.showAndWait();
                        } else {
                            Alert t = new Alert(Alert.AlertType.ERROR);
                            t.setContentText("Servidor já existe");
                            t.showAndWait();
                        }

                    } else {
                        ServidorDTO serv = new ServidorDTO(Long.parseLong(mat.getText()),
                                nome.getText(), email.getText(), telFixo.getText(), telCelular.getText(), "");

                        if (new ServidorCTR().alterarServidor(serv.getMatricula(), serv.getNome(), serv.getEmail(),
                                serv.getTelFixo(), serv.getTelCelular()) == 1) {
                            if (!(cargo.getSelectionModel().getSelectedItem().equals(l.getCodSetor().getNome()))) {
                                SetorDTO setor = new SetorCTR().procurarSetor(codigo.getSelectionModel().getSelectedItem());
                                new LotadoCTR().trocarLotato(serv, setor);
                            }
                            Alert t = new Alert(Alert.AlertType.CONFIRMATION);
                            t.setContentText("Servidor alterado!");
                            limparCampos();
                            mat.setText("");
                            t.showAndWait();
                            ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                        } else {
                            Alert t = new Alert(Alert.AlertType.ERROR);
                            t.setContentText("Servidor já existe");
                            t.showAndWait();
                        }

                    }
                }
            }
        });
    }

    void limparCampos() {
        cargo.getSelectionModel().selectFirst();
        nome.setText("");
        email.setText("");
        telFixo.setText("");
        telCelular.setText("");
    }

    void habilitar() {
        nome.setEditable(true);
        email.setEditable(true);
        telFixo.setEditable(true);
        telCelular.setEditable(true);

    }

    void desabilitar() {
        nome.setEditable(false);
        email.setEditable(false);
        telFixo.setEditable(false);
        telCelular.setEditable(false);

    }

}
