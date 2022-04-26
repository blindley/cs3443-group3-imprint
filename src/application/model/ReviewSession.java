package application.model;

import java.io.IOException;
import java.util.ArrayList;

import application.CSVLoader;

public class ReviewSession {
	FlashCardDeck deck;
	DeckProgress progress;

	String userName;
	String deckName;

	/**
	 * ReviewSession constructor. Loads the flash card deck, and the deck
	 * progress file for the specified user and deck.
	 * 
	 * @param userName	The user name for the current session
	 * @param deckName	The deck name for the current session
	 */
	public ReviewSession(String userName, String deckName) throws IOException {
		deck = new FlashCardDeck();
		progress = new DeckProgress();
		this.userName = userName;
		this.deckName = deckName;

		ArrayList<String[]> cardData;

		String deckFilename = "data/decks/" + deckName + ".csv";

		cardData = CSVLoader.loadCSV(deckFilename);

		for (int i = 0; i < cardData.size(); i++) {
			String[] card = cardData.get(i);
			String id = card[0];
			String front = card[1];
			String back = card[2];

			FlashCard fc = new FlashCard();
			fc.setFront(front);
			fc.setBack(back);

			deck.addOrUpdateCard(id, fc);
		}

		try {
			progress.load(userName, deckName, deck);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if there are any cards left to review for today.
	 * 
	 * @return true if there are any cards left to review
	 */
	public boolean hasNextCard() {
		return progress.getNextDueCardId() != null;
	}

	/**
	 * Gets the next due card for today, or null
	 * 
	 * @return The next due card
	 */
	public FlashCard getNextCard() {
		String id = progress.getNextDueCardId();
		FlashCard fc = deck.getCard(id);

		return fc;
	}

	/**
	 * Passes the next card, increasing its interval and moving to the next
	 * due card
	 */
	public void passNextCard() {
		progress.passNextCard();
	}

	/**
	 * Fails the next card, resetting its interval to 0, and moving to the
	 * next due card.
	 */
	public void failNextCard() {
		progress.failNextCard();
	}

	/**
	 * Saves the current user's progress in the current deck
	 */
	public void save() throws IOException {
		progress.save(userName, deckName);
	}
}
