package application;

import java.util.HashMap;

public class FlashCardDeck {
	HashMap<String, FlashCard> theCards;
	
	FlashCardDeck() {
		theCards = new HashMap<String, FlashCard>();
	}
	
	public FlashCard getCard(String id) {
		return theCards.get(id);
	}
	
	public void addOrUpdateCard(String id, FlashCard card) {
		theCards.put(id, card);
	}
}
