package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SceneLoader {

	/**
	 * Loads the deck selection scene
	 * 
	 * @param userName	The user name for the session
	 * @return The deck selection scene
	 */
	public static Scene loadDeckSelectionScene(String userName) throws IOException {
		if (userName != null)
			Main.setUserName(userName);

		URL url = new File("src/application/DeckSelectionScene.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 600, 450);

		url = new File("src/application/application.css").toURI().toURL();
		scene.getStylesheets().add(url.toExternalForm());

		return scene;
	}

	/**
	 * Loads the review session scene
	 * 
	 * @param userName	The user name for this session
	 * @param deckName	The deck name for this session
	 * @return The review session scene
	 */
	public static Scene loadReviewSessionScene(String userName, String deckName) throws IOException {
		ReviewSession session = new ReviewSession(userName, deckName);

		if (session.hasNextCard()) {
			Main.setReviewSession(session);

			URL url = new File("src/application/ReviewScene.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root, 600, 450);

			url = new File("src/application/application.css").toURI().toURL();
			scene.getStylesheets().add(url.toExternalForm());

			return scene;
		} else {
			Main.setReviewSession(null);
			return null;
		}
	}

	/**
	 * Load the User Login scene
	 * 
	 * @return the user login scene
	 */
	public static Scene loadUserLoginScene() throws IOException {
		URL url = new File("src/application/UserLogin.fxml").toURI().toURL();
		AnchorPane root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 550, 400);

		return scene;
	}

	/**
	 * Load the new user scene
	 * 
	 * @return the new user scene
	 */
	public static Scene loadNewUserScene() throws IOException {
		URL url = new File("src/application/NewUser.fxml").toURI().toURL();
		AnchorPane root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 625, 450);

		return scene;
	}

	/**
	 * Load the forgot password scene
	 * 
	 * @return the forgot password scene
	 */
	public static Scene loadForgotPasswordScene() throws IOException {
		URL url = new File("src/application/ForgotPassword.fxml").toURI().toURL();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		AnchorPane root = loader.load();
		Scene scene = new Scene(root, 625, 450);

		// Intializing the ForgotPassword.fxml scene
		UserLoginController fP = loader.getController();
		fP.setStart();

		return scene;
	}

}
