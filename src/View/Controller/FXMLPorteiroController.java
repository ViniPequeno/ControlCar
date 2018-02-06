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
public class FXMLPorteiroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    JFXButton btnEntrada, btnSaida, btnAlterarDados, btnSair;
    @FXML
    ImageView imagem;
    @FXML
    StackPane stack;
    Stage alterar;
    Pane entrada, saida;
    File file;
    URL sl;
    Parent root;
    public static FuncionarioDTO f = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagem();
        btnEntrada.setOnAction((EActionEvent) -> {
            colocarBorda(btnEntrada);
            if (entrada == null) {
                saida = null;
                try {
                    FXMLControleVeiculosController.estado = 1;
                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLControleVeiculos.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = (Pane) FXMLLoader.load(sl);
                    entrada = new Pane(root);

                    stack.getChildren().add(entrada);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        }
        );
        btnSaida.setOnAction((EActionEvent) -> {
            colocarBorda(btnSaida);
            if (saida == null) {
                entrada = null;
                try {
                    FXMLControleVeiculosController.estado = 0;
                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLControleVeiculos.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = (Pane) FXMLLoader.load(sl);
                    saida = new Pane(root);
                    stack.getChildren().add(saida);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        }
        );
        btnAlterarDados.setOnAction((EActionEvent) -> {
            if (alterar == null) {

                try {

                    file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLAlterarDadosFunc.fxml");
                    sl = new URL(file.toURI().toURL().toExternalForm());
                    root = FXMLLoader.load(sl);
                    alterar = new Stage();
                    alterar.setScene(new Scene(root));
                    File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
                    alterar.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    alterar.setTitle("Alterar Dados");
                    alterar.setResizable(false);
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
        }
        );
        btnSair.setOnAction(
                (EActionEvent) -> {
                    try {
                        System.out.println("dcwfrreif");
                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLTelaLogin.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());
                        Parent root = FXMLLoader.load(sl);
                        Stage s = (Stage) btnSair.getScene().getWindow();
                        s.setScene(new Scene(root));
                        s.setTitle("Tela login");
                        s.setResizable(false);
                        s.centerOnScreen();
                        s.show();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
        );
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

        btnEntrada.setStyle("-fx-border-color:transparent");
        btnSaida.setStyle("-fx-border-color:transparent");
        e.setStyle("-fx-border-color:maroon");
    }

}
