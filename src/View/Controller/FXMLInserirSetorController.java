/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.SetorCTR;
import Model.SetorDTO;
import com.jfoenix.controls.JFXTextField;
import static java.lang.Long.parseLong;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLInserirSetorController implements Initializable {

    @FXML
    private JFXTextField codigo, nome, ramal;
    @FXML
    private Button inserir;
    @FXML
    private Label label;

    private boolean codigoE, ramalE;

    private Alert a;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        codigoE = false;
        ramalE = false;

        if (FXMLSetorController.estado == 1) {

            codigo.setText(Long.toString(FXMLSetorController.setor.getCodigo()));
            nome.setText(FXMLSetorController.setor.getNome());
            ramal.setText(FXMLSetorController.setor.getRamal());

            inserir.setText("Alterar");
            label.setText("Alterar Setor");

            inserir.setDisable(true);

            codigo.setOnKeyPressed((KeyEvent) -> {
                inserir.setDisable(false);
            });
            nome.setOnKeyPressed((KeyEvent) -> {
                inserir.setDisable(false);
            });
            ramal.setOnKeyPressed((KeyEvent) -> {
                inserir.setDisable(false);
            });

            inserir.setOnAction((EActionEvent) -> {

                String nome, ramal;
                long codigo;

                codigo = Long.parseLong(this.codigo.getText());
                nome = this.nome.getText();
                ramal = this.ramal.getText();

                if (new SetorCTR().alterarSetor(codigo, nome, ramal) != 2) {
                    ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Setor já existe");
                    a.showAndWait();
                }
            });
        } else {

            codigo.setOnKeyPressed((KeyEvent event) -> {

                inserir.setDisable(false);
                SetorDTO d;

                if (event.getCode() != KeyCode.BACK_SPACE) {
                    d = new SetorCTR().procurarSetor(Long.parseLong(codigo.getText() + event.getText()));
                } else if (codigo.getText().length() <= 1) {
                    d = null;
                } else {
                    d = new SetorCTR().procurarSetor(Long.parseLong(codigo.getText().substring(0, codigo.getText().length() - 1)));
                }
                if (d != null) {
                    System.out.println("sfgh");
                    nome.setText(d.getNome());
                    ramal.setText(d.getRamal());
                    desabilitar();
                    if (d.getAtivo().equals("N")) {
                        inserir.setDisable(false);
                    } else {
                        inserir.setDisable(true);
                    }
                } else {
                    inserir.setDisable(false);
                    limpar();
                    habilitar();
                }
            });

            nome.setOnKeyPressed((KeyEvent) -> {
                if (nome.isEditable()) {
                    inserir.setDisable(false);
                }
            });

            ramal.setOnKeyPressed((KeyEvent) -> {
                if (ramal.isEditable()) {
                    inserir.setDisable(false);
                }
            });

            inserir.setOnAction((ActionEvent EActionEvent) -> {

                if (!nome.isEditable()) {
                    if (codigo.getText().equals("")) {
                        a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Codigo precisa estar ativo");
                        a.showAndWait();
                    } else {
                        a = new Alert(Alert.AlertType.WARNING);
                        a.setContentText("Deseja ativa esse setor?");
                        ButtonType btnSim = new ButtonType("Sim");
                        ButtonType btnNao = new ButtonType("Não");
                        a.getButtonTypes().setAll(btnSim, btnNao);
                        a.showAndWait().ifPresent(b -> {
                            if (b == btnSim) {
                                new SetorCTR().readmitirSetor(parseLong(codigo.getText()));
                                ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                            }
                        });

                    }
                } else if ("".equals(codigo.getText()) || "".equals(nome.getText()) || "".equals(ramal.getText())) {

                    a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Atenção!");
                    a.setContentText("Todos os campos são obrigatórios");

                    a.showAndWait();
                } else {

                    String nome, ramal = null;
                    long codigo = 0;
                    boolean erro = false;

                    try {
                        codigo = Long.parseLong(this.codigo.getText());
                    } catch (NumberFormatException ex) {

                        this.codigo.setText("");
                        this.codigo.setStyle("-fx-border-color: red;");
                        erro = true;
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Campo código inválido");
                        a.showAndWait();
                    }
                    nome = this.nome.getText();
                    try {
                        long aux = Long.parseLong(this.ramal.getText());
                        ramal = this.ramal.getText();
                    } catch (NumberFormatException ex) {

                        this.ramal.setText("");
                        this.ramal.setStyle("-fx--color: red;");
                        erro = true;
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Campo ramal inválido");
                        a.showAndWait();
                    }

                    if (!erro) {
                        if (new SetorCTR().cadastrarSetor(codigo, nome, ramal) != 2) {
                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setContentText("Cadastrado");
                            a.showAndWait();
                            ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setContentText("Setor já existe");
                            a.showAndWait();
                        }
                    }
                }
            });
        }
    }

    private void desabilitar() {
        nome.setEditable(false);
        ramal.setEditable(false);
    }

    private void habilitar() {
        nome.setEditable(true);
        ramal.setEditable(true);
    }

    private void limpar() {
        nome.setText("");
        ramal.setText("");
    }

}
