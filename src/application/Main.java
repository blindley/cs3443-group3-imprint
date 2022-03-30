package application;
	
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	static Stage primaryStage;
	static ReviewSession reviewSession;
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static ReviewSession getReviewSession() {
		return reviewSession;
	}
	
	public static void setReviewSession(ReviewSession session) {
		reviewSession = session;
	}
	
	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		Scene scene = SceneLoader.loadDeckSelectionScene("user01");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Config.initialize();
		if (Config.getValue("devMode").compareTo("true") == 0) {
			System.out.println(Config.getAllConfigData());
		}
		
		launch(args);
		
		if (reviewSession != null) {
			try {
				reviewSession.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	public void startReviewScene() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewScene.fxml"));
			
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
}
