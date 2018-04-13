package imagebank.model;

import java.io.File;
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
		this.listImages();
	}
	
	public ArrayList<Image> getImagesFile() {
		return this.img_file;
	}
	
	private void listImages() {
		String[] files = this.directory.list();
		for(int i=0; i<files.length; i++) {
			try{
				if(this.isWinOS()) {
					Image img = new Image(this.toWinPath(this.abs_directory_path)+files[i]);
					this.img_file.add(img);
				}
			}catch(){
				
			}
			
			
		}
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
		return new String(win_path).concat("/");
	}
}
