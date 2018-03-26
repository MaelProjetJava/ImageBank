package imagebank.model.tag;

public class Tag extends TaggableObject {

	private int hashCode;
	private String name

	protected Tag(String name) {
		super();
		this.name = name;
		hashCode = hashCode();
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return getName();
	}
}
