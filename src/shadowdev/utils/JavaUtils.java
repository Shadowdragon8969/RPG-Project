package shadowdev.utils;

public class JavaUtils<T> {

	public T checkNotNull(T in, T out) {
		if (in == null) {
			return out;
		}else return in;
	}
	
}
