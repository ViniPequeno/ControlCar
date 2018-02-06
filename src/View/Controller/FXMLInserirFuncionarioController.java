/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.FuncionarioCTR;
import Model.FuncionarioDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import static java.lang.Long.parseLong;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class FXMLInserirFuncionarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField nome, mat, email, telFixo, telCelular, cargo;
    @FXML
    private JFXComboBox combo;
    @FXML
    private JFXButton inserir;

    private Alert a;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargo.setVisible(false);
        List<String> l = new ArrayList<>();
        l.add("GAM");
        l.add("Porteiro");

        combo.setItems(FXCollections.observableList(l));

        mat.setOnKeyPressed((KeyEvent event) -> {

            FuncionarioDTO d;
            if (event.getCode() != KeyCode.BACK_SPACE) {
                d = new FuncionarioCTR().procurarFuncgERAL(Long.parseLong(mat.getText() + event.getText()));
            } else if (mat.getText().length() <= 1) {
                d = null;
            } else {
                d = new FuncionarioCTR().procurarFuncgERAL(Long.parseLong(mat.getText().substring(0, mat.getText().length() - 1)));
            }

            if (d != null) {
                System.out.println("sfgh");
                nome.setText(d.getNome());
                email.setText(d.getEmail());
                telCelular.setText(d.getTelCelular());
                telFixo.setText(d.getTelFixo());
                cargo.setText(d.getCargo());
                combo.setVisible(false);
                cargo.setVisible(true);
                if (d.getAtivo().equals("N")) {
                    inserir.setDisable(false);
                } else {
                    inserir.setDisable(true);
                }
                desabilitar();

            } else {
                cargo.setVisible(false);
                combo.setVisible(true);
                inserir.setDisable(false);
                limpar();
                habilitar();
            }
        });
        inserir.setOnAction((EActionEvent) -> {
            if (!nome.isEditable()) {
                if (mat.getText().equals("")) {
                    a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Atenção!");
                    a.setContentText("O campo matrícula é obrigatório");
                    a.showAndWait();
                } else {
                    a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Deseja ativa esse funcionário?");
                    ButtonType btnSim = new ButtonType("Sim");
                    ButtonType btnNao = new ButtonType("Não");
                    a.getButtonTypes().setAll(btnSim, btnNao);
                    a.showAndWait().ifPresent(b -> {
                        if (b == btnSim) {
                            new FuncionarioCTR().readmitirFunc(parseLong(mat.getText()));
                            ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                        }
                    });

                }
            } else if ("".equals(nome.getText()) || "".equals(mat.getText())) {

                a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Atenção!");
                a.setContentText("Os campos nome, matrícula  são obrigatórios");
                a.showAndWait();
            } else if (combo.getSelectionModel().getSelectedItem() == null || combo.getSelectionModel().getSelectedItem().equals("")) {

                a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Atenção!");
                a.setContentText("Por favor, selecione um cargo para o funcionário.");
                a.showAndWait();
            } else {

                String nome, email, telFixo, telCelular, cargo;
                long matricula;

                matricula = Long.parseLong(this.mat.getText());
                nome = this.nome.getText();
                email = this.email.getText();

                telFixo = this.telFixo.getText();
                telCelular = this.telCelular.getText();
                cargo = (String) this.combo.getSelectionModel().getSelectedItem();

                File file = new File(System.getProperty("user.dir") + "\\src\\Imagens\\man-user.png");

                int i = new FuncionarioCTR().cadastrarFuncionario(matricula, nome, cargo, email, "", telFixo, telCelular, cargo, file);

                if (i == 0) {

                    a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erro.");
                    a.setContentText("SQLERROR.");
                    a.showAndWait();
                } else if (i == 2) {
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erro.");
                    a.setContentText("Funcionário já existe.");
                    a.showAndWait();
                } else {
                    a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Cadastro.");
                    a.setContentText("Funcionário cadastrado!.\nSenha padrão : 123");
                    a.showAndWait();
                    ((Stage) inserir.getScene().getWindow()).fireEvent(new WindowEvent((Stage) inserir.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                }

            }
        });
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

    void limpar() {
        nome.setText("");
        email.setText("");
        telFixo.setText("");
        telCelular.setText("");

    }
}
