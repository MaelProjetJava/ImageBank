package imagebank.model;

import imagebank.model.tag.Tagger;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.Iterator;

import java.nio.file.Files;


public class Images extends Image {
	private ImageInputStream iis;
	private File img_file;
	private Iterator<ImageReader> img_iterator;
	
	public Images(String url) {
		super("file:/"+url);
		this.img_file = new File(url);
		this.metadata();
		DominantColor dc = new DominantColor(url);
		dc.getDominantColor();
	}
	
	public void metadata() {
		try {
			this.iis = ImageIO.createImageInputStream(this.img_file);
			this.img_iterator = ImageIO.getImageReaders(this.iis);
			ImageReader img_reader = this.img_iterator.next();
			img_reader.setInput(this.iis);
			
			this.tagImageSize();
			this.tagImageDimensions(img_reader);
			this.tagImageColor();
			/*this.tagImageFormat(img_reader);
			this.tagImagePath();
			this.tagImageOwner();*/
			
			this.iis.close();
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	public void tagImageColor() {
		PixelReader pix_reader = this.getPixelReader();
		Color image_color = pix_reader.getColor(0, 0);
		System.out.println("Color: "+image_color);
	}
	
	public void tagImageSize() throws IOException {
		long size = Files.size(this.img_file.toPath());
		//Tagger.tag(this, Tagger.getTag("Size", size));
	}
	
	public void tagImageDimensions(ImageReader img_reader) throws IOException {
		int width = img_reader.getWidth(0);
		int height = img_reader.getHeight(0);
		//Tagger.tag(this, Tagger.getTag("width", width));
		//Tagger.tag(this, Tagger.getTag("height", height));
	}
	
	public void tagImageFormat(ImageReader img_reader) throws IOException {
		String format = img_reader.getFormatName();
		//Tagger.tag(this, Tagger.getTag("Format", format));
	}
	
	public void tagImagePath() {
		String abs_path = this.img_file.getAbsolutePath();
		//Tagger.tag(this, Tagger.getTag("Path", abs_path));
	}
	
	public void tagImageOwner() throws IOException {
		String owner = Files.getOwner(this.img_file.toPath()).getName();
		//Tagger.tag(this, Tagger.getTag("Owner", owner));
	}
}
