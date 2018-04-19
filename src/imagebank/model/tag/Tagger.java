package imagebank.model.tag;

import java.util.LinkedHashMap;
import java.io.Serializable;

import imagebank.model.Image;

public class Tagger implements Serializable {

	private static Tagger instance = null;

	private LinkedHashMap<String,Tag> tags;

	private Tagger() {
		tags = new LinkedHashMap<>();
	}

	public static Tagger getInstance() {
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

	public static void untag(Image image, Tag tag) {

	}

	public static void untag(Tag tagged, Tag tagger) {

	}

	public static Iterable<Tag> getAllTags() {
		return getInstance().tags.values();
	}
}
