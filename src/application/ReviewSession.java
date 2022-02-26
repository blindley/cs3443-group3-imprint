package application;

import java.util.ArrayDeque;

public class ReviewSession {
	ArrayDeque<FlashCard> deck;
	
	public ReviewSession() {
		deck = new ArrayDeque<FlashCard>();
	}
	
	public int countRemaining() {
		return deck.size();
	}
	
	public void addCard(FlashCard card) {
		deck.add(card);
	}
	
	public FlashCard removeCard() {
		return deck.remove();
	}
}

