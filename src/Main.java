
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class Main extends Application {

    private File file;
    private URL url;
    private Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Imagens/Icone.jpg")));
        if (!new File("adm.ser").exists()) {
            file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLCadastrarAdm.fxml");
            url = new URL(file.toURI().toURL().toExternalForm());
            root = FXMLLoader.load(url);
        } else {
            file = new File(System.getProperty("user.dir") + "\\src\\View\\FXMLDocs\\FXMLTelaLogin.fxml");
            url = new URL(file.toURI().toURL().toExternalForm());
            root = FXMLLoader.load(url);
        }
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("ControlCar - CC");
        stage.centerOnScreen();
        stage.show();
    }
}
