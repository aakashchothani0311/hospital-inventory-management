package application;

import java.io.IOException;

import com.admincreds.Creds;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginHandler {
	
	@FXML TextField uname;
	@FXML TextField pswd;
	
	public void handleLogin() throws IOException{		
		if(Creds.uName.equals(uname.getText()) && Creds.pswd.equals(pswd.getText())) {
			Main obj = new Main();
			obj.successfulLogin();
		} else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Login Failed");
			error.setHeaderText("Invalid username or password");
			error.showAndWait();
		}
	}
}