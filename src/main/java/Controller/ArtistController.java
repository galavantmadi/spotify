package Controller;

import Client.ClientMain;

import Shared.ArtistResponse;
import Shared.RequestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistController implements Initializable {

    @FXML
    private ListView<ArtistResponse> artistLSV;

    @FXML
    private VBox root;

    @FXML
    private Hyperlink favoriteLNK;

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
        favoriteLNK.setOnAction(s->{
            loadFavoriteFXML();
        });

        //init();
        artistLSV.setCellFactory(new Callback<ListView<ArtistResponse>, ListCell<ArtistResponse>>() {
            @Override
            public ListCell<ArtistResponse> call(ListView<ArtistResponse> param) {
                return new  ListCell<ArtistResponse>(){
                    private final Label title = new Label();
                    private final VBox layout = new VBox(title);

                    @Override
                    protected void updateItem(ArtistResponse item, boolean empty) {
                        setOnMouseClicked(event -> {
                            //Main.productSelected=item;
                            //root.getScene().getWindow().hide();
                            //requestAlbum(clientMain,this)
                            loadFXML(item);
                        });
                        //setOnmo
                        if (empty || item == null || item.getId() == 0) {
                            title.setText(null);
                            //detail.setText(null);
                            setGraphic(null);
                        } else {
                            title.setText(item.getName());
                            //detail.setText(item.getPrice()+" تومان ");

                            setGraphic(layout);

                        }
                    }

                };
            }
        });
    }

    public void loadFXML(ArtistResponse item){
        FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("AlbumPage.fxml"));
        try {
            Parent parent =loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage=new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        AlbumController controller = loader.<AlbumController>getController();
        controller.setStage();
        controller.setMainPageController(this);
        controller.setClientMain(clientMain);
        requestAlbum(clientMain,controller,item.getName());
        stage.show();
    }

    public void loadFavoriteFXML(){
        FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("FavoritePage.fxml"));
        try {
            Parent parent =loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage=new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        FavoriteController controller = loader.<FavoriteController>getController();
        controller.setStage();
        controller.setMainPageController(this);
        controller.setClientMain(clientMain);
        requestFavoriteItems(clientMain,controller,ClientMain.loginResponse.getId());
        stage.show();
    }

    public void requestAlbum(ClientMain clientMain, AlbumController controller,String artistName){
        RequestClient request=new RequestClient();
        request.setId(6);
        request.setArtistName(artistName);
        clientMain.sendRequestAlbum(request);;
        if(ClientMain.artistAlbumListResponse.getStatus().equals("0")){

            controller.getAlbumListLSV().getItems().addAll((ArrayList)ClientMain.artistAlbumListResponse.getArtistAlbumResponses());
        }
    }

    public void  requestFavoriteItems(ClientMain clientMain,FavoriteController controller,int userId){
        RequestClient request=new RequestClient();
        request.setId(10);
        request.setUserId(userId);
        clientMain.sendRequestFavoriteList(request);
        if(ClientMain.favoriteSongListResponse.getStatus().equals("0")){

            controller.getFavoriteLSV().getItems().addAll((ArrayList)ClientMain.favoriteSongListResponse.getFavoriteSongResponseList());
        }
    }

    /*public void init(){
        artistLSV.getItems().clear();
        artistLSV.getItems().addAll(artistResponseList);
    }*/
}
