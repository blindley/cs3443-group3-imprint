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
	
	ReviewSession session;
	
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
		
		FlashCard nextCard = session.removeCard();		
		frontLabel.setText(nextCard.getFront());
		backLabel.setText(nextCard.getBack());
		session.addCard(nextCard);
	}
	
	public void onPassButtonPressed() {
		FlashCard nextCard = session.removeCard();		
		frontLabel.setText(nextCard.getFront());
		backLabel.setText(nextCard.getBack());
		session.addCard(nextCard);
	}
	
	public void onFailButtonPressed() {
		FlashCard nextCard = session.removeCard();		
		frontLabel.setText(nextCard.getFront());
		backLabel.setText(nextCard.getBack());
		session.addCard(nextCard);
	}
	
}
