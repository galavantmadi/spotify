package Controller;

import Client.ClientMain;
import Shared.ArtistAlbumResponse;
import Shared.ArtistSongResponse;
import Shared.FavoriteSongResponse;
import Shared.RequestClient;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FavoriteController implements Initializable {
    @FXML
    private ListView<FavoriteSongResponse> favoriteLSV;

    @FXML
    private VBox root;

    private ClientMain clientMain;

    Stage stage;
    private ArtistController artistController;

    public void setStage() {
        stage = (Stage) root.getScene().getWindow();
    }

    public void setMainPageController(ArtistController artistController) {
        this.artistController = artistController;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public ListView<FavoriteSongResponse> getFavoriteLSV() {
        return favoriteLSV;
    }

    public void setFavoriteLSV(ListView<FavoriteSongResponse> favoriteLSV) {
        this.favoriteLSV = favoriteLSV;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        favoriteLSV.setCellFactory(new Callback<ListView<FavoriteSongResponse>, ListCell<FavoriteSongResponse>>() {
            @Override
            public ListCell<FavoriteSongResponse> call(ListView<FavoriteSongResponse> param) {
                return new  ListCell<FavoriteSongResponse>(){
                    private final Label name = new Label();
                    private final Label album_name = new Label();
                    private final Label song_name = new Label();

                    private final VBox layout = new VBox(name,album_name,song_name);

                    @Override
                    protected void updateItem(FavoriteSongResponse item, boolean empty) {
                        setOnMouseClicked(event -> {
                          //Main.productSelected=item;
                            //root.getScene().getWindow().hide();
                            loadFXML(item);
                        });
                        //setOnmo
                        if (empty || item == null || item.getId() == 0) {
                            name.setText(null);
                            album_name.setText(null);
                            song_name.setText(null);
                            //detail.setText(null);
                            setGraphic(null);
                        } else {
                            name.setText(item.getName());

                            album_name.setText(item.getAlbumName());
                            song_name.setText(item.getSongName());

                            setGraphic(layout);

                        }
                    }
                };
            }
        });
    }

    private void loadFXML(FavoriteSongResponse item){
        FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("SongViewPage.fxml"));
        try {
            Parent parent =loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage=new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        SongViewController controller = loader.<SongViewController>getController();
        controller.setStage();
        //controller.setSongController(this);
        controller.setClientMain(clientMain);
        requestSongView(clientMain,controller,item.getSongId());
        stage.show();
    }
    public void requestSongView(ClientMain clientMain, SongViewController controller,int songId){
        RequestClient request=new Shared.RequestClient();
        request.setId(8);
        request.setSongId(songId);
        clientMain.sendRequestSongInfo(request);
        if(ClientMain.songInfoResponse.getStatus().equals("0")){
            controller.getArtistNameTXT().setText(ClientMain.songInfoResponse.getArtistName());
            controller.getAlbumNameTXT().setText(ClientMain.songInfoResponse.getAlbumName());
            controller.getSongNameTXT().setText(ClientMain.songInfoResponse.getSong_name());
            controller.getUrlTXT().setText(ClientMain.songInfoResponse.getUrl());
            //controller.getSongListLSV().getItems().addAll((ArrayList)ClientMain.artistSongListResponse.getArtistSongResponseList());
        }
    }

}
