package application;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewScene.fxml"));
			
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ReviewScene.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root,600,450);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			ReviewSceneController controller = loader.getController();
			primaryStage.setOnHidden(e -> controller.shutdown());
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Config.initialize();
		if (Config.getValue("devMode").compareTo("true") == 0) {
			System.out.println(Config.getAllConfigData());
		}
		
		launch(args);
	}
}
