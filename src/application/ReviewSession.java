package application;

import java.io.IOException;
import java.util.ArrayList;

public class ReviewSession {
	FlashCardDeck deck;
	DeckProgress progress;

	String userName;
	String deckName;

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
//			progress.addNewCard(id);
		}

		try {
			progress.load(userName, deckName, deck);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean hasNextCard() {
		return progress.getNextDueCardId() != null;
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
}
