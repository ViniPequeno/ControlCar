/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.FuncionarioCTR;
import Model.FuncionarioDTO;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class FXMLGAMController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnVeiculo, btnServidor, btnAlterarDados, btnSair;
    @FXML
    private ImageView imagem;
    @FXML
    private StackPane stack;

    private Pane veiculo, servidor;
    private Stage alterar;

    private File file;
    private URL sl;
    private Parent root;
    public static FuncionarioDTO f = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imagem();

        veiculo = servidor = null;
        alterar = null;

        btnVeiculo.setOnAction((javafx.event.ActionEvent ActionEvent) -> {
            colocarBorda(btnVeiculo);
            if (veiculo == null) {
                servidor = null;
                try {
                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLVeiculo.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = (Pane) FXMLLoader.load(sl);
                    veiculo = new Pane(root);
                    stack.getChildren().add(veiculo);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        });

        btnServidor.setOnAction((javafx.event.ActionEvent ActionEvent) -> {
            colocarBorda(btnServidor);
            if (servidor == null) {
                veiculo = null;
                try {
                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLServidor.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = (Pane) FXMLLoader.load(sl);
                    servidor = new Pane(root);
                    stack.getChildren().add(servidor);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnAlterarDados.setOnAction((EActionEvent) -> {
            if (alterar == null) {
                try {

                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLAlterarDadosFunc.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = FXMLLoader.load(sl);
                    alterar = new Stage();
                    File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                    alterar.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    alterar.setScene(new Scene(root));
                    alterar.setResizable(false);
                    alterar.setTitle("Alterar Dados");
                    alterar.centerOnScreen();
                    alterar.show();

                    alterar.setOnCloseRequest((WindowEvent) -> {
                        imagem();
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

    private void imagem() {
        f = new FuncionarioCTR().procurarFunc(FXMLTelaLoginController.funcionario.getMatricula());
        try {
            imagem.setImage(new Image(f.getImagem().getBinaryStream(1, f.getImagem().length())));
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }
    }

    public void colocarBorda(Node e) {

        btnVeiculo.setStyle("-fx-border-color:transparent");
        btnServidor.setStyle("-fx-border-color:transparent");
        e.setStyle("-fx-border-color:maroon");
    }

}
