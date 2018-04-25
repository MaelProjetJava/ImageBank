package imagebank.model;

import java.util.EventListener;

public interface ImageDBListener extends EventListener {

	void imageDBChanged(ImageDBEvent event);
}
