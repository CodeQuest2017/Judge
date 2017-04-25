import java.util.*;
import java.io.*;

public class Problem {
	private Map<String, String> map;
	private String out;
	
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
		out="{\n";
		this.map.forEach((k, v) -> {
			out+="\t\t\""+k+"\""+": "+v+",\n";
		});
		return out+"\t}";
	}
	public String toString() {
		return this.toJson();
	}
	
}
