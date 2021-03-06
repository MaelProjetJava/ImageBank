package imagebank.model;

import imagebank.model.tag.Tagger;
import imagebank.model.tag.Tag;
import imagebank.model.tag.TaggableObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javafx.scene.paint.Color;
import javafx.scene.image.PixelReader;

import java.util.Iterator;
import java.util.ArrayList;

import java.nio.file.Files;
import java.net.MalformedURLException;


public class Image extends TaggableObject {
	private File img_file; 
	private DominantColor dc;
	private transient javafx.scene.image.Image fx_img;
	protected transient ArrayList<Color> dominant_color;
	protected String[] name_colors;
	
	public Image(File imagePath) throws MalformedURLException {
		this.img_file = imagePath;
		this.fx_img = new javafx.scene.image.Image(
			img_file.toURI().toURL().toString(),
			false
		);
		this.dc = new DominantColor();
		this.metadata();
	}

	private void readObject(ObjectInputStream stream)
				throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		dominant_color = DominantColor.deserializeColorArray(stream);
		this.fx_img = new javafx.scene.image.Image(
			img_file.toURI().toURL().toString(),
			false
		);
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		DominantColor.serializeColorArray(stream, dominant_color);
	}

	public String getName() {
		return this.img_file.getName();
	}
	
	public javafx.scene.image.Image getFxImage() {
		return this.fx_img;
	}
	
	public void tagImageNote(int note) {
		Tagger.tag(this, Tagger.getTag("Note", note));
	}
	
	public void tagImage(String val){
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
			iis.close();
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	private void tagImageSize() throws IOException {
		long size = Files.size(this.img_file.toPath());
		//System.out.println(size/1024+"Ko");
		Tagger.tag(this, Tagger.getTag("Size", size));
	}
	
	private void tagImageDimensions(ImageReader img_reader) throws IOException {
		int width = img_reader.getWidth(0);
		int height = img_reader.getHeight(0);
		//System.out.println(width+"x"+height);
		Tagger.tag(this, Tagger.getTag("Width", width));
		Tagger.tag(this, Tagger.getTag("Height", height));
	}
	
	private void tagImageFormat(ImageReader img_reader) throws IOException {
		String format = img_reader.getFormatName();
		//System.out.println(format);
		Tagger.tag(this, Tagger.getTag(format));
		Tagger.tag(Tagger.getTag(format), Tagger.getTag("Format"));
	}
	
	private void tagImagePath() {
		String abs_path = this.img_file.getAbsolutePath();
		//System.out.println(abs_path);
		Tagger.tag(this, Tagger.getTag(abs_path));
		Tagger.tag(Tagger.getTag(abs_path), Tagger.getTag("Path"));
	}
	
	private void tagImageOwner() throws IOException {
		String owner = Files.getOwner(this.img_file.toPath()).getName();
		//System.out.println(owner);
		Tagger.tag(this, Tagger.getTag(owner));
		Tagger.tag(Tagger.getTag(owner), Tagger.getTag("Owner"));
	}
	
	private void tagImageDomColor(ImageReader img_reader, PixelReader pix_reader)
		throws IOException {
		this.dominant_color = this.dc.getDominantColor(img_reader,
								pix_reader);
		this.name_colors = this.dc.getNameDominantColor();
		//System.out.println(this.dominant_color+"\n");
		Tagger.tag(this, Tagger.getTag(this.name_colors[0]));
		Tagger.tag(this, Tagger.getTag(this.name_colors[1]));
	}
}
