package imagebank.model.tag;

public class Tag extends TaggableObject {

	private String name;
	private boolean hasValue;
	private long value;
	private Tag unvaluedTagVersion;
	private AVLTree<String,Image> taggedImages;

	protected Tag(String name) {
		super();
		this.name = name;
		this.value = 0;
		this.hasValue = false;
		this.unvaluedTagVersion = this;
	}

	protected Tag(String name, long value) {
		super();
		this.name = name;
		this.value = value;
		this.hasValue = true;
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
		return hasValue;
	}

	@Override
	public String toString() {
		return getName() + (hasValue() ? "=" + value : "");
	}
}
