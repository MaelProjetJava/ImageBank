package imagebank.util;

import java.util.SortedMap;
import java.util.Map;

public class AVLTree<K,V> implements SortedMap<K,V> {

	public static class Node<K,V> implements Map.Entry<K,V>  {
		private K key;
		private V value;
		protected char balance;
		protected Node<K,V> leftChild;
		protected Node<K,V> rightChild;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.balance = 0;
			this.leftChild = null;
			this.rightChild = null;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Node))
				return false;

			Node<?,?> o = (Node<?,?>) other;

			boolean keysEquals;
			if (this.getKey() == null)
				keysEquals = o.getKey() == null;
			else
				keysEquals = this.getKey().equals(o.getKey());

			boolean valuesEquals;
			if (this.getValue() == null) {
				valuesEquals = o.getValue() == null;
			} else {
				valuesEquals = this.getValue()
							.equals(o.getValue());
			}

			return keysEquals && valuesEquals;
		}

		@Override
		public int hashCode() {
			int a = getKey() == null ? 0 : getKey().hashCode();
			int b = getValue() == null ? 0 : getValue().hashCode();
			return a ^ b;
		}
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
}
