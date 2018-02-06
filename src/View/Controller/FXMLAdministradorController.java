/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLAdministradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView imagem;
    @FXML
    private JFXButton btnSetor, btnFuncionario, btnAlterarDados, btnSair;
    @FXML
    public StackPane stack;

    private Pane setor, funcionario;
    private Stage alterar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setor = null;
        funcionario = null;
        alterar = null;

        btnSetor.setOnAction((javafx.event.ActionEvent ActionEvent) -> {
            colocarBorda(btnSetor);
            if (setor == null) {
                funcionario = null;
                try {
                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLSetor.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());
                    Parent root = (Pane) FXMLLoader.load(sl);
                    setor = new Pane(root);
                    stack.getChildren().add(setor);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnFuncionario.setOnAction((javafx.event.ActionEvent ActionEvent) -> {
            colocarBorda(btnFuncionario);
            if (funcionario == null) {
                setor = null;
                try {
                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLFuncionario.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());
                    Parent root = (Pane) FXMLLoader.load(sl);
                    funcionario = new Pane(root);
                    stack.getChildren().add(funcionario);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnAlterarDados.setOnAction((EActionEvent) -> {
            if (alterar == null) {

                try {

                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLAlterarDados.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    alterar = new Stage();
                    alterar.setScene(new Scene(root));
                    File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                    alterar.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    alterar.setTitle("Alterar Dados");
                    alterar.setResizable(false);
                    alterar.centerOnScreen();
                    alterar.show();

                    alterar.setOnCloseRequest((WindowEvent) -> {
                        alterar.close();
                        alterar = null;
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnSair.setOnAction(
                (EActionEvent) -> {

                    try {
                        System.out.println("dcwfrreif");
                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLTelaLogin.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());
                        Parent root = FXMLLoader.load(sl);
                        Stage s = (Stage) btnSair.getScene().getWindow();
                        s.setScene(new Scene(root));
                        s.setResizable(false);
                        s.setTitle("Tela login");
                        s.centerOnScreen();
                        s.show();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                });

    }

    public void colocarBorda(Node e) {

        btnSetor.setStyle("-fx-border-color:transparent");
        btnFuncionario.setStyle("-fx-border-color:transparent");
        e.setStyle("-fx-border-color:maroon");
    }
}
