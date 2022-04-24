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
    private TextField inputField;

    @FXML
    private Label passwrdLabel;

    @FXML
    private TextField queAns;

    @FXML
    private Label quePrompt;

    @FXML
    private Label inputPrompt;

    @FXML
    private Label confirmLabel;

    @FXML
    private Label prompts;

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
    void validateUser(ActionEvent event) throws IOException {

    	try {
    		
    		if(userField.getText().isEmpty() || passwrdField.getText().isEmpty() || passwrdConfirmation.getText().isEmpty())
			{
    			throw new Exception("One or more fields is empty. Please fill out all fields to create an account.");
			}
    		
    		if(q1Field.getText().isEmpty() || q2Field.getText().isEmpty() || q3Field.getText().isEmpty())
			{
    			throw new Exception("One or more fields is empty. Please fill out all fields to create an account.");
			}
    		
    		if(q1.getText().equals("Select a Question") || q2.getText().equals("Select a Question") || q3.getText().equals("Select a Question"))
    		{
    			throw new Exception("Please select a question for all three entries.");
    		}
    		
    		if(!userField.getText().matches("^[a-zA-Z0-9]{1}[a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]*[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]?@[a-zA-Z0-9]{1}"
    		  + "[a-zA-Z0-9.\\-]*[a-zA-Z0-9]{1}\\.[a-zA-Z0-9]{2,}$")) {
    			throw new Exception("Invalid email. Please follow common email format and try again.");
    		}
    		
    		if(passwrdField.getText().length() < 8)
    		{
    			throw new Exception("Please insert a minimum password length of 8 characters.");
    		}
    		
    		for(int i = 0; i < passwrdField.getText().length();i++)
    		{
    			if((int)passwrdField.getText().charAt(i) < 32 || (int)passwrdField.getText().charAt(i) > 126)
    			{
    				throw new Exception("Invalid character used in password: " + passwrdField.getText().charAt(i) +
    									". Please use standard ASCII characters.");
    			}
    		}
    		
    		if(!passwrdConfirmation.getText().equals(passwrdField.getText()))
    		{
    			throw new Exception("The passwords entered do no match. Please try again.");
    		}
    		
    		ArrayList<String> questions = new ArrayList<String>();
    		questions.add(q1.getText());
    		questions.add(q2.getText());
    		questions.add(q3.getText());
    		
    		ArrayList<String> ans = new ArrayList<String>();
    		ans.add(q1Field.getText());
    		ans.add(q2Field.getText());
    		ans.add(q3Field.getText());
    		
    		User u = new User(userField.getText(), passwrdField.getText(),questions,ans);
    		
    		u.newAddition();
    		
    		Alert success = new Alert(AlertType.INFORMATION);
    		
    		success.setTitle("Profile Created");
    		success.setHeaderText("Succesfully created an account for: " + u.getUser());
    		success.setContentText("You can now login from the home screen.");
    		success.showAndWait();
    		
    		Stage stage = Main.getPrimaryStage();
			Scene scene = SceneLoader.loadUserLoginScene();
			
			stage.setScene(scene);
    	}
    	catch(Exception e) {
    		errorDisplay.setText(e.getMessage());
    	}
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
    
  //Sets starting postion for the ForgotPassword.fxml view
    void setStart()
    {
    	passwrdLabel.setVisible(false);
    	passwrdField.setVisible(false);
    	
    	
    	quePrompt.setVisible(false);
    	queAns.setVisible(false);
    	
    	confirmLabel.setVisible(false);
    	passwrdConfirmation.setVisible(false);
    	
    	
    }
    
    @FXML
    void forgotPasswordDisplay(ActionEvent event) throws IOException {
    	
    	Stage stage = Main.getPrimaryStage();
		Scene scene = SceneLoader.loadForgotPasswordScene();
		
		stage.setScene(scene);
    	
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
    		errorDisplay.setText(e.getMessage());
    	}
    }

    @FXML
    void newUser(ActionEvent event) throws IOException {
    	Stage stage = Main.getPrimaryStage();
		Scene scene = SceneLoader.loadNewUserScene();
		
		stage.setScene(scene);
    }
    
    @FXML
    void home_screen(ActionEvent event) throws IOException {
    	Stage stage = Main.getPrimaryStage();
		Scene scene = SceneLoader.loadUserLoginScene();
		
		stage.setScene(scene);
    }
    
    @FXML
    void passwordChange(ActionEvent event) {
    	
    	User forgot = new User(inputField.getText());

    		
		try {
			
    		if(inputPrompt.isVisible())
    		{
    			if(forgot.userPresent())
    			{
    				inputPrompt.setVisible(false);
    				inputField.setVisible(false);
    			
    				quePrompt.setText(forgot.randQuestion());
    				quePrompt.setVisible(true);
    				queAns.setVisible(true);
    			
    				prompts.setText("Please answer the prompted question: ");
    			}
    			else
    			{
    				throw new Exception("User does not exist. Please try again or if you are a new user click on the designated \"New User?\" link"
    					+ " in the home screen.");
    			}
    			
    		}
    		
    		if(!queAns.getText().isEmpty() && quePrompt.isVisible())
    		{
    			if(forgot.verifyAnswer(quePrompt.getText(), queAns.getText()))
    			{
	    			quePrompt.setVisible(false);
	    	    	queAns.setVisible(false);
	    	    	
	    	    	passwrdLabel.setVisible(true);
	    	    	passwrdField.setVisible(true);
	    	    	
	    	    	confirmLabel.setVisible(true);
	    	    	passwrdConfirmation.setVisible(true);	
	    	    	
	    	    	prompts.setText("Please choose your new password: ");
    			}
    			else
	    		{
	    			throw new Exception("The answer you chose is incorrect. Please try again. (Be aware they are case"
	    					+ "sensitive)");
	    		}
    			
    		}
    		
    		
    		
    		if(passwrdField.getText().length() < 8 && passwrdField.isVisible() 
    				&& !passwrdField.getText().isEmpty())
    		{
    			throw new Exception("Please insert a minimum password length of 8 characters.");
    		}
    		
    		for(int i = 0; i < passwrdField.getText().length();i++)
    		{
    			if((int)passwrdField.getText().charAt(i) < 32 || (int)passwrdField.getText().charAt(i) > 126 && passwrdField.isVisible())
    			{
    				throw new Exception("Invalid character used in password: " + passwrdField.getText().charAt(i) +
    									". Please use standard ASCII characters.");
    			}
    		}
    		
    		if(passwrdConfirmation.getText().equals(passwrdField.getText()) && passwrdField.isVisible() 
    				&& !passwrdField.getText().isEmpty())
    		{
    			forgot.newPassword(passwrdField.getText());
    		
	    		Alert success = new Alert(AlertType.INFORMATION);
	    		
	    		success.setTitle("Password Changed");
	    		success.setHeaderText("Succesfully changed Password for: " + forgot.getUser());
	    		success.setContentText("You can now login from the home screen.");
	    		success.showAndWait();
	    		
	    		
	    		Stage stage = Main.getPrimaryStage();
				Scene scene = SceneLoader.loadUserLoginScene();
				
				stage.setScene(scene);
    		}
    		else if(!passwrdConfirmation.getText().equals(passwrdField.getText()) && passwrdField.isVisible())
    		{
    			throw new Exception("The passwords entered do no match. Please try again.");
    		}
 
    		
    		
		}
		catch(Exception e)
		{
			prompts.setText(e.getMessage());
		}
		
    		

    }
	

}