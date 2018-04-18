package imagebank.model.tag;

public class Tag extends TaggableObject {

	private String name;
	private Long value;
	private Tag unvaluedTagVersion;
	private AVLTree<String,Image> taggedImages;

	protected Tag(String name) {
		super();
		this.name = name;
		this.value = null;
		this.unvaluedTagVersion = this;
	}

	protected Tag(String name, long value) {
		super();
		this.name = name;
		this.value = value;
		this.unvaluedTagVersion = new Tag(name);
	}

	public String getName() {
		return name;
	}

	public long getValue() throws UnvaluedTagException {
		if (!hasValue())
			throw new UnvaluedTagException();

		return value;
	}

	protected Tag getUnvaluedTag() {
		return unvaluedTagVersion;
	}

	public Iterable<Image> getTaggedImages() {

	}

	public boolean hasValue() {
		return value != null;
	}

	@Override
	public String toString() {
		return getName() + (hasValue() ? "=" + value : "");
	}
}
