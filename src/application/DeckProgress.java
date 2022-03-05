package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class DeckProgress {
	ArrayList<String> newCards;
	PriorityQueue<CardProgress> reviewQueue;
	
	DeckProgress() {
		newCards = new ArrayList<String>();
		reviewQueue = new PriorityQueue<CardProgress>(0, new CardProgressComparator());
	}
}

class CardProgress {
	public String cardId;
	public int interval;
	public Date dueDate;
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