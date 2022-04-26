package application;

import java.util.HashMap;
import java.util.TreeSet;

public class FlashCardDeck {
	HashMap<String, FlashCard> theCards;

	/**
	 * Constructor. Initilizes theCards to empty hash map
	 */
	FlashCardDeck() {
		theCards = new HashMap<String, FlashCard>();
	}

	/**
	 * Gets the flash card with the specified id
	 * 
	 * @param id		The id of the card to get
	 * @return the flash card with the specified id
	 */
	public FlashCard getCard(String id) {
		return theCards.get(id);
	}

	/**
	 * Adds a new card into the deck with the specified id, or updates the card
	 * if it already exists.
	 * 
	 * @param id	The id of the card to add or update
	 * @param card	The card contents
	 */
	public void addOrUpdateCard(String id, FlashCard card) {
		theCards.put(id, card);
	}

	/**
	 * Returns an set containing the ids of the cards, sorted in
	 * lexicographical order.
	 * 
	 * @return The set of card ids
	 */
	public TreeSet<String> getCardIds() {
		TreeSet<String> ids = new TreeSet<String>();
		for (String id : theCards.keySet()) {
			ids.add(id);
		}
		return ids;
	}
}
