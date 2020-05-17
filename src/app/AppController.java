package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AppController {

	@FXML
	public void abreJanelaAjuda(ActionEvent event) {
		
		Stage stage;
		Parent root;
		FXMLLoader loader;
		
		try {
			stage = new Stage();
			loader = new FXMLLoader(getClass().getResource("Ajuda.fxml"));
			
			root = loader.load();
			stage.setScene(new Scene(root));
			//stage.initOwner( ((Node) event.getSource()).getScene().getWindow() );
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	ImageView imageView1;
	
	private Image img1;
	private Image img2;
	
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
		fileChooser.setInitialDirectory(new File("./src/img/"));
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
	
	@FXML
	public void salvar() {
		if (img2 != null) { // Verifica se ha imagem 3
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png"));
			fileChooser.setInitialDirectory(new File("./src/img/"));
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				BufferedImage bImg = SwingFXUtils.fromFXImage(img2, null);
				try {
					ImageIO.write(bImg, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nao foi possivel salvar a imagem");
		}
	}
	
	@FXML
	public void ruidos() {
		
		/*vizinhos =  new ToggleGroup();
		vizX.setToggleGroup(vizinhos);
		
		if (vizinhos.getSelectedToggle() == viz3)
			img3 = Pdi.ruidos(img1, 1);
		
		atualizaImagem();
		*/
	}
	
	private void atualizaImagem() {
		
		/*imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());*/
	}
	
	
}

























