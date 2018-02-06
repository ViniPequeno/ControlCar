/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.CadastraCTR;
import Controller.RegistraCTR;
import Model.FuncionarioDTO;
import Model.VeiculoDTO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLControleVeiculosController implements Initializable {

    @FXML
    private TableView<VeiculoDTO> table;
    @FXML
    private TableColumn<VeiculoDTO, String> placa, marca, modelo, cor;

    @FXML
    private Label label;
    public static ObservableList<VeiculoDTO> veiculos;
    private Stage con;

    public static int estado;
    public static FuncionarioDTO f;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        f = FXMLPorteiroController.f;
        placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
        marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        cor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        atualizar();
        table.setOnMousePressed((MouseEvent event) -> {
            if (event.getClickCount() >= 2) {
                try {
                    FXMLConfirmaEntradaSaidaController.estado = estado;
                    FXMLConfirmaEntradaSaidaController.cadastra
                            = new CadastraCTR().procurarEntrada(table.getSelectionModel().getSelectedItem().getPlaca());
                    FXMLConfirmaEntradaSaidaController.porteiro = f;
                    if (estado == 0) {
                        FXMLConfirmaEntradaSaidaController.registra
                                = new RegistraCTR().procurar(table.getSelectionModel().getSelectedItem().getPlaca());
                    }
                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLConfirmaEntradaSaida.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    if (con == null) {
                        con = new Stage();
                        File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                        con.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                        con.setScene(new Scene(root));
                        con.setResizable(false);

                        con.setTitle("Controle de Veiculos");
                        con.show();

                        con.setOnCloseRequest((WindowEvent event1) -> {
                            atualizar();
                            con.close();
                            con = null;
                        });
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLControleVeiculosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLControleVeiculosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void atualizar() {
        if (estado == 1) {
            veiculos = FXCollections.observableArrayList(new RegistraCTR().listarVeiculosFora());
            table.setItems(veiculos);
        } else {
            veiculos = FXCollections.observableArrayList(new RegistraCTR().listarVeiculosDentro());
            table.setItems(veiculos);
        }
    }

}
