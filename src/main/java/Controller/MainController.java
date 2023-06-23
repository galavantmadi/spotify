package Controller;

import Client.ClientMain;
import Shared.ArtistListResponse;
import Shared.ArtistResponse;
import Shared.RequestClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ListView<ArtistResponse> artistLSV;

    @FXML
    private VBox root;

    Stage stage;

    private LoginController loginController;

    private ClientMain clientMain;

    private List<ArtistResponse> artistResponseList;

    public void setArtistResponseList(List<ArtistResponse> artistResponseList) {
        this.artistResponseList = artistResponseList;
    }

    public List<ArtistResponse> getArtistResponseList() {
        return artistResponseList;
    }

    public void setStage() {
        stage = (Stage) root.getScene().getWindow();
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public ListView<ArtistResponse> getArtistLSV() {
        return artistLSV;
    }

    public void setArtistLSV(ListView<ArtistResponse> artistLSV) {
        this.artistLSV = artistLSV;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init();
    }

    public void init(){
        artistLSV.getItems().clear();
        artistLSV.getItems().addAll(artistResponseList);
    }
}
