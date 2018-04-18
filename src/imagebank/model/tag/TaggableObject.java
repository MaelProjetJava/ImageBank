package imagebank.model.tag;

import java.io.Serializable;
import java.util.LinkedHashSet;

public abstract class TaggableObject implements Serializable {

	private LinkedHashSet<Tag> tagSet;

	protected TaggableObject() {
		tagSet = new LinkedHashSet<>();
	}

	final boolean addTag(Tag tag) {
		return tagSet.add(tag);
	}

	final boolean removeTag(Tag tag) {
		return tagSet.remove(tag);
	}

	final public boolean hasTag(Tag tag) {
		if (tagSet.contains(tag))
			return true;

		for (Tag ownedTag : tagSet) {
			if (ownedTag.hasTag(tag))
				return true;
		}

		return false;
	}

	final public Iterable<Tag> getTags() {
		return tagSet;
	}
}
