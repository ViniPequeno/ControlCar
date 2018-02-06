/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.LotadoCTR;
import Controller.ServidorCTR;
import Model.LotadoDTO;
import Model.ServidorDTO;
import View.Controller.FXMLServidorController.DataModelFuncionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLServidorController implements Initializable {

    /**
     * Initializes the controller class.
     *
     */
    @FXML
    private JFXButton btnInserir, btnAlterar, btnExcluir;
    @FXML
    private TableView<DataModelFuncionario> table;
    @FXML
    private TableColumn<DataModelFuncionario, Long> matricula;
    @FXML
    private TableColumn<DataModelFuncionario, String> nome;

    @FXML
    private TableColumn<DataModelFuncionario, String> setor;

    @FXML
    private JFXTextField txtPesquisar;

    private Stage inserir, alterar;
    public static ServidorDTO servidor;
    public static LotadoDTO lotado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inserir = null;
        alterar = null;
        matricula.setCellValueFactory(new PropertyValueFactory("mat"));
        nome.setCellValueFactory(new PropertyValueFactory("nome"));
        setor.setCellValueFactory(new PropertyValueFactory("setor"));
        setLista(new LotadoCTR().listar());
        //setLista(new LotadoCTR().listar());
        txtPesquisar.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE && txtPesquisar.getLength() > 0) {

                    setLista(new LotadoCTR().listar(txtPesquisar.getText().substring(0, txtPesquisar.getLength() - 1)));
                } else if (!(txtPesquisar.getText() + event.getText()).equals("")) {

                    setLista(new LotadoCTR().listar(txtPesquisar.getText() + event.getText()));
                } else {
                    setLista(new LotadoCTR().listar());
                }
            }
        });
        btnInserir.setOnAction((ActionEvent ActionEvent) -> {

            if (inserir == null) {
                try {
                    FXMLInserirServidorController.estado = 0;
                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirServidor.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    inserir = new Stage();
                    File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
                    inserir.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                    inserir.setScene(new Scene(root));
                    inserir.setResizable(false);
                    inserir.setTitle("Inserir Seervidor");
                    inserir.centerOnScreen();
                    inserir.show();

                    inserir.setOnCloseRequest((WindowEvent) -> {
                        inserir.close();
                        setLista(new LotadoCTR().listar());
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
                ButtonType btnNao = new ButtonType("Nao");
                dialogoExe.setContentText("Tem certeza que quer excluir?");
                dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
                dialogoExe.showAndWait().ifPresent(b -> {
                    if (b == btnSim) {
                        new ServidorCTR().excluirServidor(table.getSelectionModel().getSelectedItem().getMat());
                        setLista(new LotadoCTR().listar());
                    }
                });

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um veículo.");
                a.showAndWait();
            }
        });
        btnAlterar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Long cod = table.getSelectionModel().getSelectedItem().getMat();
                        servidor = new ServidorCTR().procurarServidor(cod);
                        lotado = new LotadoCTR().procurarServidor(servidor.getMatricula());

                        FXMLInserirServidorController.estado = 1;
                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirServidor.fxml");
                        URL sl = null;

                        sl = new URL(file.toURI().toURL().toExternalForm());

                        Parent root = FXMLLoader.load(sl);
                        inserir = new Stage();
                        File f = new File(System.getProperty("user.dir") + "src\\Imagens\\Icone.jpg");
                        inserir.getIcons().add(new Image(f.toURI().toURL().toExternalForm()));
                        inserir.setScene(new Scene(root));
                        inserir.setResizable(false);
                        inserir.setTitle("Inserir Seervidor");
                        inserir.centerOnScreen();
                        inserir.show();

                        inserir.setOnCloseRequest((WindowEvent) -> {
                            inserir.close();
                            setLista(new LotadoCTR().listar());
                            inserir = null;
                        });
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(FXMLServidorController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLServidorController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Selecione um servidor");
                    a.showAndWait();
                }
            }
        });
    }

    public void setLista(ArrayList<LotadoDTO> lotados) {

        ArrayList<DataModelFuncionario> datas = new ArrayList<>();
        for (LotadoDTO d : lotados) {
            System.out.println("Funcionario == " + d.getMatFunc().getMatricula());
            datas.add(new DataModelFuncionario(d.getMatFunc().getMatricula(),
                    d.getMatFunc().getNome(),
                    d.getCodSetor().getNome()));
        }
        table.setItems(FXCollections.observableArrayList(datas));
    }

    public class DataModelFuncionario {

        private final SimpleLongProperty mat;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty setor;

        public DataModelFuncionario(Long mat, String nome, String setor) {
            this.nome = new SimpleStringProperty(nome);
            this.mat = new SimpleLongProperty(mat);
            this.setor = new SimpleStringProperty(setor);

        }

        public String getNome() {
            return nome.get();
        }

        public long getMat() {
            return mat.get();
        }

        public String getSetor() {
            return setor.get();
        }

    }
}
