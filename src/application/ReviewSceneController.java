package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ReviewSceneController implements Initializable {
	/**
	 * The label for the front of the cards
	 */
	public Label frontLabel;
	/**
	 * The label for the back of the cards
	 */
	public Label backLabel;
	/**
	 * The button that allows the card to be flipped
	 */
	public Button flipButton;

	HBox passFailButtonGroup;

	@FXML
	private FlowPane reviewControlsPane;

	ReviewSession session;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		session = Main.getReviewSession();

		Font cardFont = new Font(36);
		frontLabel.setFont(cardFont);
		backLabel.setFont(cardFont);

		FlashCard currentCard = session.getNextCard();
		if (currentCard != null) {
			frontLabel.setText(currentCard.getFront());
			backLabel.setText(currentCard.getBack());

			flipButton = new Button();
			flipButton.setText("Flip");
			flipButton.setPrefWidth(80.0);
			flipButton.setOnAction(e -> {
				onFlipButtonPressed();
			});

			Button passButton = new Button();
			passButton.setText("Pass");
			passButton.setPrefWidth(80.0);
			passButton.setOnAction(e -> {
				onPassButtonPressed();
			});

			Button failButton = new Button();
			failButton.setText("Fail");
			failButton.setPrefWidth(80.0);
			failButton.setOnAction(e -> {
				onFailButtonPressed();
			});

			Separator passAndFailSeparator = new Separator();
			passAndFailSeparator.setVisible(false);
			passAndFailSeparator.setPrefWidth(20.0);

			passFailButtonGroup = new HBox();
			passFailButtonGroup.getChildren().addAll(failButton, passAndFailSeparator, passButton);

			flipToFront();
		} else {
			System.out.println("No cards left to review today");
		}
	}

	private void flipToFront() {
		backLabel.setVisible(false);

		reviewControlsPane.getChildren().remove(passFailButtonGroup);
		reviewControlsPane.getChildren().add(flipButton);
	}

	private void flipToBack() {
		backLabel.setVisible(true);

		reviewControlsPane.getChildren().remove(flipButton);
		reviewControlsPane.getChildren().add(passFailButtonGroup);
	}

	private void endSession() {
		try {
			session.save();
			Main.setReviewSession(null);

			String userName = Main.getUserName();
			Scene scene = SceneLoader.loadDeckSelectionScene(userName);

			Stage primaryStage = Main.getPrimaryStage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void nextCard() {
		FlashCard currentCard = session.getNextCard();

		if (currentCard == null) {
			endSession();
		} else {
			frontLabel.setText(currentCard.getFront());
			backLabel.setText(currentCard.getBack());

			flipToFront();
		}
	}

	/**
	 * if card is memorized it allows the user to press a button to pass to the next
	 * card
	 */
	public void onPassButtonPressed() {
		session.passNextCard();
		nextCard();
	}

	/**
	 * if card is not memorized when flipped the user presses the fail button which
	 * pushes the card to the back of the deck
	 */
	public void onFailButtonPressed() {
		session.failNextCard();
		nextCard();
	}

	/**
	 * this allows the user to flip the card after reading the front to the back
	 */
	public void onFlipButtonPressed() {
		flipToBack();
	}

	@FXML
	void onEndSessionButtonPressed(ActionEvent event) {
		endSession();
	}
}
