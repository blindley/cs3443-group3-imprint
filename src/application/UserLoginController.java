package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserLoginController {

	@FXML
    private RadioMenuItem sq4_1;

    @FXML
    private RadioMenuItem sq3_2;

    @FXML
    private RadioMenuItem sq2_3;

    @FXML
    private RadioMenuItem sq5_1;

    @FXML
    private RadioMenuItem sq4_2;

    @FXML
    private RadioMenuItem sq3_3;

    @FXML
    private RadioMenuItem sq5_2;

    @FXML
    private RadioMenuItem sq4_3;

    @FXML
    private RadioMenuItem sq5_3;

    @FXML
    private TextField q3Field;

    @FXML
    private PasswordField passwrdConfirmation;

    @FXML
    private RadioMenuItem sq1_1;

    @FXML
    private RadioMenuItem sq2_1;

    @FXML
    private RadioMenuItem sq1_2;

    @FXML
    private RadioMenuItem sq3_1;

    @FXML
    private RadioMenuItem sq2_2;

    @FXML
    private RadioMenuItem sq1_3;

    @FXML
    private MenuButton q1;

    @FXML
    private MenuButton q2;

    @FXML
    private MenuButton q3;

    @FXML
    private TextField q2Field;

    @FXML
    private Label errorDisplay;

    @FXML
    private TextField q1Field;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwrdField;

    ToggleGroup securityQuestions1 = new ToggleGroup();
    ToggleGroup securityQuestions2 = new ToggleGroup();
    ToggleGroup securityQuestions3 = new ToggleGroup();
    
    
    @FXML
    void validateUser(ActionEvent event) {

    }

    
    

    public void initialToggle() {
    	
    	sq1_1.setToggleGroup(securityQuestions1);
    	sq2_1.setToggleGroup(securityQuestions1);
    	sq3_1.setToggleGroup(securityQuestions1);
    	sq4_1.setToggleGroup(securityQuestions1);
    	sq5_1.setToggleGroup(securityQuestions1);
    	
    	sq1_2.setToggleGroup(securityQuestions2);
    	sq2_2.setToggleGroup(securityQuestions2);
    	sq3_2.setToggleGroup(securityQuestions2);
    	sq4_2.setToggleGroup(securityQuestions2);
    	sq5_2.setToggleGroup(securityQuestions2);
    	
    	sq1_3.setToggleGroup(securityQuestions3);
    	sq2_3.setToggleGroup(securityQuestions3);
    	sq3_3.setToggleGroup(securityQuestions3);
    	sq4_3.setToggleGroup(securityQuestions3);
    	sq5_3.setToggleGroup(securityQuestions3);
    
    }
    @FXML
    void sqSelect(ActionEvent event) {
    	
    	initialToggle();
    	ObservableList<MenuItem> listOfQ1 = sq2_1.getParentPopup().getItems();
		ObservableList<MenuItem> listOfQ2 = sq2_2.getParentPopup().getItems();
		ObservableList<MenuItem> listOfQ3 = sq2_3.getParentPopup().getItems();
		
		int currentSubQ = 0;
		String questionNum = ((RadioMenuItem) event.getSource()).getId();
		
		for(int ndx = 0; ndx < listOfQ1.size(); ndx++)
		{
			if(listOfQ1.get(ndx).getId().substring(2,3).equalsIgnoreCase(questionNum.substring(2,3)))
			{
				currentSubQ = ndx;
			}
		}
    	
    	
    	questionNum = questionNum.substring(questionNum.length()-1);
    	
    	if(questionNum.equals("1"))
    	{
    		if(q1.getText().equals("Select a Question"))
	    	{
    			q1.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
	    		
	    	}
    		else
    		{
    			
    			for(int ndx = 0; ndx < listOfQ1.size(); ndx++)
    			{
    				if(listOfQ1.get(ndx).getText().equalsIgnoreCase(q1.getText()))
    				{
    					listOfQ1.get(ndx).setVisible(true);
    					listOfQ2.get(ndx).setVisible(true);
    					listOfQ3.get(ndx).setVisible(true);
    				}
    			}
    			
    			q1.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
    			
    			
    		}
    		
    	}
    	else if(questionNum.equals("2"))
    	{
    		if(q2.getText().equals("Select a Question"))
	    	{
    			q2.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
	    		
	    	}
    		else
    		{
    			
    			for(int ndx = 0; ndx < listOfQ1.size(); ndx++)
    			{
    				if(listOfQ1.get(ndx).getText().equalsIgnoreCase(q2.getText()))
    				{
    					listOfQ1.get(ndx).setVisible(true);
    					listOfQ2.get(ndx).setVisible(true);
    					listOfQ3.get(ndx).setVisible(true);
    				}
    			}
    			
    			q2.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
    			
    			
    		}
    	}
    	else
    	{
    		if(q3.getText().equals("Select a Question"))
	    	{
    			q3.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
	    		
	    	}
    		else
    		{
    			
    			for(int ndx = 0; ndx < listOfQ1.size(); ndx++)
    			{
    				if(listOfQ1.get(ndx).getText().equalsIgnoreCase(q3.getText()))
    				{
    					listOfQ1.get(ndx).setVisible(true);
    					listOfQ2.get(ndx).setVisible(true);
    					listOfQ3.get(ndx).setVisible(true);
    				}
    			}
    			
    			q3.setText(listOfQ1.get(currentSubQ).getText());
	    		
    			listOfQ1.get(currentSubQ).setVisible(false);
    			listOfQ2.get(currentSubQ).setVisible(false);
    			listOfQ3.get(currentSubQ).setVisible(false);
    			
    			
    		}
    	}
    }
    
    @FXML
    void forgotPasswordDisplay(ActionEvent event) {

    }

    @FXML
    void verifyUser(ActionEvent event) {
    	
    	try {
    		
    		if(userField.getText().isEmpty() || passwrdField.getText().isEmpty())
			{
    			throw new Exception("One or more fields is empty. Please fill out all fields to login.");
			}
    		else
    		{
    			User login = new User(userField.getText(), passwrdField.getText());
    			
    			login.userVerification();
    			
    			Stage stage = Main.getPrimaryStage();
    			Scene scene = SceneLoader.loadDeckSelectionScene(login.getUser());
    			
    			stage.setScene(scene);
    		}
    		
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void newUser(ActionEvent event) throws IOException {
    	Stage stage = Main.getPrimaryStage();
		Scene scene = SceneLoader.loadNewUserScene();
		
		stage.setScene(scene);
    }
    
    @FXML
    void home_screen(ActionEvent event) {

    }
	

}