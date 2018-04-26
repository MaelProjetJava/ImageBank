package imagebank.model;

import imagebank.util.AVLTree;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList; 

public class ImageFile implements Serializable {
	private AVLTree<String, Image> images;
	private String abs_directory_path;
	private File directory;
	
	public ImageFile(String directory_path) throws NotDirectoryException {
		this.directory = new File(directory_path);
		if(!directory.isDirectory()) throw new NotDirectoryException();
		this.abs_directory_path = directory.getAbsolutePath();
		this.images = new AVLTree<String, Image>();
	}
	
	public AVLTree<String, Image> getImagesFile() {
		this.listImages();
		return this.images;
	}
	
	public void addImage(String path, ArrayList<Image> list_img) {
		File newImg = new File(path);
		if(newImg.exists() && this.isImageFile(newImg)) {
			this.updateListImage(path);
		}
		
	}
	
	private void updateListImage(String path_file) {
		Image img = null;
		if(this.isWinOS())
			img = new Image(this.toWinPath(path_file));
		else
			img = new Image(path_file);
		this.images.put(img.getName(), img);
	}
	
	private void listImages() {
		String[] files = this.directory.list();
		for(int i=0; i<files.length; i++) {
			String file_abs_path = this.abs_directory_path+"/"+files[i];
			if(!this.isImageFile(new File(file_abs_path)))
				continue;
			this.updateListImage(file_abs_path);
		}
	}
	
	private boolean isImageFile(File img_file) {
		boolean is_png = true;
		boolean is_jpg = true;
		try {
			FileReader file_reader = new FileReader(img_file);
			char[] buffer = new char[4];
			char[] jpg = {0xff, 0xd8, 0xff};
			char[] png = {0x89, 'P', 'N', 'G'};
			file_reader.read(buffer);
			for(int i=0; i<buffer.length; i++) {
				if(png[i]!=buffer[i])
					is_png = false;
				if(i<jpg.length)
					is_jpg = (jpg[i]!=buffer[i]) ? false : true;
			}
			
			file_reader.close();
		}
		catch(IOException ioe) {
			return false;
		}
		return is_png || is_jpg;
	}
	
	private boolean isWinOS() {
		return System.getProperty("os.name").contains("Windows");
	}
	
	private String toWinPath(String abs_path) {
		char[] win_path = abs_path.toCharArray();
		for(int i=0; i<win_path.length; i++) {
			if(win_path[i]=='\\')
				win_path[i] = '/';
		}
		return new String(win_path);
	}
}
