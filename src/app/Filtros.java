package app;

import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVInterface;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Filtros {

	public static Image ruidos(Image imagem, int tipoVizinhos) {
		try {
			int w = (int) imagem.getWidth();
			int h = (int) imagem.getHeight();

			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w, h);
			PixelWriter pw = wi.getPixelWriter();

			for (int i = 1; i < w - 1; i++) {
				for (int j = 1; j < h - 1; j++) {
					Color corA = pr.getColor(i, j);
					Pixel p = new Pixel(corA.getRed(), corA.getGreen(), corA.getBlue(), i, j);
					Utils.eliminaRuido(imagem, p);

					Pixel vetor[] = null;
					if (tipoVizinhos == Constantes.VIZINHOS3x3) {
						vetor = p.viz3;
					}

					double red = Utils.mediana(vetor, Constantes.CANALR);
					double green = Utils.mediana(vetor, Constantes.CANALG);
					double blue = Utils.mediana(vetor, Constantes.CANALB);
					Color corN = new Color(red, green, blue, corA.getOpacity());
					pw.setColor(i, j, corN);

				}
			}

			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image jrg(Image imagem) {

		double porcentagemSubImg01;
		double porcentagemSubImg02;
		
		Image auxiliar01;
		Image resultado;

		if (imagem == null) {
			return null;
		}

		// Removendo ruido da imagem
		resultado = ruidos(imagem, Constantes.VIZINHOS3x3);
		if (resultado == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao remover ruido", "Erro detectado", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Capturando as bordas da imagem
/*		auxiliar01 = sobel(resultado);
		if (auxiliar01 == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao aplicar sobel", "Erro detectado", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		porcentagemSubImg01 = 100 / 100;
		porcentagemSubImg02 =  50 / 100;
		
		resultado = subtracao(resultado, auxiliar01, porcentagemSubImg01, porcentagemSubImg02);
		if (resultado == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao subtrair imagens", "Erro detectado", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		*/
		return resultado;
	}

	private static Image canny(Image img) {

		Mat dst;
		Mat src, srcBlur, srcEdges;
		
		src = Utils.image2Mat(img);
		if(src.empty()) {
			return null;
		}
	
		srcBlur = new Mat();
		Imgproc.blur(src, srcBlur, new Size(3,3));
		
		srcEdges = new Mat();
		Imgproc.Canny(srcBlur, srcEdges, 3, 9, 3, false);
		
		dst = new Mat(src.size(), CvType.CV_8UC3, Scalar.all(0));
        src.copyTo(dst, srcEdges);
		
        return Utils.mat2Image(dst);
	}
	
	private static Image sobel(Image img) {
		
		Mat grad;
		Mat src, src_gray; 
		Mat grad_x, grad_y;
		Mat abs_grad_x, abs_grad_y;
		
        int scale = 1;
        int delta = 0;
        int ddepth = CvType.CV_16S;
		
		src_gray = new Mat();
		
		src = Utils.image2Mat(img);

		if(src.empty()) {
			JOptionPane.showConfirmDialog(null, "Erro ao popular", "Erro detectado", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);			
			return null;
		}
		
		// Reduz noise
		Imgproc.GaussianBlur(src, src, new Size(3,3), 0,0, Core.BORDER_DEFAULT);
		
		// Converte para cinza
		Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_RGB2GRAY);
		
		grad_x = new Mat();
		Imgproc.Sobel(src_gray, grad_x, ddepth, 1, 0, 3, scale, delta, Core.BORDER_DEFAULT);
		
		grad_y = new Mat();
		Imgproc.Sobel(src_gray, grad_y, ddepth, 0, 1, 3, scale, delta, Core.BORDER_DEFAULT);
	
		abs_grad_x = new Mat();
		Core.convertScaleAbs(grad_x, abs_grad_x);
		
		abs_grad_y = new Mat();
		Core.convertScaleAbs(grad_y, abs_grad_y);
		
		grad = new Mat();
		Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad);
		
		return Utils.mat2Image(grad);
	}
	
	private static Image subtracao(Image img1, Image img2, double ti1, double ti2) {

		int w, h;
		int h1, h2;
		int w1, w2;

		h1 = (int) img1.getHeight();
		h2 = (int) img2.getHeight();

		w1 = (int) img1.getWidth();
		w2 = (int) img2.getWidth();

		w = Math.min(w1, w2);
		h = Math.min(h1, h2);

		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();

		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 1; i < w; i++) {
			for (int j = 1; j < h; j++) {

				Color corImg1 = pr1.getColor(i, j);
				Color corImg2 = pr2.getColor(i, j);

				double r = (corImg1.getRed() * ti1) - (corImg2.getRed() * ti2);
				double g = (corImg1.getGreen() * ti1) - (corImg2.getGreen() * ti2);
				double b = (corImg1.getBlue() * ti1) - (corImg2.getBlue() * ti2);

				r = r < 0 ? 0 : r;
				g = g < 0 ? 0 : g;
				b = b < 0 ? 0 : b;

				Color newCor = new Color(r, g, b, 1);
				pw.setColor(i, j, newCor);
			}
		}

		return wi;
	}

}
