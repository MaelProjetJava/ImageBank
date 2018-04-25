package imagebank.model;

import java.io.FileNotFoundException;

public class NotDirectoryException extends FileNotFoundException {
	public NotDirectoryException() {
		super("This is not a directory");
	}
}
