package imagebank.model;

public class NotDirectoryException extends Exception {
	public NotDirectoryException() {
		super("Directory not found");
	}
}
