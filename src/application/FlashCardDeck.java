package application;

import java.util.HashMap;
import java.util.TreeSet;

public class FlashCardDeck {
	HashMap<String, FlashCard> theCards;

	FlashCardDeck() {
		theCards = new HashMap<String, FlashCard>();
	}
	/**
	 * @param id
	 * @return
	 */
	public FlashCard getCard(String id) {
		return theCards.get(id);
	}	
	/**
	 * @param id
	 * @param card
	 */
	public void addOrUpdateCard(String id, FlashCard card) {
		theCards.put(id, card);
	}
	/**
	 * @return
	 */
	public TreeSet<String> getCardIds() {
		TreeSet<String> ids = new TreeSet<String>();
		for (String id : theCards.keySet()) {
			ids.add(id);
		}
		return ids;
	}
}
