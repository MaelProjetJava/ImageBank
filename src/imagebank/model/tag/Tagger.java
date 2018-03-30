package imagebank.model.tag;

import java.util.LinkedHashMap;

public class Tagger implements Serializable {

	private static Tagger instance = null;

	private LinkedHashMap<String,Tag> tags;

	private Tagger() {
		tags = new LinkedHashMap<>();
	}

	public Tagger getInstance() {
		if (instance == null)
			instance = new Tagger();

		return instance;
	}

	public static Tag getTag(String name) {

	}

	public static Tag getTag(String name, long value)
						throws UnvaluedTagException {

	}

	public static void tag(Image image, Tag tag) {

	}

	public static void tag(Tag tagged, Tag tagger) {

	}

	public static Iterable<Tag> getAllTags() {
		return tags.values();
	}

}
