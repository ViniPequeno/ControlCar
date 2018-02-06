/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.AdminstratorCTR;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLCadastrarAdmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField nome, mat;
    @FXML
    private PasswordField senha, confirmar;
    @FXML
    private Button cadastrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cadastrar.setOnAction((ActionEvent e) -> {

            String nome = this.nome.getText();
            String mat = this.mat.getText();
            String senha = this.senha.getText();
            String confirmar = this.confirmar.getText();

            if (("".equals(nome)) || ("".equals(mat)) || ("".equals(senha))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Todos os campos devem ser preenchidos.");
                a.showAndWait();
            } else {

                if (senha.equals(confirmar)) {
                    new AdminstratorCTR().cadastra(nome, mat, senha);

                    try {

                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLTelaLogin.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());
                        Parent root = FXMLLoader.load(sl);
                        Stage stage = (Stage) this.nome.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.centerOnScreen();
                        stage.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {

                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("As senhas precisam ser iguais.");
                    a.showAndWait();
                }
            }
        });
    }
}
