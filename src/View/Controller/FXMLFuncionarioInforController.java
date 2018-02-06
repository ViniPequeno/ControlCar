/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Controller;

import Model.FuncionarioDTO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FXMLFuncionarioInforController implements Initializable {

    @FXML
    private ImageView imagem;
    @FXML
    private Label nome, mat, cargo, email, telFixo, telCelular;

    private FuncionarioDTO funcionario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        funcionario = FXMLFuncionarioController.funcionario;
        try {
            imagem.setImage(new Image(funcionario.getImagem().getBinaryStream(1, funcionario.getImagem().length())));
            nome.setText(nome.getText() + " " + funcionario.getNome());
            mat.setText(mat.getText() + " " + funcionario.getMatricula());
            cargo.setText(cargo.getText() + " " + funcionario.getCargo());
            email.setText(email.getText() + " " + funcionario.getEmail());
            telFixo.setText(telFixo.getText() + " " + funcionario.getTelFixo());
            telCelular.setText(telCelular.getText() + " " + funcionario.getTelCelular());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFuncionarioInforController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
