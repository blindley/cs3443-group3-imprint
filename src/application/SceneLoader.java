package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneLoader {
	
	public static Scene loadDeckSelectionScene(String userName) throws IOException {
		URL url = new File("src/application/DeckSelectionScene.fxml").toURI().toURL();
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 600, 450);
		
		url = new File("src/application/application.css").toURI().toURL();
		scene.getStylesheets().add(url.toExternalForm());
		
		return scene;
	}
	
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
}
