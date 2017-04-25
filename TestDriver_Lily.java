import java.util.*;
import java.io.*;

public class TestDriver_Lily {
	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", "2013");
		map.put("name", "\"Donovan\"");
		map.put("number", "16");
		map.put("hash", "A539GJ40AVKG");
		map.put("compileTimeError", "null");
		map.put("runTimeError", "null");
		map.put("lineCountError", "null");
		map.put("expected", "[]");
		map.put("actual", "[]");
		map.put("counter", "6");
		
		Problem prob = new Problem(map);
		
		System.out.println(ParseJson.parseJson("json.json").toString());
	}
}