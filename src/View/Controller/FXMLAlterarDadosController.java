/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Model.AdminstratorDAO;
import Model.AdminstratorDTO;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLAlterarDadosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button alterar;
    @FXML
    private TextField mat, nome, senhaV;
    @FXML
    private PasswordField senha;
    @FXML
    private RadioButton mostrar;

    private AdminstratorDTO administrador;
    private Alert a;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        senhaV.setVisible(false);
        senhaV.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("entou3");
                senha.setText(senhaV.getText());
            }
        });
        senha.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("entrou2");
                senhaV.setText(senha.getText());
            }
        });
        mostrar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (mostrar.isSelected()) {

                    senha.setVisible(false);
                    senhaV.setVisible(true);
                } else {

                    senhaV.setVisible(false);
                    senha.setVisible(true);

                }
            }
        });

        administrador = new AdminstratorDAO().lerArquivo();

        mat.setText(administrador.getMatricula());
        nome.setText(administrador.getNome());

        alterar.setDisable(true);

        mat.setOnKeyPressed((KeyEvent) -> {
            alterar.setDisable(false);
        });
        nome.setOnKeyPressed((KeyEvent) -> {
            alterar.setDisable(false);
        });
        senha.setOnKeyPressed((KeyEvent) -> {
            alterar.setDisable(false);
        });

        senhaV.setOnKeyPressed((KeyEvent) -> {
            alterar.setDisable(false);
        });

        alterar.setOnAction((EActionEvent) -> {

            if ("".equals(mat.getText()) || "".equals(nome.getText())) {

                a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Atenção!");
                a.setContentText("Os campos matrícula e nome não podem estar vazios.");
                a.showAndWait();
            } else {

                String x = administrador.getSenha();

                DialogPass dp = new DialogPass();
                Optional<String> result = dp.showAndWait();
                if (result.isPresent()) {

                    if (result.get().equals(administrador.getSenha())) {
                        System.out.println("sdgt");
                        String nome, mat, senha;
                        if ("".equals(this.senha.getText())) {
                            senha = x;
                        } else {
                            senha = this.senha.getText();
                        }

                        nome = this.nome.getText();
                        mat = this.mat.getText();

                        administrador = new AdminstratorDTO();
                        administrador.setMatricula(mat);
                        administrador.setNome(nome);
                        administrador.setSenha(senha);

                        new AdminstratorDAO().salvar(administrador);
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText("Alterado");
                        a.showAndWait();

                        ((Stage) alterar.getScene().getWindow()).fireEvent(new WindowEvent((Stage) alterar.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                    } else {
                        System.out.println(administrador.getSenha());
                        Alert dialogoExe = new Alert(Alert.AlertType.WARNING);
                        ButtonType btn = new ButtonType("Erro");
                        dialogoExe.setContentText("Senha errada");
                        dialogoExe.showAndWait();
                    }
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
            ButtonType buttonPass = new ButtonType("Confirma", ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(buttonPass, ButtonType.CANCEL);
            pass = new PasswordField();
            pass.setPromptText("Senha");
            HBox hbox = new HBox();
            hbox.getChildren().add(pass);
            hbox.setPadding(new Insets(20));
            HBox.setHgrow(pass, Priority.ALWAYS);
            File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
            try {
                ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
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
