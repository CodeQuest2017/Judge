import java.util.*;
import java.io.*;

public class test {
	public static void main(String[] args) throws Exception {
		Map<String, String> options = new HashMap<String, String>();
		// Usage: java test 2013 Donovan 16
		int year = Integer.parseInt(args[0]);
		String name = args[1];
		int num = Integer.parseInt(args[2]);
		options.put("inDir", "../Cases/" + year + "/sample/in/");
		options.put("outDir", "../Cases/" + year + "/sample/out/");
		options.put("compileDir", "../" + year + "/" + name + "/");
		
		Map<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("year", String.valueOf(year));
		parameters.put("name", ParseJson.escape(name));
		parameters.put("number", String.valueOf(num));

		Judge b = new Judge(num, options, parameters);
		b.test();

		Problem c = b.getProblem();
		
		List<Problem> d = new JsonArrayList<Problem>();
		d.add(c);
		
		System.out.println(d);
	}
}