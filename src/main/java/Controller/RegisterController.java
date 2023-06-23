package Controller;

import Client.ClientMain;
import Shared.RequestClient;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField phoneTXT;

    @FXML
    private Label resultLBL;

    @FXML
    private VBox root;

    @FXML
    private Button saveBTN;

    @FXML
    private Button exitBTN;

    Stage stage;

    private StartController startController;

    private ClientMain clientMain;

    public void setStartController(StartController startController) {
        this.startController = startController;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setStage() {
        stage = (Stage) root.getScene().getWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveBTN.setOnAction(s->{
            RequestClient request=new Shared.RequestClient();
            request.setId(1);
            request.setUsername(usernameField.getText());
            request.setPassword(passField.getText());
            request.setPhone(phoneTXT.getText());
            clientMain.sendCreateUser(request);
            if(ClientMain.response.getStatus().equals("0")){
                root.getScene().getWindow().hide();
                StartController.clientMain=clientMain;
                FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("StartPage.fxml"));
                try {
                    Parent parent =loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage=new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.show();
            }
        });
        exitBTN.setOnAction(s->{
            root.getScene().getWindow().hide();
            StartController.clientMain=clientMain;
            FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("StartPage.fxml"));
            try {
                Parent parent =loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage=new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        });
    }
}
