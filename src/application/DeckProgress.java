package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class DeckProgress {
	CardProgress nextCard;
	ArrayDeque<String> newCards;
	PriorityQueue<CardProgress> reviewQueue;

	int maxNewCardsPerDay = 5;
	int newCardsAddedToday;

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public DeckProgress() {
		nextCard = null;
		newCards = new ArrayDeque<String>();
		reviewQueue = new PriorityQueue<CardProgress>(new CardProgressComparator());

		newCardsAddedToday = 0;
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	private void moveNewCardToReviewQueue() {
		if (newCardsAddedToday < maxNewCardsPerDay) {
			String nextNewCardId = newCards.poll();
			if (nextNewCardId != null) {
				CardProgress newCard = new CardProgress();
				newCard.cardId = nextNewCardId;
				newCard.interval = 0;
				newCard.dueDate = now().minusNanos(10000000);
				reviewQueue.add(newCard);

				newCardsAddedToday++;
			}
		}
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	private void updateNextCard() {
		if (nextCard == null) {
			CardProgress nextReviewCard = reviewQueue.peek();

			if (nextReviewCard != null) {
				LocalDateTime today = now();
				if (nextReviewCard.dueDate.compareTo(today) <= 0) {
					nextCard = reviewQueue.remove();
					return;
				}
			}

			moveNewCardToReviewQueue();
			nextReviewCard = reviewQueue.peek();

			if (nextReviewCard != null) {
				LocalDateTime today = now();
				if (nextReviewCard.dueDate.compareTo(today) <= 0) {
					nextCard = reviewQueue.remove();
					return;
				}
			}
		}
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	void addNewCard(String id) {
		newCards.add(id);
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public String getNextDueCardId() {
		updateNextCard();
		if (nextCard == null) {
			return null;
		}

		return nextCard.cardId;
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	public void passNextCard() {
		updateNextCard();
		if (nextCard != null) {
			if (nextCard.interval == 0) {
				nextCard.interval = 1;
			} else {
				nextCard.interval *= 2;
			}

			nextCard.dueDate = today().plusDays(nextCard.interval);
			reviewQueue.add(nextCard);
			nextCard = null;
		}
	}

	/**
	 * When the user fails a card it gets moved to the back of the queue until the
	 * user passes the card
	 */
	public void failNextCard() {
		moveNewCardToReviewQueue();

		updateNextCard();
		if (nextCard != null) {
			nextCard.interval = 0;
			nextCard.dueDate = now();
			reviewQueue.add(nextCard);
			nextCard = null;
		}
	}

	/**
	 * @param userName
	 * @param deckName
	 * @throws IOException
	 */
	public void save(String userName, String deckName) throws IOException {
		String path = buildProgressFilePath(userName, deckName);
		File file = new File(path);
		file.getParentFile().mkdirs();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

			String mostRecentSessionInfo = "#mostRecentSession," + today() + "," + newCardsAddedToday;
			writer.write(mostRecentSessionInfo);
			writer.newLine();

			if (nextCard != null) {
				writer.write(nextCard.toString());
				writer.newLine();
			}

			for (CardProgress cp : reviewQueue) {
				writer.write(cp.toString());
				writer.newLine();
			}

			for (String cardId : newCards) {
				String line = "#cardProgress," + cardId + ",0," + "new";
				writer.write(line);
				writer.newLine();
			}
		}
	}

	/**
	 * @param userName
	 * @param deckName
	 * @param deck
	 * @throws IOException
	 */
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
			if (row[0].equals("#mostRecentSession")) {
				LocalDateTime mostRecentSessionDate = LocalDateTime.parse(row[1]);
				if (mostRecentSessionDate.compareTo(today()) >= 0) {
					newCardsAddedToday = Integer.parseInt(row[2]);
				} else {
					newCardsAddedToday = 0;
				}
			} else if (row[0].equals("#cardProgress")) {
				String id = row[1];
				int interval = Integer.parseInt(row[2]);
				if (row[3].compareTo("new") == 0) {
					newCards.add(id);
				} else {
					LocalDateTime dueDate = LocalDateTime.parse(row[3]);
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

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	private static String buildProgressFilePath(String userName, String deckName) {
		return "data/users/" + userName + "/" + deckName + ".csv";
	}

	private static LocalDateTime now() {
		// Used for simulating a different date, for testing the scheduling
		// functionality
		int dateOffset = 0;
		String dateOffsetStr = Config.getValue("dateOffset");
		if (!dateOffsetStr.isEmpty()) {
			dateOffset = Integer.parseInt(dateOffsetStr);
		}

		return LocalDateTime.now().plusDays(dateOffset);
	}

	/**
	 * TODO: purpose of the function
	 * 
	 * @param TODO: purpose of the parameter
	 * @return TODO: What does this function return?
	 */
	private static LocalDateTime today() {
		return now().toLocalDate().atStartOfDay();
	}
}

class CardProgress {
	public String cardId;
	public int interval;
	public LocalDateTime dueDate;

	@Override
	public String toString() {
		return "#cardProgress," + cardId + "," + interval + "," + dueDate;
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
