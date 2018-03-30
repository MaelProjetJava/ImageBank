package imagebank.model.tag;

public class UnvaluedTagException extends RuntimeException {

	public UnvaluedTagException() {
		super();
	}

	public UnvaluedTagException(String message) {
		super(message);
	}

	public UnvaluedTagException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnvaluedTagException(Throwable cause) {
		super(cause);
	}
}
