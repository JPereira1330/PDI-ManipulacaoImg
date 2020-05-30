package app;

import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Filtros {

	public static Image ruidos(Image imagem, int tipoVizinhos) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w, h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=1; i<w-1; i++) {
				for(int j=1; j<h-1; j++) {
					Color corA = pr.getColor(i, j);
					Pixel p = new Pixel(corA.getRed(), corA.getGreen(), corA.getBlue(), i, j);
					eliminaRuido(imagem, p);
					
					Pixel vetor[] = null;
					if(tipoVizinhos==Constantes.VIZINHOS3x3) {
						vetor = p.viz3;
					}
					//adicionar mais filtros aqui a medida q for fazendo
					
					double red = mediana(vetor, Constantes.CANALR);
					double green = mediana(vetor, Constantes.CANALG);
					double blue = mediana(vetor, Constantes.CANALB);
					Color corN = new Color(red, green, blue, corA.getOpacity());
					pw.setColor(i, j, corN);
		
				}
			}
			
			return wi;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void eliminaRuido(Image imagem, Pixel p) {
		
		//filtro media 3
		p.viz3 = media3(imagem, p);
	}
	
	
	public static Pixel[] media3(Image imagem, Pixel p) {
		Pixel [] viz3 = new Pixel[9];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x, p.y-1);
		viz3[0] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y-1);
		cor = pr.getColor(p.x, p.y+1);
		viz3[1] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x, p.y+1);
		cor = pr.getColor(p.x-1, p.y);
		viz3[2] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y);
		cor = pr.getColor(p.x+1, p.y);
		viz3[3] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y);
		cor = pr.getColor(p.x-1, p.y+1);
		viz3[4] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y+1);
		cor = pr.getColor(p.x+1, p.y-1);
		viz3[5] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y-1);
		cor = pr.getColor(p.x-1, p.y-1);
		viz3[6] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x-1, p.y-1);
		cor = pr.getColor(p.x+1, p.y+1);
		viz3[7] = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), p.x+1, p.y+1);
		viz3[8] = p;
		return viz3;
	}
	
	public static double mediana(Pixel[] pixels, int canal) {
		double v[] = new double[pixels.length];
		for(int i=0; i<pixels.length; i++) {
			if(canal == Constantes.CANALR) {
				v[i] = pixels[i].r;
			}
			if(canal == Constantes.CANALG) {
				v[i] = pixels[i].g;
			}
			if(canal == Constantes.CANALB) {
				v[i] = pixels[i].b;
			}
		}
		Arrays.sort(v);
		return v[v.length/2];
	}
	
}
