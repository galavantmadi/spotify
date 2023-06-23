package Com;

import Client.ClientMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main  extends Application{


    public static void main(String[] args){
        //Com.Main client = new Com.Main("127.0.0.1", 5000);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(this.getClass().getClassLoader().getResource("StartPage.fxml"));
        loader.load();
        primaryStage.setScene(new Scene(loader.getRoot()));
        primaryStage.setResizable(false);
        //ClientMain clientMain=loader.<ClientMain>getController();
        primaryStage.show();
    }
}
