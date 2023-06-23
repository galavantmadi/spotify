package Controller;

import Client.ClientMain;
import Shared.ArtistAlbumResponse;
import Shared.RequestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AlbumController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private ListView<ArtistAlbumResponse> albumListLSV;

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

    public ListView<ArtistAlbumResponse> getAlbumListLSV() {
        return albumListLSV;
    }

    public void setAlbumListLSV(ListView<ArtistAlbumResponse> albumListLSV) {
        this.albumListLSV = albumListLSV;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        albumListLSV.setCellFactory(new Callback<ListView<ArtistAlbumResponse>, ListCell<ArtistAlbumResponse>>() {
            @Override
            public ListCell<ArtistAlbumResponse> call(ListView<ArtistAlbumResponse> param) {
                return new  ListCell<ArtistAlbumResponse>(){
                    private final Label title = new Label();
                    private ImageView imageView = new ImageView();
                    private final VBox layout = new VBox(title,imageView);

                    @Override
                    protected void updateItem(ArtistAlbumResponse item, boolean empty) {
                        setOnMouseClicked(event -> {
//                            //Main.productSelected=item;
                            //root.getScene().getWindow().hide();
                            loadFXML(item);
                        });
                        //setOnmo
                        if (empty || item == null || item.getId() == 0) {
                            title.setText(null);
                            //detail.setText(null);
                            setGraphic(null);
                        } else {
                            title.setText(item.getAlbumName());
                            File file = new File(item.getCoverImage());
                            Image image = new Image(file.toURI().toString());
                            //Image image = new Image(item.getCoverImage(), true) ;
                            imageView.setImage(image);
                            imageView.setFitHeight(100);
                            imageView.setFitWidth(100);
                            //detail.setText(item.getPrice()+" تومان ");

                            setGraphic(layout);

                        }
                    }
                };
            }
        });
    }

    public void loadFXML(ArtistAlbumResponse item){
        FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("SongPage.fxml"));
        try {
            Parent parent =loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage=new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        SongController controller = loader.<SongController>getController();
        controller.setStage();
        controller.setClientMain(clientMain);
        controller.setAlbumController(this);
        requestSong(clientMain,controller,item.getAlbumName());
        stage.show();
    }

    public void requestSong(ClientMain clientMain, SongController controller,String albumName){
        RequestClient request=new RequestClient();
        request.setId(7);
        request.setAlbumName(albumName);
        clientMain.sendRequestSong(request);
        if(ClientMain.artistSongListResponse.getStatus().equals("0")){

            controller.getSongListLSV().getItems().addAll((ArrayList)ClientMain.artistSongListResponse.getArtistSongResponseList());
        }
    }
}
