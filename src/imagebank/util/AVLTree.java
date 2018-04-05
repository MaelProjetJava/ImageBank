package imagebank.util;

import java.util.SortedMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Deque;
import java.util.ArrayDeque;

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
	}

	@Override
	public String toString() {
		return stringify(root, "");
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

	public static <K extends Comparable<K>,V> Node<K,V>
		insert(Deque<PathStep<Node<K,V>>> insertPath, Node<K,V> node) {

		PathStep<Node<K,V>> leafPathStep = insertPath.getFirst();

		if (leafPathStep.getDirection() == Direction.RIGHT)
			leafPathStep.getNode().rightChild = node;
		else
			leafPathStep.getNode().leftChild = node;

		return retrace(1, insertPath);
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
