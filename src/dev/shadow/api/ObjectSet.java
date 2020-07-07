package dev.shadow.api;

public class ObjectSet<T, K> {

	public T key;
	public K value;
	
	public ObjectSet(T key, K value) {
		this.key = key;
		this.value = value;
	}
	
}
