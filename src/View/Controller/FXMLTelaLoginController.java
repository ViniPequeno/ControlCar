/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.AdminstratorCTR;
import Controller.FuncionarioCTR;
import Model.AdminstratorDTO;
import Model.FuncionarioDTO;
import java.io.File;
import java.io.IOException;
import static java.lang.Long.parseLong;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLTelaLoginController implements Initializable {

    @FXML
    private TextField mat;
    @FXML
    private PasswordField senha;
    @FXML
    private Button finalizar;

    public static FuncionarioDTO funcionario = null;

    private Parent root;
    private URL url;
    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLFuncionarioController.funcionario = null;
        FXMLGAMController.f = null;
        FXMLPorteiroController.f = null;
        finalizar.setOnAction((ActionEvent event) -> {

            AdminstratorDTO adm;
            String mat = this.mat.getText();
            String senha = this.senha.getText();

            if ("".equals(mat) || "".equals(senha)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Todos os campos devem ser preenchidos.");
                alert.showAndWait();
            } else {
                if (new AdminstratorCTR().permitirAcesso(mat, senha)) {

                    try {
                        file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLAdministrador.fxml");
                        url = new URL(file.toURI().toURL().toExternalForm());
                        root = FXMLLoader.load(url);
                        Stage stage = (Stage) this.mat.getScene().getWindow();
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.centerOnScreen();
                        stage.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (new FuncionarioCTR().permitirAcesso(parseLong(mat), senha)) {

                    funcionario = new FuncionarioCTR().procurarFunc(parseLong(mat));
                    if (funcionario.getAtivo().equals("N")) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Você não tem mais acesso ao sistema");
                        a.showAndWait();
                    } else {
                        if (funcionario.getSenha().equals("123")) {
                            DialogPass x = new DialogPass();

                            Optional<String> result = x.showAndWait();
                            if (result.isPresent()) {
                                Alert a;
                                if (result.get().equals("")) {
                                    a = new Alert(Alert.AlertType.ERROR);
                                    a.setContentText("A senha não pode ficar em branco");
                                    a.showAndWait();
                                } else if (result.get().length() <= 5 || result.get().length() > 100) {
                                    a = new Alert(Alert.AlertType.ERROR);
                                    a.setContentText("A senha precisa ser maior de 6 e menos de 100 caracteres");
                                    a.showAndWait();
                                } else {
                                    new FuncionarioCTR().inserirSenha(funcionario.getMatricula(), result.get());
                                    TelaFuncionario();
                                }
                            }
                        } else {
                            TelaFuncionario();
                        }

                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nenhum cadastro encontrado.");
                    alert.showAndWait();
                }
            }
        });
    }

    private class DialogPass extends Dialog<String> {

        private PasswordField pass;
        private TextField passV;
        private RadioButton mostrar;

        public DialogPass() {

            setResizable(false);
            setTitle("Senha");
            setHeaderText("Digite sua senha nova");
            ButtonType buttonPass = new ButtonType("Confirma", ButtonBar.ButtonData.FINISH);
            getDialogPane().getButtonTypes().addAll(buttonPass);

            pass = new PasswordField();
            pass.setPromptText("Senha");
            passV = new TextField();

            passV.setPromptText("Senha");
            mostrar = new RadioButton("mostrar senha");

            passV.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.BACK_SPACE) {
                        pass.setText(passV.getText().substring(0, passV.getText().length() - 1));
                    } else {
                        pass.setText(passV.getText() + event.getText());
                    }
                }
            });
            pass.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.BACK_SPACE) {
                        passV.setText(pass.getText().substring(0, pass.getText().length() - 1));
                    } else {
                        passV.setText(pass.getText() + event.getText());
                    }
                }
            });

            HBox hbox = new HBox();

            hbox.getChildren().add(pass);
            hbox.getChildren().add(mostrar);
            File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");

            try {
                ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(new Image(f.toURI().toURL().toExternalForm()));

            } catch (MalformedURLException ex) {
                System.out.println(ex.getMessage());
            }
            mostrar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (mostrar.isSelected()) {
                        hbox.getChildren().remove(mostrar);
                        hbox.getChildren().remove(pass);
                        hbox.getChildren().add(passV);
                        hbox.getChildren().add(mostrar);

                    } else {
                        hbox.getChildren().remove(mostrar);
                        hbox.getChildren().remove(passV);
                        hbox.getChildren().add(pass);
                        hbox.getChildren().add(mostrar);

                    }
                }
            });
            HBox.setHgrow(pass, Priority.ALWAYS);
            HBox.setHgrow(passV, Priority.SOMETIMES);
            HBox.setHgrow(mostrar, Priority.ALWAYS);
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

    public void TelaFuncionario() {
        if (funcionario.getCargo().equals("Porteiro")) {
            try {
                file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLPorteiro.fxml");
                url = new URL(file.toURI().toURL().toExternalForm());
                root = FXMLLoader.load(url);
                Stage stage = (Stage) this.mat.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.hide();
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLGAM.fxml");
                url = new URL(file.toURI().toURL().toExternalForm());
                root = FXMLLoader.load(url);
                Stage stage = (Stage) this.mat.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.hide();
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
