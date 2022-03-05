package application;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DeckProgress {
	CardProgress nextCard;
	ArrayDeque<String> newCards;
	PriorityQueue<CardProgress> reviewQueue;
	
	DeckProgress() {
		nextCard = null;
		newCards = new ArrayDeque<String>();
		reviewQueue = new PriorityQueue<CardProgress>(0, new CardProgressComparator());
	}
	
	void updateNextCard() {
		nextCard = null;
		
		CardProgress nextReviewCard = reviewQueue.element();
		if (nextReviewCard != null) {
			LocalDate today = LocalDate.now();
			if (nextReviewCard.dueDate.compareTo(today) <= 0) {
				nextCard = reviewQueue.remove();
			}
		}
		
		String nextNewCardId = newCards.poll();
		if (nextNewCardId != null) {
			nextCard = new CardProgress();
			nextCard.cardId = nextNewCardId;
			nextCard.interval = 0;
			nextCard.dueDate = LocalDate.now();
		}
	}
	
	String getNextDueCardId() {
		if (nextCard == null) { return null; }
		
		return nextCard.cardId;
	}
	
	void passNextDueCard() {
		if (nextCard.interval == 0) {
			nextCard.interval = 1;
		} else {
			nextCard.interval *= 2;
		}
		
		nextCard.dueDate = LocalDate.now().plusDays(nextCard.interval);
		reviewQueue.add(nextCard);
		
		updateNextCard();
	}
	
	void failNextDueCard() {
		nextCard.interval = 0;
		nextCard.dueDate = LocalDate.now();
		reviewQueue.add(nextCard);
		updateNextCard();
	}
}

class CardProgress {
	public String cardId;
	public int interval;
	public LocalDate dueDate;
}

class CardProgressComparator implements Comparator<CardProgress> {

	@Override
	public int compare(CardProgress arg0, CardProgress arg1) {
		int result = arg0.dueDate.compareTo(arg1.dueDate);
		if (result == 0) {
			result = arg0.interval - arg1.interval;
			
			if (result == 0) {
				result = arg0.cardId.compareTo(arg1.cardId);
			}
		}		
		
		return result;
	}
	
}