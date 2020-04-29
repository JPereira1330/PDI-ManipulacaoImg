package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

	@FXML
	public void abreJanelaAjuda(ActionEvent event) {
		
		Stage stage;
		Parent root;
		FXMLLoader loader;
		
		AjudaController controller;
		
		try {
			stage = new Stage();
			loader = new FXMLLoader(getClass().getResource("App.fxml"));
			
			root = loader.load();
			stage.setScene(new Scene(root));
			stage.initOwner( ((Node) event.getSource()).getScene().getWindow() );
			stage.show();
			
			controller = (AjudaController) loader.getController();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
