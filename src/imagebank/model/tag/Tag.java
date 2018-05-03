package imagebank.model.tag;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import imagebank.util.AVLTree;
import static imagebank.util.AVLTree.*;
import imagebank.model.Image;

public class Tag extends TaggableObject {

	private String name;
	private Long value;
	private Tag unvaluedTagVersion;
	private Node<Long,Tag> valuesTreeRoot;
	private AVLTree<String,Image> taggedImages;

	protected Tag(String name) {
		super();
		this.name = name;
		this.value = null;
		this.unvaluedTagVersion = this;
		this.valuesTreeRoot = null;
		this.taggedImages = new AVLTree<>();
	}

	protected Tag(String name, long value, Tag unvaluedTagVersion) {
		this(name);
		this.value = value;
		this.unvaluedTagVersion = unvaluedTagVersion;
		this.valuesTreeRoot = new Node<>(value, this);
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

	protected Tag getValuedTag(long value) throws UnvaluedTagException {
		if (!hasValue())
			throw new UnvaluedTagException();

		Node<Long,Tag> foundNode = search(valuesTreeRoot, value);
		return foundNode != null ? foundNode.getValue() : null;
	}

	protected Tag addValuedTag(Tag valuedTag)
						throws UnvaluedTagException {
		if (!hasValue() || !valuedTag.hasValue())
			throw new UnvaluedTagException();

		Node<Long,Tag> newNode = valuedTag.valuesTreeRoot;
		Deque<PathStep<Node<Long,Tag>>> insertPath
					= buildPath(valuesTreeRoot, newNode);

		return insert(insertPath, newNode).getValue();
	}

	protected Tag removeValuedTag(Tag valuedTag)
						throws UnvaluedTagException {
		if (!hasValue() || !valuedTag.hasValue())
			throw new UnvaluedTagException();

		Deque<PathStep<Node<Long,Tag>>> deletePath
				= buildPath(valuesTreeRoot,
					valuedTag.valuesTreeRoot.getKey());

		return delete(deletePath, valuesTreeRoot).getValue();
	}

	protected Iterable<Tag> getValuedTags(long start, long end)
						throws UnvaluedTagException {
		if (!hasValue())
			throw new UnvaluedTagException();

		return () -> new Iterator<Tag>() {
			private Deque<PathStep<Node<Long,Tag>>> currentPath =
					firstBounded(valuesTreeRoot,
							Long.valueOf(start));

			@Override
			public boolean hasNext() {
				return currentPath != null && getNodeFromPath(
					valuesTreeRoot, currentPath).getKey()
							<= end;
			}

			@Override
			public Tag next() {
				if (currentPath == null)
					throw new NoSuchElementException();

				Node<Long,Tag> node = getNodeFromPath(
						valuesTreeRoot, currentPath);

				if (node.getKey() > end)
					throw new NoSuchElementException();

				currentPath = successor(valuesTreeRoot,
								currentPath);
				return node.getValue();
			}
		};
	}

	protected void addTaggedImage(Image image) {
		taggedImages.put(image.getName(), image);
	}

	protected void removeTaggedImage(Image image) {
		taggedImages.remove(image.getName());
	}

	public Iterable<Image> getTaggedImages() {
		return taggedImages.values();
	}

	public boolean hasValue() {
		return value != null;
	}

	@Override
	public String toString() {
		return getName() + (hasValue() ? "=" + value : "");
	}
}
