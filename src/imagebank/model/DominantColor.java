package imagebank.model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class DominantColor {
	private ArrayList<Color> colors;
	private Color[] refColors;
	private int sizeRefColors;
	private Image img;	
	
	public DominantColor(String img_filename) {
		this.colors = new ArrayList<Color>();
		this.refColors = {Color.black, Color.white, Color.cyan,
			Color.yellow, Color.red, Color.blue, Color.green,
			Color.magenta };
		this.img = new Image("file:/"+img_filename);
	}
	
	public void getDominantColor(){
		PixelReader pix_reader = this.img.getPixelReader();
		double width = this.img.getWidth();
		double height = this.img.getHeight();
		
	}
	
	public Color distanceColors(Color img_pix){
		int r = img_pix.getRed();
		int g = img_pix.getGreen();
		int b = img_pix.getBlue();
		int img_main_canals = this.twoMainCanals(r,g,b);
		Color colSelected = this.refColors[0];
		for(int i=1; i<this.sizeRefColors; i++){
			Color c = this.refColors[i];
			int col_main_canals = this.twoMainCanals(c.getRed(),
						     c.getGreen(), c.getBlue());
			if(sameMainCanals(img_main_canals, col_main_canals)){
				
			}	
	}
	
	public int[] getDistanceColors(Color img_color, Color col, int[] can){
		int[] distance = {0,0,0};
		distance[0]
	}

	public boolean sameMainCanals(int[] img_can, int[] col_can){
		return img_can[0]==col_can[0] && img_can[1]==col_man[1]&&
							img_can[2]==col_man[2];
	}

	public twoMainCanal(int r, int g, int b){
		int main_canals = {0,0,0};
		if(r>=g && r>=b){
			main_canals[0] = 1;
			if(g>=b){
				main_canals[1] = 1;
			}else{
				main_canals[2] = 1;
			}
		else if(g>=r && g>=b){
			main_canals[1] = 1;
			if(r>=b){
				main_canals[0] = 1;
			}else{
				main_canals[2] = 1;
			}
		}else{
			main_canals[2] = 1;
			if(r>=g){
				main_canals[0] = 1;
			}else{
				main_canals[1] = 1;
			}
		}
		return main_canals;
	}
}
