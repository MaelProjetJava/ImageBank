package imagebank.util;

import java.util.SortedMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Deque;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.io.Serializable;

public class AVLTree<K,V> extends AbstractMap<K,V>
				implements SortedMap<K,V>, Serializable {

	public static class Node<K,V> extends AbstractMap.SimpleEntry<K,V> {
		protected byte balance;
		protected Node<K,V> leftChild;
		protected Node<K,V> rightChild;

		public Node(K key, V value) {
			super(key, value);
			this.balance = 0;
			this.leftChild = null;
			this.rightChild = null;
		}
	}

	private enum Direction {
		LEFT,
		RIGHT
	}

	private static class PathStep<T> {
		private T node;
		private Direction direction;

		public PathStep(T node, Direction direction) {
			this.node = node;
			this.direction = direction;
		}

		public T getNode() {
			return node;
		}

		public Direction getDirection() {
			return direction;
		}

		public void setNode(T node) {
			this.node = node;
		}

	}

	private static class NaturalComparator<T>
					implements Comparator<T>, Serializable {

		@Override
		@SuppressWarnings("unchecked")
		public int compare(T o1, T o2) {
			return ((Comparable<? super T>) o1).compareTo(o2);
		}
	}

	private static class ComparableKey<T>
			implements Comparable<ComparableKey<T>>, Serializable {

		private Comparator<? super T> comparator;
		private T key;

		public static <T> ComparableKey<T> of(T key) {
			ComparableKey<T> comparableKey = new ComparableKey<>();
			comparableKey.key = key;
			comparableKey.comparator = new NaturalComparator<T>();
			return comparableKey;
		}

		public static <T> ComparableKey<T>
				of(T key, Comparator<? super T> comparator) {
			ComparableKey<T> comparableKey = new ComparableKey<>();
			comparableKey.key = key;
			comparableKey.comparator = comparator;
			return comparableKey;
		}

		public T getWrappedKey() {
			return key;
		}

		@Override
		public int compareTo(ComparableKey<T> o) {
			return comparator.compare(key, o.key);
		}

		@Override
		public String toString() {
			return key.toString();
		}

		public boolean bounded(ComparableKey<T> lowerBound,
						ComparableKey<T> upperBound) {
			return lowerBounded(lowerBound)
						&& upperBounded(upperBound);
		}

		public boolean lowerBounded(ComparableKey<T> lowerBound) {
			if (lowerBound == null)
				return true;
			else
				return compareTo(lowerBound) >= 0;
		}

		public boolean upperBounded(ComparableKey<T> upperBound) {
			if (upperBound == null)
				return true;
			else
				return compareTo(upperBound) < 0;
		}
	}

	private static class NodeWrapper<K,V> extends
						AbstractMap.SimpleEntry<K,V> {

		private Node<ComparableKey<K>,V> node;

		public NodeWrapper(Node<ComparableKey<K>,V> node) {
			super(node.getKey().getWrappedKey(), node.getValue());
			this.node = node;
		}

		@Override
		public V setValue(V value) {
			super.setValue(value);
			return node.setValue(value);
		}
	}

	private class Iterator implements java.util.Iterator<Map.Entry<K,V>> {
		private Deque<PathStep<Node<ComparableKey<K>,V>>> currentPath;
		private NodeWrapper<K,V> currentNode;
		private ComparableKey<K> upperBound;

		public Iterator() {
			currentPath = first(root);
			currentNode = null;
			upperBound = null;
		}

		public Iterator(ComparableKey<K> lowerBound,
						ComparableKey<K> upperBound) {
			this.currentNode = null;
			this.upperBound = upperBound;

			if (lowerBound == null) {
				currentPath = first(root);
				return;
			}

			currentPath = buildPath(root, lowerBound);
			if (getNodeFromPath(root, currentPath) == null)
				currentPath = successor(root, currentPath);
		}

		@Override
		public boolean hasNext() {
			return currentPath != null &&
				getNodeFromPath(root, currentPath).getKey()
						.upperBounded(upperBound);
		}

		@Override
		public Map.Entry<K,V> next() {
			currentNode = null;

			if (currentPath == null)
				throw new NoSuchElementException();

			Node<ComparableKey<K>,V> node =
					getNodeFromPath(root, currentPath);

			if (!node.getKey().upperBounded(upperBound))
				throw new NoSuchElementException();

			currentNode = new NodeWrapper<>(node);

			currentPath = successor(root, currentPath);
			return currentNode;
		}

		@Override
		public void remove() {
			if (currentNode == null)
				throw new IllegalStateException();

			Node<ComparableKey<K>,V> successor = null;
			if (currentPath != null)
				successor = getNodeFromPath(root, currentPath);

			AVLTree.this.remove(currentNode.getKey());
			if (successor != null)
				currentPath = buildPath(root, successor);

			currentNode = null;
		}
	}

	private class EntrySet extends AbstractSet<Map.Entry<K,V>> {

		@Override
		public void clear() {
			root = null;
		}

		@Override
		public boolean contains(Object o) {
			@SuppressWarnings("unchecked")
			Map.Entry<K,V> entry = (Map.Entry<K,V>) o;
			Node<ComparableKey<K>,V> foundNode = search(root,
					createComparableKey(entry.getKey()));

			if (foundNode == null)
				return false;

			return foundNode.getValue().equals(entry.getValue());
		}

		@Override
		public java.util.Iterator<Map.Entry<K,V>> iterator() {
			return new Iterator();
		}

		@Override
		public boolean remove(Object o) {
			@SuppressWarnings("unchecked")
			Map.Entry<K,V> entry = (Map.Entry<K,V>) o;

			@SuppressWarnings("unchecked")
			Deque<PathStep<Node<ComparableKey<K>,V>>> deletePath
					= buildPath(root, createComparableKey(
							entry.getKey()));

			Node<ComparableKey<K>,V> nodeToDelete =
				getNodeFromPath(root, deletePath);

			if (nodeToDelete == null)
				return false;
			else if (!nodeToDelete.getValue()
						.equals(entry.getValue()))
				return false;

			root = delete(deletePath, root);
			size--;
			return true;
		}

		@Override
		public int size() {
			return size;
		}
	}

	private Node<ComparableKey<K>,V> root;
	private Comparator<? super K> userComparator;
	private int size = 0;
	private Set<Map.Entry<K,V>> entrySet = null;

	public AVLTree() {
		root = null;
		userComparator = null;
	}

	public AVLTree(Comparator<? super K> comparator) {
		root = null;
		userComparator = comparator;
	}

	public AVLTree(Map<? extends K,? extends V> m) {
		super();
		putAll(m);
	}

	public AVLTree(SortedMap<K,? extends V> m) {
		root = null;
		userComparator = m.comparator();
		putAll(m);
	}

	@Override
	public Comparator<? super K> comparator() {
		return userComparator;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		return search(root, createComparableKey((K) key)) != null;
	}

	@Override
	public V get(Object key) {
		@SuppressWarnings("unchecked")
		Node<ComparableKey<K>,V> foundNode =
				search(root, createComparableKey((K) key));

		return foundNode != null ? foundNode.getValue() : null;
	}

	@Override
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();

		Node<ComparableKey<K>,V> newNode = new Node<>(
			createComparableKey(key),
			value
		);

		if (root == null) {
			root = newNode;
			size++;
			return null;
		}

		Deque<PathStep<Node<ComparableKey<K>,V>>> insertPath
						= buildPath(root, newNode);

		PathStep<Node<ComparableKey<K>,V>> leafPathStep =
							insertPath.getFirst();

		Node<ComparableKey<K>,V> oldNode =
					getNodeFromPathStep(leafPathStep);

		if (oldNode != null) {
			V oldValue = oldNode.getValue();
			oldNode.setValue(newNode.getValue());
			return oldValue;
		}

		root = insert(insertPath, newNode);
		size++;
		return null;
	}

	private ComparableKey<K> createComparableKey(K key) {
		ComparableKey<K> comparableKey;

		if (userComparator != null)
			comparableKey = ComparableKey.of(key, userComparator);
		else
			comparableKey = ComparableKey.of(key);

		return comparableKey;
	}

	private static <K,V> Node<K,V> getNodeFromPath(Node<K,V> root,
					Deque<PathStep<Node<K,V>>> path) {

		if (path.peekFirst() != null)
			return getNodeFromPathStep(path.getFirst());
		else
			return root;
	}

	private static <K,V> Node<K,V>
				getNodeFromPathStep(PathStep<Node<K,V>> step) {
		if (step.getDirection() == Direction.RIGHT)
			return step.getNode().rightChild;
		else
			return step.getNode().leftChild;
	}

	@Override
	public V remove(Object key) {
		@SuppressWarnings("unchecked")
		Deque<PathStep<Node<ComparableKey<K>,V>>> deletePath
				= buildPath(root, createComparableKey((K) key));

		Node<ComparableKey<K>,V> nodeToDelete =
					getNodeFromPath(root, deletePath);

		if (nodeToDelete == null)
			return null;

		root = delete(deletePath, root);
		size--;
		return nodeToDelete.getValue();
	}

	@Override
	public Set<Map.Entry<K,V>> entrySet() {
		if (entrySet == null);
			entrySet = new EntrySet();

		return entrySet;
	}

	@Override
	public K firstKey() {
		return getNodeFromPath(root, first(root)).getKey()
							.getWrappedKey();
	}

	@Override
	public K lastKey() {
		return getNodeFromPath(root, last(root)).getKey()
							.getWrappedKey();
	}

	@Override
	public String toString() {
		return stringify(root, "");
	}

	public static <K extends Comparable<K>,V>
			Node<K,V> search(Node<K,V> root, K key) {

		Node<K,V> currentNode = root;
		while (currentNode != null && currentNode.getKey()
							.compareTo(key) != 0) {

			if (key.compareTo(currentNode.getKey()) > 0)
				currentNode = currentNode.rightChild;
			else
				currentNode = currentNode.leftChild;
		}

		return currentNode;
	}

	public static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
				buildPath(Node<K,V> root, K key) {
		return buildPath(root, new Node<>(key, null));
	}

	public static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
				buildPath(Node<K,V> root, Node<K,V> node) {

		Deque<PathStep<Node<K,V>>> pathStack = new ArrayDeque<>();

		Node<K,V> currentNode = root;
		while (currentNode != null && currentNode.getKey()
					.compareTo(node.getKey()) != 0) {

			if (node.getKey().compareTo(currentNode.getKey()) > 0) {
				pathStack.addFirst(new PathStep<>(
					currentNode, Direction.RIGHT
				));
				currentNode = currentNode.rightChild;
			} else {
				pathStack.addFirst(new PathStep<>(
					currentNode, Direction.LEFT
				));
				currentNode = currentNode.leftChild;
			}
		}

		return pathStack;
	}

	public static <K extends Comparable<K>,V>
			Deque<PathStep<Node<K,V>>> first(Node<K,V> root) {

		if (root != null)
			return leastDescendant(root, new ArrayDeque<>());
		else
			return null;
	}

	public static <K extends Comparable<K>,V>
			Deque<PathStep<Node<K,V>>> last(Node<K,V> root) {

		if (root != null)
			return greatestDescendant(root, new ArrayDeque<>());
		else
			return null;
	}

	public static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
			successor(Node<K,V> root,
				Deque<PathStep<Node<K,V>>> currentPath) {

		Node<K,V> currentNode = getNodeFromPath(root, currentPath);

		if (currentNode != null && currentNode.rightChild != null) {
			currentPath.addFirst(
				new PathStep<>(currentNode, Direction.RIGHT)
			);

			currentNode = currentNode.rightChild;
			return leastDescendant(root, currentPath);
		}

		PathStep<Node<K,V>> currentStep;
		do {
			currentStep = currentPath.pollFirst();
			if (currentStep == null)
				return null;
		} while (currentStep.getDirection() == Direction.RIGHT);

		return currentPath;
	}

	public static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
			predecessor(Node<K,V> root,
				Deque<PathStep<Node<K,V>>> currentPath) {

		Node<K,V> currentNode = getNodeFromPath(root, currentPath);

		if (currentNode != null && currentNode.leftChild != null) {
			currentPath.addFirst(
				new PathStep<>(currentNode, Direction.LEFT)
			);

			currentNode = currentNode.leftChild;
			return greatestDescendant(root, currentPath);
		}

		PathStep<Node<K,V>> currentStep;
		do {
			currentStep = currentPath.pollFirst();
			if (currentStep == null)
				return null;
		} while (currentStep.getDirection() == Direction.LEFT);

		return currentPath;
	}

	private static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
			leastDescendant(Node<K,V> root,
				Deque<PathStep<Node<K,V>>> currentPath) {

		Node<K,V> currentNode = getNodeFromPath(root, currentPath);

		while (currentNode.leftChild != null) {
			currentPath.addFirst(
				new PathStep<>(currentNode, Direction.LEFT)
			);

			currentNode = currentNode.leftChild;
		}

		return currentPath;
	}

	private static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
			greatestDescendant(Node<K,V> root,
				Deque<PathStep<Node<K,V>>> currentPath) {

		Node<K,V> currentNode = getNodeFromPath(root, currentPath);

		while (currentNode.rightChild != null) {
			currentPath.addFirst(
				new PathStep<>(currentNode, Direction.RIGHT)
			);

			currentNode = currentNode.rightChild;
		}

		return currentPath;
	}

	public static <K extends Comparable<K>,V> Node<K,V>
		insert(Deque<PathStep<Node<K,V>>> insertPath, Node<K,V> node) {

		PathStep<Node<K,V>> leafPathStep = insertPath.getFirst();

		if (leafPathStep.getDirection() == Direction.RIGHT)
			leafPathStep.getNode().rightChild = node;
		else
			leafPathStep.getNode().leftChild = node;

		return retrace(1, insertPath);
	}

	public static <K extends Comparable<K>,V> Node<K,V>
		delete(Deque<PathStep<Node<K,V>>> deletePath, Node<K,V> root) {

		Node<K,V> nodeToDelete = getNodeFromPath(root, deletePath);

		if (nodeToDelete.leftChild != null
					&& nodeToDelete.rightChild != null) {
			deletePath = deleteWithChilds(deletePath, root);
		} else {
			if (nodeToDelete == root) {
				if (nodeToDelete.leftChild != null)
					return nodeToDelete.leftChild;
				else if (nodeToDelete.rightChild != null)
					return nodeToDelete.rightChild;
				else
					return null;
			}

			removeChild(deletePath.peekFirst().getNode(),
								nodeToDelete);
		}

		return retrace(-1, deletePath);
	}

	private static <K extends Comparable<K>,V> Deque<PathStep<Node<K,V>>>
			deleteWithChilds(Deque<PathStep<Node<K,V>>> deletePath,
							Node<K,V> root) {

		Node<K,V> nodeToDelete = getNodeFromPath(root, deletePath);
		PathStep<Node<K,V>> transientStep = new PathStep<>(
			nodeToDelete,
			Direction.RIGHT
		);

		deletePath.addFirst(transientStep);
		deletePath = leastDescendant(root, deletePath);

		Node<K,V> replacementNode = getNodeFromPath(root, deletePath);

		removeChild(deletePath.getFirst().getNode(), replacementNode);
		replacementNode.rightChild = nodeToDelete.rightChild;
		replacementNode.leftChild = nodeToDelete.leftChild;
		replacementNode.balance = nodeToDelete.balance;

		transientStep.setNode(replacementNode);
		return deletePath;
	}

	private static <K,V> Node<K,V> retrace(int delta,
				Deque<PathStep<Node<K,V>>> retracePath) {

		PathStep<Node<K,V>> pathStep = retracePath.removeFirst();

		Node<K,V> parent;
		boolean heightChangeAbsorbed;
		do {
			parent = pathStep.getNode();

			if (pathStep.getDirection() == Direction.RIGHT)
				parent.balance += delta;
			else
				parent.balance -= delta;

			if (parent.balance == 2)
				parent = rebalanceRight(retracePath, parent);
			else if (parent.balance == -2)
				parent = rebalanceLeft(retracePath, parent);

			int tmp = parent.balance == 0 ? 1 : 0;
			heightChangeAbsorbed = (1 - delta) / 2 - tmp != 0;
		} while (!heightChangeAbsorbed
			&& (pathStep = retracePath.pollFirst()) != null);

		/* On renvoit la nouvelle racine de l'arbre */
		PathStep<Node<K,V>> rootPathStep = retracePath.peekLast();
		return rootPathStep != null ? rootPathStep.getNode() : parent;
	}

	private static <K,V> Node<K,V> rebalanceRight(
		Deque<PathStep<Node<K,V>>> retracePath, Node<K,V> parent) {

		Node<K,V> child = parent.rightChild;
		Node<K,V> newParent;

		if (child.balance == -1)
			newParent = rotateRightLeft(parent);
		else
			newParent = rotateRightRight(parent);

		PathStep<Node<K,V>> grandpa = retracePath.peekFirst();
		if (grandpa != null)
			changeChild(grandpa.getNode(), newParent, parent);

		return newParent;
	}

	private static <K,V> Node<K,V> rebalanceLeft(
		Deque<PathStep<Node<K,V>>> retracePath, Node<K,V> parent) {

		Node<K,V> child = parent.leftChild;
		Node<K,V> newParent;

		if (child.balance == 1)
			newParent = rotateLeftRight(parent);
		else
			newParent = rotateLeftLeft(parent);

		PathStep<Node<K,V>> grandpa = retracePath.peekFirst();
		if (grandpa != null)
			changeChild(grandpa.getNode(), newParent, parent);

		return newParent;
	}

	private static <K,V> void changeChild(Node<K,V> parent,
				Node<K,V> newChild, Node<K,V> oldChild) {

		if (oldChild == parent.leftChild)
			parent.leftChild = newChild;
		else
			parent.rightChild = newChild;
	}

	private static <K,V>
			void removeChild(Node<K,V> parent, Node<K,V> child) {

		if (child.leftChild != null || child.rightChild != null) {
			if (child.leftChild != null)
				changeChild(parent, child.leftChild, child);
			else
				changeChild(parent, child.rightChild, child);
		} else {
			if (child == parent.leftChild)
				parent.leftChild = null;
			else
				parent.rightChild = null;
		}
	}

	private static <K,V> Node<K,V> rotateRightRight(Node<K,V> root) {
		Node<K,V> rootRightChild = root.rightChild;

		root.rightChild = rootRightChild.leftChild;
		rootRightChild.leftChild = root;

		if (rootRightChild.balance == 0) {
			root.balance = 1;
			rootRightChild.balance = -1;
		} else {
			root.balance = 0;
			rootRightChild.balance = 0;
		}

		return rootRightChild;
	}

	private static <K,V> Node<K,V> rotateLeftLeft(Node<K,V> root) {
		Node<K,V> rootLeftChild = root.leftChild;

		root.leftChild = rootLeftChild.rightChild;
		rootLeftChild.rightChild = root;

		if (rootLeftChild.balance == 0) {
			root.balance = -1;
			rootLeftChild.balance = 1;
		} else {
			root.balance = 0;
			rootLeftChild.balance = 0;
		}

		return rootLeftChild;
	}

	private static <K,V> Node<K,V> rotateLeftRight(Node<K,V> root) {
		Node<K,V> rootLeftChild = root.leftChild;
		Node<K,V> rootRightLeftChild = rootLeftChild.rightChild;

		root.leftChild = rootRightLeftChild.rightChild;
		rootLeftChild.rightChild = rootRightLeftChild.leftChild;
		rootRightLeftChild.rightChild = root;
		rootRightLeftChild.leftChild = rootLeftChild;

		if (rootRightLeftChild.balance == 1) {
			root.balance = 0;
			rootLeftChild.balance = -1;
		} else if (rootRightLeftChild.balance == 0) {
			root.balance = 0;
			rootLeftChild.balance = 0;
		} else {
			root.balance = 1;
			rootLeftChild.balance = 0;
		}
		rootRightLeftChild.balance = 0;

		return rootRightLeftChild;
	}

	private static <K,V> Node<K,V> rotateRightLeft(Node<K,V> root) {
		Node<K,V> rootRightChild = root.rightChild;
		Node<K,V> rootLeftRightChild = rootRightChild.leftChild;

		root.rightChild = rootLeftRightChild.leftChild;
		rootRightChild.leftChild = rootLeftRightChild.rightChild;
		rootLeftRightChild.leftChild = root;
		rootLeftRightChild.rightChild = rootRightChild;

		if (rootLeftRightChild.balance == 1) {
			root.balance = -1;
			rootRightChild.balance = 0;
		} else if (rootLeftRightChild.balance == 0) {
			root.balance = 0;
			rootRightChild.balance = 0;
		} else {
			root.balance = 0;
			rootRightChild.balance = 1;
		}
		rootLeftRightChild.balance = 0;

		return rootLeftRightChild;
	}

	public static <K,V> String stringify(Node<K,V> root, String prefix) {
		if (root == null)
			return prefix + "(nil)\n";

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(prefix).append(root.getKey()).append("\n");
		strBuilder.append(prefix).append("|\\\n");
		strBuilder.append(stringify(root.rightChild, prefix + "|"));
		strBuilder.append(stringify(root.leftChild, prefix));

		return strBuilder.toString();
	}
}
