/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.CadastraCTR;
import Controller.RegistraCTR;
import Model.CadastraDTO;
import Model.RegistraDTO;
import Model.VeiculoDTO;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLVeiculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnListar, btnInserir, btnExcluir;
    @FXML
    private JFXTextField pesquisar;
    @FXML
    private TableView<DataModelVeiculo> tableVeiculos;
    @FXML
    private TableView<DataModelRegistra> tableAcidentes;
    @FXML
    private TableColumn<DataModelVeiculo, String> placaVeiculo, nomePropVeiculo, nomeFuncVeiculo;
    @FXML
    private TableColumn<DataModelRegistra, Long> nomePropAcidentes, numeroAcidentes;
    @FXML
    private TableColumn<DataModelRegistra, String> placaAcidentes;
    @FXML
    private JFXComboBox opcao;
    public static VeiculoDTO veiculo;
    public static ObservableList<VeiculoDTO> veiculos;

    private Stage inserir, mostrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        veiculos = null;
        inserir = null;
        mostrar = null;
        placaVeiculo.setCellValueFactory(new PropertyValueFactory<>("placa"));
        nomePropVeiculo.setCellValueFactory(new PropertyValueFactory<>("nomeProp"));
        nomeFuncVeiculo.setCellValueFactory(new PropertyValueFactory<>("nomeFunc"));
        placaAcidentes.setCellValueFactory(new PropertyValueFactory<>("placa"));
        nomePropAcidentes.setCellValueFactory(new PropertyValueFactory<>("nomeProp"));
        numeroAcidentes.setCellValueFactory(new PropertyValueFactory<>("numero"));
        opcao.getItems().add("Veiculos");
        opcao.getItems().add("Acidentes");
        opcao.getSelectionModel().select(0);
        opcao.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                iniciar();
            }
        });
        iniciar();

    }

    private void atualizarVeiculo(ArrayList<CadastraDTO> l) {

        ArrayList<DataModelVeiculo> datas = new ArrayList<>();
        if (l != null) {
            for (CadastraDTO d : l) {
                datas.add(new DataModelVeiculo(d.getMatProp().getNome(),
                        d.getMatFun().getNome(), d.getPlaca().getPlaca(), d.getMatProp().getMatricula()));
            }
        }

        tableVeiculos.setItems(FXCollections.observableArrayList(datas));

    }

    private void atualizarAcidente(ArrayList<RegistraDTO> y) {
        ArrayList<DataModelRegistra> datas = new ArrayList<>();
        if (y != null) {
            for (RegistraDTO d : y) {
                datas.add(new DataModelRegistra(d.getNumero(),
                        d.getVeiculo().getPlaca(), d.getServidorDTO().getNome()));
            }
        }
        tableAcidentes.setItems(FXCollections.observableArrayList(datas));
    }

    private void iniciar() {
        if (opcao.getSelectionModel().getSelectedIndex() == 0) {
            veiculos();
        } else {
            acidentes();
        }
    }

    public class DataModelVeiculo {

        private final SimpleStringProperty nomeProp;
        private final SimpleStringProperty nomeFunc;
        private final SimpleStringProperty placa;
        private final Long mat;

        public DataModelVeiculo(String nomeProp, String nomeFunc, String placa, Long mat) {
            this.nomeProp = new SimpleStringProperty(nomeProp);
            this.nomeFunc = new SimpleStringProperty(nomeFunc);
            this.placa = new SimpleStringProperty(placa);
            this.mat = mat;
        }

        public String getNomeProp() {
            return nomeProp.get();
        }

        public String getNomeFunc() {
            return nomeFunc.get();
        }

        public String getPlaca() {
            return placa.get();
        }

        public Long getMat() {
            return mat;
        }

    }

    public class DataModelRegistra {

        private final SimpleLongProperty numero;
        private final SimpleStringProperty placa;
        private final SimpleStringProperty nomeProp;

        public DataModelRegistra(long numero, String placa, String nomeProp) {
            this.numero = new SimpleLongProperty(numero);
            this.placa = new SimpleStringProperty(placa);
            this.nomeProp = new SimpleStringProperty(nomeProp);

        }

        public String getNomeProp() {
            return nomeProp.get();
        }

        public String getPlaca() {
            return placa.get();
        }

        public Long getNumero() {
            return numero.get();
        }

    }

    private void veiculos() {
        tableAcidentes.setVisible(false);
        tableVeiculos.setVisible(true);
        pesquisar.setPromptText("Procuar veiculo");
        atualizarVeiculo(new CadastraCTR().listarVeiculo());
        btnListar.setText("Relatório de entradas");
        btnListar.setOnAction((ActionEvent event) -> {
            Stage s = (Stage) btnListar.getScene().getWindow();
            if (new RegistraCTR().listarEntradas() == null) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Não houve movimentação no estacionamento");
                a.showAndWait();
            } else {
                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                    fc.setInitialFileName("Lista de Entradas");
                    File file1 = fc.showSaveDialog(s);
                    if (file1 != null) {
                        OutputStream file = new FileOutputStream(file1);
                        Document document = new Document();
                        PdfWriter.getInstance(document, file);
                        document.open();
                        document.addCreationDate();
                        document.addTitle("Lista de Entradas de Veiculos");
                        cabecalho(document);
                        Paragraph p = new Paragraph("Lista de movimentação dos veiculos\n\n");
                        p.setAlignment(Paragraph.ALIGN_CENTER);
                        p.setFont(new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD));
                        document.add(p);
                        for (RegistraDTO r : new RegistraCTR().listarEntradas()) {
                            document.add(new Paragraph("Número do registro: " + r.getNumero()));
                            document.add(new Paragraph("Placa: " + r.getVeiculo().getPlaca()));
                            document.add(new Paragraph("Motorista: " + r.getServidorDTO().getNome()));
                            document.add(new Paragraph("Porteiro da entrada: " + r.getFuncionarioEntrada().getNome()));
                            document.add(new Paragraph("Entrada: " + r.getDataEntrada() + " Horário: " + r.getHoraEntrada()));
                            String saida, saidaFunc;
                            if (r.getFuncionarioSaida() != null) {
                                saida = "Saida: " + r.getDataSaida() + " Horário: " + r.getHoraSaida();
                                saidaFunc = "Porteiro da saída: " + r.getFuncionarioSaida().getNome();
                            } else {
                                saida = "Saida: ------";
                                saidaFunc = "Porteiro da Saída: -------";
                            }
                            document.add(new Paragraph(saidaFunc));
                            document.add(new Paragraph(saida));
                            document.add(new Paragraph("-----------------------------------------------------------"));

                        }
                        document.close();
                        file.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

            }
        });

        pesquisar.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE && pesquisar.getLength() > 0) {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo(pesquisar.getText().substring(0, pesquisar.getLength() - 1)));
                } else if (!(pesquisar.getText() + event.getText()).equals("")) {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo(pesquisar.getText() + event.getText()));
                } else {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo());
                }
            }
        });
        btnInserir.setVisible(true);
        btnInserir.setOnAction((ActionEvent ActionEvent) -> {

            if (inserir == null) {
                try {

                    File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLInserirVeiculo.fxml");
                    URL sl = new URL(file.toURI().toURL().toExternalForm());

                    Parent root = FXMLLoader.load(sl);
                    inserir = new Stage();
                    File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                    inserir.getIcons().add(new javafx.scene.image.Image(f.toURI().toURL().toExternalForm()));
                    inserir.setScene(new Scene(root));
                    inserir.setResizable(false);
                    inserir.setTitle("Inserir Veículo");
                    inserir.centerOnScreen();
                    inserir.show();

                    inserir.setOnCloseRequest((WindowEvent) -> {
                        inserir.close();
                        atualizarVeiculo(new CadastraCTR().listarVeiculo());
                        inserir = null;
                    });

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnExcluir.setOnAction((ActionEvent ActionEvent) -> {
            System.out.println("js dvf");
            if (tableVeiculos.getSelectionModel().getSelectedItem() != null) {
                new CadastraCTR().excluirCadastra(
                        tableVeiculos.getSelectionModel().getSelectedItem().getMat(),
                        tableVeiculos.getSelectionModel().getSelectedItem().placa.get());
                atualizarVeiculo(new CadastraCTR().listarVeiculo());

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um veículo.");
                a.showAndWait();
            }
        });
    }

    private void acidentes() {
        tableVeiculos.setVisible(false);
        tableAcidentes.setVisible(true);
        pesquisar.setPromptText("Procurar placa");
        atualizarAcidente(new RegistraCTR().listarObs());
        btnListar.setText("Relatório de acidentes");
        btnListar.setOnAction((ActionEvent event) -> {
            Stage s = (Stage) btnListar.getScene().getWindow();
            if (new RegistraCTR().listarEntradas() == null) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Não há nenhum acidente");
                a.showAndWait();
            } else {
                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                    fc.setInitialFileName("Lista de Acidentes");
                    File file1 = fc.showSaveDialog(s);
                    if (file1 != null) {
                        OutputStream file = new FileOutputStream(file1);
                        Document document = new Document();
                        PdfWriter.getInstance(document, file);
                        document.open();
                        document.addCreationDate();
                        document.addTitle("Lista de Observações");
                        cabecalho(document);
                        Paragraph p = new Paragraph("Lista de Observações dos veiculos\n\n");
                        p.setAlignment(Paragraph.ALIGN_CENTER);
                        p.setFont(new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD));
                        document.add(p);

                        for (RegistraDTO r : new RegistraCTR().listarObs()) {
                            document.add(new Paragraph("Número do registro: " + r.getNumero()));
                            document.add(new Paragraph("Placa: " + r.getVeiculo().getPlaca()));
                            document.add(new Paragraph("Motorista: " + r.getServidorDTO().getNome()));
                            document.add(new Paragraph("Porteiro da entrada: " + r.getFuncionarioEntrada().getNome()));
                            document.add(new Paragraph("Entrada: " + r.getDataEntrada() + " Horário: " + r.getHoraEntrada()));
                            String saida, saidaFunc;
                            if (r.getFuncionarioSaida() != null) {
                                saida = "Saida: " + r.getDataSaida() + " Horário: " + r.getHoraSaida();
                                saidaFunc = "Porteiro da saída: " + r.getFuncionarioSaida().getNome();
                            } else {
                                saida = "Saida: ------";
                                saidaFunc = "Porteiro da Saída: -------";
                            }
                            document.add(new Paragraph(saidaFunc));
                            document.add(new Paragraph(saida));
                            document.add(new Paragraph(r.getObs()));
                            document.add(new Paragraph("-----------------------------------------------------------"));

                        }
                        document.close();
                        file.close();
                    }
                } catch (Exception e) {
                    System.out.println("Pedro é homem");
                    System.out.println(e.getMessage());
                }
            }
        });
        pesquisar.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE && pesquisar.getLength() > 0) {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo(pesquisar.getText().substring(0, pesquisar.getLength() - 1)));
                } else if (!(pesquisar.getText() + event.getText()).equals("")) {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo(pesquisar.getText() + event.getText()));
                } else {

                    atualizarVeiculo(new CadastraCTR().listarVeiculo());
                }
            }
        });
        pesquisar.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE && pesquisar.getLength() > 0) {

                    atualizarAcidente(new RegistraCTR().listarObs(pesquisar.getText().substring(0, pesquisar.getLength() - 1)));
                } else if (!(pesquisar.getText() + event.getText()).equals("")) {

                    atualizarAcidente(new RegistraCTR().listarObs(pesquisar.getText() + event.getText()));
                } else {

                    atualizarAcidente(new RegistraCTR().listarObs());
                }
            }
        });
        btnInserir.setVisible(false);
        tableAcidentes.setOnMouseClicked((MouseEvent evt) -> {
            if (evt.getClickCount() == 2) {

                Long num = tableAcidentes.getSelectionModel().getSelectedItem().getNumero();
                FXMLRelatorioInforController.r = new RegistraCTR().procurar(num);
                if (mostrar == null) {
                    try {

                        File file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLRelatorioInfor.fxml");
                        URL sl = new URL(file.toURI().toURL().toExternalForm());

                        Parent root = FXMLLoader.load(sl);
                        mostrar = new Stage();
                        File f = new File(System.getProperty("user.dir") + "\\src\\Imagens\\Icone.jpg");
                        mostrar.getIcons().add(new javafx.scene.image.Image(f.toURI().toURL().toExternalForm()));
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
        btnExcluir.setOnAction((ActionEvent ActionEvent) -> {
            System.out.println("js dvf");
            if (tableAcidentes.getSelectionModel().getSelectedItem() != null) {
                new RegistraCTR().excluirObservacao(tableAcidentes.getSelectionModel().getSelectedItem().getNumero());
                atualizarAcidente(new RegistraCTR().listarObs());

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Selecione um veículo.");
                a.showAndWait();
            }
        });

    }

    private void cabecalho(Document document) throws BadElementException, IOException, DocumentException {
        Paragraph p = new Paragraph("INSTITUTO FEDERAL DE EDUCAÇÃO, CIENCIAS E TECNOLOGIA DO AMAZONAS");
        Paragraph p2 = new Paragraph("GERENCIA DE ADMINSTRAÇÃO E MANUTANÇÃO");
        p.setFont(new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setFont(new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        document.add(p2);
    }

}
