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
	 * Returns the primary (and only) stage for the application, to be shared
	 * by all scenes.
	 * 
	 * @return the primary stage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the current review session.
	 * 
	 * @return The review session
	 */
	public static ReviewSession getReviewSession() {
		return reviewSession;
	}

	/**
	 * Sets the current review session.
	 * 
	 * @param session	The new session
	 */
	public static void setReviewSession(ReviewSession session) {
		reviewSession = session;
	}

	/**
	 * @return the username for the current user
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * Sets the current user name
	 * 
	 * @param newUserName the new value for the current user name
	 */
	public static void setUserName(String newUserName) {
		userName = newUserName;
	}

	/**
	 * The starting point for a JavaFX application. Transfers control to the
	 * user login scene.
	 * 
	 * @param stage		The primary stage for the application
	 */
	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		
		Scene scene;		
		if (Config.getValue("skipLogin").compareTo("true") == 0) {
			scene = SceneLoader.loadDeckSelectionScene("testuser");
		} else {
			scene = SceneLoader.loadUserLoginScene();
		}
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("ImprinT");
		primaryStage.show();
	}

	/**
	 * The entry point for the application. Loads configuration data, then
	 * launches JavaFX. Saves the review session in case the user exits their
	 * session abnormally.
	 * 
	 * @param args	command line arguments, unused
	 */
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
