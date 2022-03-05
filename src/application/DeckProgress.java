package application;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DeckProgress {
	CardProgress nextCard;
	ArrayDeque<String> newCards;
	PriorityQueue<CardProgress> reviewQueue;
	
	public DeckProgress() {
		nextCard = null;
		newCards = new ArrayDeque<String>();
		reviewQueue = new PriorityQueue<CardProgress>(new CardProgressComparator());
	}
	
	private void updateNextCard() {
		if (nextCard == null) {
			CardProgress nextReviewCard = reviewQueue.peek();
			if (nextReviewCard != null) {
				LocalDate today = LocalDate.now();
				if (nextReviewCard.dueDate.compareTo(today) <= 0) {
					nextCard = reviewQueue.remove();
					return;
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
	}
	
	void addNewCard(String id) {
		newCards.add(id);
	}
	
	public String getNextDueCardId() {
		updateNextCard();
		if (nextCard == null) { return null; }
		
		return nextCard.cardId;
	}
	
	public void passNextCard() {
		updateNextCard();
		if (nextCard != null) {		
			if (nextCard.interval == 0) {
				nextCard.interval = 1;
			} else {
				nextCard.interval *= 2;
			}
			
			nextCard.dueDate = LocalDate.now().plusDays(nextCard.interval);
			reviewQueue.add(nextCard);
			nextCard = null;
		}
	}
	
	public void failNextCard() {
		updateNextCard();
		if (nextCard != null) {
			nextCard.interval = 0;
			nextCard.dueDate = LocalDate.now();
			reviewQueue.add(nextCard);
			nextCard = null;
		}
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