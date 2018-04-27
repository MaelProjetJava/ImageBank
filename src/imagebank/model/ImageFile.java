package imagebank.model;

import imagebank.util.AVLTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList; 
import java.net.MalformedURLException;

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
			this.updateListImage(newImg);
		}
		
	}
	
	private void updateListImage(File imagePath) {
		try {
			Image img = new Image(imagePath);
			this.images.putIfAbsent(img.getName(), img);
		} catch (MalformedURLException e) {
			/**
			 * L'URL est créé à partir d'une URI, elle même créé
			 * à partir dun File: l'URL devrait donc toujours être
			 * bien formée.
			 */
		}
	}
	
	private void listImages() {
		File[] files = this.directory.listFiles();
		for(int i=0; i<files.length; i++) {
			if(!this.isImageFile(files[i]))
				continue;
			this.updateListImage(files[i]);
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
