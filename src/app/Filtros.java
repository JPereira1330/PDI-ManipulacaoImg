package app;

import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Filtros {

	
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
