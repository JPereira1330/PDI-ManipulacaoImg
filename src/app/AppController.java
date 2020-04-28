package app;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AppController {
	@FXML
	ImageView imageView1;
	
	private Image img1;
	
	@FXML
	public void abreImagem1() {
		img1 = abreImagem(imageView1, img1);
	}
	
	private Image abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if (f != null) {
			image = new Image(f.toURI().toString());
			imageView.setImage(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
		}
		return image;
	}
	
	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png",
				"*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP"));
		fileChooser.setInitialDirectory(new File("C:/Users/gabri/Desktop/PDI/img"));
		File imgSelec = fileChooser.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
