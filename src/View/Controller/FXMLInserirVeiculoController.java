/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Controller.CadastraCTR;
import Controller.FuncionarioCTR;
import Controller.VeiculoCTR;
import Model.FuncionarioDTO;
import Model.VeiculoDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLInserirVeiculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    JFXTextField placa, cor, modelo, marca, anoFabri, funcionario;
    @FXML
    JFXButton inserir;

    FuncionarioDTO f;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        f = FXMLGAMController.f;

        placa.setOnKeyPressed((KeyEvent event) -> {

            VeiculoDTO d = new VeiculoCTR().pesquisar(placa.getText() + event.getText());
            System.out.println(placa.getText() + event.getText());
            if (event.getCode() != KeyCode.BACK_SPACE) {
                d = new VeiculoCTR().pesquisar(placa.getText() + event.getText());
            } else if (placa.getText().length() <= 1) {
                d = null;
            } else {
                d = new VeiculoCTR().pesquisar(placa.getText().substring(0, placa.getText().length() - 1));
            }
            if (d != null) {
                System.out.println("sfgh");
                cor.setText(d.getCor());
                modelo.setText(d.getModelo());
                marca.setText(d.getMarca());
                anoFabri.setText(d.getAnoFabri());
                desabilitar();
            } else {
                habilitar();
            }
        });

        inserir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!modelo.isEditable()) {
                    if (placa.getText().equals("") || funcionario.getText().equals("")) {
                        Alert t = new Alert(Alert.AlertType.ERROR);
                        t.setContentText("Preencha os campos placa e funcionario");
                        t.showAndWait();
                    } else {
                        VeiculoDTO v = new VeiculoDTO(placa.getText(), marca.getText(), cor.getText(), modelo.getText(),
                                anoFabri.getText());
                        FuncionarioDTO s
                                = new FuncionarioCTR().procurarFunc(Long.parseLong(funcionario.getText()));
                        if (new VeiculoCTR().cadastraVeiculo(v.getPlaca(), v.getCor(), v.getMarca(), v.getModelo(), v.getAnoFabri()) == 2) {
                            Alert a = new Alert(Alert.AlertType.WARNING);
                            a.setContentText("Veiculo já existe, continuar com outro proprietário?");
                            a.getButtonTypes().remove(0);
                            a.getButtonTypes().add(ButtonType.NO);
                            a.getButtonTypes().add(ButtonType.YES);
                            Optional<ButtonType> o = a.showAndWait();
                            if (o.isPresent()) {
                                if (o.get() == ButtonType.YES) {
                                    if (new CadastraCTR().adicionarCadastra(s, f, v) == 2) {
                                        Alert b = new Alert(Alert.AlertType.ERROR);
                                        b.setContentText("Esse veiculo já está cadastrado com esse funcionário");
                                        b.showAndWait();
                                    } else {
                                        Alert b = new Alert(Alert.AlertType.CONFIRMATION);
                                        b.setContentText("Cadastrado");
                                        b.showAndWait();
                                        limparTela();
                                    }
                                }
                            } else {
                                new CadastraCTR().readmitirCadastra(Long.parseLong(funcionario.getText()), placa.getText());
                                Alert t = new Alert(Alert.AlertType.ERROR);
                                t.setContentText("Veiculo ativado");
                                t.showAndWait();
                                limparTela();
                            }
                        }
                    }
                } else if (placa.getText().equals("") || cor.getText().equals("") || modelo.getText().equals("")
                        || marca.getText().equals("") || anoFabri.getText().equals("") || funcionario.getText().equals("")) {
                    Alert t = new Alert(Alert.AlertType.ERROR);
                    t.setContentText("Preencha tudo");
                    t.showAndWait();
                } else {
                    VeiculoDTO v = new VeiculoDTO(placa.getText(), marca.getText(), cor.getText(), modelo.getText(),
                            anoFabri.getText());
                    FuncionarioDTO s
                            = new FuncionarioCTR().procurarFunc(Long.parseLong(funcionario.getText()));
                    if (s != null) {
                        if (s.getAtivo().equals("N")) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setContentText("Servidor demitido");
                            a.showAndWait();

                        } else {
                            if (new VeiculoCTR().cadastraVeiculo(v.getPlaca(), v.getCor(), v.getMarca(), v.getModelo(), v.getAnoFabri()) == 1) {
                                if (new CadastraCTR().adicionarCadastra(s, f, v) == 2) {
                                    Alert b = new Alert(Alert.AlertType.ERROR);
                                    b.setContentText("Esse veiculo já está cadastrado com esse funcionário");
                                    b.showAndWait();
                                } else {
                                    Alert b = new Alert(Alert.AlertType.CONFIRMATION);
                                    b.setContentText("Cadastrado");
                                    b.showAndWait();
                                    limparTela();
                                }

                            } else {
                                Alert b = new Alert(Alert.AlertType.ERROR);
                                b.setContentText("Campo mal inserido");
                                b.showAndWait();
                            }
                        }
                    } else {
                        Alert t = new Alert(Alert.AlertType.ERROR);
                        t.setContentText("Cara não existe");
                        t.showAndWait();
                    }

                }
            }

        }
        );
    }

    public void limparTela() {
        placa.setText("");
        cor.setText("");
        modelo.setText("");
        marca.setText("");
        anoFabri.setText("");
    }

    public void habilitar() {
        cor.setText("");
        modelo.setText("");
        marca.setText("");
        anoFabri.setText("");
        cor.setEditable(true);
        modelo.setEditable(true);
        marca.setEditable(true);
        anoFabri.setEditable(true);
    }

    public void desabilitar() {
        cor.setEditable(false);
        modelo.setEditable(false);
        marca.setEditable(false);
        anoFabri.setEditable(false);
    }

}
