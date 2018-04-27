package imagebank.model;

import imagebank.util.AVLTree;

import java.io.File;
import java.io.FileInputStream;
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
			FileInputStream file_reader =
						new FileInputStream(img_file);
			byte[] buffer = new byte[4];
			byte[] jpg = {(byte) 0xff, (byte) 0xd8, (byte) 0xff};
			byte[] png = {(byte) 0x89, (byte) 0x50, (byte) 0x4e,
								(byte) 0x47};
			file_reader.read(buffer);
			for(int i=0; i<buffer.length; i++) {
				if(png[i]!=buffer[i])
					is_png = false;
				if(i < jpg.length && jpg[i] != buffer[i])
					is_jpg = false;
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
