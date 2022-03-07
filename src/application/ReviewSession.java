package application;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class ReviewSession {
	FlashCardDeck deck;
	DeckProgress progress;
	
	String userName;
	String deckName;
	
	public ReviewSession(String userName, String deckName) {
		deck = new FlashCardDeck();
		progress = new DeckProgress();
		this.userName = userName;
		this.deckName = deckName;
		
		ArrayList<String[]> cardData;
		
		String deckFilename = "data/decks/" + deckName + ".csv";
		
		try {
			cardData = CSVLoader.loadCSV(deckFilename);
		} catch (IOException e) {
			cardData = generateSampleCardData();
		}
		
		for (int i = 0; i < cardData.size(); i++) {
			String[] card = cardData.get(i);
			String id = card[0];
			String front = card[1];
			String back = card[2];
			
			FlashCard fc = new FlashCard();
			fc.setFront(front);
			fc.setBack(back);
			
			deck.addOrUpdateCard(id, fc);
//			progress.addNewCard(id);
		}
		
		try {
			progress.load(userName, deckName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FlashCard getNextCard() {
		String id = progress.getNextDueCardId();
		FlashCard fc = deck.getCard(id);
		
		return fc;
	}
	
	public void passNextCard() {
		progress.passNextCard();
	}
	
	public void failNextCard() {
		progress.failNextCard();
	}
	
	public void save() throws IOException {
		progress.save(userName, deckName);
	}
	
	static private ArrayList<String[]> generateSampleCardData() {
		ArrayList<String[]> cardData = new ArrayList<String[]>();
		
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
		
		return cardData;
	}
}

