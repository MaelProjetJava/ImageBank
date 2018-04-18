package imagebank.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 

public class ImageFile {
	private ArrayList<Image> img_file;
	private String abs_directory_path;
	private File directory;
	
	public ImageFile(String directory_path) throws NotDirectoryException {
		this.directory = new File(directory_path);
		if(!directory.isDirectory()) throw new NotDirectoryException();
		this.abs_directory_path = directory.getAbsolutePath();
		this.img_file = new ArrayList<Image>();
	}
	
	public ArrayList<Image> getImagesFile() {
		this.listImages();
		return this.img_file;
	}
	
	private void listImages() {
		String[] files = this.directory.list();
		Image img = null;
		for(int i=0; i<files.length; i++) {
			if(!this.isImageFile(new File(this.abs_directory_path+"/"+files[i])))
				continue;
			if(this.isWinOS())
				img = new Image(this.toWinPath(this.abs_directory_path)+"/"+files[i]);
			else
				img = new Image(this.abs_directory_path+"/"+files[i]);
			this.img_file.add(img);
		}
	}
	
	private boolean isImageFile(File img_file) {
		boolean is_png = true;
		boolean is_jpg = true;
		try {
			FileReader file_reader = new FileReader(img_file);
			char[] buffer = new char[4];
			char[] jpg = {'ÿ', 'Ø', 'ÿ'};
			char[] png = {'‰', 'P', 'N', 'G'};
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
			System.err.println(ioe.getMessage());
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
