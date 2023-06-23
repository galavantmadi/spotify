package Controller;

import Client.ClientMain;

import Shared.ArtistSongResponse;


import Shared.RequestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private ListView<ArtistSongResponse> songListLSV;

    private ClientMain clientMain;

    Stage stage;
    private AlbumController albumController;

    public void setStage() {
        stage = (Stage) root.getScene().getWindow();
    }

    public void setSongListLSV(ListView<ArtistSongResponse> songListLSV) {
        this.songListLSV = songListLSV;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setAlbumController(AlbumController albumController) {
        this.albumController = albumController;
    }

    public ListView<ArtistSongResponse> getSongListLSV() {
        return songListLSV;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songListLSV.setCellFactory(new Callback<ListView<ArtistSongResponse>, ListCell<ArtistSongResponse>>() {
            @Override
            public ListCell<ArtistSongResponse> call(ListView<ArtistSongResponse> param) {
                return new  ListCell< ArtistSongResponse >(){
                    private final Label name = new Label();
                    private final Label url = new Label();
                    private final VBox layout = new VBox(name,url);

                    @Override
                    protected void updateItem(ArtistSongResponse item, boolean empty) {
                        setOnMouseClicked(event -> {
//                            //Main.productSelected=item;
                            //root.getScene().getWindow().hide();
                            loadFXML(item);
                        });
                        //setOnmo
                        if (empty || item == null || item.getId() == 0) {
                            name.setText(null);
                            url.setText(null);
                            setGraphic(null);
                        } else {
                            name.setText(item.getSongName());
                            url.setText(item.getUrl());

                            setGraphic(layout);

                        }
                    }
                };
            }
        });
    }
    private void loadFXML(ArtistSongResponse item){
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
        controller.setSongController(this);
        controller.setClientMain(clientMain);
        requestSongView(clientMain,controller,item.getId());
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
