package app;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Utils {

	public static void atualizaImageView(ImageView imgView, Image img) {
		imgView.setImage(img);
		imgView.setFitWidth(img.getWidth());
		imgView.setFitHeight(img.getHeight());
	}

	public static void createANewTab(TabPane tabPane, Image img) {
		String name = "Resultado - " + tabPane.getTabs().size();
		ImageView novaImage = new ImageView(img);
		Tab tab = new Tab(name, novaImage);
		tab.setClosable(true);
		tabPane.getTabs().add(tab);
	}

	public static void eliminaRuido(Image imagem, Pixel p) {
		p.viz3 = media3(imagem, p);
	}

	public static Pixel[] media3(Image imagem, Pixel p) {
		Pixel[] viz3 = new Pixel[9];
		PixelReader pr = imagem.getPixelReader();

		Color cor = pr.getColor(p.x, p.y - 1);
		viz3[0] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y - 1);
		cor = pr.getColor(p.x, p.y + 1);
		viz3[1] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y + 1);
		cor = pr.getColor(p.x - 1, p.y);
		viz3[2] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x - 1, p.y);
		cor = pr.getColor(p.x + 1, p.y);
		viz3[3] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x + 1, p.y);
		cor = pr.getColor(p.x - 1, p.y + 1);
		viz3[4] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x - 1, p.y + 1);
		cor = pr.getColor(p.x + 1, p.y - 1);
		viz3[5] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x + 1, p.y - 1);
		cor = pr.getColor(p.x - 1, p.y - 1);
		viz3[6] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x - 1, p.y - 1);
		cor = pr.getColor(p.x + 1, p.y + 1);
		viz3[7] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x + 1, p.y + 1);
		viz3[8] = p;
		return viz3;
	}

	public static double mediana(Pixel[] pixels, int canal) {
		double v[] = new double[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			if (canal == Constantes.CANALR) {
				v[i] = pixels[i].r;
			}
			if (canal == Constantes.CANALG) {
				v[i] = pixels[i].g;
			}
			if (canal == Constantes.CANALB) {
				v[i] = pixels[i].b;
			}
		}
		Arrays.sort(v);
		return v[v.length / 2];
	}

	public static Image mat2Image(Mat frame) {

		MatOfByte buffer;

		buffer = new MatOfByte();
		Imgcodecs.imencode(".png", frame, buffer);

		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}

	public static Mat image2Mat(Image img) {

		int r, g, b;
		Mat matOut;
		byte[] data;
		int databuffer[];
		BufferedImage bImage;

		bImage = SwingFXUtils.fromFXImage(img, null);

		if (bImage.getType() == BufferedImage.TYPE_INT_RGB || bImage.getType() == BufferedImage.TYPE_INT_ARGB) {

			matOut = new Mat(bImage.getHeight(), bImage.getWidth(), CvType.CV_8UC3);
			data = new byte[bImage.getHeight() * bImage.getWidth() * (int) matOut.elemSize()];
			databuffer = bImage.getRGB(0, 0, bImage.getWidth(), bImage.getHeight(), null, 0, bImage.getWidth());

			for (int i = 0; i < databuffer.length; i++) {
				data[i * 3 + 2] = (byte) ((databuffer[i] >> 16) & 0xFF);
				data[i * 3 + 1] = (byte) ((databuffer[i] >> 8) & 0xFF);
				data[i * 3 + 0] = (byte) ((databuffer[i] >> 0) & 0xFF);
			}

		} else {

			matOut = new Mat(bImage.getHeight(), bImage.getWidth(), CvType.CV_8UC1);
			data = new byte[bImage.getHeight() * bImage.getWidth() * (int) matOut.elemSize()];
			databuffer = bImage.getRGB(0, 0, bImage.getWidth(), bImage.getHeight(), null, 0, bImage.getWidth());

			for (int i = 0; i < databuffer.length; i++) {
				r = (byte) ((databuffer[i] >> 16) & 0xFF);
				g = (byte) ((databuffer[i] >> 8) & 0xFF);
				b = (byte) ((databuffer[i] >> 0) & 0xFF);
				data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
			}

		}

		matOut.put(0, 0, data);
		return matOut;
	}

	public static int getGrayScale(int rgb) {

		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = (rgb) & 0xff;

		int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

		return gray;
	}

}
