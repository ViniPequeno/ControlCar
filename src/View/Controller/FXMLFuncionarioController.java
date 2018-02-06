/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.FuncionarioCTR;
import Model.FuncionarioDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLFuncionarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    public TableView<FuncionarioDTO> table;
    @FXML
    private JFXButton btnInserir, btnExcluir;
    @FXML
    private TableColumn<FuncionarioDTO, Long> colunaMat;
    @FXML
    private TableColumn<FuncionarioDTO, String> colunaNome;
    @FXML
    private TableColumn<FuncionarioDTO, String> colunaCargo;

    public static FuncionarioDTO funcionario;
    public static ObservableList<FuncionarioDTO> funcionarios;

    private Stage inserir, mostrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        funcionarios = null;
        inserir = null;

        colunaMat.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        funcionarios = FXCollections.observableArrayList(new FuncionarioCTR().listarFuncionario());
        table.setItems(funcionarios);

        table.setOnMouseClicked((MouseEvent evt) -> {
            if (evt.getClickCount() == 2) {

                funcionario = table.getSelectionModel().getSelectedItem();
                if (mostrar == null) {
                    try {

                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLFuncionarioInfor.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());

                        Parent root = FXMLLoader.load(sl);
                        mostrar = new Stage();
                        File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                        mostrar.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                        mostrar.setScene(new Scene(root));
                        mostrar.setResizable(false);
                        mostrar.setTitle("Informações do funcionário");
                        mostrar.centerOnScreen();
                        mostrar.show();

                        mostrar.setOnCloseRequest((WindowEvent) -> {
                            mostrar = null;
                        });

                    } catch (IOException ex) {

                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        btnInserir.setOnAction((EActionEvent) -> {

            if (inserir == null) {
                try {

                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirFuncionario.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    inserir = new Stage();
                    File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                    inserir.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    inserir.setScene(new Scene(root));
                    inserir.setResizable(false);
                    inserir.setTitle("Inserir Funcionário");
                    inserir.centerOnScreen();
                    inserir.show();

                    inserir.setOnCloseRequest((WindowEvent) -> {
                        inserir.close();
                        atualizar(new FuncionarioCTR().listarFuncionario());
                        inserir = null;
                    });

                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }
            }
        });

        btnExcluir.setOnAction((ActionEvent ActionEvent) -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType btnSim = new ButtonType("Sim");
                ButtonType btnNao = new ButtonType("Não");
                dialogoExe.setContentText("Tem certeza que quer excluir?");
                dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
                dialogoExe.showAndWait().ifPresent(b -> {
                    if (b == btnSim) {
                        new FuncionarioCTR().excluirFuncionario(table.getSelectionModel().getSelectedItem().getMatricula());
                        atualizar(new FuncionarioCTR().listarFuncionario());
                    }
                });

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um funcionário.");
                a.showAndWait();
            }
        });

        txtPesquisar.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.BACK_SPACE && txtPesquisar.getLength() > 0) {
                System.out.println(txtPesquisar.getText().substring(0, txtPesquisar.getLength() - 1));
                atualizar(new FuncionarioCTR().listarFuncionario(txtPesquisar.getText().substring(0, txtPesquisar.getLength() - 1)));
            } else if (!(txtPesquisar.getText() + event.getText()).equals("")) {

                atualizar(new FuncionarioCTR().listarFuncionario(txtPesquisar.getText() + event.getText()));
            } else {
                atualizar(new FuncionarioCTR().listarFuncionario());
            }
        });

    }

    private void atualizar(List<FuncionarioDTO> l) {

        funcionarios.removeAll();
        funcionarios = FXCollections.observableArrayList(l);
        table.setItems(funcionarios);
    }

}
