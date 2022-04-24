package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.User;
//import application.model.User;
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
    private TextField userField;

    @FXML
    private PasswordField passwrdField;


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
    			
    			System.out.println("Hello");
    		}
    		
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    void newUser(ActionEvent event) {

    }
	

}