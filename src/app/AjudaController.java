package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AjudaController {

	@FXML private Button btClose;
	
	@FXML
	protected void handleCloseBtn(ActionEvent event) {
		Stage stage = (Stage) btClose.getScene().getWindow();
		stage.close();
	}

}
