package imagebank.model.tag;

import java.io.Serializable;

public abstract class TaggableObject implements Taggable, Serializable {

	private HashSet<Tag> tagSet;

	protected TaggableObject() {
		tagSet = new HashSet<>();
	}

	@Override
	public boolean addTag(Tag tag) {
		return tagSet.add(tag);
	}

	@Override
	public boolean remove(Tag tag) {
		return tagSet.remove(tag);
	}

	@Override
	public boolean hasTag(Tag tag) {
		return tagSet.contains(tag);
	}

	@Override
	public Iterable<Tag> getTags() {
		return tagSet;
	}
}
