package Controller;


import Client.ClientMain;
import Shared.RequestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginBTN;

    @FXML
    private Button exitBTN;

    @FXML
    private VBox root;

    @FXML
    private Label resultLBL;

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

        resultLBL.setText("");
        resultLBL.setTextFill(Color.BLACK);
        loginBTN.setOnAction(s->{
            resultLBL.setText("");
            resultLBL.setTextFill(Color.BLACK);
            try {
                checkLogin(clientMain);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        exitBTN.setOnAction(s->{
            //clientMain.closeConnection();
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
    private void checkLogin(ClientMain clientMain) throws IOException{
        RequestClient request=new RequestClient();
        request.setId(3);
        request.setUsername(usernameField.getText());
        request.setPassword(passField.getText());
        request.setType(0);
        clientMain.sendRequestLogin(request);
        if(ClientMain.loginResponse.getStatus().equals("-1")){
            resultLBL.setText(ClientMain.loginResponse.getMessage());
            resultLBL.setTextFill(Color.RED);

        }else if(ClientMain.loginResponse.getStatus().equals("0")){
            resultLBL.setText(ClientMain.loginResponse.getMessage());
            resultLBL.setTextFill(Color.RED);
            root.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("ArtistPage.fxml"));
            Parent parent =loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            ArtistController controller=loader.<ArtistController>getController();
            controller.setStage();
            controller.setLoginController(this);
            controller.setClientMain(clientMain);
            requestArtist(clientMain,controller);

            stage.show();
        }
        //clientMain.init(request);
    }

    public void requestArtist(ClientMain clientMain, ArtistController controller){
        RequestClient request=new RequestClient();
        request.setId(5);
        clientMain.sendRequestArtist(request);;
        if(ClientMain.artistListResponse.getStatus().equals("0")){

            controller.getArtistLSV().getItems().addAll((ArrayList)ClientMain.artistListResponse.getArtistResponseList());
        }
    }
}
