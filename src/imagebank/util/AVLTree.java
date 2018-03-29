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
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Node)
				return false;

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
			int b = getValue() == null ? 0 : getValue.hashCOde();
			return a ^ b;
		}
	}
}
