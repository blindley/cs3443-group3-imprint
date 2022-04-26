package application;

public class FlashCard {
	String front;
	String back;

	/**
	 * Getter for card front
	 * 
	 * @return the front text of the card
	 */
	String getFront() {
		return front;
	}

	/**
	 * Getter for the back of the card
	 * 
	 * @return the back text of the card
	 */
	String getBack() {
		return back;
	}

	/**
	 * Setter for the front of the card
	 * 
	 * @param  newFront		The new text for the card front
	 */
	void setFront(String newFront) {
		this.front = newFront;
	}

	/**
	 * Setter for the back of the card
	 * 
	 * @param newBack		The new text for the card back
	 */
	void setBack(String newBack) {
		this.back = newBack;
	}
}
