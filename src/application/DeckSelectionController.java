package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeckSelectionController implements Initializable {

    @FXML
    private ListView<String> deckListView;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File deckDirectory = new File("data/decks");
		
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) { return name.endsWith(".csv"); }			
		};
		
		File[] deckFileList = deckDirectory.listFiles(filter);
		ObservableList<String> deckNameList = FXCollections.observableArrayList();
		
		for (int i = 0; i < deckFileList.length; i++) {
			String deckName = deckFileList[i].getName();
			int end = deckName.lastIndexOf(".csv");
			deckName = deckName.substring(0, end);
			deckNameList.add(deckName); 
		}
		
		deckListView.setItems(deckNameList);
	}
	
	@FXML
    void onBeginSessionButtonClicked(ActionEvent event) throws IOException {
		String userName = Main.getUserName();
		String deckName = deckListView.getSelectionModel().getSelectedItem();
		Scene scene = SceneLoader.loadReviewSessionScene(userName, deckName);
		
		if (scene != null) {		
	    	Stage primaryStage = Main.getPrimaryStage();
	    	primaryStage.setScene(scene);
	    	primaryStage.show();
		} else {
			System.out.println("No cards left to review in that deck");
		}
    }
    
}
