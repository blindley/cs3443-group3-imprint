package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

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
	
	public TreeSet<String> getCardIds() {
		TreeSet<String> ids = new TreeSet<String>();
		for (String id : theCards.keySet()) {
			ids.add(id);
		}
		return ids;
	}
}
