package imagebank.model;

import java.util.EventObject;

public class ImageDBEvent extends EventObject {

	public ImageDBEvent(ImageDB source) throws IllegalArgumentException {
		super(source);
	}

	@Override
	public ImageDB getSource() {
		return (ImageDB) source;
	}
}
