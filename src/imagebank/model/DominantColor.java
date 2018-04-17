package imagebank.model;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.imageio.ImageReader;

public class DominantColor {
	private final Color[] refColor = {
			Color.rgb(230,20,20),Color.rgb(152,103,52),Color.rgb(230,230,75),Color.rgb(20,230,20),
			Color.rgb(75,230,230),Color.rgb(52,103,152),Color.rgb(20,20,230),Color.rgb(152,52,103),
			Color.rgb(230,75,230),Color.rgb(223,223,233),Color.rgb(160,160,160),Color.rgb(96,96,96),
			Color.rgb(32,32,32)};
	private final String[] name_colors = {"Red", "Brown", "Yellow", "Green","Cyan", "DarkCyan", "Blue",
			"DarkMagenta", "Magenta", "White", "LighGray", "Gray", "DarkGray", "Black"};
	private ArrayList<Color> d_colors;
	private String[] nameDominantColor;

	public DominantColor() {
		this.d_colors = new ArrayList<Color>();
		this.nameDominantColor = new String[2];
	}	
	
	public ArrayList<Color> getDominantColor(ImageReader img_reader, PixelReader pix_reader)
	throws IOException {
		int[] nb_ref_colors = this.countRefColors(img_reader, pix_reader);
		this.d_colors = this.mainColors(nb_ref_colors); 
		return this.d_colors; 
	}

	public String[] getNameDominantColor(){
		return this.nameDominantColor;
	}

	public String[] getAllNameColors(){
		return this.name_colors;
	}

	public Color[] getAllColors(){
		return this.refColor;
	}

	private ArrayList<Color> mainColors(int[] nb_ref_colors){
		ArrayList<Color> main_colors = new ArrayList<Color>();
		int first = 0;
		int second = 1;
		for(int i=2; i<this.refColor.length; i++) {
			if(nb_ref_colors[first]<nb_ref_colors[i]) {
				if(nb_ref_colors[second]<nb_ref_colors[first])
					second = first;
				first = i;
			}
			else if(nb_ref_colors[second]<nb_ref_colors[i]) {
				second = i;
			}
		}
		if(nb_ref_colors[first]!=0){
			main_colors.add(this.refColor[first]);
			this.nameDominantColor[0] = this.name_colors[first];
		}
		if(nb_ref_colors[second]!=0){
			main_colors.add(this.refColor[second]);
			this.nameDominantColor[1] = this.name_colors[second];
		}
		return main_colors;
	}
	
	private int[] countRefColors(ImageReader img_reader, PixelReader pix_reader) throws IOException {
		int w = img_reader.getWidth(0);
		int h = img_reader.getHeight(0);
		int[] nb_ref_colors = new int[this.refColor.length];
		for(int i=0 ; i<w; i++){
			for(int j=0; j<h; j++) {
				Color pix_img_col = this.getPixelColor(pix_reader.getColor(i,j));
				this.updateNbRefColor(nb_ref_colors, pix_img_col);
			}
			
		}
		return nb_ref_colors;
	}
	
	private void updateNbRefColor(int[] nb_ref_colors, Color pix_img_col) {
		for(int i=0; i<this.refColor.length; i++) {
			if(this.refColor[i].equals(pix_img_col)) {nb_ref_colors[i]++;}
		}
	}
	
	private Color getPixelColor(Color img_col){
		int[] img_main_channel = this.priorityChannel(img_col);
		Color col_selected = this.refColor[0];
		double[] dist_img_selected = this.getDistanceColors(img_col, col_selected, this.priorityChannel(col_selected));
		for(int i=0; i<this.refColor.length; i++){
			Color ref_color = this.refColor[i];
			int[] ref_main_channel = this.priorityChannel(ref_color);
			if(this.samePriorityChannel(img_main_channel, ref_main_channel)) {
				double[] dist_ref_img = this.getDistanceColors(img_col, ref_color, img_main_channel);
				col_selected = this.betterColor(dist_img_selected, dist_ref_img, col_selected, ref_color);
				if(col_selected.equals(ref_color)) dist_img_selected = dist_ref_img;
			}
		}
		return col_selected;
	}
	
	private int[] priorityChannel(Color color) {
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();
		int[] main_canals = {0,0,0};
		double min = Math.min(Math.min(r, g), b);
		double max = Math.max(Math.max(r, g), b);
		double precision = 50.0/255.0;
		if(max-min<=precision) {
			return main_canals;
		}
		if(r==max) {
			if(r-g>precision) 
				main_canals[1] = 1;
			if(r-b>precision)
				main_canals[2] = 1;
		}
		else if(g==max) {
			if(g-r>precision)
				main_canals[0] = 1;
			if(g-b>precision)
				main_canals[2] = 1;
		}
		else {
			if(b-r>precision)
				main_canals[0] = 1;
			if(b-g>precision)
				main_canals[1] = 1;
		}
		return main_canals;
	}

	
	private boolean samePriorityChannel(int[] main_c1, int[] main_c2) {
		return main_c1[0]==main_c2[0] && main_c1[1]==main_c2[1] && main_c1[2]==main_c2[2];
	}
	
	private Color betterColor(double[] dist, double[] dist2, Color col, Color col2) {
		if(dist[0]<=dist2[0] && dist[1]<=dist2[1] && dist[2]<=dist2[2])
			return col;
		return col2;
	}
	
	private double[] getDistanceColors(Color img_color, Color color, int[] priority){
		double[] distance = {0,0,0};
		if(priority[0]==0)
			distance[0] = Math.abs(img_color.getRed()-color.getRed());
		if(priority[1]==0)
			distance[1] = Math.abs(img_color.getGreen()-color.getGreen());
		if(priority[2]==0)
			distance[2] = Math.abs(img_color.getBlue()-color.getBlue());
		return distance;
	}
}
