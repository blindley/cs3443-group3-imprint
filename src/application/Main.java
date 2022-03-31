package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


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
}
