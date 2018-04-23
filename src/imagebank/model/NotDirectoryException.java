package imagebank.model;

public class NotDirectoryException extends Exception {
	public NotDirectoryException() {
		super("This is not a directory");
	}
}
