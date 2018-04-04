package imagebank.util;

import java.util.SortedMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Deque;
import java.util.ArrayDeque;

public class AVLTree<K,V> extends AbstractMap<K,V>
				implements SortedMap<K,V>, Serializable {

	public static class Node<K,V> extends AbstractMap.SimpleEntry<K,V> {
		protected char balance;
		protected Node<K,V> leftChild;
		protected Node<K,V> rightChild;

		public Node(K key, V value) {
			super(key, value);
			this.balance = 0;
			this.leftChild = null;
			this.rightChild = null;
		}

	}

	@Override
	public String toString() {
		return stringify(root, "");
	}

	public static <K extends Comparable<K>,V> Deque<Node<K,V>>
				buildPath(Node<K,V> root, Node<K,V> node) {

		Deque<Node<K,V>> pathStack = new ArrayDeque<>();

		Node<K,V> currentNode = root;
		while (currentNode != null
				&& currentNode.getKey()
					.compareTo(node.getKey()) != 0) {

			pathStack.addFirst(currentNode);

			if (node.getKey().compareTo(currentNode.getKey()) > 0)
				currentNode = currentNode.rightChild;
			else
				currentNode = currentNode.leftChild;
		}

		pathStack.addFirst(currentNode);
		return pathStack;
	}

	public static <K extends Comparable<K>,V> Node<K,V>
			insert(Deque<Node<K,V>> insertPath, Node<K,V> node) {

		Node<K,V> leaf = insertPath.getFirst();

		if (node.getKey().compareTo(leaf.getKey()) > 0)
			leaf.rightChild = node;
		else
			leaf.leftChild = node;

		insertPath.addFirst(node);
		return retrace(1, insertPath);
	}

	private static <K,V> Node<K,V> retrace(int delta,
				Deque<Node<K,V>> retracePath) {

		Node<K,V> child = retracePath.removeFirst();
		Node<K,V> parent = retracePath.removeFirst();

		boolean heightChangeAbsorbed;
		do {
			if (child == parent.rightChild)
				parent.balance += delta;
			else
				parent.balance -= delta;

			if (parent.balance == 2)
				parent = rebalanceRight(retracePath, parent);
			else if (parent.balance == -2)
				parent = rebalanceLeft(retracePath, parent);

			child = parent;
			int tmp = parent.balance == 0 ? 1 : 0;
			heightChangeAbsorbed = (1 - delta) / 2 - tmp != 0;
		} while (!heightChangeAbsorbed
				&& (parent = retracePath.pollFirst()) != null);

		/* On renvoit la nouvelle racine de l'arbre */
		Node<K,V> root = retracePath.peekLast();
		return root != null ? root : (parent != null ? parent : child);
	}

	private static <K,V> Node<K,V> rebalanceRight(
			Deque<Node<K,V>> retracePath, Node<K,V> parent) {

		Node<K,V> child = parent.rightChild;
		Node<K,V> newParent;

		if (child.balance == -1)
			newParent = rotateRightLeft(parent);
		else
			newParent = rotateRightRight(parent);

		Node<K,V> grandpa = retracePath.peekFirst();
		if (grandpa != null)
			changeChild(grandpa, newParent, parent);

		return newParent;
	}

	private static <K,V> Node<K,V> rebalanceLeft(
			Deque<Node<K,V>> retracePath, Node<K,V> parent) {

		Node<K,V> child = parent.leftChild;
		Node<K,V> newParent;

		if (child.balance == 1)
			newParent = rotateLeftRight(parent);
		else
			newParent = rotateLeftLeft(parent);

		Node<K,V> grandpa = retracePath.peekFirst();
		if (grandpa != null)
			changeChild(grandpa, newParent, parent);

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
			root.balance = (char) 1;
			rootRightChild.balance = (char) -1;
		} else {
			root.balance = (char) 0;
			rootRightChild.balance = (char) 0;
		}

		return rootRightChild;
	}

	private static <K,V> Node<K,V> rotateLeftLeft(Node<K,V> root) {
		Node<K,V> rootLeftChild = root.leftChild;

		root.leftChild = rootLeftChild.rightChild;
		rootLeftChild.rightChild = root;

		if (rootLeftChild.balance == 0) {
			root.balance = (char) -1;
			rootLeftChild.balance = (char) 1;
		} else {
			root.balance = (char) 0;
			rootLeftChild.balance = (char) 0;
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
			root.balance = (char) 0;
			rootLeftChild.balance = (char) -1;
		} else if (rootRightLeftChild.balance == 0) {
			root.balance = (char) 0;
			rootLeftChild.balance = (char) 0;
		} else {
			root.balance = (char) 1;
			rootLeftChild.balance = (char) 0;
		}
		rootRightLeftChild.balance = (char) 0;

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
			root.balance = (char) -1;
			rootRightChild.balance = (char) 0;
		} else if (rootLeftRightChild.balance == 0) {
			root.balance = (char) 0;
			rootRightChild.balance = 0;
		} else {
			root.balance = (char) 0;
			rootRightChild.balance = (char) 1;
		}
		rootLeftRightChild.balance = (char) 0;

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
