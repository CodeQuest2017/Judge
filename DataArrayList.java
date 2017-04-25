import java.util.*;

public class DataArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 7265826591276585626L;
	public String toString() {
		Iterator<E> iterator = iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		while(iterator.hasNext()) {
			E next = iterator.next();
			sb.append(ParseJson.escape("" + next) + ", ");
		}
		sb.setLength(sb.length() - 2);
		return sb.append("]").toString();
	}
}
