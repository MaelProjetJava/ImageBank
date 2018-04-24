package imagebank.model.tag;

import java.util.EventObject;

public class TaggerEvent extends EventObject {

	public TaggerEvent(Tagger source) throws IllegalArgumentException {
		super(source);
	}

	@Override
	public Tagger getSource() {
		return (Tagger) source;
	}
}
