package app;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import javafx.embed.swing.SwingFXUtils;
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

	public static Image jrg(Image imagem, double valueSlider) {

		Image parte01;
		Image parte02;
		Image resultado;

		if (imagem == null) {
			return null;
		}

		// Removendo ruido da imagem
		parte01 = ruidos(imagem, Constantes.VIZINHOS3x3);
		if (parte01 == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao remover ruido", "Erro detectado", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Capturando as bordas da imagem
		parte02 = sobel(imagem);
		if (parte02 == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao aplicar sobel", "Erro detectado", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		parte01 = subtracao(parte01, parte02, 1, 0.5);
		if (parte01 == null) {
			JOptionPane.showConfirmDialog(null, "Erro ao subtrair imagens", "Erro detectado",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		parte02 = aplicarLimiarizacao(imagem, valueSlider);
		
		resultado = adicao(parte01, parte02, 1, 0.1);

		return resultado;
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

	public static Image adicao(Image img1, Image img2, double ti1, double ti2) {

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

				double r = (corImg1.getRed() * ti1) + (corImg2.getRed() * ti2);
				double g = (corImg1.getGreen() * ti1) + (corImg2.getGreen() * ti2);
				double b = (corImg1.getBlue() * ti1) + (corImg2.getBlue() * ti2);

				r = r > 1 ? 1 : r;
				g = g > 1 ? 1 : g;
				b = b > 1 ? 1 : b;

				Color newCor = new Color(r, g, b, 1);
				pw.setColor(i, j, newCor);
			}
		}

		return wi;
	}
	
	public static Image sobel(Image img) {

        BufferedImage image = SwingFXUtils.fromFXImage(img, null);

        int x = image.getWidth();
        int y = image.getHeight();

        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

				int val00 = Utils.getGrayScale(image.getRGB(i - 1, j - 1));
                int val01 = Utils.getGrayScale(image.getRGB(i - 1, j));
                int val02 = Utils.getGrayScale(image.getRGB(i - 1, j + 1));

                int val10 = Utils.getGrayScale(image.getRGB(i, j - 1));
                int val11 = Utils.getGrayScale(image.getRGB(i, j));
                int val12 = Utils.getGrayScale(image.getRGB(i, j + 1));

                int val20 = Utils.getGrayScale(image.getRGB(i + 1, j - 1));
                int val21 = Utils.getGrayScale(image.getRGB(i + 1, j));
                int val22 = Utils.getGrayScale(image.getRGB(i + 1, j + 1));

                int gx =  ((-1 * val00) + (0 * val01) + (1 * val02)) 
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                int gy =  ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if(maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int)(edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                image.setRGB(i, j, edgeColor);
            }
        }

        img = SwingFXUtils.toFXImage(image, null);
        
		return img;
	}

	public static Image aplicarLimiarizacao(Image imagem, double value) {

		int w, h;
		Color corA, corN;
		PixelReader pr;
		WritableImage wi;
		PixelWriter pw;

		if (imagem == null) {
			JOptionPane.showConfirmDialog(null, "Nao foi possivel localizar imagem", "Erro detectado",
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		w = (int) imagem.getWidth();
		h = (int) imagem.getHeight();

		pr = imagem.getPixelReader();
		wi = new WritableImage(w, h);
		pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {

				corA = pr.getColor(i, j);
				if (corA.getRed() <= value)
					pw.setColor(i, j, Color.BLACK);
				else {
					pw.setColor(i, j, Color.WHITE);
				}

			}
		}

		return wi;
	}
	
}
