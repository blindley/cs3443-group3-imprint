package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;

public class PassOrFailController implements Initializable {
	public Labeled frontLabel;
	public Labeled backLabel;
	public Button passButton;
	public Button failButton;
	public Button flipButton;
	
	ReviewSession session;
	FlashCard currentCard;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		session = new ReviewSession();
		
		ArrayList<String[]> cardData = new ArrayList<String[]>();
		
		try {
			cardData = CSVLoader.loadCSV("state-capitals.csv");
		} catch (IOException e) {
			String[] sampleCardData = {
				"What is the capital of France?", "Paris",
				"What is the derivative of sin(x)?", "cos(x)",
				"What is the opposite of left?", "right",
				"What is the average distance from the Earth to the Sun", "93 million miles",
				"How many letters are in the alphabet?", "26"
			};

			cardData = new ArrayList<String[]>();

			for (int i = 0; i < sampleCardData.length / 2; i++) {
				String[] row = new String[3];
				row[0] = "" + i;
				row[1] = sampleCardData[i * 2];
				row[2] = sampleCardData[i * 2 + 1];
				cardData.add(row);
			}
		}
		
		for (int i = 0; i < cardData.size(); i++) {
			String front = cardData.get(i)[1];
			String back = cardData.get(i)[2];
			
			FlashCard fc = new FlashCard();
			fc.setFront(front);
			fc.setBack(back);
			
			session.addCard(fc);
		}
		
		currentCard = session.removeCard();		
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
	
	public void onPassButtonPressed() {
		session.addCard(currentCard);
		currentCard = session.removeCard();		
		frontLabel.setText(currentCard.getFront());
		backLabel.setText(currentCard.getBack());
		
		flipToFront();
	}
	
	public void onFailButtonPressed() {
		session.addCard(currentCard);
		currentCard = session.removeCard();		
		frontLabel.setText(currentCard.getFront());
		backLabel.setText(currentCard.getBack());
		
		flipToFront();
	}
	
	public void onFlipButtonPressed() {
		flipToBack();
	}
	
}
