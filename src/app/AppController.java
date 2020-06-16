package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController {
	
	private double xOffSet = 0;
	private double yOffSet = 0;
	
	@FXML private Button btMin;
	@FXML private Button btMax;
	@FXML private Button btClose;
	@FXML private Pane topPane;
	
	@FXML
	ImageView imageView1;
	
	private Image img1;
	private Image img2;
	
	@FXML
	protected void handleCloseBtn(ActionEvent event) {
		Stage stage = (Stage) btClose.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	protected void handleMinBtn(ActionEvent event) {
		Stage stage = (Stage) btMin.getScene().getWindow();
		stage.setIconified(true);
	}
	
	@FXML
	protected void handleMaxBtn(ActionEvent event) {
		Stage stage = (Stage) btMax.getScene().getWindow();
		if(!stage.isMaximized()) {
			stage.setMaximized(true);
		} else {
			stage.setMaximized(false);
		}
	}
	
	@FXML
	protected void handleClickPane(MouseEvent event) {
		Stage stage = (Stage) topPane.getScene().getWindow();
		xOffSet = stage.getX() - event.getScreenX();
		yOffSet = stage.getY() - event.getScreenY();
	}
	
	@FXML
	protected void handleMovPane(MouseEvent event) {
		Stage stage = (Stage) topPane.getScene().getWindow();
		stage.setX(event.getScreenX() + xOffSet);
		stage.setY(event.getScreenY() + yOffSet);
	}
	
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
	public void abreJanelaAjuda(ActionEvent event) {
		
		Stage stage;
		Parent root;
		FXMLLoader loader;
		
		try {
			stage = new Stage();
			loader = new FXMLLoader(getClass().getResource("Ajuda.fxml"));
			
			root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("PDI - Ajuda");
			stage.getIcons().add(new Image("./img/icon.png"));
			stage.setResizable(false);
			//stage.initOwner( ((Node) event.getSource()).getScene().getWindow() );
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void media3() {
		if(img1 != null) {

			/* Chama método RUIDOS da classe FILTROS, passando como parametro a img e o número 1,
			 * q segundo a classe CONSTANTES siginifica o filtro media 3.
			 */
			img2 = Filtros.ruidos(img1, 1);
			atualizaImagem();
		}else {
			JOptionPane.showMessageDialog(null, "Selecione uma imagem");
		}
	
	}
	
	private void atualizaImagem() {
		
		/*imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());*/
	}
	
	
}

























