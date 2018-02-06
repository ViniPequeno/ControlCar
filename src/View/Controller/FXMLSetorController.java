/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.SetorCTR;
import Model.SetorDTO;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLSetorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static SetorDTO setor;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    public TableView<SetorDTO> table;
    @FXML
    private JFXButton btnInserir, btnAlterar, btnExcluir;
    @FXML
    private TableColumn<SetorDTO, Long> colunaCod;
    @FXML
    private TableColumn<SetorDTO, String> colunaNome;
    @FXML
    private TableColumn<SetorDTO, String> colunaRamal;

    public static ObservableList<SetorDTO> setores;
    public static int estado;

    private Stage inserir, alterar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setores = null;
        estado = 0;
        inserir = alterar = null;
        colunaCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaRamal.setCellValueFactory(new PropertyValueFactory<>("ramal"));

        setores = FXCollections.observableArrayList(new SetorCTR().listarSetor());
        table.setItems(setores);
        btnInserir.setOnAction((ActionEvent ActionEvent) -> {
            if (inserir == null) {
                try {

                    estado = 0;

                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirSetor.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    inserir = new Stage();
                    File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                    inserir.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    inserir.setScene(new Scene(root));
                    inserir.setResizable(false);
                    inserir.setTitle("Inserir Setor");
                    inserir.centerOnScreen();
                    inserir.show();

                    inserir.setOnCloseRequest((WindowEvent) -> {
                        inserir.close();
                        atualizar(new SetorCTR().listarSetor());
                        inserir = null;
                    });

                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }
            }
        });

        txtPesquisar.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.BACK_SPACE && txtPesquisar.getLength() > 0) {
                System.out.println(txtPesquisar.getText().substring(0, txtPesquisar.getLength() - 1));
                atualizar(new SetorCTR().listarSetorNome(txtPesquisar.getText().substring(0, txtPesquisar.getLength() - 1)));
            } else if (!(txtPesquisar.getText() + event.getText()).equals("")) {

                atualizar(new SetorCTR().listarSetorNome(txtPesquisar.getText() + event.getText()));
            } else {
                System.out.println("entrou2");
                atualizar(new SetorCTR().listarSetor());
            }
        });

        btnAlterar.setOnAction((ActionEvent ActionEvent) -> {
            if (table.getSelectionModel().getSelectedItem() != null) {

                if (alterar == null) {

                    estado = 1;
                    setor = table.getSelectionModel().getSelectedItem();
                    try {
                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirSetor.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());
                        Parent root = FXMLLoader.load(sl);

                        alterar = new Stage();
                        alterar.setScene(new Scene(root));
                        alterar.setTitle("Alterar Setor");
                        File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
                        alterar.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                        alterar.setResizable(false);
                        alterar.centerOnScreen();
                        alterar.show();
                        alterar.setOnCloseRequest((WindowEvent) -> {
                            alterar.close();
                            atualizar(new SetorCTR().listarSetor());
                            alterar = null;
                        });
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um setor");
                a.showAndWait();
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
                        new SetorCTR().excluirSetor(table.getSelectionModel().getSelectedItem().getCodigo());
                        atualizar(new SetorCTR().listarSetor());
                    }
                });

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um setor");
                a.showAndWait();
            }
        });
    }

    private void atualizar(List<SetorDTO> l) {

        setores.removeAll();
        setores = FXCollections.observableArrayList(l);
        table.setItems(setores);
    }
}
