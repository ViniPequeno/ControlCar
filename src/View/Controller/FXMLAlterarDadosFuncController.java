/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.FuncionarioCTR;
import Model.FuncionarioDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLAlterarDadosFuncController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField nome, mat, email, telFixo, telCelular, senhaV;
    @FXML
    private JFXPasswordField senha;
    @FXML
    private JFXButton trocar, alterar;
    @FXML
    private JFXRadioButton mostrar;
    private FuncionarioDTO f;
    private File fi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        senhaV.setVisible(false);
        senhaV.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                senha.setText(senhaV.getText());
            }
        });
        senha.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                senhaV.setText(senha.getText());
            }
        });
        mostrar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (mostrar.isSelected()) {

                    senha.setVisible(false);
                    senhaV.setVisible(true);
                    alterar.setDisable(false);
                } else {

                    senhaV.setVisible(false);
                    senha.setVisible(true);
                    alterar.setDisable(false);

                }
            }
        });

        if (FXMLPorteiroController.f == null) {
            f = FXMLGAMController.f;
        } else {
            f = FXMLPorteiroController.f;
        }
        nome.setText(f.getNome());
        mat.setText("" + f.getMatricula());
        mat.setEditable(false);
        email.setText(f.getEmail());
        telFixo.setText(f.getTelFixo());
        telCelular.setText(f.getTelCelular());
        alterar.setDisable(true);
        nome.setOnKeyTyped((KeyEvent event) -> {
            alterar.setDisable(false);
        });
        mat.setOnKeyTyped((KeyEvent event) -> {
            alterar.setDisable(false);
        });
        email.setOnKeyTyped((KeyEvent event) -> {
            alterar.setDisable(false);
        });
        telFixo.setOnKeyTyped((KeyEvent event) -> {
            alterar.setDisable(false);
        });
        telCelular.setOnKeyTyped((KeyEvent event) -> {
            alterar.setDisable(false);
        });

        trocar.setOnAction((ActionEvent e) -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Trocar Imagem");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            fi = fc.showOpenDialog(trocar.getScene().getWindow());

            try {
                if (fi != null) {

                    Image i = new Image(fi.toURI().toURL().toExternalForm());
                    alterar.setDisable(false);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Imagem trocado");
                    a.showAndWait();
                }
            } catch (MalformedURLException ex) {
                new RuntimeException(ex);
            }
        });
        alterar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String senha1;
                if (mat.getText().equals("") || nome.getText().equals("")) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Todos os campos devem ser preenchidos");
                    a.showAndWait();
                }
                if (senha.getText().equals("")) {
                    senha1 = f.getSenha();
                } else {
                    senha1 = senha.getText();
                }
                if (senha1.length() >= 6 && senha1.length() <= 100) {
                    DialogPass dp = new DialogPass();
                    Optional<String> result = dp.showAndWait();
                    if (result.isPresent()) {
                        if (result.get().equals(f.getSenha())) {
                            new FuncionarioCTR().alterarFuncionario(Long.parseLong(mat.getText()), nome.getText(),
                                    f.getCargo().substring(0, 1), email.getText(),
                                    senha1, telFixo.getText(), telCelular.getText(),
                                    f.getAtivo().substring(0, 1), fi);
                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            a.setContentText("Feito!");
                            a.showAndWait();
                            ((Stage) alterar.getScene().getWindow()).fireEvent(new WindowEvent(((Stage) alterar.getScene().getWindow()), WindowEvent.WINDOW_CLOSE_REQUEST));
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setContentText("Senha incorreta");
                            a.showAndWait();
                        }
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Senha não pode conter menos de 5 ou mais de 100 caracteres");
                    a.showAndWait();
                }

            }
        });
    }

    public class DialogPass extends Dialog<String> {

        private PasswordField pass;

        public DialogPass() {
            setResizable(false);
            setTitle("Senha");
            setHeaderText("Entre com sua senha");
            ButtonType buttonPass = new ButtonType("Confirma", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(buttonPass, ButtonType.CANCEL);
            pass = new PasswordField();
            pass.setPromptText("Senha");
            HBox hbox = new HBox();
            hbox.getChildren().add(pass);
            hbox.setPadding(new Insets(20));
            HBox.setHgrow(pass, Priority.ALWAYS);
            File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
            try {
                ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
            } catch (MalformedURLException ex) {
                System.out.println(ex.getMessage());;
            }
            getDialogPane().setContent(hbox);
            Platform.runLater(() -> pass.requestFocus());
            setResultConverter(dialogButton -> {
                if (dialogButton == buttonPass) {
                    return pass.getText();
                }
                return null;

            });
        }

        public PasswordField getPasseordField() {
            return pass;
        }
    }
}
