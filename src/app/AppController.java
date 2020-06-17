package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class AppController {
	
	private double xOffSet = 0;
	private double yOffSet = 0;
	
	@FXML private Button btMin;
	@FXML private Button btMax;
	@FXML private Button btClose;
	@FXML private Pane topPane;

	@FXML private TabPane tabPane;
	
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	
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
	public void abreJanelaAjuda(ActionEvent event) {
		Window.openWinHelp(getClass().getResource("Ajuda.fxml"));		
	}

	@FXML
	public void abreImagem1() {
		img1 = FileManipulation.abreImagem(imageView1, img1);
	}
	
	@FXML
	public void salvar() {
		if (img1 != null) {
			JOptionPane.showMessageDialog(null, "Nao foi possivel salvar a imagem");
			return;
		}
		FileManipulation.salvaImagem(img1);
	}
	
	
	@FXML
	public void aplicarMedia3() {
		if(img1 == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma imagem");
			return;
		}
		img1 = Filtros.ruidos(img1, Constantes.VIZINHOS3x3);
		Utils.atualizaImageView(imageView1, img1);
		Utils.createANewTab(tabPane);
	}	
	
	@FXML
	public void aplicarWiener() {
		if(img1 == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma imagem");
			return;
		}
		img1 = Filtros.ruidos(img1, 1);
		Utils.atualizaImageView(imageView1, img1);
		Utils.createANewTab(tabPane);
	}	
	
	@FXML
	public void aplicarJRG() {
		if(img1 == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma imagem");
			return;
		}
		img1 = Filtros.jrg(img1);
		Utils.atualizaImageView(imageView1, img1);
		Utils.createANewTab(tabPane);
	}	
}

























