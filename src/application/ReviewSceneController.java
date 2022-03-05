package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class ReviewSceneController implements Initializable {
	public Labeled frontLabel;
	public Labeled backLabel;
	public Button passButton;
	public Button failButton;
	public Button flipButton;
	
	ReviewSession session;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		session = new ReviewSession();
		
		FlashCard currentCard = session.getNextCard();
		frontLabel.setText(currentCard.getFront());
		backLabel.setText(currentCard.getBack());
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
		try {
			session.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
