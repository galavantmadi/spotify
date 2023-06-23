package Controller;

import Client.ClientMain;
import Shared.RequestClient;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SongViewController implements Initializable {
    @FXML
    private TextField songNameTXT;

    @FXML
    private Label resultLBL;

    @FXML
    private VBox root;

    @FXML
    private Button downloadBTN;

    @FXML
    private TextField albumNameTXT;

    @FXML
    private TextField artistNameTXT;

    @FXML
    private TextField urlTXT;

    @FXML
    private Button stopBTN;

    @FXML
    private Button favoriteBTN;

    public static MediaPlayer mediaPlayer;

    Stage stage;
    private SongController songController;
    public void setStage() {
        stage = (Stage) root.getScene().getWindow();
    }

    public void setSongController(SongController songController) {
        this.songController = songController;
    }
    private ClientMain clientMain;

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setSongNameTXT(TextField songNameTXT) {
        this.songNameTXT = songNameTXT;
    }

    public void setResultLBL(Label resultLBL) {
        this.resultLBL = resultLBL;
    }

    public void setAlbumNameTXT(TextField albumNameTXT) {
        this.albumNameTXT = albumNameTXT;
    }

    public void setArtistNameTXT(TextField artistNameTXT) {
        this.artistNameTXT = artistNameTXT;
    }

    public void setUrlTXT(TextField urlTXT) {
        this.urlTXT = urlTXT;
    }

    public TextField getSongNameTXT() {
        return songNameTXT;
    }

    public Label getResultLBL() {
        return resultLBL;
    }

    public TextField getAlbumNameTXT() {
        return albumNameTXT;
    }

    public TextField getArtistNameTXT() {
        return artistNameTXT;
    }

    public TextField getUrlTXT() {
        return urlTXT;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //songNameTXT.setText(clientMain.songInfoResponse.getSong_name());

        downloadBTN.setOnAction(s->{
            File srcFolder = new File("./src/main/java/Server/Resources");
            File destFolder = new File("./src/main/java/Client/Downloads");
            try {
                copyFolder(srcFolder,destFolder,songNameTXT.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stopBTN.setOnAction(s->{
            File destFolder = new File("./src/main/java/Client/Downloads");
            stopMp3(destFolder,songNameTXT.getText());
        });

        favoriteBTN.setOnAction(s->{
            RequestClient request=new RequestClient();
            request.setId(9);
            request.setSongId(ClientMain.songInfoResponse.getId());
            request.setUserId(ClientMain.loginResponse.getId());
            clientMain.sendRequestFavorite(request);
            resultLBL.setText(ClientMain.response.getMessage());
        });
    }

    public void copyFolder(File src, File dest,String fileName)
            throws IOException {

        if(src.isDirectory()){

            //if directory not exists, create it
            if(!dest.exists()){
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            List<String> files = Arrays.stream(src.list()).filter(c->c.equals(fileName)).collect(Collectors.toList());

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile,fileName);
                Media hit = new Media(destFile.toURI().toString());
                mediaPlayer = new MediaPlayer(hit);
                mediaPlayer.play();
            }

        }else{
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }

    public void stopMp3(File dest,String fileName){
        //File destFile = new File(dest, fileName);
        //Media hit = new Media(destFile.toURI().toString());
        //mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.stop();
    }
}
