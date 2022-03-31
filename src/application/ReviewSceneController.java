package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ReviewSceneController implements Initializable {
	public Label frontLabel;
	public Label backLabel;
	public Button passButton;
	public Button failButton;
	public Button flipButton;
	Separator passAndFailSeparator;
	
	@FXML
    private FlowPane reviewControlsPane;
	
	ReviewSession session;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		session = Main.getReviewSession();
		
		FlashCard currentCard = session.getNextCard();
		// TODO: This shouldn't be necessary. I should be able to end the session, but getWindow() is returning null
		if (currentCard != null) {
			frontLabel.setText(currentCard.getFront());
			backLabel.setText(currentCard.getBack());
			
			passButton = new Button();
			passButton.setText("Pass");
			passButton.setPrefWidth(80.0);
			passButton.setOnAction(e -> {
				onPassButtonPressed();
			});
			
			failButton = new Button();
			failButton.setText("Fail");
			failButton.setPrefWidth(80.0);
			failButton.setOnAction(e -> {
				onFailButtonPressed();
			});
			
			flipButton = new Button();
			flipButton.setText("Flip");
			flipButton.setPrefWidth(80.0);
			flipButton.setOnAction(e -> {
				onFlipButtonPressed();
			});
			
			passAndFailSeparator = new Separator();
			passAndFailSeparator.setPrefWidth(20.0);
			
//			reviewControlsPane.getChildren().addAll(passButton, failButton, flipButton);			
			
			flipToFront();
		} else {
			System.out.println("No cards left to review today");
		}
	}
	
	private void flipToFront() {
		backLabel.setVisible(false);
		
		reviewControlsPane.getChildren().removeAll(failButton, passAndFailSeparator, passButton);
		reviewControlsPane.getChildren().add(flipButton);
	}
	
	private void flipToBack() {
		backLabel.setVisible(true);
		
		reviewControlsPane.getChildren().remove(flipButton);
		reviewControlsPane.getChildren().addAll(failButton, passAndFailSeparator, passButton);
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
		} catch(Exception e) {
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
	
	public void onPassButtonPressed() {
		session.passNextCard();		
		nextCard();
	}
	
	public void onFailButtonPressed() {
		session.failNextCard();
		nextCard();
	}
	
	public void onFlipButtonPressed() {
		flipToBack();
	}

    @FXML
    void onEndSessionButtonPressed(ActionEvent event) {
    	endSession();
    }
}
