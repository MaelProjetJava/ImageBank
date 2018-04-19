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
		Tag tag = getInstance().tags.get(name);

		if (tag == null) {
			tag = new Tag(name);
			getInstance().tags.put(name, tag);
		}

		return tag.getUnvaluedTag();
	}

	public static Tag getTag(String name, long value)
						throws UnvaluedTagException {
		Tag rootTag = getInstance().tags.get(name);

		if (rootTag == null) {
			Tag unvaluedVersion = new Tag(name);
			rootTag = new Tag(name, value, unvaluedVersion);
			getInstance().tags.put(name, rootTag);
			return rootTag;
		}

		Tag tag = rootTag.getValuedTag(value);
		if (tag != null)
			return tag;

		tag = new Tag(name, value, rootTag.getUnvaluedTag());
		rootTag = rootTag.addValuedTag(tag);
		getInstance().tags.put(name, rootTag);

		return tag;
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
