package application;

import java.io.IOException;
import java.util.ArrayList;

public class ReviewSession {
	FlashCardDeck deck;
	DeckProgress progress;

	String userName;
	String deckName;

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
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
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public boolean hasNextCard() {
		return progress.getNextDueCardId() != null;
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public FlashCard getNextCard() {
		String id = progress.getNextDueCardId();
		FlashCard fc = deck.getCard(id);

		return fc;
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public void passNextCard() {
		progress.passNextCard();
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public void failNextCard() {
		progress.failNextCard();
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public void save() throws IOException {
		progress.save(userName, deckName);
	}
}
