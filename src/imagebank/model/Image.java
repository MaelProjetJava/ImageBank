package imagebank.model;

import imagebank.model.tag.Tagger;
import imagebank.model.tag.Tag;
import imagebank.model.tag.TaggableObject;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javafx.scene.paint.Color;
import javafx.scene.image.PixelReader;

import java.util.Iterator;
import java.util.ArrayList;

import java.nio.file.Files;


public class Image extends TaggableObject {	
	private File img_file; 
	private DominantColor dc;
	private javafx.scene.image.Image fx_img;
	protected ArrayList<Color> dominant_color;
	protected String[] name_colors;
	
	public Image(String url) {
		this.fx_img = new javafx.scene.image.Image("file:/"+url);
		this.img_file = new File(url);
		this.dc = new DominantColor();
		this.metadata();
	}
	
	public void tagImageNote(int note) {
		Tagger.tag(this, Tagger.getTag("Note", note));
	}
	
	public void tagImageTag(String val){
		Tagger.tag(this, Tagger.getTag(val));
	}
	
	public ArrayList<Color> getDominantColor(){
		return this.dominant_color;
	}
	
	private void metadata() {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(this.img_file);
			Iterator<ImageReader> img_iterator = ImageIO.getImageReaders(iis);
			ImageReader img_reader = img_iterator.next();
			PixelReader pix_reader = this.fx_img.getPixelReader();
			img_reader.setInput(iis);
			
			this.tagImageSize();
			this.tagImageDimensions(img_reader);
			this.tagImageFormat(img_reader);
			this.tagImagePath();
			this.tagImageOwner();
			this.tagImageDomColor(img_reader, pix_reader);
					
			System.out.println("DC: "+dominant_color);
			iis.close();
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	private void tagImageSize() throws IOException {
		long size = Files.size(this.img_file.toPath());
		System.out.println(size/1024+"Ko");
		Tagger.tag(this, Tagger.getTag("Size", size));
	}
	
	private void tagImageDimensions(ImageReader img_reader) throws IOException {
		int width = img_reader.getWidth(0);
		int height = img_reader.getHeight(0);
		System.out.println(width+"x"+height);
		Tagger.tag(this, Tagger.getTag("Width", width));
		Tagger.tag(this, Tagger.getTag("Height", height));
	}
	
	private void tagImageFormat(ImageReader img_reader) throws IOException {
		String format = img_reader.getFormatName();
		System.out.println(format);
		Tagger.tag(this, Tagger.getTag("Format"));
		Tagger.tag(Tagger.getTag("Format"), Tagger.getTag(format));
	}
	
	private void tagImagePath() {
		String abs_path = this.img_file.getAbsolutePath();
		System.out.println(abs_path);
		Tagger.tag(this, Tagger.getTag("Path"));
		Tagger.tag(Tagger.getTag("Path"), Tagger.getTag(abs_path));
	}
	
	private void tagImageOwner() throws IOException {
		String owner = Files.getOwner(this.img_file.toPath()).getName();
		System.out.println(owner);
		Tagger.tag(this, Tagger.getTag("Owner"));
		Tagger.tag(Tagger.getTag("Owner"), Tagger.getTag(owner));
	}
	
	private void tagImageDomColor(ImageReader img_reader, PixelReader pix_reader){
		this.dominant_color = this.dc.getDominantColor(img_reader,
								pix_reader);
		this.name_colors = this.dc.getNameDominantColor();
		Tagger.tag(this, Tagger.getTag(this.name_colors[0]);
		Tagger.tag(this, Tagger.getTag(this.name_colors[1]);
	}
}
