package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

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
				LocalDate today = now();
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
				nextCard.dueDate = now();
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
			
			nextCard.dueDate = now().plusDays(nextCard.interval);
			reviewQueue.add(nextCard);
			nextCard = null;
		}
	}
	
	public void failNextCard() {
		updateNextCard();
		if (nextCard != null) {
			nextCard.interval = 0;
			nextCard.dueDate = now();
			reviewQueue.add(nextCard);
			nextCard = null;
		}
	}
	
	public void save(String userName, String deckName) throws IOException {
		String path = buildProgressFilePath(userName, deckName);
		File file = new File(path);
		file.getParentFile().mkdirs();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			if (nextCard != null) {
				writer.write(nextCard.toString());
				writer.newLine();
			}
			
			for (CardProgress cp : reviewQueue) {
				writer.write(cp.toString());
				writer.newLine();
			}
			
			for (String cardId : newCards) {
				String line = cardId + ",0," + "new";
				writer.write(line);
				writer.newLine();
			}
		}
	}
	
	public void load(String userName, String deckName, FlashCardDeck deck) throws IOException {
		ArrayList<String[]> csvContent;
		String path = buildProgressFilePath(userName, deckName);
		
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			csvContent = CSVLoader.loadCSV(path);			
		} else {
			csvContent = new ArrayList<String[]>();
		}
		
		TreeSet<String> cardIds = deck.getCardIds();
		
		for (String[] row : csvContent) {
			if (row.length >= 3) {
				String id = row[0];
				int interval = Integer.parseInt(row[1]);
				if (row[2].compareTo("new") == 0) {
					newCards.add(id);
				} else {
					LocalDate dueDate = LocalDate.parse(row[2]);
					CardProgress cp = new CardProgress();
					cp.cardId = id;
					cp.interval = interval;
					cp.dueDate = dueDate;
					reviewQueue.add(cp);
				}
				
				cardIds.remove(id);
			}
		}
		
		// add all remaining card ids
		for (String id : cardIds) {
			newCards.add(id);
		}
	}
	
	private static String buildProgressFilePath(String userName, String deckName) {
		return "data/users/" + userName + "/" + deckName + ".csv";
	}

	private static LocalDate now() {
		// Used for simulating a different date, for testing the scheduling functionality
		int dateOffset = 0;
		String dateOffsetStr = Config.getValue("dateOffset");
		if (dateOffsetStr != null) {
			dateOffset = Integer.parseInt(dateOffsetStr);
		}
		
		return LocalDate.now().plusDays(dateOffset);
	}
}

class CardProgress {
	public String cardId;
	public int interval;
	public LocalDate dueDate;
	
	@Override
	public String toString() {
		return cardId + "," + interval + "," + dueDate;
	}
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