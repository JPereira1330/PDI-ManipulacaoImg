package app;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Window {

	public static void openWinHelp(URL url) {
		Stage stage;
		Parent root;
		FXMLLoader loader;
		
		try {
			stage = new Stage();
			loader = new FXMLLoader(url);
			
			root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("PDI - Ajuda");
			stage.getIcons().add(new Image("./img/icon.png"));
			stage.setResizable(false);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
