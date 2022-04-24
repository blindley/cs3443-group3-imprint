package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	static Stage primaryStage;

	static ReviewSession reviewSession;
	static String userName;
	/**
	 * @return the primary stage for scenebuilder
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	/**
	 * @return The review session for the cards
	 */
	public static ReviewSession getReviewSession() {
		return reviewSession;
	}
	/**
	 * @param session
	 */
	public static void setReviewSession(ReviewSession session) {
		reviewSession = session;
	}
	/**
	 * @return the username for the user
	 */
	public static String getUserName() {
		return userName;
	}
	/**
	 * @param newUserName when the user creates a new username
	 */
	public static void setUserName(String newUserName) {
		userName = newUserName;
	}

	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		// Scene scene = SceneLoader.loadDeckSelectionScene("user01");
		Scene scene = SceneLoader.loadUserLoginScene();
		primaryStage.setScene(scene);

		primaryStage.setTitle("ImprinT");
		primaryStage.show();
	}
	/**
	 * @param args the main string
	 */
	public static void main(String[] args) {

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
