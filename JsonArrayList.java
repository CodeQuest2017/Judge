import java.util.*;

public class JsonArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 7296729673955326372L;
	public String toString() {
		Iterator<E> iterator = iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		
		while(iterator.hasNext()) {
			E next = iterator.next();
			sb.append("\t" + next + ",\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.append("];").toString();
	}
}
