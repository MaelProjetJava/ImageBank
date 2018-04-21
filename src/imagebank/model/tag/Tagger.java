package imagebank.model.tag;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;

import imagebank.model.Image;

public class Tagger implements Serializable {

	private static Tagger instance = null;

	private LinkedHashMap<String,Tag> tags;

	private Tagger() {
		tags = new LinkedHashMap<>();
	}

	private void readObject(ObjectInputStream stream)
				throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		instance = this;
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

	public static Iterable<Tag> getTags(String name, long start, long end)
						throws UnvaluedTagException {
		Tag tag = getInstance().tags.get(name);

		if (tag == null) {
			return () -> new Iterator<Tag>() {
				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public Tag next() {
					throw new NoSuchElementException();
				}
			};
		} else {
			return tag.getValuedTags(start, end);
		}
	}

	private static void addImageToTag(Image image, Tag tag) {
		tag.addTaggedImage(image);

		for (Tag parentTag : tag.getTags())
			addImageToTag(image, parentTag);
	}

	public static void tag(Image image, Tag tag) {
		/* If the Image is already tagged by the Tag*/
		if (!((TaggableObject) image).addTag(tag))
			return;

		addImageToTag(image, tag);
	}

	public static void tag(Tag tagged, Tag tagger) {
		/* If the Tag is already tagged by the Tag */
		if (!tagged.addTag(tagger))
			return;

		for (Image image : tagged.getTaggedImages())
			addImageToTag(image, tagger);
	}

	private static void removeImageFromTag(Image image, Tag tag) {
		tag.removeTaggedImage(image);

		for (Tag parentTag : tag.getTags()) {
			if (!image.hasTag(parentTag))
				removeImageFromTag(image, parentTag);
		}
	}

	public static void untag(Image image, Tag tag) {
		/* If the Image is not actually tagged by the Tag */
		if (!((TaggableObject) image).removeTag(tag))
			return;

		removeImageFromTag(image, tag);
	}

	public static void untag(Tag tagged, Tag tagger) {
		/* If the Tag is not actually tagger by the Tag */
		if (!tagged.removeTag(tagger))
			return;

		for (Image image : tagged.getTaggedImages()) {
			if (!image.hasTag(tagger))
				removeImageFromTag(image, tagger);
		}
	}

	public static Iterable<Tag> getAllTags() {
		return getInstance().tags.values();
	}
}
