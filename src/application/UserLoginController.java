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


    @FXML
    void validateUser(ActionEvent event) {

    }

    @FXML
    void sqSelect(ActionEvent event) {

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