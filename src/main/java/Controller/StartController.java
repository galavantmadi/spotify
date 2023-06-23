package Controller;

import Client.ClientMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private Hyperlink enterLNK;

    @FXML
    private Hyperlink registerLNK;

    public static ClientMain clientMain;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enterLNK.setOnAction(s->{
            if (clientMain == null) {
                clientMain=new ClientMain("127.0.0.1", 5000);

            }

            root.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("LoginPage.fxml"));
            try {
                Parent parent =loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage=new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            LoginController controller=loader.<LoginController>getController();
            controller.setStage();
            controller.setStartController(this);
            controller.setClientMain(clientMain);
            stage.show();
        });
        registerLNK.setOnAction(s->{
            if (clientMain == null) {
                clientMain=new ClientMain("127.0.0.1", 5000);

            }
            root.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("RegisterPage.fxml"));
            try {
                Parent parent =loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage=new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            RegisterController controller=loader.<RegisterController>getController();
            controller.setStage();
            controller.setStartController(this);
            controller.setClientMain(clientMain);
            stage.show();
        });
    }
}
