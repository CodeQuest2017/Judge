import java.util.*;
import java.io.*;

public class ParseJson {
	public static Map<String, String> map;
	public static List<Problem> inFile(String file) throws Exception {
		Scanner scan = new Scanner(new File(file));
		List<Problem> problems = new JsonArrayList<Problem>();
		while(scan.hasNextLine()) {
			String next = scan.nextLine();
			if(next.indexOf("{") == 1) {
				map = new LinkedHashMap<String, String>();
				String keyValuePair = scan.nextLine();
				while(keyValuePair.indexOf("}") != 1) {
					map.put(keyValuePair.substring(3, keyValuePair.indexOf(": ")-1), keyValuePair.substring(keyValuePair.indexOf(": ")+2, keyValuePair.length()-1));
					keyValuePair = scan.nextLine();
				}
				Problem prob = new Problem(map);
				problems.add(prob);
			}
		}
		return problems;
	}
	public static String escape(String input) {
		int x = input.length();
		if(input == null || x == 0) return "\"\"";
		StringBuilder sb = new StringBuilder(x + 4);

		sb.append('"');

		for(int i = 0; i < x; i++) {
			char c = input.charAt(i);
			
			if(c == '\\' || c == '"') {
				sb.append('\\');
				sb.append(c);
				continue;
			}

			if(c == '/') {
				sb.append('\\');
				sb.append(c);
				continue;
			}

			if(c == '\b') {
				sb.append("\\b");
				continue;
			}

			if(c == '\t') {
				sb.append("\\t");
				continue;
			}

			if(c == '\n') {
				sb.append("\\n");
				continue;
			}

			if(c == '\f') {
				sb.append("\\f");
				continue;
			}

			if(c == '\r') {
				sb.append("\\r");
				continue;
			}

			if(c < ' ') {
				String t = "000" + Integer.toHexString(c);
				sb.append("\\u" + t.substring(t.length() - 4));
			} else {
				sb.append(c);
			}

		}
		
		sb.append('"');
		return sb.toString();
	}
}
