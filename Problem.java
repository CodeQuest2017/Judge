import java.util.*;
import java.io.*;

public class Problem {
	private Map<String, String> map;
	private StringBuilder out = new StringBuilder();
	
	public Problem(Map<String, String> map) {
		this.setMap(map);
	}
	public Map<String, String> getMap() {
		return this.map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String toJson() {
		out.append("{\n");
		this.map.forEach((k, v) -> {
			out.append("\t\t\"" + k + "\"" + ": " + v + ",\n");
		});
		// out.deleteCharAt(out.lastIndexOf(","));
		return out.toString() + "\t}";
	}
	public String toString() {
		return this.toJson();
	}
	
}
