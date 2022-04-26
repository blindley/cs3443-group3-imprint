package application;

import java.util.HashMap;
import java.util.TreeSet;

public class FlashCardDeck {
	HashMap<String, FlashCard> theCards;

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	FlashCardDeck() {
		theCards = new HashMap<String, FlashCard>();
	}

	//**
	* TODO: purpose of the function
	* 
	* @param TODO: purpose of the parameter
	* @return TODO: What does this function return?
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
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public TreeSet<String> getCardIds() {
		TreeSet<String> ids = new TreeSet<String>();
		for (String id : theCards.keySet()) {
			ids.add(id);
		}
		return ids;
	}
}
