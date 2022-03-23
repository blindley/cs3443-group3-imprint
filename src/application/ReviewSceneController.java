package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReviewSceneController implements Initializable {
	public Label frontLabel;
	public Label backLabel;
	public Button passButton;
	public Button failButton;
	public Button flipButton;
	
	ReviewSession session;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String startupDeckName = Config.getValue("startupDeck");
		if (startupDeckName == null) {
			startupDeckName = "sample-deck";
		}
		
//		session = new ReviewSession("user01", startupDeckName);
//		
//		FlashCard currentCard = session.getNextCard();
//		// TODO: This shouldn't be necessary. I should be able to end the session, but getWindow() is returning null
//		if (currentCard != null) {
//			frontLabel.setText(currentCard.getFront());
//			backLabel.setText(currentCard.getBack());
//		}
	}
	
	public void initSession(String userName, String deckName) {
		session = new ReviewSession(userName, deckName);
		
		FlashCard currentCard = session.getNextCard();
		// TODO: This shouldn't be necessary. I should be able to end the session, but getWindow() is returning null
		if (currentCard != null) {
			frontLabel.setText(currentCard.getFront());
			backLabel.setText(currentCard.getBack());
		}
	}
	
	public void shutdown() {
		try {
			session.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void flipToFront() {
		passButton.setVisible(false);
		failButton.setVisible(false);
		backLabel.setVisible(false);
		
		flipButton.setVisible(true);
	}
	
	private void flipToBack() {
		passButton.setVisible(true);
		failButton.setVisible(true);
		backLabel.setVisible(true);
		
		flipButton.setVisible(false);		
	}
	
	private void endSession() {
//		try {
//			session.save();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		Stage stage = (Stage) flipButton.getScene().getWindow();
		stage.close();
	}
	
	private void nextCard() {
		FlashCard currentCard = session.getNextCard();
		
		if (currentCard == null) {
			endSession();
		} else {
			frontLabel.setText(currentCard.getFront());
			backLabel.setText(currentCard.getBack());
		
			flipToFront();
		}
	}
	
	public void onPassButtonPressed() {
		session.passNextCard();		
		nextCard();
	}
	
	public void onFailButtonPressed() {
		session.failNextCard();
		nextCard();
	}
	
	public void onFlipButtonPressed() {
		flipToBack();
	}
}
