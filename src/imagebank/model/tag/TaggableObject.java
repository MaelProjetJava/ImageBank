package imagebank.model.tag;

import java.io.Serializable;
import java.util.LinkedHashSet;

public abstract class TaggableObject implements Taggable, Serializable {

	private LinkedHashSet<Tag> tagSet;

	protected TaggableObject() {
		tagSet = new LinkedHashSet<>();
	}

	@Override
	protected boolean addTag(Tag tag) {
		return tagSet.add(tag);
	}

	@Override
	protected boolean remove(Tag tag) {
		return tagSet.remove(tag);
	}

	@Override
	public boolean hasTag(Tag tag) {
		if (tagSet.contains(tag))
			return true;

		for (Tag ownedTag : tagSet) {
			if (ownedTag.hasTag(tag))
				return true;
		}

		return false;
	}

	@Override
	public Iterable<Tag> getTags() {
		return tagSet;
	}
}
