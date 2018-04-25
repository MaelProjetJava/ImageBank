package imagebank.model.tag;

import java.util.EventListener;

public interface TaggerListener extends EventListener {

	void tagsChanged(TaggerEvent event);
}
