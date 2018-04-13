package imagebank.model.tag;

public interface Taggable {
	boolean addTag(Tag tag);
	boolean removeTag(Tag tag);
	boolean hasTag(Tag tag);
	Iterable<Tag> getTags();
}
